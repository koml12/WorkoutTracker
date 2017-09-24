package com.example.mohit31.workouttracker.Activities;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.mohit31.workouttracker.Database.WorkoutListContract;
import com.example.mohit31.workouttracker.Database.WorkoutViewDbHelper;
import com.example.mohit31.workouttracker.R;
import com.example.mohit31.workouttracker.Utils.DatabaseMethods;

public class AddWorkoutActivity extends AppCompatActivity {

    private EditText mWorkoutNameEditText;
    private Button mAddWorkoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_workout);

        WorkoutViewDbHelper dbHelper = new WorkoutViewDbHelper(getApplicationContext());
        final SQLiteDatabase database = dbHelper.getWritableDatabase();
        final Cursor cursor = DatabaseMethods.getWorkoutItems(database);



        mWorkoutNameEditText = (EditText) findViewById(R.id.et_add_workout);
        mAddWorkoutButton = (Button) findViewById(R.id.btn_add_workout);

        mAddWorkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mWorkoutNameEditText.getText().toString().trim().isEmpty()) {
                    Snackbar.make(findViewById(R.id.activity_add_workout), "You must enter a workout name.", Snackbar.LENGTH_LONG)
                            .show();
                    return;
                }

                String workoutName = mWorkoutNameEditText.getText().toString();
                int id = View.generateViewId();
                int count = cursor.getCount();

                ContentValues contentValues = new ContentValues();
                contentValues.put(WorkoutListContract.WorkoutListEntry.COLUMN_WORKOUT_NAME, workoutName);
                contentValues.put(WorkoutListContract.WorkoutListEntry.COLUMN_WORKOUT_KEY, count + 1);

                database.insert(WorkoutListContract.WorkoutListEntry.TABLE_NAME, null, contentValues);

                startActivity(new Intent(getApplicationContext(), WorkoutListActivity.class));

            }
        });
    }
}
