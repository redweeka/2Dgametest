package com.example.a2d_game_test.events;

import android.view.MotionEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class MotionEventEnumFactory {
    private static final Map<Integer, MotionEventEnum> motionEventEnumMap;

    static {
        motionEventEnumMap = new HashMap<>();

        motionEventEnumMap.put(MotionEvent.ACTION_DOWN, MotionEventEnum.ACTION_DOWN);
        motionEventEnumMap.put(MotionEvent.ACTION_MOVE, MotionEventEnum.ACTION_MOVE);
        motionEventEnumMap.put(MotionEvent.ACTION_UP, MotionEventEnum.ACTION_UP);
    }

    public static Optional<MotionEventEnum> crateEventEnum(Integer monitorEventAction) {
        return Optional.ofNullable( motionEventEnumMap.getOrDefault(monitorEventAction, null));
    }
}
