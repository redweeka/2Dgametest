package com.example.a2d_game_test.models.graphics;

import static com.example.a2d_game_test.utilities.Constants.GraphicsConstants.SPRITE_PIXELS_WIDTH;

import android.graphics.Canvas;
import android.graphics.Rect;

public class Sprite {

    private final SpriteSheet spriteSheet;
    private final Rect rect;

    public Sprite(SpriteSheet spriteSheet, Rect rect) {
        this.spriteSheet = spriteSheet;
        this.rect = rect;
    }

    public void draw(Canvas canvas, int positionX, int positionY) {
        canvas.drawBitmap(
                this.spriteSheet.getBitmap(),
                this.rect,
                new Rect(positionX, positionY, positionX + SPRITE_PIXELS_WIDTH, positionY + SPRITE_PIXELS_WIDTH),
                null
        );
    }

    public int height() {
        return this.rect.height();
    }

    public int width() {
        return this.rect.width();
    }
}
