package com.example.a2d_game_test.models.gamePanels;

import static com.example.a2d_game_test.utilities.Constants.GameLoopDetailsConstants.*;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import androidx.core.content.ContextCompat;

import com.example.a2d_game_test.R;
import com.example.a2d_game_test.gameEngine.GameLoop;

public class GamePerformances {

    private final GameLoop gameLoop;
    private final Context context;

    public GamePerformances(Context context, GameLoop gameLoop){
        this.context = context;
        this.gameLoop = gameLoop;
    }

    public void draw(Canvas canvas) {
        drawUpdatesPerSecond(canvas);
        drawFramesPerSecond(canvas);
    }

    public void drawUpdatesPerSecond(Canvas canvas) {
        double averageUpdatesPerSecond = this.gameLoop.getAverageUpdatesPerSecond();
        String textToDraw = this.getTextFormattedTODraw(UPDATES_PER_SECOND_TEXT, averageUpdatesPerSecond);
        Paint paint = getGameLoopTextPaint();

        canvas.drawText(textToDraw, TEXT_POSITION_X, TEXT_POSITION_Y, paint);
    }

    public void drawFramesPerSecond(Canvas canvas) {
        double averageFramesPerSecond = this.gameLoop.getAverageFramesPerSecond();
        String textToDraw = this.getTextFormattedTODraw(FRAME_PER_SECOND_TEXT, averageFramesPerSecond);
        Paint paint = getGameLoopTextPaint();
        canvas.drawText(textToDraw, TEXT_POSITION_X, (TEXT_POSITION_Y * 2), paint);
    }

    public Paint getGameLoopTextPaint() {
        Paint paint = new Paint();
        int color = ContextCompat.getColor(this.context, R.color.teal_200);
        paint.setColor(color);
        paint.setTextSize(TEXT_SIZE);

        return paint;
    }

    /**
     * Make all texts in line. for example:
     * "<TEXT>      : <DOUBLE_NUMBER>"
     * "<LONG_TEXT> : <DOUBLE_NUMBER>"
     */
    private String getTextFormattedTODraw(String textKey, double valve) {
        return String.format("%" + -MAX_DISPLAY_TEXT_LENGTH + "s" + TEXT_BORDER + " %.1f", textKey, valve);
    }
}
