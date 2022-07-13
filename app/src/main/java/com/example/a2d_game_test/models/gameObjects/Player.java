package com.example.a2d_game_test.models.gameObjects;

import static com.example.a2d_game_test.utilities.Constants.EnemyConstants.ENEMY_DAMAGE_POINTS;
import static com.example.a2d_game_test.utilities.Constants.MovementSpeedConstants.PLAYER_MAX_SPEED;
import static com.example.a2d_game_test.utilities.Constants.PlayerConstants.PLAYER_MAX_HEALTH_POINTS;

import android.content.Context;
import android.graphics.Canvas;

import androidx.core.content.ContextCompat;

import com.example.a2d_game_test.R;
import com.example.a2d_game_test.models.gamePanels.HealthBar;
import com.example.a2d_game_test.models.gamePanels.Joystick;
import com.example.a2d_game_test.models.graphics.PlayerAnimator;
import com.example.a2d_game_test.utilities.Utils;

public class Player extends CircleGameObject {
    private final Joystick joystick;
    private float currHealthPoints;
    private HealthBar healthBar;
    private PlayerAnimator playerAnimator;
    private PlayerState playerState;

    public Player(Context context, double playerPositionX, double playerPositionY, double playerHitRadius, Joystick joystick, PlayerAnimator spriteAnimator) {
        super(playerPositionX, playerPositionY, playerHitRadius, ContextCompat.getColor(context, R.color.blue));

        this.joystick = joystick;
        this.healthBar = new HealthBar(context, this);
        restartCurrHealthPoints();
        this.playerAnimator = spriteAnimator;
        this.playerState = new PlayerState(this);
    }

    @Override
    public void update() {
        updatePlayerVelocity();
        updatePlayerPosition();
        updatePlayerDirection();
        this.playerState.update();
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

    @Override
    public void draw(Canvas canvas, GameDisplay gameDisplay) {
        this.playerAnimator.draw(canvas, gameDisplay, this);
        this.healthBar.draw(canvas, gameDisplay);
    }

    public void subtractHealthPoint() {
        this.currHealthPoints -= ENEMY_DAMAGE_POINTS;

        if (this.currHealthPoints <= 0) {
            this.currHealthPoints = 0;
        }
    }

    public float currHealthPoints() {
        return this.currHealthPoints;
    }

    public double positionX() {
        return this.positionX;
    }

    public double positionY() {
        return this.positionY;
    }

    public void restartCurrHealthPoints() {
        this.currHealthPoints = PLAYER_MAX_HEALTH_POINTS;
    }

    public boolean isMoving() {
        return this.velocityX != 0 || this.velocityY != 0;
    }

    public PlayerState playerState() {
        return this.playerState;
    }
}
