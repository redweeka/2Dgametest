package com.example.a2d_game_test.models.gameObjects;

import android.graphics.Rect;

// Needed to be in every game object - for moving the world around a chosen object
public class GameDisplay {
    public final Rect SCREEN_RECT_DISPLAY;
    private final GameObject centerObject;
    private final double displayCenterX;
    private final double displayCenterY;
    private final int screenPixelsWidth;
    private final int screenPixelsHeight;
    private double gameDisplayCoordinateOffsetX;
    private double gameDisplayCoordinateOffsetY;
    private double gameCenterX;
    private double gameCenterY;

    public GameDisplay(GameObject gameObject, int screenPixelsWidth, int screenPixelsHeight) {
        this.SCREEN_RECT_DISPLAY = new Rect(0, 0, screenPixelsWidth, screenPixelsHeight);
        this.centerObject = gameObject;
        this.displayCenterX = screenPixelsWidth/2.0;
        this.displayCenterY = screenPixelsHeight/2.0;

        this.screenPixelsWidth = screenPixelsWidth;
        this.screenPixelsHeight = screenPixelsHeight;

        update();
    }

    public void update() {
        this.gameCenterX = this.centerObject.positionX;
        this.gameCenterY = this.centerObject.positionY;

        this.gameDisplayCoordinateOffsetX = this.displayCenterX - gameCenterX;
        this.gameDisplayCoordinateOffsetY = this.displayCenterY - gameCenterY;
    }

    public double gameDisplayCoordinateX(double positionX) {
        return positionX + this.gameDisplayCoordinateOffsetX;
    }

    public double gameDisplayCoordinateY(double positionY) {
        return positionY + this.gameDisplayCoordinateOffsetY;
    }

    public Rect getGameRect() {
        return new Rect(
                (int)(this.gameCenterX - this.screenPixelsWidth/2),
                (int)(this.gameCenterY - this.screenPixelsHeight/2),
                (int)(this.gameCenterX + this.screenPixelsWidth/2),
                (int)(this.gameCenterY + this.screenPixelsHeight/2)
        );
    }
}
