package com.example.a2d_game_test.gameEngine;

import static com.example.a2d_game_test.utilities.Constants.GameLoopConstants.*;

import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;

public class GameLoop extends Thread {

    private static final String TAG = "GameLoop";

    private final Game game;
    private final SurfaceHolder surfaceHolder;
    private boolean isRunning = false;
    private double averageUpdatesPerSecond = 0;
    private double averageFramesPerSecond = 0;

    public GameLoop(Game game, SurfaceHolder surfaceHolder) {
        this.game = game;
        this.surfaceHolder = surfaceHolder;
    }

    public double getAverageUpdatesPerSecond() {
        return this.averageUpdatesPerSecond;
    }

    public double getAverageFramesPerSecond() {
        return this.averageFramesPerSecond;
    }

    public void startLoop() {
        this.isRunning = true;
        start();
    }

    @Override
    public void run() {
        super.run();

        // Time vars
        int updatesCount = 0;
        int framesPerSecondCount = 0;

        long startTime = System.currentTimeMillis();
        long elapsedTime;
        long sleepTime;

        Canvas canvas = null;

        // The game loop
        while (this.isRunning) {

            // Update and render things from game class
            try {
                canvas = this.surfaceHolder.lockCanvas();

                synchronized (this.surfaceHolder) {
                    this.game.update();
                    updatesCount++;
                    this.game.draw(canvas);
                }
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (canvas != null) {
                        this.surfaceHolder.unlockCanvasAndPost(canvas);
                        framesPerSecondCount++;
                    }
                } catch (IllegalStateException e) {
                    e.printStackTrace();
                }
            }


            // Pause the game loop if needed, to not exceed the target updates-per-second
            elapsedTime = System.currentTimeMillis() - startTime;
            sleepTime = (long) (updatesCount * UPDATES_PER_SECOND_PERIOD - elapsedTime);

            if (sleepTime > 0) {
                try {
                    sleep(sleepTime);
                } catch (InterruptedException e) {
                    Log.e(TAG, "run: problem with sleeping", e);
                }
            }

            // Skip frames to keep up with target updates-per-second
            while (sleepTime < 0 && updatesCount < MAX_UPDATES_PER_SECOND - 1) {
                this.game.update();
                updatesCount++;
                elapsedTime = System.currentTimeMillis() - startTime;
                sleepTime = (long) (updatesCount * UPDATES_PER_SECOND_PERIOD - elapsedTime);
            }

            // Calculate updates and frames-per-second
            elapsedTime = System.currentTimeMillis() - startTime;

            if (elapsedTime >= SECOND_IN_MILLISECOND) {
                this.averageUpdatesPerSecond = updatesCount / (1E-3 * elapsedTime);
                this.averageFramesPerSecond = framesPerSecondCount / (1E-3 * elapsedTime);
                updatesCount = 0;
                framesPerSecondCount = 0;
                startTime = System.currentTimeMillis();
            }
        }
    }
}
