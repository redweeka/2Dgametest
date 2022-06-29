package com.example.a2d_game_test.models.graphics;

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

    public Sprite getPlayerSprite() {
        return new Sprite(this, new Rect(0,0, PLAYER_SPRITE_RADIUS, PLAYER_SPRITE_RADIUS));
    }

    public Bitmap getBitmap() {
        return this.bitmap;
    }
}
