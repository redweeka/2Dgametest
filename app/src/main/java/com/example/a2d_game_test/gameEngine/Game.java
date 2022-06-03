package com.example.a2d_game_test.gameEngine;

import static com.example.a2d_game_test.utilities.Constants.ENEMY_RADIUS;
import static com.example.a2d_game_test.utilities.Constants.INNER_JOYSTICK_RADIUS;
import static com.example.a2d_game_test.utilities.Constants.OUTER_JOYSTICK_RADIUS;
import static com.example.a2d_game_test.utilities.Constants.PLAYER_RADIUS;
import static com.example.a2d_game_test.utilities.Constants.START_ENEMY_POSITION_X;
import static com.example.a2d_game_test.utilities.Constants.START_ENEMY_POSITION_Y;
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
import com.example.a2d_game_test.events.MotionEventEnum;
import com.example.a2d_game_test.events.MotionEventEnumFactory;
import com.example.a2d_game_test.models.Enemy;
import com.example.a2d_game_test.models.Joystick;
import com.example.a2d_game_test.models.Player;

import java.util.Optional;

public class Game extends SurfaceView implements SurfaceHolder.Callback {
    private final GameLoop gameLoop;
    private final Joystick joystick;
    private final Player player;
    private final Enemy enemy;

    public Game(Context context) {
        super(context);

        SurfaceHolder surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
        this.gameLoop = new GameLoop(this, surfaceHolder);

        // Initialize Game objects
        this.joystick = new Joystick(getContext(), START_JOYSTICK_POSITION_X, START_JOYSTICK_POSITION_Y, OUTER_JOYSTICK_RADIUS, INNER_JOYSTICK_RADIUS);
        this.player = new Player(getContext(), START_PLAYER_POSITION_X, START_PLAYER_POSITION_Y, PLAYER_RADIUS, this.joystick);
        this.enemy = new Enemy(getContext(), START_ENEMY_POSITION_X, START_ENEMY_POSITION_Y, ENEMY_RADIUS, this.player);

        // Everybody doing it
        setFocusable(true);
    }

    /**
     * moving player by action event
     *
     * the function get the selected action and moving the player on monitor
     * see MotionEventEnum
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Optional<MotionEventEnum> optionalMotionEventEnum = MotionEventEnumFactory.crateEventEnum(event.getAction());

        return optionalMotionEventEnum.map(motionEventEnum -> motionEventEnum.onTouchEvent(this.joystick, event)).orElseGet(() -> super.onTouchEvent(event));
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
        this.enemy.draw(canvas);
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

    public void update() {
        // Update game state
        this.joystick.update();
        this.player.update();
        this.enemy.update();
    }
}
