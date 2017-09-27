package com.example.mohit31.workouttracker.activities;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.example.mohit31.workouttracker.adapters.WorkoutViewAdapter;
import com.example.mohit31.workouttracker.database.WorkoutViewDbHelper;
import com.example.mohit31.workouttracker.R;
import com.example.mohit31.workouttracker.utils.DatabaseMethods;

public class WorkoutViewActivity extends AppCompatActivity {

    private int key;
    private SQLiteDatabase mDatabase;
    private Cursor mCursor;
    private WorkoutViewAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button mStartWorkoutButton = (Button) findViewById(R.id.btn_start_workout);
        Button mToAddExerciseButton = (Button) findViewById(R.id.btn_to_add_exercise);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        key = extras.getInt("WORKOUT_KEY");

        WorkoutViewDbHelper helper = new WorkoutViewDbHelper(getApplicationContext());
        mDatabase = helper.getWritableDatabase();
        mCursor = DatabaseMethods.getExercisesForWorkout(mDatabase, key);

        RecyclerView exerciseRecyclerView = (RecyclerView) findViewById(R.id.rv_workout_view);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        exerciseRecyclerView.setLayoutManager(mLayoutManager);

        DividerItemDecoration mDividerDecoration = new DividerItemDecoration(exerciseRecyclerView.getContext(),
                mLayoutManager.getOrientation());
        exerciseRecyclerView.addItemDecoration(mDividerDecoration);

        mAdapter = new WorkoutViewAdapter(getApplicationContext(), mCursor);
        exerciseRecyclerView.setAdapter(mAdapter);

        mStartWorkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mAdapter.getItemCount() == 0) {
                    Toast.makeText(getApplicationContext(), "No exercises added yet!", Toast.LENGTH_LONG).show();
                    return;
                }
                Intent intent = new Intent(getApplicationContext(), StartWorkoutActivity.class);
                intent.putExtra("WORKOUT_KEY", key);
                startActivity(intent);
            }
        });


        mToAddExerciseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddExerciseActivity.class);
                intent.putExtra("WORKOUT_KEY", key);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = new MenuInflater(getApplicationContext());
        inflater.inflate(R.menu.workout_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_workout_settings:
                Intent intent = new Intent(getApplicationContext(), WorkoutSettingsActivity.class);
                intent.putExtra("WORKOUT_KEY", key);
                startActivity(intent);
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        mCursor = mAdapter.swapCursor(DatabaseMethods.getExercisesForWorkout(mDatabase, key));
    }
}
