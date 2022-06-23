package com.example.a2d_game_test.models.gameObjects;

import android.graphics.Canvas;

import com.example.a2d_game_test.utilities.Utils;

public abstract class GameObject {
    protected double positionX;
    protected double positionY;
    protected double velocityX;
    protected double velocityY;
    protected double directionX;
    protected double directionY;

    public GameObject(double positionX, double positionY) {
        this.positionX = positionX;
        this.positionY = positionY;
    }

    public abstract void draw(Canvas canvas, GameDisplay gameDisplay);

    public abstract void update();

    protected static double distanceBetweenObjects(GameObject object1, GameObject object2) {
        return Utils.calculateDistanceBetween2Points(
                object1.positionX - object2.positionX,
                object1.positionY - object2.positionY
        );
    }

    protected double directionX() {
        return this.directionX;
    }

    protected double directionY() {
        return this.directionY;
    }
}
