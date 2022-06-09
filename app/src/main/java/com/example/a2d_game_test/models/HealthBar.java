package com.example.a2d_game_test.models;

import static com.example.a2d_game_test.utilities.Constants.Player.PLAYER_MAX_HEALTH;

import android.graphics.Canvas;

// A bar that represent the life of the player
public class HealthBar extends GameObject {

    private final Player player;

    public HealthBar(Player player) {
        super(player.positionX, player.positionY);

        this.player = player;
    }

    @Override
    public void draw(Canvas canvas) {
        float playerPositionX = (float) this.player.positionX;
        float playerPositionY = (float) this.player.positionY;
        float distanceToPlayer = ;
        float healthPointPercentage = this.player.currHealthPoints /PLAYER_MAX_HEALTH;

        // Draw health bar background
        canvas.drawRect(
                healthBarFrameLeftPosition,
                healthBarFrameTopPosition,
                healthBarFrameRightPosition,
                healthBarFrameBottomPosition,
                healthBarFramePaint
        );

        // Draw the health on the health bar background
        canvas.drawRect(
                healthBarLeftPosition,
                healthBarTopPosition,
                healthBarRightPosition,
                healthBarBottomPosition,
                healthBarPaint
        );
    }

    @Override
    public void update() {

    }
}
