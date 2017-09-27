package com.example.mohit31.workouttracker.activities;

import android.app.AlertDialog;
import android.app.NotificationManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.example.mohit31.workouttracker.database.WorkoutViewContract;
import com.example.mohit31.workouttracker.database.WorkoutViewDbHelper;
import com.example.mohit31.workouttracker.R;
import com.example.mohit31.workouttracker.utils.DatabaseMethods;

public class StartWorkoutActivity extends AppCompatActivity {

    private TextView mExerciseNameTextView;
    private TextView mRepsSetsTextView;
    private TextView mRestTimeTextView;
    private TextView mTimerTextView;
    private TextView mSetsRemainingTextView;
    private TextView mWorkoutCompleteTextView;
    private Button mSetCompletedButton;
    private Button mNextExerciseButton;
    private Button mFinishWorkoutButton;

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
        mFinishWorkoutButton = (Button) findViewById(R.id.btn_finish_workout);

        final int key = getIntent().getExtras().getInt("WORKOUT_KEY");

        WorkoutViewDbHelper helper = new WorkoutViewDbHelper(this);
        final SQLiteDatabase database = helper.getReadableDatabase();
        final Cursor cursor = DatabaseMethods.getExercisesForWorkout(database, key);

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
                    mTimerTextView.setText(R.string.blank_timer);
                }
                if (setsRemaining == 0) {
                    showLogWeightDialog(StartWorkoutActivity.this, cursor, database);
                    /* Code is duplicated in the below onClick method, but there are so many moving parts to it that
                     * it is easier to just copy the code over twice.
                     */
                    // noinspection Duplicates
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
                        mFinishWorkoutButton.setVisibility(View.VISIBLE);
                    }
                } else {
                    timer = setTimer(restTime, mTimerTextView, getApplicationContext());
                }
            }
        });


        mNextExerciseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (timer != null) {
                    timer.cancel();
                    mTimerTextView.setText(R.string.blank_timer);
                }

                showLogWeightDialog(StartWorkoutActivity.this, cursor, database);

                /* Code is duplicated in the above onClick method, but there are so many moving parts to it that
                 * it is easier to just copy the code over twice.
                 */
                // noinspection Duplicates
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
                    mFinishWorkoutButton.setVisibility(View.VISIBLE);
                }
            }
        });

        mFinishWorkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), WorkoutViewActivity.class);
                intent.putExtra("WORKOUT_KEY", key);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });

    }


    /**
     * Takes in the rest time in seconds and a TextView. Creates a CountdownTimer with the rest time, and updates
     * the TextView every second to show the timer running down.
     *
     * @param time              The time that the timer should count down from.
     * @param textView          The TextView that shows the status of the timer.
     * @param context           The Context of the application, needed for constructors.
     * @return                  A CountDownTimer object that the application can manipulate and get data from.
     */
    private CountDownTimer setTimer(int time, final TextView textView, final Context context) {
        return new CountDownTimer(time * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int time = (int) millisUntilFinished / 1000;
                int minutes = time / 60;
                int seconds = time % 60;
                String minuteString;
                String secondString;
                if (minutes < 10) {
                    minuteString = "0" + String.valueOf(minutes);
                } else {
                    minuteString = String.valueOf(minutes);
                }

                if (seconds < 10) {
                    secondString = "0" + String.valueOf(seconds);
                } else {
                    secondString = String.valueOf(seconds);
                }
                String timeRemaining = minuteString + ":" + secondString;
                textView.setText(timeRemaining);
            }

            @Override
            public void onFinish() {
                NotificationCompat.Builder mBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(context)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("Workout Tracker")
                        .setContentText("Time for the next set!");
                int notificationID = 001;
                NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                notificationManager.notify(notificationID, mBuilder.build());
            }
        }.start();
    }


    /**
     * Sets all exercise info TextViews to the correct information. Used when we move rows (aka move to the next exercise).
     *
     * @param cursor                The cursor that gets the data from the database, so we can update the rest of the data.
     * @param exerciseNameView      The TextView with the exercise name information.
     * @param repsSetsView          The TextView with the number of reps and sets.
     * @param restTimeView          The TextView with the rest time for an exercise, in seconds.
     * @param setsRemainingView     The TextView with the sets remaining for the exercise, starts at just the number of sets.
     * @return                      An int array, with the sets and rest time for the exercise, so we can use it later.
     */
    public int[] updateExercise(Cursor cursor, TextView exerciseNameView, TextView repsSetsView, TextView restTimeView,
                              TextView setsRemainingView) {
        String exerciseName = cursor.getString(cursor.getColumnIndex(WorkoutViewContract.WorkoutViewEntry.COLUMN_EXERCISE_NAME));
        int reps = cursor.getInt(cursor.getColumnIndex(WorkoutViewContract.WorkoutViewEntry.COLUMN_REPS));
        int sets = cursor.getInt(cursor.getColumnIndex(WorkoutViewContract.WorkoutViewEntry.COLUMN_SETS));
        int restTime = cursor.getInt(cursor.getColumnIndex(WorkoutViewContract.WorkoutViewEntry.COLUMN_REST_TIME));
        String repSetString = String.valueOf(sets) + "x" + String.valueOf(reps);
        String restTimeString = "Rest: " + String.valueOf(restTime) + " seconds";
        String message = String.valueOf(sets) + " sets remaining";

        exerciseNameView.setText(exerciseName);
        repsSetsView.setText(repSetString);
        restTimeView.setText(restTimeString);
        setsRemainingView.setText(message);

        return new int[]{sets, restTime};
    }


    /**
     * Decrements the current remaining set count by 1, and updates a TextView to reflect this.
     *
     * @param currentSetsRemaining      The current number of sets remaining.
     * @param textView                  The TextView that shows the number of sets remaining
     * @return                          An int representing the new number of sets remaining (currentSetsRemaining - 1)
     */
    private int updateSetsRemaining(int currentSetsRemaining, TextView textView) {
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


    /**
     * Shows a dialog to the user asking for them to log the weight they just lifted, and updates the database.
     *
     * @param context               Activity context.
     * @param cursor                Cursor to index database and find ID to update with.
     * @param database              Database to apply changes to.
     */
    public void showLogWeightDialog(Context context, final Cursor cursor, final SQLiteDatabase database) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Log Weight");

        final EditText logWeightInput = new EditText(context);
        logWeightInput.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        builder.setView(logWeightInput);

        builder.setPositiveButton("LOG", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                double weight =  Double.parseDouble(logWeightInput.getText().toString());

                // I have no idea why the cursor needs to move back one and then forward one, but the app breaks without it.
                cursor.moveToPrevious();

                String id = cursor.getString(cursor.getColumnIndex(WorkoutViewContract.WorkoutViewEntry._ID));
                ContentValues contentValues = new ContentValues();
                contentValues.put(WorkoutViewContract.WorkoutViewEntry.COLUMN_WEIGHT, weight);
                database.update(WorkoutViewContract.WorkoutViewEntry.TABLE_NAME, contentValues, "_id = " + id, null);

                cursor.moveToNext();
            }
        });

        builder.setNegativeButton("NO THANKS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        builder.show();
    }
}
