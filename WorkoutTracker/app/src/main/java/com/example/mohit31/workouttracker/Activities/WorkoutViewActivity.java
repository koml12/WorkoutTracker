package com.example.mohit31.workouttracker.Activities;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import com.example.mohit31.workouttracker.Adapters.WorkoutViewAdapter;
import com.example.mohit31.workouttracker.Database.WorkoutViewDbHelper;
import com.example.mohit31.workouttracker.R;
import com.example.mohit31.workouttracker.Utils.DatabaseMethods;

public class WorkoutViewActivity extends AppCompatActivity {
    private Button mStartWorkoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mStartWorkoutButton = (Button) findViewById(R.id.btn_start_workout);

        WorkoutViewDbHelper helper = new WorkoutViewDbHelper(getApplicationContext());
        SQLiteDatabase mDatabase = helper.getWritableDatabase();
        Cursor cursor = DatabaseMethods.getAllItems(mDatabase);

        RecyclerView exerciseRecyclerView = (RecyclerView) findViewById(R.id.rv_workout_view);
        exerciseRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        WorkoutViewAdapter mAdapter = new WorkoutViewAdapter(getApplicationContext(), cursor);
        exerciseRecyclerView.setAdapter(mAdapter);


        mStartWorkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), StartWorkoutActivity.class);
                startActivity(intent);
            }
        });


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddExerciseActivity.class);
                startActivity(intent);
            }
        });
    }




}
