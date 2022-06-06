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
import com.example.a2d_game_test.events.MotionEventEnum;
import com.example.a2d_game_test.events.MotionEventEnumFactory;
import com.example.a2d_game_test.models.CircleGameObject;
import com.example.a2d_game_test.models.Enemy;
import com.example.a2d_game_test.models.Joystick;
import com.example.a2d_game_test.models.Player;

import java.util.Optional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Game extends SurfaceView implements SurfaceHolder.Callback {
    private final GameLoop gameLoop;
    private final Joystick joystick;
    private final Player player;
    private List<Enemy> enemies = new ArrayList<>();

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
     * moving player by action event
     * <p>
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
        this.enemies.forEach(enemy -> enemy.draw(canvas));
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

        // Update all enemies
        this.enemies.forEach(Enemy::update);

        // Check if enemies catch the player
        this.enemies = this.enemies.stream().filter(enemy -> !CircleGameObject.isColliding(enemy, this.player)).collect(Collectors.toList());
    }
}
