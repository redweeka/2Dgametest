package com.example.a2d_game_test.models;

import static com.example.a2d_game_test.utilities.Constants.ENEMY_SPEED;

import android.content.Context;

import androidx.core.content.ContextCompat;

import com.example.a2d_game_test.R;

// Enemy will always move towards the player
public class Enemy extends CircleGameObject {

    private final Player player;

    public Enemy(Context context, double positionX, double positionY, double radius, Player player) {
        super(positionX, positionY, radius, ContextCompat.getColor(context, R.color.enemy));

        this.player = player;
    }

    @Override
    // Update the velocity to be to the player direction
    public void update() {
        // Calculate vector from enemy to player (in x,y)
        double distanceToPlayerX = this.player.positionX() - super.positionX;
        double distanceToPlayerY = this.player.positionY() - super.positionY;

        // Calculate absolute distance between enemy and player
        double absoluteDistanceToPlayer = super.distanceBetweenObjects(this, this.player);

        // Calculate direction from enemy to player
        if (absoluteDistanceToPlayer > 0) {
            double playerDirectionX = distanceToPlayerX / absoluteDistanceToPlayer;
            double playerDirectionY = distanceToPlayerY / absoluteDistanceToPlayer;

            // Set the velocity in the player direction
            super.velocityX = playerDirectionX * ENEMY_SPEED;
            super.velocityY = playerDirectionY * ENEMY_SPEED;
        } else {
            // Means there is a problem with the calculation or enemy reached player
            super.velocityX = 0;
            super.velocityY = 0;
        }

        // Update enemy position
        super.positionX += super.velocityX;
        super.positionY += super.velocityY;
    }
}