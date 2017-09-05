package com.example.mohit31.workouttracker.Utils;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.mohit31.workouttracker.Database.WorkoutViewContract;

/**
 * Created by mohit31 on 9/1/17.
 */
public class DatabaseMethods {
    public static Cursor getAllItems(SQLiteDatabase database) {
        String[] columns = {
                WorkoutViewContract.WorkoutViewEntry.COLUMN_EXERCISE_NAME,
                WorkoutViewContract.WorkoutViewEntry.COLUMN_REPS,
                WorkoutViewContract.WorkoutViewEntry.COLUMN_SETS,
                WorkoutViewContract.WorkoutViewEntry.COLUMN_REST_TIME
        };
        return database.query(WorkoutViewContract.WorkoutViewEntry.TABLE_NAME,
                columns,
                null,
                null,
                null,
                null,
                null,
                null);
    }
}
