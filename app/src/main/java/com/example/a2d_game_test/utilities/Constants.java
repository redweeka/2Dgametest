package com.example.a2d_game_test.utilities;

public class Constants {
    public static final float TEXT_SIZE = 56;

    public static class GameLoop {
        public static final int SECOND_IN_MILLISECOND = 1000;
        public static final double MAX_UPDATES_PER_SECOND = 60.0;
        public static final double UPDATES_PER_SECOND_PERIOD = 1E+3 / MAX_UPDATES_PER_SECOND;
    }

    public static class Player {
        public static final int START_PLAYER_POSITION_Y = 466;
        public static final int START_PLAYER_POSITION_X = START_PLAYER_POSITION_Y * 2;
        public static final int PLAYER_RADIUS = 36;
    }

    public static class Enemy {
        public static final double ENEMIES_SPAWNS_PER_MINUTE = 20.0;
        public static final double ENEMIES_SPAWNS_PER_SECOND = ENEMIES_SPAWNS_PER_MINUTE / 60.0;
        public static final double UPDATES_PER_SPAWN = GameLoop.MAX_UPDATES_PER_SECOND / ENEMIES_SPAWNS_PER_SECOND;
        public static final int ENEMY_SPAWN_MAX_POSITION_X = 1666;
        public static final int ENEMY_SPAWN_MAX_POSITION_Y = 866;
        public static final int ENEMY_RADIUS = 36;
    }

    public static class Movement {
        public static final double PLAYER_PIXELS_PER_SECOND_SPEED = 666.6;
        public static final double PLAYER_MAX_SPEED = PLAYER_PIXELS_PER_SECOND_SPEED / GameLoop.MAX_UPDATES_PER_SECOND;
        public static final double ENEMY_SPEED = PLAYER_MAX_SPEED * 0.6;
    }

    public static class Joystick {
        public static final int START_JOYSTICK_POSITION_Y = 766;
        public static final int START_JOYSTICK_POSITION_X = 266;
        public static final int OUTER_JOYSTICK_RADIUS = 166;
        public static final int INNER_JOYSTICK_RADIUS = 36;
    }

    public static class GameDetails {
        public static final String UPDATES_PER_SECOND = "UpdatesPerSecond";
        public static final String FRAME_PER_SECOND = "FramesPerSecond";
        public static final int MAX_TEXT_LENGTH = UPDATES_PER_SECOND.length() + 1;
        public static final String TEXT_BORDER = ":";
        public static final float TEXT_HEIGHT = TEXT_SIZE;
    }

    public static class Bullet {
        public static final int BULLET_RADIUS = 26;
        public static final double BULLET_SPEED = Movement.PLAYER_MAX_SPEED;
    }
}
