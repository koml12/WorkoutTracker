package com.example.mohit31.workouttracker.utils;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.mohit31.workouttracker.database.WeightContract;

/**
 * Created by mohit31 on 10/10/17.
 */
public class WorkoutDataPoint {

    private Queue<Integer> dateQueue;
    private Queue<Float> weightQueue;
    private int dateSize;
    private int weightSize;

    public WorkoutDataPoint(String exerciseID, SQLiteDatabase database) {
        Cursor cursor = DatabaseMethods.getWeightsFromExercise(database, exerciseID);

        cursor.moveToFirst();
        dateQueue = new Queue<>();
        weightQueue = new Queue<>();

        do {
            int timeStamp = cursor.getInt(cursor.getColumnIndex(WeightContract.WeightEntry.COLUMN_DATE));
            float weight = cursor.getFloat(cursor.getColumnIndex(WeightContract.WeightEntry.COLUMN_WEIGHT));
            dateQueue.enqueue(timeStamp);
            weightQueue.enqueue(weight);
        } while(cursor.moveToNext());
        dateSize = dateQueue.getSize();
        weightSize = dateQueue.getSize();
    }

    public int getDatePoint() {
        dateSize--;
        return dateQueue.dequeue();
    }

    public float getWeightPoint() {
        weightSize--;
        return weightQueue.dequeue();
    }


    public int getDateSize() {
        return dateSize;
    }

    public int getWeightSize() {
        return weightSize;
    }
}
