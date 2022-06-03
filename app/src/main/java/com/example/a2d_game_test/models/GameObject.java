package com.example.a2d_game_test.models;

import android.graphics.Canvas;

import com.example.a2d_game_test.utilities.Utils;

public abstract class GameObject {
    protected double positionX;
    protected double positionY;
    protected double velocityX;
    protected double velocityY;

    public GameObject(double positionX, double positionY) {
        this.positionX = positionX;
        this.positionY = positionY;
    }

    public abstract void draw(Canvas canvas);

    public abstract void update();

    protected static double distanceBetweenObjects(GameObject object1, GameObject object2) {
        return Utils.calculateDistanceBetween2Points(
                object1.positionX - object2.positionX,
                object1.positionY - object2.positionY
        );
    }
}
