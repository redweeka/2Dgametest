package com.example.a2d_game_test.models;

import static com.example.a2d_game_test.utilities.Constants.PLAYER_MAX_SPEED;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import androidx.core.content.ContextCompat;

import com.example.a2d_game_test.R;

public class Player {
    private double playerPositionX;
    private double playerPositionY;
    private final double playerRadius;
    private final Paint playerPaint;
    private double velocityX;
    private double velocityY;

    public Player(Context context, double playerPositionX, double playerPositionY, double playerRadius){
        this.playerPositionX = playerPositionX;
        this.playerPositionY = playerPositionY;
        this.playerRadius = playerRadius;

        this.playerPaint = new Paint();
        int playerColor = ContextCompat.getColor(context, R.color.player);
        this.playerPaint.setColor(playerColor);
    }

    public void draw(Canvas canvas) {
        canvas.drawCircle((float) playerPositionX, (float) playerPositionY, (float) playerRadius, playerPaint);
    }

    public void update(Joystick joystick) {
        this.velocityX = joystick.actuatorX() * PLAYER_MAX_SPEED;
        this.velocityY = joystick.actuatorY() * PLAYER_MAX_SPEED;
        this.playerPositionX += this.velocityX;
        this.playerPositionY += this.velocityY;
    }

    public void setPosition(double positionX, double positionY) {
        this.playerPositionX = positionX;
        this.playerPositionY = positionY;
    }
}
