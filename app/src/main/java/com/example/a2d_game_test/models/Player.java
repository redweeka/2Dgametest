package com.example.a2d_game_test.models;

import static com.example.a2d_game_test.utilities.Constants.Enemy.ENEMY_DAMAGE_POINTS;
import static com.example.a2d_game_test.utilities.Constants.MovementSpeed.PLAYER_MAX_SPEED;
import static com.example.a2d_game_test.utilities.Constants.Player.PLAYER_MAX_HEALTH_POINTS;

import android.content.Context;
import android.graphics.Canvas;

import androidx.core.content.ContextCompat;

import com.example.a2d_game_test.R;
import com.example.a2d_game_test.utilities.Utils;

public class Player extends CircleGameObject {
    private final Joystick joystick;
    public float currHealthPoints;
    private HealthBar healthBar;

    public Player(Context context, double playerPositionX, double playerPositionY, double playerRadius, Joystick joystick) {
        super(playerPositionX, playerPositionY, playerRadius, ContextCompat.getColor(context, R.color.blue));

        this.joystick = joystick;
        this.healthBar = new HealthBar(context, this);
        this.currHealthPoints = PLAYER_MAX_HEALTH_POINTS;
    }

    @Override
    public void update() {
        updatePlayerVelocity();
        updatePlayerPosition();
        updatePlayerDirection();
    }

    private void updatePlayerVelocity() {
        super.velocityX = this.joystick.actuatorX() * PLAYER_MAX_SPEED;
        super.velocityY = this.joystick.actuatorY() * PLAYER_MAX_SPEED;
    }

    private void updatePlayerPosition() {
        super.positionX += super.velocityX;
        super.positionY += super.velocityY;
    }

    private void updatePlayerDirection() {
        if (velocityX != 0 || velocityY != 0) {
            // Normalize velocity to get direction (by velocity unit vector)
            double distance = Utils.calculateDistanceBetween2Points(super.velocityX, super.velocityY);
            super.directionX = super.velocityX/distance;
            super.directionY = super.velocityY/distance;
        }
    }

    public void setPosition(double positionX, double positionY) {
        super.positionX = positionX;
        super.positionY = positionY;
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        this.healthBar.draw(canvas);
    }

    public void subtractHealthPoint() {
        this.currHealthPoints -= ENEMY_DAMAGE_POINTS;

        if (this.currHealthPoints <= 0) {
            this.currHealthPoints = 0;
        }
    }
}
