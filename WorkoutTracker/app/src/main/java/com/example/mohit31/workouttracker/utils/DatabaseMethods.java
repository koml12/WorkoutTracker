package com.example.mohit31.workouttracker.utils;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.mohit31.workouttracker.database.WeightContract;
import com.example.mohit31.workouttracker.database.WorkoutListContract;
import com.example.mohit31.workouttracker.database.WorkoutViewContract;

/**
 * Created by mohit31 on 9/1/17.
 */

/**
 * Random utility methods for databases, mainly for getting Cursor objects with specific conditions.
 */
public class DatabaseMethods {
    /**
     * Get all rows and columns of the exercise table.
     *
     * @param database              SQLite database containing the exercise table.
     * @return                      Cursor containing all items of the exercise table.
     */
    public static Cursor getAllExerciseItems(SQLiteDatabase database) {
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

    /**
     * Get all rows and columns from the workout table.
     *
     * @param database              SQLite database containing the workout table.
     * @return                      Cursor containing all items from the workout table.
     */
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

    /**
     * Gets the rows and all the columns of the exercise table corresponding to a WorkoutKey of 'key'.
     *
     * @param database              SQLite database containing the exercise table.
     * @param key                   int representing the WorkoutKey column of the table.
     * @return                      Cursor with only the rows of the exercise table that have WorkoutKey == key.
     */
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

    /**
     * Gets the row and all the columns of the workout table corresponding to a WorkoutKey of 'key'.
     * NOTE: there will only be one row returned, since the WorkoutKey attribute is unique to each row.
     *
     * @param database              SQLite database containing the workout table.
     * @param key                   int representing the WorkoutKey column of the table.
     * @return                      Cursor with only one row, that has WorkoutKey == key.
     */
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

    /**
     * Gets all rows of the weights table corresponding to a given ExerciseKey.
     *
     * @param database              Database with all the tables in it.
     * @param id                    ID of the exercise, as a String.
     * @return                      Cursor with all the rows of the weights table that correspond to 'id'.
     */
    public static Cursor getWeightsFromExercise(SQLiteDatabase database, String id) {
        String[] columns = {
                WeightContract.WeightEntry.COLUMN_EXERCISE,
                WeightContract.WeightEntry.COLUMN_WEIGHT,
                WeightContract.WeightEntry.COLUMN_DATE,
        };
        return database.query(WeightContract.WeightEntry.TABLE_NAME,
                columns,
                WeightContract.WeightEntry.COLUMN_EXERCISE + " = " + id,
                null,
                null,
                null,
                null,
                null);
    }


}
