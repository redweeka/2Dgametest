package com.example.a2d_game_test.models.graphics;

import static com.example.a2d_game_test.utilities.Constants.Graphics.PLAYER_SPRITES_AMOUNT;
import static com.example.a2d_game_test.utilities.Constants.PlayerConstants.PLAYER_SPRITE_RADIUS;

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
            int currLeftSpritePosition = playerSpritesIndex * PLAYER_SPRITE_RADIUS;

            playerSprites[playerSpritesIndex] = new Sprite(
                    this,
                    new Rect(
                            currLeftSpritePosition,
                            0,
                            currLeftSpritePosition + PLAYER_SPRITE_RADIUS,
                            PLAYER_SPRITE_RADIUS
                    )
            );
        }

        return playerSprites;
    }

    public Bitmap getBitmap() {
        return this.bitmap;
    }
}
