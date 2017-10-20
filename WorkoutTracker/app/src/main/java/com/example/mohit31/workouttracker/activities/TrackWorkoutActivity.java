package com.example.mohit31.workouttracker.activities;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import com.example.mohit31.workouttracker.R;
import com.example.mohit31.workouttracker.database.WeightContract;
import com.example.mohit31.workouttracker.database.WorkoutViewContract;
import com.example.mohit31.workouttracker.database.WorkoutViewDbHelper;
import com.example.mohit31.workouttracker.utils.DatabaseMethods;
import com.example.mohit31.workouttracker.utils.DayAxisFormatter;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import java.util.ArrayList;
import java.util.List;

public class TrackWorkoutActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_workout);

        final Spinner mExerciseNameSpinner = (Spinner) findViewById(R.id.spinner_exercise_name);
        final LineChart mWeightLineChart = (LineChart) findViewById(R.id.chart_line_weights);
        final TextView mNoWeightDataTextView = (TextView) findViewById(R.id.tv_no_weights);

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
                R.layout.spinner_exercise_item, exerciseNames);
        mExerciseNameSpinner.setAdapter(mArrayAdapter);

        mExerciseNameSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if (position == 0) {
                    mNoWeightDataTextView.setVisibility(View.INVISIBLE);
                    mWeightLineChart.setVisibility(View.INVISIBLE);
                    return;
                }

                // Move Cursor to position - 1, since position 0 is occupied by a select message.
                if(mCursor.moveToPosition(position - 1)) {
                    String exerciseID = mCursor.getString(mCursor.getColumnIndex(WorkoutViewContract.WorkoutViewEntry._ID));
                    Cursor mWeightsCursor = DatabaseMethods.getWeightsFromExercise(mDatabase, exerciseID);
                    if (mWeightsCursor.getCount() == 0) {
                        mNoWeightDataTextView.setVisibility(View.VISIBLE);
                        mWeightLineChart.setVisibility(View.INVISIBLE);
                        return;
                    }
                    List<Entry> entries = new ArrayList<Entry>();
                    mWeightsCursor.moveToFirst();

                    do {
                        long date = mWeightsCursor.getLong(mWeightsCursor.getColumnIndex(WeightContract.WeightEntry.COLUMN_DATE));
                        float weight = mWeightsCursor.getFloat(mWeightsCursor.getColumnIndex(WeightContract.WeightEntry.COLUMN_WEIGHT));
                        entries.add(new Entry(date, weight));
                    } while(mWeightsCursor.moveToNext());

                    LineDataSet dataSet = new LineDataSet(entries, "");
                    LineData lineData = new LineData(dataSet);
                    XAxis axis = mWeightLineChart.getXAxis();
                    axis.setValueFormatter(new DayAxisFormatter());

                    mWeightLineChart.setData(lineData);

                    // Customize chart attributes
                    mWeightLineChart.setDrawGridBackground(false);
                    mWeightLineChart.setGridBackgroundColor(Color.WHITE);
                    mWeightLineChart.getAxisLeft().setDrawGridLines(false);
                    mWeightLineChart.getAxisLeft().setAxisMinimum(0);
                    mWeightLineChart.getAxisRight().setEnabled(false);
                    mWeightLineChart.getXAxis().setDrawGridLines(false);
                    mWeightLineChart.getDescription().setEnabled(false);
                    mWeightLineChart.getLegend().setEnabled(false);

                    mWeightLineChart.invalidate();
                    mWeightLineChart.setVisibility(View.VISIBLE);
                    mNoWeightDataTextView.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }
}
