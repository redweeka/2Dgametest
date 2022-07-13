package com.example.a2d_game_test.models.graphics;

import static com.example.a2d_game_test.utilities.Constants.GraphicsConstants.PLAYER_SPRITES_AMOUNT;
import static com.example.a2d_game_test.utilities.Constants.GraphicsConstants.SPRITE_PIXELS_HEIGHT;
import static com.example.a2d_game_test.utilities.Constants.GraphicsConstants.SPRITE_PIXELS_WIDTH;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import com.example.a2d_game_test.R;

public class SpriteSheet {
    private final Bitmap bitmap;

    public SpriteSheet(Context context) {
        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        // TODO: 6/29/2022 check what happen without it
        bitmapOptions.inScaled = false;
        this.bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.sprite_sheet, bitmapOptions);
    }

    public Sprite[] getPlayerSprites() {
        Sprite[] playerSprites = new Sprite[PLAYER_SPRITES_AMOUNT];

        // Get player picture size sprites in first row of the sprite sheet
        for (int playerSpritesIndex = 0; playerSpritesIndex < playerSprites.length; playerSpritesIndex++) {
            int currLeftSpritePosition = playerSpritesIndex * SPRITE_PIXELS_WIDTH;
            playerSprites[playerSpritesIndex] = getSpriteByIndex(0, currLeftSpritePosition);
        }

        return playerSprites;
    }

    public Sprite getWaterSprite() {
        return getSpriteByIndex(1, 0);
    }

    public Sprite getGreenGroundSprite() {
        return getSpriteByIndex(1, 1);
    }

    public Sprite getGrassSprite(int grassType) {
        return getSpriteByIndex(1, 2 + grassType);
    }

    private Sprite getSpriteByIndex(int rowIndex, int columnIndex) {
        return new Sprite(
                this,
                new Rect(
                        columnIndex * SPRITE_PIXELS_WIDTH,
                        rowIndex * SPRITE_PIXELS_HEIGHT,
                        (columnIndex + 1) * SPRITE_PIXELS_WIDTH,
                        (rowIndex + 1) * SPRITE_PIXELS_HEIGHT
                )
        );
    }

    public Bitmap getBitmap() {
        return this.bitmap;
    }
}
