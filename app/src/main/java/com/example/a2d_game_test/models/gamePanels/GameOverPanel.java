package com.example.a2d_game_test.models.gamePanels;

import static com.example.a2d_game_test.utilities.Constants.GameOverConstants.*;
import static com.example.a2d_game_test.utilities.Constants.PlayerConstants.START_PLAYER_POSITION_X;
import static com.example.a2d_game_test.utilities.Constants.PlayerConstants.START_PLAYER_POSITION_Y;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import androidx.core.content.ContextCompat;

import com.example.a2d_game_test.R;

public class GameOverPanel {

    private Paint gameOverPaint = new Paint();

    public GameOverPanel(Context context) {
        this.gameOverPaint.setColor(ContextCompat.getColor(context, R.color.game_over));
        this.gameOverPaint.setTextSize(GAME_OVER_TEXT_SIZE);
    }

    public void draw(Canvas canvas) {
        canvas.drawText(GAME_OVER_TEXT, GAME_OVER_POSITION_X, GAME_OVER_POSITION_Y, this.gameOverPaint);
    }
}
