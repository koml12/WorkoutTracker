package com.example.mohit31.workouttracker.utils;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.mohit31.workouttracker.database.WorkoutListContract;
import com.example.mohit31.workouttracker.database.WorkoutViewContract;

/**
 * Created by mohit31 on 9/1/17.
 */

public class DatabaseMethods {
    public static Cursor getAllItems(SQLiteDatabase database) {
        String[] columns = {
                WorkoutViewContract.WorkoutViewEntry.COLUMN_EXERCISE_NAME,
                WorkoutViewContract.WorkoutViewEntry.COLUMN_REPS,
                WorkoutViewContract.WorkoutViewEntry.COLUMN_SETS,
                WorkoutViewContract.WorkoutViewEntry.COLUMN_REST_TIME,
                WorkoutViewContract.WorkoutViewEntry._ID,
                WorkoutViewContract.WorkoutViewEntry.COLUMN_WORKOUT_KEY,
                WorkoutViewContract.WorkoutViewEntry.COLUMN_WEIGHT,
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


    public static Cursor getWorkoutItems(SQLiteDatabase database) {
        String[] columns = {
                WorkoutListContract.WorkoutListEntry.COLUMN_WORKOUT_NAME,
                WorkoutListContract.WorkoutListEntry.COLUMN_WORKOUT_KEY,
                WorkoutListContract.WorkoutListEntry._ID};
        return database.query(WorkoutListContract.WorkoutListEntry.TABLE_NAME,
                columns,
                null,
                null,
                null,
                null,
                null,
                null);
    }


    public static Cursor getExercisesForWorkout(SQLiteDatabase database, int key) {
        String[] columns = {
                WorkoutViewContract.WorkoutViewEntry.COLUMN_EXERCISE_NAME,
                WorkoutViewContract.WorkoutViewEntry.COLUMN_REPS,
                WorkoutViewContract.WorkoutViewEntry.COLUMN_SETS,
                WorkoutViewContract.WorkoutViewEntry.COLUMN_REST_TIME,
                WorkoutViewContract.WorkoutViewEntry._ID,
                WorkoutViewContract.WorkoutViewEntry.COLUMN_WORKOUT_KEY,
                WorkoutViewContract.WorkoutViewEntry.COLUMN_WEIGHT,
        };
        return database.query(WorkoutViewContract.WorkoutViewEntry.TABLE_NAME,
                columns,
                WorkoutViewContract.WorkoutViewEntry.COLUMN_WORKOUT_KEY + "=" + key,
                null,
                null,
                null,
                null,
                null);
    }


    public static Cursor getWorkoutFromKey(SQLiteDatabase database, int key) {
        String[] columns = {
                WorkoutListContract.WorkoutListEntry.COLUMN_WORKOUT_NAME,
                WorkoutListContract.WorkoutListEntry.COLUMN_WORKOUT_KEY,
                WorkoutListContract.WorkoutListEntry._ID,
        };
        return database.query(WorkoutListContract.WorkoutListEntry.TABLE_NAME,
                columns,
                WorkoutListContract.WorkoutListEntry.COLUMN_WORKOUT_KEY + " = " + key,
                null,
                null,
                null,
                null,
                null);
    }
}
