package com.example.a2d_game_test.map;

import android.graphics.Canvas;
import android.graphics.Rect;

import com.example.a2d_game_test.models.graphics.Sprite;
import com.example.a2d_game_test.models.graphics.SpriteSheet;

public class GrassTile extends Tile {
    private final Sprite grassSprite;
    private final Sprite groundSprite;

    public GrassTile(SpriteSheet spriteSheet, Rect mapLocationRect, int type) {
        super(mapLocationRect);
        this.grassSprite = spriteSheet.getGrassSprite(type);
        this.groundSprite = spriteSheet.getGreenGroundSprite();
    }

    @Override
    public void draw(Canvas canvas) {
        this.groundSprite.draw(canvas, mapLocationRect.left, mapLocationRect.top);
        this.grassSprite.draw(canvas, mapLocationRect.left, mapLocationRect.top);
    }
}
