package com.example.a2d_game_test.models;

import android.graphics.Canvas;
import android.graphics.Paint;

public abstract class CircleGameObject extends GameObject {
    protected final double radius;
    protected final Paint paint;

    public CircleGameObject(double positionX, double positionY, double radius, int paintColor) {
        super(positionX, positionY);

        this.radius = radius;

        this.paint = new Paint();
        this.paint.setColor(paintColor);
    }

    /**
     * Get distance between center of the objects and both of the objects radii
     * If the distance between the objects centers is fewer then both of the objects radii
     * Meaning circleGameObjects are colliding -> return true
     * Else -> return false
     */
    public static boolean isColliding(CircleGameObject object1, CircleGameObject object2) {
        double distance = distanceBetweenObjects(object1, object2);
        double distanceToCollision = object1.radius + object2.radius;

        return distance < distanceToCollision;
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawCircle((float) this.positionX, (float) this.positionY, (float) this.radius, this.paint);
    }

    @Override
    public abstract void update();
}
