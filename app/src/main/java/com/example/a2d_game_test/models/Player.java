package com.example.a2d_game_test.models;

import static com.example.a2d_game_test.utilities.Constants.PLAYER_MAX_SPEED;

import android.content.Context;
import android.graphics.Canvas;

import androidx.core.content.ContextCompat;

import com.example.a2d_game_test.R;

public class Player extends CircleGameObject {
    private final Joystick joystick;

    public Player(Context context, double playerPositionX, double playerPositionY, double playerRadius, Joystick joystick) {
        super(playerPositionX, playerPositionY, playerRadius, ContextCompat.getColor(context, R.color.player));

        this.joystick = joystick;
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
    }

    @Override
    public void update() {
        updatePlayerVelocity();
        updatePlayerPosition();
    }

    private void updatePlayerVelocity() {
        super.velocityX = this.joystick.actuatorX() * PLAYER_MAX_SPEED;
        super.velocityY = this.joystick.actuatorY() * PLAYER_MAX_SPEED;
    }

    private void updatePlayerPosition() {
        super.positionX += super.velocityX;
        super.positionY += super.velocityY;
    }

    public void setPosition(double positionX, double positionY) {
        super.positionX = positionX;
        super.positionY = positionY;
    }
}
