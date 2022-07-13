package com.example.a2d_game_test.models.graphics;

import static com.example.a2d_game_test.utilities.Constants.GraphicsConstants.MAX_UPDATES_BEFORE_NEXT_MOVE_FRAME;
import static com.example.a2d_game_test.utilities.Constants.GraphicsConstants.SPRITE_PIXELS_WIDTH;

import android.graphics.Canvas;

import com.example.a2d_game_test.models.gameObjects.GameDisplay;
import com.example.a2d_game_test.models.gameObjects.Player;

public class PlayerAnimator {

    private final Sprite[] playerSprites;
    private int notMovingIndex = 0;

    // Start at 1 and later will toggle between 1 and 2
    private int movingIndex = 1;
    private int updatesBeforeNextMoveFrame;

    public PlayerAnimator(Sprite[] playerSprites) {
        this.playerSprites = playerSprites;
    }

    public void draw(Canvas canvas, GameDisplay gameDisplay, Player player) {
        switch (player.playerState().state()) {
            case NOT_MOVING:
                drawFrame(canvas, gameDisplay, player, this.playerSprites[this.notMovingIndex]);

                break;
            case START_MOVING:
                this.updatesBeforeNextMoveFrame = MAX_UPDATES_BEFORE_NEXT_MOVE_FRAME;
                drawFrame(canvas, gameDisplay, player, this.playerSprites[this.movingIndex]);

                break;
            case MOVING:
                this.updatesBeforeNextMoveFrame--;

                if (this.updatesBeforeNextMoveFrame == 0) {
                    this.updatesBeforeNextMoveFrame = MAX_UPDATES_BEFORE_NEXT_MOVE_FRAME;
                    toggleMovingIndex();
                }

                drawFrame(canvas, gameDisplay, player, this.playerSprites[this.movingIndex]);

                break;
            default:
                break;
        }
    }

    private void toggleMovingIndex() {
        this.movingIndex = this.movingIndex == 1 ? 2 : 1;
    }

    public void drawFrame(Canvas canvas, GameDisplay gameDisplay, Player player, Sprite sprite) {
        // Draw player picture in the center
        sprite.draw(
                canvas,
                (int) gameDisplay.gameDisplayCoordinateX(player.positionX()) - sprite.width()/2,
                (int) gameDisplay.gameDisplayCoordinateY(player.positionY()) - sprite.height()/2
        );
    }
}
