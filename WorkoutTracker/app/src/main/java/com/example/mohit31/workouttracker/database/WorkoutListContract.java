package com.example.mohit31.workouttracker.database;

import android.provider.BaseColumns;

/**
 * Created by mohit31 on 9/11/17.
 */
public final class WorkoutListContract {

    private WorkoutListContract() {

    }

    public static class WorkoutListEntry implements BaseColumns {
        public static final String TABLE_NAME = "workoutList";
        public static final String _ID = "_id";
        public static final String COLUMN_WORKOUT_NAME = "name";
        public static final String COLUMN_WORKOUT_KEY = "key";
    }
}
