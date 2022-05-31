package com.example.a2d_game_test.utilities;

public class Utils {

    public static double calculateDistanceBetween2Points(double deltaX, double deltaY) {
        return Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2));
    }
}
