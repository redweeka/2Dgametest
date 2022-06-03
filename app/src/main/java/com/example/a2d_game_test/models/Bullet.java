package com.example.a2d_game_test.models;

import static com.example.a2d_game_test.utilities.Constants.ENEMY_RADIUS;
import static com.example.a2d_game_test.utilities.Constants.ENEMY_SPAWN_MAX_POSITION_X;
import static com.example.a2d_game_test.utilities.Constants.ENEMY_SPAWN_MAX_POSITION_Y;

import android.content.Context;

import androidx.core.content.ContextCompat;

import com.example.a2d_game_test.R;

public class Bullet extends CircleGameObject{

    private final CircleGameObject shooter;

    public Bullet(Context context, CircleGameObject shooter) {
        super(Math.random() * ENEMY_SPAWN_MAX_POSITION_X, Math.random() * ENEMY_SPAWN_MAX_POSITION_Y, ENEMY_RADIUS, ContextCompat.getColor(context, R.color.enemy));

        this.shooter = shooter;
    }

    @Override
    public void update() {}
}
