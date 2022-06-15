package com.example.a2d_game_test.utilities;

public class Constants {
    public static class GameLoopConstants {
        public static final int SECOND_IN_MILLISECOND = 1000;
        public static final double MAX_UPDATES_PER_SECOND = 60.0;
        public static final double UPDATES_PER_SECOND_PERIOD = 1E+3 / MAX_UPDATES_PER_SECOND;
    }

    public static class PlayerConstants {
        public static final int START_PLAYER_POSITION_Y = 466;
        public static final int START_PLAYER_POSITION_X = START_PLAYER_POSITION_Y * 2;
        public static final int PLAYER_RADIUS = 36;
        public static final int PLAYER_MAX_HEALTH_POINTS = 10;
    }

    public static class EnemyConstants {
        public static final double ENEMIES_SPAWNS_PER_MINUTE = 20.0;
        public static final double ENEMIES_SPAWNS_PER_SECOND = ENEMIES_SPAWNS_PER_MINUTE/60.0;
        public static final double ENEMIES_UPDATES_PER_SPAWN = GameLoopConstants.MAX_UPDATES_PER_SECOND/ENEMIES_SPAWNS_PER_SECOND;
        public static final int ENEMY_SPAWN_MAX_POSITION_X = 1666;
        public static final int ENEMY_SPAWN_MAX_POSITION_Y = 866;
        public static final int ENEMY_RADIUS = 36;
        public static final int ENEMY_DAMAGE_POINTS = 1;
    }

    public static class MovementSpeedConstants {
        public static final double PLAYER_PIXELS_PER_SECOND_SPEED = 866.2;
        public static final double PLAYER_MAX_SPEED = PLAYER_PIXELS_PER_SECOND_SPEED / GameLoopConstants.MAX_UPDATES_PER_SECOND;
        public static final double ENEMY_SPEED = PLAYER_MAX_SPEED * 0.6;
        public static final double BULLET_SPEED = PLAYER_MAX_SPEED * 1.6;
    }

    public static class JoystickConstants {
        public static final int START_JOYSTICK_POSITION_Y = 766;
        public static final int START_JOYSTICK_POSITION_X = 266;
        public static final int OUTER_JOYSTICK_RADIUS = 166;
        public static final int INNER_JOYSTICK_RADIUS = 36;
    }

    public static class GameLoopDetailsConstants {
        public static final float TEXT_SIZE = 56;
        public static final String UPDATES_PER_SECOND_TEXT = "UpdatesPerSecond";
        public static final String FRAME_PER_SECOND_TEXT = "FramesPerSecond";
        public static final int MAX_DISPLAY_TEXT_LENGTH = UPDATES_PER_SECOND_TEXT.length() + 1;
        public static final String TEXT_BORDER = ":";
        public static final float TEXT_POSITION_X = 0;
        public static final float TEXT_POSITION_Y = TEXT_SIZE;
    }

    public static class BulletConstants {
        public static final int BULLET_RADIUS = 26;
    }

    public static class HealthBarConstants {
        public static final int HEALTH_BAR_DISTANCE_FROM_PLAYER = 36;
        public static final int HEALTH_BAR_FRAME_WIDTH = 96;
        public static final int HEALTH_BAR_FRAME_HEIGHT = 26;
        public static final int HEALTH_BAR_PADDING = 6;
        public static final int HEALTH_BAR_WIDTH = HEALTH_BAR_FRAME_WIDTH - 2*HEALTH_BAR_PADDING;
    }

    public static class GameOverConstants {
        public static final String GAME_OVER_TEXT = "Game Over Champ!";
        public static final float GAME_OVER_TEXT_SIZE = 150;
        public static final float GAME_OVER_POSITION_X = 800;
        public static final float GAME_OVER_POSITION_Y = 200;
    }
}
