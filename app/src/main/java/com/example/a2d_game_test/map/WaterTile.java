package com.example.a2d_game_test.map;

import android.graphics.Canvas;
import android.graphics.Rect;

import com.example.a2d_game_test.models.graphics.Sprite;
import com.example.a2d_game_test.models.graphics.SpriteSheet;

public class WaterTile extends Tile {
    private final Sprite sprite;

    public WaterTile(SpriteSheet spriteSheet, Rect mapLocationRect) {
        super(mapLocationRect);
        this.sprite = spriteSheet.getWaterSprite();
    }

    @Override
    public void draw(Canvas canvas) {
        this.sprite.draw(canvas, mapLocationRect.left, mapLocationRect.top);
    }
}
