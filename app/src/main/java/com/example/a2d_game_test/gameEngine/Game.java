package com.example.a2d_game_test.gameEngine;

import static com.example.a2d_game_test.utilities.Constants.GameDetails.FRAME_PER_SECOND;
import static com.example.a2d_game_test.utilities.Constants.GameDetails.MAX_TEXT_LENGTH;
import static com.example.a2d_game_test.utilities.Constants.GameDetails.TEXT_BORDER;
import static com.example.a2d_game_test.utilities.Constants.GameDetails.TEXT_HEIGHT;
import static com.example.a2d_game_test.utilities.Constants.GameDetails.UPDATES_PER_SECOND;
import static com.example.a2d_game_test.utilities.Constants.Joystick.INNER_JOYSTICK_RADIUS;
import static com.example.a2d_game_test.utilities.Constants.Joystick.OUTER_JOYSTICK_RADIUS;
import static com.example.a2d_game_test.utilities.Constants.Joystick.START_JOYSTICK_POSITION_X;
import static com.example.a2d_game_test.utilities.Constants.Joystick.START_JOYSTICK_POSITION_Y;
import static com.example.a2d_game_test.utilities.Constants.Player.PLAYER_RADIUS;
import static com.example.a2d_game_test.utilities.Constants.Player.START_PLAYER_POSITION_X;
import static com.example.a2d_game_test.utilities.Constants.Player.START_PLAYER_POSITION_Y;
import static com.example.a2d_game_test.utilities.Constants.TEXT_SIZE;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.example.a2d_game_test.R;
import com.example.a2d_game_test.models.Bullet;
import com.example.a2d_game_test.models.CircleGameObject;
import com.example.a2d_game_test.models.Enemy;
import com.example.a2d_game_test.models.Joystick;
import com.example.a2d_game_test.models.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

public class Game extends SurfaceView implements SurfaceHolder.Callback {
    private final GameLoop gameLoop;
    private final Joystick joystick;
    private final Player player;
    private List<Enemy> enemies = new ArrayList<>();
    private List<Bullet> bullets = new ArrayList<>();
    private int joystickPointerId;
    private int reloadedBullets = 0;

    public Game(Context context) {
        super(context);

        SurfaceHolder surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
        this.gameLoop = new GameLoop(this, surfaceHolder);

        // Initialize the main game objects
        this.joystick = new Joystick(
                getContext(),
                START_JOYSTICK_POSITION_X,
                START_JOYSTICK_POSITION_Y,
                OUTER_JOYSTICK_RADIUS,
                INNER_JOYSTICK_RADIUS
        );
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
        this.gameLoop.startLoop();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        drawUpdatesPerSecond(canvas);
        drawFramesPerSecond(canvas);

        this.joystick.draw(canvas);
        this.player.draw(canvas);
        this.enemies.forEach(enemy -> enemy.draw(canvas));
        this.bullets.forEach(bullet -> bullet.draw(canvas));
    }

    public void drawUpdatesPerSecond(Canvas canvas) {
        double averageUpdatesPerSecond = this.gameLoop.getAverageUpdatesPerSecond();
        String textToDraw = this.getTextFormattedTODraw(UPDATES_PER_SECOND, averageUpdatesPerSecond);

        int color = ContextCompat.getColor(getContext(), R.color.teal_200);
        Paint paint = new Paint();

        paint.setColor(color);
        paint.setTextSize(TEXT_SIZE);

        canvas.drawText(textToDraw, 0, TEXT_HEIGHT, paint);
    }

    public void drawFramesPerSecond(Canvas canvas) {
        double averageFramesPerSecond = this.gameLoop.getAverageFramesPerSecond();
        String textToDraw = this.getTextFormattedTODraw(FRAME_PER_SECOND, averageFramesPerSecond);

        int color = ContextCompat.getColor(getContext(), R.color.teal_200);
        Paint paint = new Paint();

        paint.setColor(color);
        paint.setTextSize(TEXT_SIZE);

        canvas.drawText(textToDraw, 0, (TEXT_HEIGHT * 2), paint);
    }

    /**
     * @return: "<TEXT> : <DOUBLE_NUMBER>"
     */
    private String getTextFormattedTODraw(String textKey, double valve) {
        return String.format("%" + -MAX_TEXT_LENGTH + "s" + TEXT_BORDER + " %f", textKey, valve);
    }

    // Responsible for game update and everything that happen when it does
    public void update() {

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

    /**
     * Enemies that catch the player, or get hit by a bullet, will vanish, bullet that hit will vanish too.
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

                    // If the enemy hit the player or got shot and remove the enemy
                    return !CircleGameObject.isColliding(enemy, this.player) && !isEnemyGotShot.get();
                }
        ).collect(Collectors.toList());
    }
}
