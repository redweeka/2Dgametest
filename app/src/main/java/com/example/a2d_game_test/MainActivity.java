package com.example.a2d_game_test;

import android.app.Activity;
import android.os.Bundle;

import com.example.a2d_game_test.gameEngine.Game;

public class MainActivity extends Activity {

    private Game game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Game will render things to main activity
        this.game = new Game(this);
        setContentView(this.game);
    }

    @Override
    protected void onPause() {
        this.game.pause();
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        // Disable back button
    }
}