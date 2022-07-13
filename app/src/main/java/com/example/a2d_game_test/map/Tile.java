package com.example.a2d_game_test.map;

import android.graphics.Canvas;
import android.graphics.Rect;

import com.example.a2d_game_test.models.graphics.SpriteSheet;

public abstract class Tile {

    protected final Rect mapLocationRect;

    public Tile(Rect mapLocationRect) {
        this.mapLocationRect = mapLocationRect;
    }

    public enum TileType {
        WATER_TILE,
        GREEN_GROUND_TILE,
        GRASS_TILE_1,
        GRASS_TILE_2,
        GRASS_TILE_3,
    }

    public static Tile tile(int tileIndex, SpriteSheet spriteSheet, Rect mapLocationRect) {
        switch (TileType.values()[tileIndex]) {
            case WATER_TILE:
                return new WaterTile(spriteSheet, mapLocationRect);
            case GREEN_GROUND_TILE:
                return new GroundTile(spriteSheet, mapLocationRect);
            case GRASS_TILE_1:
                return new GrassTile(spriteSheet, mapLocationRect, 0);
            case GRASS_TILE_2:
                return new GrassTile(spriteSheet, mapLocationRect, 1);
            case GRASS_TILE_3:
                return new GrassTile(spriteSheet, mapLocationRect, 2);
            default:
                return null;
        }
    }

    public abstract void draw(Canvas canvas);
}
