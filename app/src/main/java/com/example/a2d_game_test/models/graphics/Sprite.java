package com.example.a2d_game_test.models.graphics;

import android.graphics.Canvas;
import android.graphics.Rect;

public class Sprite {

    private final SpriteSheet spriteSheet;
    private final Rect rect;

    public Sprite(SpriteSheet spriteSheet, Rect rect) {
        this.spriteSheet = spriteSheet;
        this.rect = rect;
    }

    public void draw(Canvas canvas, int positionX, int positionY, int size) {
        canvas.drawBitmap(
                this.spriteSheet.getBitmap(),
                this.rect,
                new Rect(positionX, positionY, positionX + size, positionY + size),
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
