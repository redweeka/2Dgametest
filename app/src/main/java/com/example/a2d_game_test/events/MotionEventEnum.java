package com.example.a2d_game_test.events;

import android.view.MotionEvent;

import com.example.a2d_game_test.models.Joystick;

public enum MotionEventEnum {
    ACTION_DOWN {
        @Override
        public boolean onTouchEvent(Joystick joystick, MotionEvent event) {
            if (joystick.isPressed((double) event.getX(), (double) event.getY())) {
                joystick.setIsPressed(true);
            }

            return true;
        }
    }, ACTION_MOVE {
        @Override
        public boolean onTouchEvent(Joystick joystick, MotionEvent event) {
            if (joystick.isPressed()) {
                joystick.setActuator((double) event.getX(), (double) event.getY());
            }

            return true;
        }
    }, ACTION_UP {
        @Override
        public boolean onTouchEvent(Joystick joystick, MotionEvent event) {
            joystick.setIsPressed(false);
            joystick.resetActuator();

            return true;
        }
    };

    public abstract boolean onTouchEvent(Joystick joystick, MotionEvent event);
}
