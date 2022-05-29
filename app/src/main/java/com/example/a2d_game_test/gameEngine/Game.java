package com.example.a2d_game_test.gameEngine;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.example.a2d_game_test.R;
import com.example.a2d_game_test.models.Player;

public class Game extends SurfaceView implements SurfaceHolder.Callback {
    private final GameLoop gameLoop;
    private final Player player;

    public Game(Context context) {
        super(context);

        SurfaceHolder surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
        this.gameLoop = new GameLoop(this, surfaceHolder);

        // Initialize player
        this.player = new Player(getContext(), 2*500, 500, 30);

        // Everybody do it
        setFocusable(true);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                player.setPosition((double) event.getX(), (double) event.getY());

                return true;
            default:
                break;
        }

        return super.onTouchEvent(event);
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

        this.player.draw(canvas);
    }

    public void drawUpdatesPerSecond(Canvas canvas){
        String averageUpdatesPerSecond = Double.toString(this.gameLoop.getAverageUpdatesPerSecond());

        int color = ContextCompat.getColor(getContext(), R.color.silver);

        Paint paint = new Paint();
        paint.setColor(color);
        paint.setTextSize(66);

        canvas.drawText("UpdatesPerSecond: " + averageUpdatesPerSecond, 166, 366, paint);
    }

    public void drawFramesPerSecond(Canvas canvas){
        String averageFramesPerSecond = Double.toString(this.gameLoop.getAverageFramesPerSecond());

        int color = ContextCompat.getColor(getContext(), R.color.red);

        Paint paint = new Paint();
        paint.setColor(color);
        paint.setTextSize(66);

        canvas.drawText("FramesPerSecond: " + averageFramesPerSecond, 266, 666, paint);
    }

    public void update() {
        // Update game state
        player.update();
    }
}
