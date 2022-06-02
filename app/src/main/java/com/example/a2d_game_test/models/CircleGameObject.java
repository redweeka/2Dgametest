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

    @Override
    public void draw(Canvas canvas) {
        canvas.drawCircle((float) this.positionX, (float) this.positionY, (float) this.radius, this.paint);
    }

    @Override
    public abstract void update();
}
