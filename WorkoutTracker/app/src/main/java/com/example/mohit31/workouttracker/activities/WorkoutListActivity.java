package com.example.mohit31.workouttracker.activities;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import com.example.mohit31.workouttracker.adapters.WorkoutListAdapter;
import com.example.mohit31.workouttracker.database.WorkoutViewDbHelper;
import com.example.mohit31.workouttracker.R;
import com.example.mohit31.workouttracker.utils.DatabaseMethods;

public class WorkoutListActivity extends AppCompatActivity {

    // Used for updating data, so their scope has to be class-level so Activity methods can access them.
    private SQLiteDatabase mDatabase;
    private Cursor mCursor;
    private WorkoutListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        WorkoutViewDbHelper mDbHelper = new WorkoutViewDbHelper(getApplicationContext());
        mDatabase = mDbHelper.getWritableDatabase();
        mCursor = DatabaseMethods.getWorkoutItems(mDatabase);

        RecyclerView workoutRecyclerView = (RecyclerView) findViewById(R.id.rv_workout_list);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        workoutRecyclerView.setLayoutManager(mLayoutManager);

        DividerItemDecoration mDividerDecoration = new DividerItemDecoration(workoutRecyclerView.getContext(),
                mLayoutManager.getOrientation());
        workoutRecyclerView.addItemDecoration(mDividerDecoration);

        mAdapter = new WorkoutListAdapter(getApplicationContext(), mCursor);
        workoutRecyclerView.setAdapter(mAdapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AddWorkoutActivity.class));
            }
        });
    }

    /**
     * Gets an updated Cursor for the database, and notifies the adapter that the data set has changed, so the RecyclerView can update.
     *
     * Usage: occurs every time a workout is added to the database
     */
    @Override
    protected void onResume() {
        super.onResume();
        mCursor = mAdapter.swapCursor(DatabaseMethods.getWorkoutItems(mDatabase));
    }
}
