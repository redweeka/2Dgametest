package com.example.a2d_game_test;

import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;

public class GameLoop extends Thread {
    private static final String TAG = "GameLoop";
    private Game game;
    private SurfaceHolder surfaceHolder;
    private boolean isRunning = false;

    public GameLoop(Game game, SurfaceHolder surfaceHolder) {
        this.game = game;
        this.surfaceHolder = surfaceHolder;
    }

    public double getAverageUpdatesPerSecond() {
        return 0;
    }

    public double getAverageFramesPerSecond() {
        return 0;
    }

    public void startLoop() {
        this.isRunning = true;
        start();
    }

    @Override
    public void run() {
        super.run();

        Canvas canvas;

        // The game loop
        while (this.isRunning){

            // Update and render things from game class
            try {
                canvas = surfaceHolder.lockCanvas();
                game.update();
                game.draw(canvas);
                surfaceHolder.unlockCanvasAndPost(canvas);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }


            // Pause the game loop to not exceed the update for second that we want

            // Skip frames to keep up with target updates per second

            // Calculate updates and frames per second

        }
    }
}
