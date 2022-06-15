package com.example.a2d_game_test.models.gameObjects;

import static com.example.a2d_game_test.utilities.Constants.BulletConstants.BULLET_RADIUS;
import static com.example.a2d_game_test.utilities.Constants.MovementSpeedConstants.BULLET_SPEED;

import android.content.Context;

import androidx.core.content.ContextCompat;

import com.example.a2d_game_test.R;

public class Bullet extends CircleGameObject {

    public Bullet(Context context, CircleGameObject shooter) {
        super(
                shooter.positionX,
                shooter.positionY,
                BULLET_RADIUS,
                ContextCompat.getColor(context, R.color.bullet)
        );

        initBulletVelocity(shooter);
    }

    private void initBulletVelocity(CircleGameObject shooter) {
        // If player has not move choose a random direction
        double bulletVelocityX = shooter.directionX() * BULLET_SPEED;
        double bulletVelocityY = shooter.directionY() * BULLET_SPEED;

        if (bulletVelocityX == 0  && bulletVelocityY == 0) {
            bulletVelocityX = BULLET_SPEED;
            bulletVelocityY = BULLET_SPEED;
        }

        super.velocityX = bulletVelocityX;
        super.velocityY = bulletVelocityY;
    }

    @Override
    public void update() {
        super.positionX += super.velocityX;
        super.positionY += super.velocityY;
    }
}
