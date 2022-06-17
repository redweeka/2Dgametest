package com.example.a2d_game_test.gameEngine;

import static com.example.a2d_game_test.utilities.Constants.JoystickConstants.*;
import static com.example.a2d_game_test.utilities.Constants.PlayerConstants.*;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;
import com.example.a2d_game_test.models.gameObjects.*;
import com.example.a2d_game_test.models.gamePanels.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

public class Game extends SurfaceView implements SurfaceHolder.Callback {
    private GameLoop gameLoop;
    private final Player player;
    private final Joystick joystick;
    private int joystickPointerId;
    private List<Enemy> enemies = new ArrayList<>();
    private List<Bullet> bullets = new ArrayList<>();
    private int reloadedBullets = 0;
    private final GameOverPanel gameOverPanel;
    private final GamePerformances gamePerformances;

    public Game(Context context) {
        super(context);

        SurfaceHolder surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
        createNewGameLoop(surfaceHolder);

        // Initialize all non-interactive game objects (such as panels)
        this.gameOverPanel = new GameOverPanel(context);
        this.joystick = new Joystick(
                getContext(),
                START_JOYSTICK_POSITION_X,
                START_JOYSTICK_POSITION_Y,
                OUTER_JOYSTICK_RADIUS,
                INNER_JOYSTICK_RADIUS
        );
        this.gamePerformances = new GamePerformances(context, this.gameLoop);

        // Initialize the main game objects
        this.player = new Player(
                getContext(),
                START_PLAYER_POSITION_X,
                START_PLAYER_POSITION_Y,
                PLAYER_RADIUS,
                this.joystick
        );

        // Everybody doing it
        setFocusable(true);
    }

    private void createNewGameLoop(@NonNull SurfaceHolder holder) {
        this.gameLoop = new GameLoop(this, holder);
    }

    /**
     * Moving player by action event:
     * The function get the motion event and rendering the objects on the screen accordingly
     * See MotionEventEnum
     */
    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        switch (motionEvent.getActionMasked()) {
            // User touched the screen
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                // If joystick already pressed -> reload bulled
                // Else if joystick just pressed -> indicate that the joystick moving
                // Else -> reload bullet
                if (this.joystick.isPressed()) {
                    this.reloadedBullets++;
                } else if (this.joystick.isLocation(motionEvent.getX(), motionEvent.getY())) {
                    this.joystickPointerId = motionEvent.getPointerId(motionEvent.getActionIndex());
                    this.joystick.setIsPressed(true);
                } else {
                    // Add bullet to be fire when player ready
                    this.reloadedBullets++;
                }

                return true;

            // User move the finger on the screen
            case MotionEvent.ACTION_MOVE:
                if (this.joystick.isPressed()) {
                    this.joystick.setActuator(motionEvent.getX(), motionEvent.getY());
                }

                return true;

            // User untouched the screen
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                // If the user untouched the joystick
                if (this.joystickPointerId == motionEvent.getPointerId(motionEvent.getActionIndex())) {
                    this.joystick.setIsPressed(false);
                    this.joystick.resetActuator();
                }

                return true;
            default:
                return super.onTouchEvent(motionEvent);
        }
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        // If game loop (which is a thread) terminated due to game pause -> create new game loop
        if (this.gameLoop.getState().equals(Thread.State.TERMINATED)) {
            createNewGameLoop(holder);
        }

        this.gameLoop.startLoop();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {}

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {}

    public void pause() {
        this.gameLoop.stopLoop();
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        this.player.draw(canvas);
        this.enemies.forEach(enemy -> enemy.draw(canvas));
        this.bullets.forEach(bullet -> bullet.draw(canvas));

        this.joystick.draw(canvas);
        this.gamePerformances.draw(canvas);

        // If player dead -> game over
        if (player.currHealthPoints() <= 0) {
            this.gameOverPanel.draw(canvas);
        }
    }

    // Responsible for game update and everything that happen when it does
    public void update() {
        // Only keep updating game if player is alive
        if (player.currHealthPoints() > 0) {
            this.joystick.update();
            this.player.update();

            // Spawn enemy when the time is right
            if (Enemy.readyToSpawn()) {
                Enemy newEnemy = new Enemy(getContext(), this.player);
                this.enemies.add(newEnemy);
            }

            // Add bullet to shot
            if (this.reloadedBullets > 0) {
                this.bullets.add(new Bullet(getContext(), this.player));
                this.reloadedBullets--;
            }

            // Update all enemies and bullets
            this.enemies.forEach(Enemy::update);
            this.bullets.forEach(Bullet::update);

            vanishCollidingEnemiesAndBullets();
        }
    }

    /**
     * Enemies that catch the player -> will vanish and subtract player life
     * Enemies that get hit by a bullet, will vanish with the bullet.
     */
    private void vanishCollidingEnemiesAndBullets() {
        this.enemies = this.enemies.stream().filter(
                enemy -> {
                    AtomicBoolean isEnemyGotShot = new AtomicBoolean(false);

                    // Check if any bullet hit the enemy
                    this.bullets = this.bullets.stream().filter(bullet -> {
                        boolean isBulletHitEnemy = CircleGameObject.isColliding(bullet, enemy);

                        if (isBulletHitEnemy) {
                            isEnemyGotShot.set(true);
                        }

                        return !isBulletHitEnemy;
                    }).collect(Collectors.toList());

                    // Check if enemy catch the player and subtract the player life
                    boolean isEnemyCatchPlayer = CircleGameObject.isColliding(enemy, this.player);

                    if (isEnemyCatchPlayer) {
                        this.player.subtractHealthPoint();
                    }

                    // If the enemy catch the player or got shot and remove the enemy
                    return !isEnemyCatchPlayer && !isEnemyGotShot.get();
                }
        ).collect(Collectors.toList());
    }
}