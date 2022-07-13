package com.example.a2d_game_test.map;

import android.graphics.Canvas;
import android.graphics.Rect;

import com.example.a2d_game_test.models.graphics.Sprite;
import com.example.a2d_game_test.models.graphics.SpriteSheet;

public class GroundTile extends Tile {
    private final Sprite sprite;

    public GroundTile(SpriteSheet spriteSheet, Rect mapLocationRect) {
        super(mapLocationRect);
        this.sprite = spriteSheet.getGreenGroundSprite();
    }

    @Override
    public void draw(Canvas canvas) {
        this.sprite.draw(canvas, mapLocationRect.left, mapLocationRect.top);
    }
}
