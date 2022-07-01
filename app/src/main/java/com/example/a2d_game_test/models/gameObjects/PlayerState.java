package com.example.a2d_game_test.models.gameObjects;

public class PlayerState {

    public enum State {
        NOT_MOVING,
        START_MOVING,
        MOVING
    }

    private Player player;
    private State state;

    public PlayerState(Player player){
        this.player = player;
        this.state = State.NOT_MOVING;
    }

    public State state() {
        return this.state;
    }

    public void update() {
        switch (this.state) {
            case NOT_MOVING:
                if (this.player.isMoving()) {
                    this.state = State.START_MOVING;
                }

                break;
            case START_MOVING:
                if (this.player.isMoving()) {
                    this.state = State.MOVING;
                } else {
                    this.state = State.NOT_MOVING;
                }

                break;
            case MOVING:
                if (!this.player.isMoving()) {
                    this.state = State.NOT_MOVING;
                }

                break;
            default:
                break;
        }
    }
}
