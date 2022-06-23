package com.example.a2d_game_test.models.gameObjects;

// Needed to be in every game object - for moving the world around a chosen object
public class GameDisplay {

    private double gameDisplayCoordinateOffsetX;
    private double gameDisplayCoordinateOffsetY;
    private final double displayCenterX;
    private final double displayCenterY;
    private final GameObject centerObject;

    public GameDisplay(GameObject gameObject, int screenPixelsWidth, int screenPixelsHeight) {
        this.centerObject = gameObject;

        this.displayCenterX = screenPixelsWidth/2.0;
        this.displayCenterY = screenPixelsHeight/2.0;
    }

    public void update() {
        double gameCenterX = this.centerObject.positionX;
        double gameCenterY = this.centerObject.positionY;

        this.gameDisplayCoordinateOffsetX = this.displayCenterX - gameCenterX;
        this.gameDisplayCoordinateOffsetY = this.displayCenterY - gameCenterY;
    }

    public double gameDisplayCoordinateX(double positionX) {
        return positionX + this.gameDisplayCoordinateOffsetX;
    }

    public double gameDisplayCoordinateY(double positionY) {
        return positionY + this.gameDisplayCoordinateOffsetY;
    }
}
