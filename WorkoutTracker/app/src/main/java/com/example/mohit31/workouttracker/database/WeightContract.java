package com.example.mohit31.workouttracker.database;

import android.provider.BaseColumns;

/**
 * Created by mohit31 on 9/30/17.
 */
public class WeightContract {

    private WeightContract() {}

    public static class WeightEntry implements BaseColumns {
        public static final String TABLE_NAME = "weights";
        public static final String COLUMN_DATE = "date";
        public static final String COLUMN_WEIGHT = "weight";
        public static final String COLUMN_EXERCISE = "exercise";
    }
}
