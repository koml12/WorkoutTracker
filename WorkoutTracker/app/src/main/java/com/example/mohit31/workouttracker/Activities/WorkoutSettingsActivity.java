package com.example.mohit31.workouttracker.Activities;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import com.example.mohit31.workouttracker.Database.WorkoutListContract;
import com.example.mohit31.workouttracker.Database.WorkoutViewContract;
import com.example.mohit31.workouttracker.Database.WorkoutViewDbHelper;
import com.example.mohit31.workouttracker.R;

public class WorkoutSettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_settings);

        Button mDeleteWorkoutButton = (Button) findViewById(R.id.btn_delete_workout);

        final int key = getIntent().getExtras().getInt("WORKOUT_KEY");

        WorkoutViewDbHelper helper = new WorkoutViewDbHelper(getApplicationContext());
        final SQLiteDatabase mDatabase = helper.getWritableDatabase();

        mDeleteWorkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDatabase.delete(WorkoutListContract.WorkoutListEntry.TABLE_NAME,
                        WorkoutListContract.WorkoutListEntry.COLUMN_WORKOUT_KEY + " = " + key, null);
                mDatabase.delete(WorkoutViewContract.WorkoutViewEntry.TABLE_NAME,
                        WorkoutViewContract.WorkoutViewEntry.COLUMN_WORKOUT_KEY + " = " + key, null);
                startActivity(new Intent(getApplicationContext(), WorkoutListActivity.class));
            }
        });

    }
}
