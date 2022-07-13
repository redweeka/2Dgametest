package com.example.a2d_game_test.map;

import static com.example.a2d_game_test.utilities.Constants.TiledMapConstants.*;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

import com.example.a2d_game_test.models.gameObjects.GameDisplay;
import com.example.a2d_game_test.models.graphics.SpriteSheet;

public class TiledMap {

    private final MapLayout mapLayout;
    private Tile[][] tiledMap;
    private SpriteSheet spriteSheet;
    private Bitmap mapBitmap;

    public TiledMap(SpriteSheet spriteSheet) {
        this.mapLayout = new MapLayout();
        this.spriteSheet = spriteSheet;

        initializeTiledMap();
    }

    private void initializeTiledMap() {
        this.tiledMap = new Tile[ROW_TILES_AMOUNT][COLUMN_TILES_AMOUNT];
        int[][] mapLLayout = this.mapLayout.getMapLayout();

        for (int rowIndex = 0; rowIndex < ROW_TILES_AMOUNT; rowIndex++) {
            for (int columnIndex = 0; columnIndex < COLUMN_TILES_AMOUNT; columnIndex++) {
                tiledMap[rowIndex][columnIndex] = Tile.tile(
                        mapLLayout[rowIndex][columnIndex],
                        this.spriteSheet,
                        getRectByIndex(rowIndex, columnIndex));
            }
        }

        Bitmap.Config config = Bitmap.Config.ARGB_8888;
        this.mapBitmap = Bitmap.createBitmap(
             COLUMN_TILES_AMOUNT*TILE_PIXELS_WIDTH,
             ROW_TILES_AMOUNT*TILE_PIXELS_HEIGHT,
             config
        );

        Canvas mapCanvas = new Canvas(this.mapBitmap);

        for (int rowIndex = 0; rowIndex < ROW_TILES_AMOUNT; rowIndex++) {
            for (int columnIndex = 0; columnIndex < COLUMN_TILES_AMOUNT; columnIndex++) {
                tiledMap[rowIndex][columnIndex].draw(mapCanvas);
            }
        }
    }

    private Rect getRectByIndex(int rowIndex, int columnIndex) {
        return new Rect(
                columnIndex * TILE_PIXELS_WIDTH,
                rowIndex * TILE_PIXELS_HEIGHT,
                (columnIndex + 1) * TILE_PIXELS_WIDTH,
                (rowIndex + 1) * TILE_PIXELS_HEIGHT
        );
    }

    public void draw(Canvas canvas, GameDisplay gameDisplay) {
        canvas.drawBitmap(this.mapBitmap, gameDisplay.getGameRect(), gameDisplay.SCREEN_RECT_DISPLAY, null);
    }
}
