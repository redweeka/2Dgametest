package com.example.a2d_game_test.map;

import static com.example.a2d_game_test.utilities.Constants.TiledMapConstants.COLUMN_TILES_AMOUNT;
import static com.example.a2d_game_test.utilities.Constants.TiledMapConstants.ROW_TILES_AMOUNT;

import java.util.Random;

public class MapLayout {
    private int[][] mapLayout = new int[ROW_TILES_AMOUNT][COLUMN_TILES_AMOUNT];

    public MapLayout() {
        initializeLayout();
    }

    private void initializeLayout() {
        Random random = new Random();

        for (int mapRowIndex = 0; mapRowIndex < ROW_TILES_AMOUNT; mapRowIndex++) {
            for (int mapColumnIndex = 0; mapColumnIndex < COLUMN_TILES_AMOUNT; mapColumnIndex++) {
                this.mapLayout[mapRowIndex][mapColumnIndex] = random.nextInt(5);
            }
        }
    }

    public int[][] getMapLayout() {
        return this.mapLayout;
    }
}
