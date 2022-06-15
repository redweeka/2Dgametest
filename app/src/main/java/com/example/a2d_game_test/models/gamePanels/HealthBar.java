package com.example.a2d_game_test.models.gamePanels;

import static com.example.a2d_game_test.utilities.Constants.HealthBarConstants.*;
import static com.example.a2d_game_test.utilities.Constants.PlayerConstants.PLAYER_MAX_HEALTH_POINTS;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import androidx.core.content.ContextCompat;

import com.example.a2d_game_test.R;
import com.example.a2d_game_test.models.gameObjects.GameObject;
import com.example.a2d_game_test.models.gameObjects.Player;

// A bar that represent the life of the player
public class HealthBar extends GameObject {

    private final Player player;
    private final Paint healthBarFramePaint = new Paint();
    private final Paint healthBarPaint = new Paint();

    public HealthBar(Context context, Player player) {
        super(player.positionX(), player.positionY());

        this.player = player;
        this.healthBarFramePaint.setColor(ContextCompat.getColor(context, R.color.silver));
        this.healthBarPaint.setColor(ContextCompat.getColor(context, R.color.green));
    }

    @Override
    public void draw(Canvas canvas) {
        float playerPositionX = (float) this.player.positionX();
        float playerPositionY = (float) this.player.positionY();
        float distanceToPlayer = HEALTH_BAR_DISTANCE_FROM_PLAYER;
        // Calculate life in percentage to show graphic health bar
        float healthPointPercentage = this.player.currHealthPoints() / PLAYER_MAX_HEALTH_POINTS;

        // Calculate exact health bar frame properties
        float healthBarFrameLeftPosition = playerPositionX - HEALTH_BAR_FRAME_WIDTH /2;
        float healthBarFrameRightPosition = playerPositionX + HEALTH_BAR_FRAME_WIDTH /2;
        float healthBarFrameBottomPosition = playerPositionY - distanceToPlayer;
        float healthBarFrameTopPosition = healthBarFrameBottomPosition - HEALTH_BAR_FRAME_HEIGHT;

        // Draw health bar frame
        canvas.drawRect(
                healthBarFrameLeftPosition,
                healthBarFrameTopPosition,
                healthBarFrameRightPosition,
                healthBarFrameBottomPosition,
                this.healthBarFramePaint
        );

        // Calculate exact health bar properties


        // Draw the health on the health bar background
        float healthBarLeftPosition = healthBarFrameLeftPosition + HEALTH_BAR_PADDING;
        // Dynamic to actually show the amount of life
        float healthBarRightPosition = healthBarLeftPosition + HEALTH_BAR_WIDTH*healthPointPercentage;
        float healthBarBottomPosition = healthBarFrameBottomPosition - HEALTH_BAR_PADDING;
        float healthBarTopPosition = healthBarFrameTopPosition + HEALTH_BAR_PADDING;

        canvas.drawRect(
                healthBarLeftPosition,
                healthBarTopPosition,
                healthBarRightPosition,
                healthBarBottomPosition,
                this.healthBarPaint
        );
    }

    @Override
    public void update() {

    }
}