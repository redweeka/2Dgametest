package com.example.a2d_game_test;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

public class Game extends SurfaceView implements SurfaceHolder.Callback {
    private GameLoop gameLoop;
    private Context context;

    public Game(Context context) {
        super(context);

        SurfaceHolder surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);

        this.context = context;
        this.gameLoop = new GameLoop(this, surfaceHolder);

        // Everybody do it
        setFocusable(true);
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
    }

    public void drawUpdatesPerSecond(Canvas canvas){
        String averageUpdatesPerSecond = Double.toString(this.gameLoop.getAverageUpdatesPerSecond());

        int color = ContextCompat.getColor(context, R.color.silver);

        Paint paint = new Paint();
        paint.setColor(color);
        paint.setTextSize(66);

        canvas.drawText("UpdatesPerSecond: " + averageUpdatesPerSecond, 166, 366, paint);
    }

    public void drawFramesPerSecond(Canvas canvas){
        String averageFramesPerSecond = Double.toString(this.gameLoop.getAverageFramesPerSecond());

        int color = ContextCompat.getColor(context, R.color.red);

        Paint paint = new Paint();
        paint.setColor(color);
        paint.setTextSize(66);

        canvas.drawText("FramesPerSecond: " + averageFramesPerSecond, 266, 666, paint);
    }

    public void update() {
        // Update game state
    }
}
