package com.example.mohit31.workouttracker.database;

import android.provider.BaseColumns;

/**
 * Created by mohit31 on 8/29/17.
 */
public final class WorkoutViewContract {

    private WorkoutViewContract() {}

    public static class WorkoutViewEntry implements BaseColumns {
        public static final String _ID = "_id";
        public static final String TABLE_NAME = "workoutInfo";
        public static final String COLUMN_EXERCISE_NAME = "exerciseName";
        public static final String COLUMN_SETS = "sets";
        public static final String COLUMN_REPS = "reps";
        public static final String COLUMN_REST_TIME = "restTime";
        public static final String COLUMN_WORKOUT_KEY = "workoutKey";
        public static final String COLUMN_WEIGHT = "weight";
    }
}
