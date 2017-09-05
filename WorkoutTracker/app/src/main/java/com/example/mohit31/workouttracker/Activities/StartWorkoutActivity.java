package com.example.mohit31.workouttracker.Activities;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.example.mohit31.workouttracker.Database.WorkoutViewContract;
import com.example.mohit31.workouttracker.Database.WorkoutViewDbHelper;
import com.example.mohit31.workouttracker.R;
import com.example.mohit31.workouttracker.Utils.DatabaseMethods;

public class StartWorkoutActivity extends AppCompatActivity {

    private TextView mExerciseNameTextView;
    private TextView mRepsSetsTextView;
    private TextView mRestTimeTextView;
    private TextView mTimerTextView;
    private TextView mSetsRemainingTextView;
    private TextView mWorkoutCompleteTextView;
    private Button mSetCompletedButton;
    private Button mNextExerciseButton;

    private int restTime = 0;
    private int setsRemaining;
    private CountDownTimer timer = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_workout);

        mExerciseNameTextView = (TextView) findViewById(R.id.tv_start_workout_exercise_name);
        mRepsSetsTextView = (TextView) findViewById(R.id.tv_start_workout_reps_sets);
        mRestTimeTextView = (TextView) findViewById(R.id.tv_start_workout_rest_time);
        mTimerTextView = (TextView) findViewById(R.id.tv_start_workout_timer);
        mSetsRemainingTextView = (TextView) findViewById(R.id.tv_start_workout_sets_remaining);
        mWorkoutCompleteTextView = (TextView) findViewById(R.id.tv_start_workout_workout_completed);
        mSetCompletedButton = (Button) findViewById(R.id.btn_start_workout_set_completed);
        mNextExerciseButton = (Button) findViewById(R.id.btn_start_workout_next_exercise);

        WorkoutViewDbHelper helper = new WorkoutViewDbHelper(this);
        SQLiteDatabase database = helper.getReadableDatabase();
        final Cursor cursor = DatabaseMethods.getAllItems(database);



        if (cursor.moveToFirst()) {
            int[] exerciseData = updateExercise(cursor, mExerciseNameTextView, mRepsSetsTextView, mRestTimeTextView, mSetsRemainingTextView);
            setsRemaining = exerciseData[0];
            restTime = exerciseData[1];

        }

        mSetCompletedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setsRemaining = updateSetsRemaining(setsRemaining, mSetsRemainingTextView);
                if (timer != null) {
                    timer.cancel();
                }
                if (setsRemaining == 0) {
                    if (cursor.moveToNext()) {
                        int[] exerciseData = updateExercise(cursor, mExerciseNameTextView, mRepsSetsTextView, mRestTimeTextView, mSetsRemainingTextView);
                        setsRemaining = exerciseData[0];
                        restTime = exerciseData[1];

                    } else {
                        mExerciseNameTextView.setVisibility(View.INVISIBLE);
                        mRepsSetsTextView.setVisibility(View.INVISIBLE);
                        mRestTimeTextView.setVisibility(View.INVISIBLE);
                        mSetsRemainingTextView.setVisibility(View.INVISIBLE);
                        mNextExerciseButton.setVisibility(View.INVISIBLE);
                        mSetCompletedButton.setVisibility(View.INVISIBLE);
                        mTimerTextView.setVisibility(View.INVISIBLE);
                        mWorkoutCompleteTextView.setVisibility(View.VISIBLE);
                    }
                } else {
                    timer = setTimer(restTime, mTimerTextView);
                }
            }
        });


        mNextExerciseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (timer != null) {
                    timer.cancel();
                }
                if (cursor.moveToNext()) {
                    int [] exerciseData = updateExercise(cursor, mExerciseNameTextView, mRepsSetsTextView, mRestTimeTextView, mSetsRemainingTextView);
                    setsRemaining = exerciseData[0];
                    restTime = exerciseData[1];
                } else {
                    mExerciseNameTextView.setVisibility(View.INVISIBLE);
                    mRepsSetsTextView.setVisibility(View.INVISIBLE);
                    mRestTimeTextView.setVisibility(View.INVISIBLE);
                    mSetsRemainingTextView.setVisibility(View.INVISIBLE);
                    mNextExerciseButton.setVisibility(View.INVISIBLE);
                    mSetCompletedButton.setVisibility(View.INVISIBLE);
                    mTimerTextView.setVisibility(View.INVISIBLE);
                    mWorkoutCompleteTextView.setVisibility(View.VISIBLE);
                }
            }
        });
    }


    /* Takes in the rest time in seconds and a TextView. Creates a CountdownTimer with the rest time, and updates
     * the TextView every second to show the timer running down.
     */
    public CountDownTimer setTimer(int time, final TextView textView) {
        return new CountDownTimer(time * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                String timeRemaining = String.valueOf(millisUntilFinished / 1000) + " seconds remaining";
                textView.setText(timeRemaining);
            }

            @Override
            public void onFinish() {
                Toast.makeText(getApplicationContext(), "Move on to the next rep!", Toast.LENGTH_LONG).show();
            }
        }.start();
    }


    /* Sets all TextViews to their corresponding column values from the database. Returns the sets and rest time,
     * because they are used in other functions. All other data is not needed other than to set the TextViews, so it
     * makes sense to encapsulate it all in a function to reduce the use of unnecessary variables.
     * Usage: When we move rows in the database, we have to run this method to update the views of the app to reflect
     *        the new row.
     */
    public int[] updateExercise(Cursor cursor, TextView exerciseNameView, TextView repsSetsView, TextView restTimeView,
                              TextView setsRemainingView) {
        String exerciseName = cursor.getString(cursor.getColumnIndex(WorkoutViewContract.WorkoutViewEntry.COLUMN_EXERCISE_NAME));
        int reps = cursor.getInt(cursor.getColumnIndex(WorkoutViewContract.WorkoutViewEntry.COLUMN_REPS));
        int sets = cursor.getInt(cursor.getColumnIndex(WorkoutViewContract.WorkoutViewEntry.COLUMN_SETS));
        int restTime = cursor.getInt(cursor.getColumnIndex(WorkoutViewContract.WorkoutViewEntry.COLUMN_REST_TIME));
        String repSetString = String.valueOf(sets) + "x" + String.valueOf(reps);
        String restTimeString = String.valueOf(restTime) + " seconds";
        String message = String.valueOf(sets) + " sets remaining";

        exerciseNameView.setText(exerciseName);
        repsSetsView.setText(repSetString);
        restTimeView.setText(restTimeString);
        setsRemainingView.setText(message);

        return new int[]{sets, restTime};
    }


    public int updateSetsRemaining(int currentSetsRemaining, TextView textView) {
        currentSetsRemaining--;
        if (currentSetsRemaining == 0) {
            return 0;
        } else if (currentSetsRemaining == 1) {
            String message = "1 set remaining";
            textView.setText(message);
        } else {
            String message = String.valueOf(currentSetsRemaining) + " sets remaining";
            textView.setText(message);
        }
        return currentSetsRemaining;
    }
}
