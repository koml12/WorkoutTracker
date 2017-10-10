package com.example.mohit31.workouttracker.activities;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import com.example.mohit31.workouttracker.R;
import com.example.mohit31.workouttracker.adapters.WeightListAdapter;
import com.example.mohit31.workouttracker.database.WorkoutViewContract;
import com.example.mohit31.workouttracker.database.WorkoutViewDbHelper;
import com.example.mohit31.workouttracker.utils.DatabaseMethods;

import java.util.ArrayList;
import java.util.List;

public class TrackWorkoutActivity extends AppCompatActivity {
    WeightListAdapter mWeightListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_workout);

        final Spinner mExerciseNameSpinner = (Spinner) findViewById(R.id.spinner_exercise_name);
        final RecyclerView mWeightsRecyclerView = (RecyclerView) findViewById(R.id.rv_weight_list);

        int key = getIntent().getExtras().getInt("WORKOUT_KEY");
        WorkoutViewDbHelper mDbHelper = new WorkoutViewDbHelper(getApplicationContext());
        final SQLiteDatabase mDatabase = mDbHelper.getReadableDatabase();

        final Cursor mCursor = DatabaseMethods.getExercisesForWorkout(mDatabase, key);

        List<String> exerciseNames = new ArrayList<>();
        exerciseNames.add("Choose exercise name");
        mCursor.moveToFirst();
        do {
            exerciseNames.add(mCursor.getString(mCursor.getColumnIndex(WorkoutViewContract.WorkoutViewEntry.COLUMN_EXERCISE_NAME)));
        } while (mCursor.moveToNext());


        ArrayAdapter<String> mArrayAdapter = new ArrayAdapter<>(getApplicationContext(),
                R.layout.support_simple_spinner_dropdown_item, exerciseNames);
        mExerciseNameSpinner.setAdapter(mArrayAdapter);


        mExerciseNameSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if (position == 0) {
                    return;
                }
                // Move Cursor to position - 1, since position 0 is occupied by a select message.
                if(mCursor.moveToPosition(position - 1)) {
                    String exerciseID = mCursor.getString(mCursor.getColumnIndex(WorkoutViewContract.WorkoutViewEntry._ID));
                    Cursor mWeightsCursor = DatabaseMethods.getWeightsFromExercise(mDatabase, exerciseID);
                    mWeightListAdapter = new WeightListAdapter(getApplicationContext(), mWeightsCursor, exerciseID);
                    mWeightsRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    mWeightsRecyclerView.setAdapter(mWeightListAdapter);
                    mExerciseNameSpinner.setVisibility(View.INVISIBLE);
                    mWeightsRecyclerView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }
}
