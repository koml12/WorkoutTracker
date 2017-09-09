package com.example.mohit31.workouttracker.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by mohit31 on 8/29/17.
 */
public class WorkoutViewDbHelper extends SQLiteOpenHelper {

    public final String CREATE_DATABASE = "CREATE TABLE " + WorkoutViewContract.WorkoutViewEntry.TABLE_NAME + " ("
            + WorkoutViewContract.WorkoutViewEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + WorkoutViewContract.WorkoutViewEntry.COLUMN_EXERCISE_NAME + " TEXT, "
            + WorkoutViewContract.WorkoutViewEntry.COLUMN_REPS + " INTEGER, "
            + WorkoutViewContract.WorkoutViewEntry.COLUMN_SETS + " INTEGER, "
            + WorkoutViewContract.WorkoutViewEntry.COLUMN_REST_TIME + " INTEGER)";

    public final String DELETE_DATABASE = "DROP TABLE " + WorkoutViewContract.WorkoutViewEntry.TABLE_NAME;

    private static final String DATABASE_NAME = "workoutInfo.db";
    private static int DATABASE_VERSION = 1;


    public WorkoutViewDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_DATABASE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL(DELETE_DATABASE);
        sqLiteDatabase.execSQL(CREATE_DATABASE);
        DATABASE_VERSION = newVersion;

    }

}
