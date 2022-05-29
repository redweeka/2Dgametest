package com.example.a2d_game_test.models;

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

    public void update() {

    }

    public void setPosition(double positionX, double positionY) {
        this.playerPositionX = positionX;
        this.playerPositionY = positionY;
    }
}
