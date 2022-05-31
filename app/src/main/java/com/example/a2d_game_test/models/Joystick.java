package com.example.a2d_game_test.models;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import androidx.core.content.ContextCompat;

import com.example.a2d_game_test.R;
import com.example.a2d_game_test.utilities.Utils;

public class Joystick {
    // Joystick design members
    private final int outerJoystickCirclePositionX;
    private final int outerJoystickCirclePositionY;
    private final int outerJoystickRadius;
    private final Paint outerJoystickPaint;

    private int innerJoystickCirclePositionX;
    private int innerJoystickCirclePositionY;
    private final int innerJoystickRadius;
    private final Paint innerJoystickPaint;

    // Joystick action members
    private double joystickCenterToTouchDistance;
    private boolean isPressed;
    private double actuatorX;
    private double actuatorY;

    public Joystick(Context context, int joystickPositionX, int joystickPositionY, int outerJoystickRadius, int innerJoystickRadius) {
        // Outer joystick
        this.outerJoystickCirclePositionX = joystickPositionX;
        this.outerJoystickCirclePositionY = joystickPositionY;
        this.outerJoystickRadius = outerJoystickRadius;

        this.outerJoystickPaint = new Paint();
        int outerJoystickColor = ContextCompat.getColor(context, R.color.outer_joystick);
        this.outerJoystickPaint.setColor(outerJoystickColor);
        this.outerJoystickPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        // Inner joystick
        this.innerJoystickCirclePositionX = joystickPositionX;
        this.innerJoystickCirclePositionY = joystickPositionY;
        this.innerJoystickRadius = innerJoystickRadius;

        this.innerJoystickPaint = new Paint();
        int innerJoystickColor = ContextCompat.getColor(context, R.color.inner_joystick);
        this.innerJoystickPaint.setColor(innerJoystickColor);
        this.innerJoystickPaint.setStyle(Paint.Style.FILL_AND_STROKE);
    }

    public void draw(Canvas canvas) {
        // Draw outer circle
        canvas.drawCircle(
                this.outerJoystickCirclePositionX,
                this.outerJoystickCirclePositionY,
                this.outerJoystickRadius,
                this.outerJoystickPaint
        );

        // Draw inner circle
        canvas.drawCircle(
                this.innerJoystickCirclePositionX,
                this.innerJoystickCirclePositionY,
                this.innerJoystickRadius,
                this.innerJoystickPaint
        );
    }

    public void update() {
        updateInnerCirclePosition();
    }

    private void updateInnerCirclePosition() {
        this.innerJoystickCirclePositionX = (int) (this.outerJoystickCirclePositionX + this.actuatorX*outerJoystickRadius);
        this.innerJoystickCirclePositionY = (int) (this.outerJoystickCirclePositionY + this.actuatorY*outerJoystickRadius);
    }

    public boolean isPressed(double touchPositionX, double touchPositionY) {
        this.joystickCenterToTouchDistance = Utils.calculateDistanceBetween2Points(
                this.outerJoystickCirclePositionX - touchPositionX,
                this.outerJoystickCirclePositionY - touchPositionY
        );

        return this.joystickCenterToTouchDistance < this.outerJoystickRadius;
    }

    public void setIsPressed(boolean isPressed) {
        this.isPressed = isPressed;
    }

    public boolean isPressed() {
        return this.isPressed;
    }

    // How much the inner joystick get pulled
    public void setActuator(double touchPositionX, double touchPositionY) {
        double deltaX = touchPositionX - this.outerJoystickCirclePositionX;
        double deltaY = touchPositionY - this.outerJoystickCirclePositionY;

        double deltaDistance = Utils.calculateDistanceBetween2Points(deltaX, deltaY);

        if (deltaDistance < this.outerJoystickRadius) {
            this.actuatorX = deltaX/this.outerJoystickRadius;
            this.actuatorY = deltaY/this.outerJoystickRadius;
        } else {
            this.actuatorX = deltaX/deltaDistance;
            this.actuatorY = deltaY/deltaDistance;
        }
    }

    public void resetActuator() {
        this.actuatorX = 0.0;
        this.actuatorY = 0.0;
    }

    public double actuatorX() {
        return this.actuatorX;
    }

    public double actuatorY() {
        return this.actuatorY;
    }
}
