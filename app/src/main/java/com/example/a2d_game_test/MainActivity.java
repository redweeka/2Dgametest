package com.example.a2d_game_test;

import android.app.Activity;
import android.os.Bundle;

import com.example.a2d_game_test.gameEngine.Game;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Class Game will render things to main activity
        setContentView(new Game(this));
    }
}