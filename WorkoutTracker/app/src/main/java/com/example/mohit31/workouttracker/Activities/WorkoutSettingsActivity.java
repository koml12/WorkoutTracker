package com.example.mohit31.workouttracker.Activities;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.example.mohit31.workouttracker.Database.WorkoutListContract;
import com.example.mohit31.workouttracker.Database.WorkoutViewContract;
import com.example.mohit31.workouttracker.Database.WorkoutViewDbHelper;
import com.example.mohit31.workouttracker.R;
import com.example.mohit31.workouttracker.Utils.DatabaseMethods;

public class WorkoutSettingsActivity extends AppCompatActivity {

    EditText mWorkoutNameEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_settings);

        mWorkoutNameEditText = (EditText) findViewById(R.id.et_edit_workout_name);

        Button mUpdateWorkoutButton = (Button) findViewById(R.id.btn_update_workout);
        Button mDeleteWorkoutButton = (Button) findViewById(R.id.btn_delete_workout);

        final int key = getIntent().getExtras().getInt("WORKOUT_KEY");

        WorkoutViewDbHelper helper = new WorkoutViewDbHelper(getApplicationContext());
        final SQLiteDatabase mDatabase = helper.getWritableDatabase();
        Cursor cursor = DatabaseMethods.getWorkoutFromKey(mDatabase, key);
        cursor.moveToFirst();
        String workoutName = cursor.getString(cursor.getColumnIndex(WorkoutListContract.WorkoutListEntry.COLUMN_WORKOUT_NAME));

        mWorkoutNameEditText.setText(workoutName);

        mDeleteWorkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDatabase.delete(WorkoutListContract.WorkoutListEntry.TABLE_NAME,
                        WorkoutListContract.WorkoutListEntry.COLUMN_WORKOUT_KEY + " = " + key, null);
                startActivity(new Intent(getApplicationContext(), WorkoutListActivity.class));
            }
        });

        mUpdateWorkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String changedWorkoutName = mWorkoutNameEditText.getText().toString();
                if (changedWorkoutName.trim().isEmpty()) {
                    Snackbar.make(findViewById(R.id.activity_workout_settings), "You must enter a workout name", Snackbar.LENGTH_LONG).show();
                    return;
                }
                ContentValues contentValues = new ContentValues();
                contentValues.put(WorkoutListContract.WorkoutListEntry.COLUMN_WORKOUT_NAME, changedWorkoutName);
                mDatabase.update(WorkoutListContract.WorkoutListEntry.TABLE_NAME, contentValues,
                        WorkoutListContract.WorkoutListEntry.COLUMN_WORKOUT_KEY + " = " + key, null);
                startActivity(new Intent(getApplicationContext(), WorkoutListActivity.class));
            }
        });
    }
}
