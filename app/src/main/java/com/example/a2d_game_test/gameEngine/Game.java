package com.example.a2d_game_test.gameEngine;

import static com.example.a2d_game_test.utilities.Constants.INNER_JOYSTICK_RADIUS;
import static com.example.a2d_game_test.utilities.Constants.OUTER_JOYSTICK_RADIUS;
import static com.example.a2d_game_test.utilities.Constants.PLAYER_RADIUS;
import static com.example.a2d_game_test.utilities.Constants.START_JOYSTICK_POSITION_X;
import static com.example.a2d_game_test.utilities.Constants.START_JOYSTICK_POSITION_Y;
import static com.example.a2d_game_test.utilities.Constants.START_PLAYER_POSITION_X;
import static com.example.a2d_game_test.utilities.Constants.START_PLAYER_POSITION_Y;
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
import java.util.stream.Collectors;

public class Game extends SurfaceView implements SurfaceHolder.Callback {
    private final GameLoop gameLoop;
    private final Joystick joystick;
    private final Player player;
    private List<Enemy> enemies = new ArrayList<>();
    private List<Bullet> bullets = new ArrayList<>();

    public Game(Context context) {
        super(context);

        SurfaceHolder surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
        this.gameLoop = new GameLoop(this, surfaceHolder);

        // Initialize Game objects
        this.joystick = new Joystick(getContext(), START_JOYSTICK_POSITION_X, START_JOYSTICK_POSITION_Y, OUTER_JOYSTICK_RADIUS, INNER_JOYSTICK_RADIUS);
        this.player = new Player(getContext(), START_PLAYER_POSITION_X, START_PLAYER_POSITION_Y, PLAYER_RADIUS, this.joystick);

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
        switch (motionEvent.getAction()) {
            // User touched the screen
            case MotionEvent.ACTION_DOWN:
                // If only joystick pressed -> indicate that the joystick moving
                // Else -> shoot bullet
                if (this.joystick.isPressed(motionEvent.getX(), motionEvent.getY())) {
                    this.joystick.setIsPressed(true);
                } else {
                    this.bullets.add(new Bullet(getContext(), this.player));
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
                this.joystick.setIsPressed(false);
                this.joystick.resetActuator();

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
    }

    public void drawUpdatesPerSecond(Canvas canvas) {
        String averageUpdatesPerSecond = Double.toString(this.gameLoop.getAverageUpdatesPerSecond());

        int color = ContextCompat.getColor(getContext(), R.color.silver);

        Paint paint = new Paint();
        paint.setColor(color);
        paint.setTextSize(TEXT_SIZE);

        // Random numbers
        canvas.drawText("UpdatesPerSecond: " + averageUpdatesPerSecond, 156, 246, paint);
    }

    public void drawFramesPerSecond(Canvas canvas) {
        String averageFramesPerSecond = Double.toString(this.gameLoop.getAverageFramesPerSecond());

        int color = ContextCompat.getColor(getContext(), R.color.red);

        Paint paint = new Paint();
        paint.setColor(color);
        paint.setTextSize(TEXT_SIZE);

        // Random numbers
        canvas.drawText("FramesPerSecond: " + averageFramesPerSecond, 156, 426, paint);
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

        // Update all enemies
        this.enemies.forEach(Enemy::update);

        // Check if enemies catch the player
        this.enemies = this.enemies.stream().filter(enemy -> !CircleGameObject.isColliding(enemy, this.player)).collect(Collectors.toList());
    }
}
