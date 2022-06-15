package com.example.a2d_game_test.models;

import static com.example.a2d_game_test.utilities.Constants.Enemy.ENEMIES_UPDATES_PER_SPAWN;
import static com.example.a2d_game_test.utilities.Constants.Enemy.ENEMY_RADIUS;
import static com.example.a2d_game_test.utilities.Constants.Enemy.ENEMY_SPAWN_MAX_POSITION_X;
import static com.example.a2d_game_test.utilities.Constants.Enemy.ENEMY_SPAWN_MAX_POSITION_Y;
import static com.example.a2d_game_test.utilities.Constants.MovementSpeed.ENEMY_SPEED;

import android.content.Context;

import androidx.core.content.ContextCompat;

import com.example.a2d_game_test.R;

// Enemy will always move towards the player
public class Enemy extends CircleGameObject {

    private static double updatesUntilNextRespawn = ENEMIES_UPDATES_PER_SPAWN;
    private final Player player;

    public Enemy(Context context, Player player) {
        super(
                Math.random() * ENEMY_SPAWN_MAX_POSITION_X,
                Math.random() * ENEMY_SPAWN_MAX_POSITION_Y,
                ENEMY_RADIUS,
                ContextCompat.getColor(context, R.color.red)
        );

        this.player = player;
    }

    // Check if need to spawn new enemy by number of spawns per second
    public static boolean readyToSpawn() {
        boolean readyToSpawn = false;

        if (updatesUntilNextRespawn <= 0) {
            updatesUntilNextRespawn += ENEMIES_UPDATES_PER_SPAWN;
            readyToSpawn = true;
        } else {
            updatesUntilNextRespawn--;
            readyToSpawn = false;
        }

        return readyToSpawn;
    }

    @Override
    // Update the velocity to be to the player direction
    public void update() {
        // Calculate vector from enemy to player (in x,y)
        double distanceToPlayerX = this.player.positionX - super.positionX;
        double distanceToPlayerY = this.player.positionY - super.positionY;

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