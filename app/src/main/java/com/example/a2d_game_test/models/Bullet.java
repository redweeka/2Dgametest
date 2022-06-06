package com.example.a2d_game_test.models;

import static com.example.a2d_game_test.utilities.Constants.BULLET_RADIUS;
import static com.example.a2d_game_test.utilities.Constants.BULLET_SPEED;
import static com.example.a2d_game_test.utilities.Constants.ENEMY_RADIUS;
import static com.example.a2d_game_test.utilities.Constants.ENEMY_SPAWN_MAX_POSITION_X;
import static com.example.a2d_game_test.utilities.Constants.ENEMY_SPAWN_MAX_POSITION_Y;
import static com.example.a2d_game_test.utilities.Constants.PLAYER_MAX_SPEED;

import android.content.Context;

import androidx.core.content.ContextCompat;

import com.example.a2d_game_test.R;

public class Bullet extends CircleGameObject{

    public Bullet(Context context, CircleGameObject shooter) {
        super(
                shooter.positionX,
                shooter.positionY,
                BULLET_RADIUS,
                ContextCompat.getColor(context, R.color.bullet)
        );

        super.velocityX = shooter.directionX() * BULLET_SPEED;
        super.velocityY = shooter.directionY() * BULLET_SPEED;
    }

    @Override
    public void update() {
        super.positionX += super.velocityX;
        super.positionY += super.velocityY;
    }
}
