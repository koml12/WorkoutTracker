package com.example.mohit31.workouttracker.Activities;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import com.example.mohit31.workouttracker.Adapters.WorkoutListAdapter;
import com.example.mohit31.workouttracker.Database.WorkoutViewDbHelper;
import com.example.mohit31.workouttracker.R;
import com.example.mohit31.workouttracker.Utils.DatabaseMethods;

public class WorkoutListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        
        WorkoutViewDbHelper mDbHelper = new WorkoutViewDbHelper(getApplicationContext());
        SQLiteDatabase mDatabase = mDbHelper.getWritableDatabase();

        Cursor mCursor = DatabaseMethods.getWorkoutItems(mDatabase);



        RecyclerView workoutRecyclerView = (RecyclerView) findViewById(R.id.rv_workout_list);
        workoutRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        WorkoutListAdapter mAdapter = new WorkoutListAdapter(getApplicationContext(), mCursor);
        workoutRecyclerView.setAdapter(mAdapter);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AddWorkoutActivity.class));
            }
        });
    }

}
