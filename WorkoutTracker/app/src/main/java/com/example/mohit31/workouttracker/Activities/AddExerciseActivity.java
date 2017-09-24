package com.example.mohit31.workouttracker.Activities;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.example.mohit31.workouttracker.Database.WorkoutViewContract;
import com.example.mohit31.workouttracker.Database.WorkoutViewDbHelper;
import com.example.mohit31.workouttracker.R;

public class AddExerciseActivity extends AppCompatActivity {

    private EditText mExerciseNameEditText;
    private EditText mRepsEditText;
    private EditText mSetsEditText;
    private EditText mRestTimeEditText;
    private Button mAddExerciseButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_exercise);

        mExerciseNameEditText = (EditText) findViewById(R.id.et_exercise_name);
        mRepsEditText = (EditText) findViewById(R.id.et_reps);
        mSetsEditText = (EditText) findViewById(R.id.et_sets);
        mRestTimeEditText = (EditText) findViewById(R.id.et_rest_time);
        mAddExerciseButton = (Button) findViewById(R.id.btn_add_exercise);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        final int key = extras.getInt("WORKOUT_KEY");

        WorkoutViewDbHelper dbHelper = new WorkoutViewDbHelper(this);
        final SQLiteDatabase database = dbHelper.getWritableDatabase();

        mAddExerciseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String exerciseName = mExerciseNameEditText.getText().toString();

                String repsString = mRepsEditText.getText().toString();
                String setsString = mSetsEditText.getText().toString();
                String restTimeString = mRestTimeEditText.getText().toString();

                // Only check for empty string, since getText() will never return null.
                if (exerciseName.trim().isEmpty() || repsString.trim().isEmpty() || setsString.trim().isEmpty() || restTimeString.trim().isEmpty()) {
                    Snackbar.make(findViewById(R.id.activity_add_exercise), "All fields must be filled", Snackbar.LENGTH_LONG)
                            .show();
                    return;
                }

                int repsInt = Integer.parseInt(repsString);
                int setsInt = Integer.parseInt(setsString);
                int restTimeInt = Integer.parseInt(restTimeString);

                addExercise(exerciseName, repsInt, setsInt, restTimeInt, key, database);

                Intent intent = new Intent(getApplicationContext(), WorkoutViewActivity.class);
                intent.putExtra("WORKOUT_KEY", key);
                startActivity(intent);

            }
        });

    }


    public long addExercise(String name, int reps, int sets, int rest, int key, SQLiteDatabase database) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(WorkoutViewContract.WorkoutViewEntry.COLUMN_EXERCISE_NAME, name);
        contentValues.put(WorkoutViewContract.WorkoutViewEntry.COLUMN_REPS, reps);
        contentValues.put(WorkoutViewContract.WorkoutViewEntry.COLUMN_SETS, sets);
        contentValues.put(WorkoutViewContract.WorkoutViewEntry.COLUMN_REST_TIME, rest);
        contentValues.put(WorkoutViewContract.WorkoutViewEntry.COLUMN_WORKOUT_KEY, key);
        contentValues.put(WorkoutViewContract.WorkoutViewEntry.COLUMN_WEIGHT, 0);

        return database.insert(WorkoutViewContract.WorkoutViewEntry.TABLE_NAME, null, contentValues);
    }
}
