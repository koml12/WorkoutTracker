package com.example.mohit31.workouttracker.activities;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.example.mohit31.workouttracker.database.WorkoutViewContract;
import com.example.mohit31.workouttracker.database.WorkoutViewDbHelper;
import com.example.mohit31.workouttracker.R;

public class EditExerciseActivity extends AppCompatActivity {
    private EditText mExerciseNameEditText;
    private EditText mRepsEditText;
    private EditText mSetsEditText;
    private EditText mRestTimeEditText;
    private EditText mWeightEditText;
    private EditText mNotesEditText;
    private Button mUpdateExerciseButton;
    private Button mDeleteExerciseButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_exercise);

        mExerciseNameEditText = (EditText) findViewById(R.id.et_edit_exercise_name);
        mRepsEditText = (EditText) findViewById(R.id.et_edit_exercise_reps);
        mSetsEditText = (EditText) findViewById(R.id.et_edit_exercise_sets);
        mRestTimeEditText = (EditText) findViewById(R.id.et_edit_exercise_rest_time);
        mWeightEditText = (EditText) findViewById(R.id.et_edit_exercise_weight);
        mNotesEditText = (EditText) findViewById(R.id.et_edit_exercise_notes);
        mUpdateExerciseButton = (Button) findViewById(R.id.btn_update_exercise);
        mDeleteExerciseButton = (Button) findViewById(R.id.btn_delete_exercise);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        String exerciseName = extras.getString("EXERCISE_NAME");
        String reps = extras.getString("REPS");
        String sets = extras.getString("SETS");
        String restTime = extras.getString("REST_TIME");
        String weight = extras.getString("WEIGHT");
        String notes = extras.getString("NOTES");
        final int key = extras.getInt("WORKOUT_KEY");
        final String id = extras.getString("EXERCISE_ID");

        mExerciseNameEditText.setText(exerciseName);
        mRepsEditText.setText(reps);
        mSetsEditText.setText(sets);
        mRestTimeEditText.setText(restTime);
        mWeightEditText.setText(weight);
        mNotesEditText.setText(notes);

        WorkoutViewDbHelper helper = new WorkoutViewDbHelper(getApplicationContext());
        final SQLiteDatabase database = helper.getWritableDatabase();

        mUpdateExerciseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String changedName = mExerciseNameEditText.getText().toString();
                String changedRepsString = mRepsEditText.getText().toString();
                String changedSetsString = mSetsEditText.getText().toString();
                String changedRestTimeString = mRestTimeEditText.getText().toString();
                String changedWeightString = mWeightEditText.getText().toString();
                String changedNotesString = mNotesEditText.getText().toString();


                if (changedName.trim().isEmpty() || changedRepsString.trim().isEmpty() ||
                        changedSetsString.trim().isEmpty() || changedRestTimeString.trim().isEmpty()) {
                    Snackbar.make(findViewById(R.id.activity_edit_exercise), "All fields must be filled",
                            Snackbar.LENGTH_LONG).show();
                    return;
                }

                int changedReps = Integer.parseInt(changedRepsString);
                int changedSets = Integer.parseInt(changedSetsString);
                int changedRestTime = Integer.parseInt(changedRestTimeString);
                double changedWeight = Double.parseDouble(changedWeightString);

                ContentValues contentValues = new ContentValues();
                contentValues.put(WorkoutViewContract.WorkoutViewEntry.COLUMN_EXERCISE_NAME, changedName);
                contentValues.put(WorkoutViewContract.WorkoutViewEntry.COLUMN_REPS, changedReps);
                contentValues.put(WorkoutViewContract.WorkoutViewEntry.COLUMN_SETS, changedSets);
                contentValues.put(WorkoutViewContract.WorkoutViewEntry.COLUMN_REST_TIME, changedRestTime);
                contentValues.put(WorkoutViewContract.WorkoutViewEntry.COLUMN_WEIGHT, changedWeight);
                contentValues.put(WorkoutViewContract.WorkoutViewEntry.COLUMN_NOTES, changedNotesString);

                database.update(WorkoutViewContract.WorkoutViewEntry.TABLE_NAME, contentValues, "_id = " + String.valueOf(id), null);

                Intent intent = new Intent(getApplicationContext(), WorkoutViewActivity.class);
                intent.putExtra("WORKOUT_KEY", key);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });

        mDeleteExerciseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database.delete(WorkoutViewContract.WorkoutViewEntry.TABLE_NAME, "_id = " + String.valueOf(id), null);

                Intent intent = new Intent(getApplicationContext(), WorkoutViewActivity.class);
                intent.putExtra("WORKOUT_KEY", key);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });
    }
}
