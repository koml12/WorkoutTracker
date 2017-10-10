package com.example.mohit31.workouttracker.adapters;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.mohit31.workouttracker.activities.EditExerciseActivity;
import com.example.mohit31.workouttracker.database.WorkoutViewContract;
import com.example.mohit31.workouttracker.R;

/**
 * Created by mohit31 on 8/30/17.
 */


public class WorkoutViewAdapter extends RecyclerView.Adapter<WorkoutViewAdapter.WorkoutViewViewHolder>{
    private Context mContext;
    private Cursor mExerciseDbCursor;


    public WorkoutViewAdapter(Context context, Cursor cursor) {
        mContext = context;
        mExerciseDbCursor = cursor;
    }


    class WorkoutViewViewHolder extends RecyclerView.ViewHolder {
        TextView mExerciseNameTextView;
        TextView mRepsSetsTextView;
        TextView mRestTimeTextView;
        TextView mWeightTextView;

        WorkoutViewViewHolder(final View itemView) {
            super(itemView);
            mExerciseNameTextView = (TextView) itemView.findViewById(R.id.tv_exercise_name);
            mRepsSetsTextView = (TextView) itemView.findViewById(R.id.tv_exercise_reps_sets);
            mRestTimeTextView = (TextView) itemView.findViewById(R.id.tv_exercise_rest);
            mWeightTextView = (TextView) itemView.findViewById(R.id.tv_exercise_item_weight);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    /* Gets all the data from the TextViews and puts them in a Bundle to send to the next Activity.
                     * NOTE: reps, sets, and rest time are now being stored as Strings, not ints as they usually are.
                     */
                    String exerciseName = mExerciseNameTextView.getText().toString();
                    String[] setsAndReps = mRepsSetsTextView.getText().toString().split("x");
                    String sets = setsAndReps[0];
                    String reps = setsAndReps[1];
                    String[] restTimeArray = mRestTimeTextView.getText().toString().split(" ");
                    mExerciseDbCursor.moveToPosition(getAdapterPosition());
                    String id = mExerciseDbCursor.getString(mExerciseDbCursor.getColumnIndex(WorkoutViewContract.WorkoutViewEntry._ID));
                    int key = mExerciseDbCursor.getInt(mExerciseDbCursor.getColumnIndex(WorkoutViewContract.WorkoutViewEntry.COLUMN_WORKOUT_KEY));
                    String[] weightArray = mWeightTextView.getText().toString().split(" ");

                    Intent intent = new Intent(mContext, EditExerciseActivity.class);
                    intent.putExtra("EXERCISE_NAME", exerciseName);
                    intent.putExtra("REPS", reps);
                    intent.putExtra("SETS", sets);
                    intent.putExtra("REST_TIME", restTimeArray[1]);
                    intent.putExtra("EXERCISE_ID", id);
                    intent.putExtra("WORKOUT_KEY", key);
                    intent.putExtra("WEIGHT", weightArray[2]);

                    mContext.startActivity(intent);

                }
            });
        }
    }


    @Override
    public WorkoutViewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View v = LayoutInflater.from(context).inflate(R.layout.exercise_item, parent, false);
        return new WorkoutViewViewHolder(v);
    }


    @Override
    public int getItemCount() {
        return mExerciseDbCursor.getCount();
    }


    @Override
    public void onBindViewHolder(WorkoutViewViewHolder holder, int position) {
        if (!mExerciseDbCursor.moveToPosition(position)) {
            return;
        }
        setExerciseName(holder.mExerciseNameTextView);
        setRepsAndSets(holder.mRepsSetsTextView);
        setRestTime(holder.mRestTimeTextView);
        setWeight(holder.mWeightTextView);
    }


    /* Sets the text of the parameter TextView to an exercise name from the database.
     */

    /**
     * Sets the text of a TextView to the exercise name for a particular exercise.
     *
     * @param textView                  TextView that displays the name for a particular exercise.
     */
    public void setExerciseName(TextView textView) {
        String exerciseName = mExerciseDbCursor.getString(mExerciseDbCursor.getColumnIndex(WorkoutViewContract.WorkoutViewEntry.COLUMN_EXERCISE_NAME));
        textView.setText(exerciseName);
    }


    /**
     * Sets the text of a TextView to the reps and sets of a particular exercise. Changes the int values for reps and
     * sets to a String of the from "sets x reps"
     *
     * @param textView                  TextView that displays the reps and sets for an exercise.
     */
    public void setRepsAndSets(TextView textView) {
        int reps = mExerciseDbCursor.getInt(mExerciseDbCursor.getColumnIndex(WorkoutViewContract.WorkoutViewEntry.COLUMN_REPS));
        int sets = mExerciseDbCursor.getInt(mExerciseDbCursor.getColumnIndex(WorkoutViewContract.WorkoutViewEntry.COLUMN_SETS));
        String repsAndSets = String.valueOf(sets) + "x" + String.valueOf(reps);
        textView.setText(repsAndSets);
    }


    /**
     * Sets the text of a TextView to the rest time in seconds for a particular exercise.
     *
     * @param textView                  TextView that displays the rest time for an exercise.
     */
    public void setRestTime(TextView textView) {
        int restTime = mExerciseDbCursor.getInt(mExerciseDbCursor.getColumnIndex(WorkoutViewContract.WorkoutViewEntry.COLUMN_REST_TIME));
        String restTimeString = "Rest: " + String.valueOf(restTime) + " seconds";
        textView.setText(restTimeString);
    }


    /**
     * Sets the text of a TextView to the last weight lifted by the user for a particular exercise.
     *
     * @param textView                  TextView that displays the current weight for an exercise.
     */
    public void setWeight(TextView textView) {
        double weight = mExerciseDbCursor.getInt(mExerciseDbCursor.getColumnIndex(WorkoutViewContract.WorkoutViewEntry.COLUMN_WEIGHT));
        String weightString = "Last weight: " + String.valueOf(weight) + " lbs";
        textView.setText(weightString);
    }

    /**
     * Takes in a new cursor, and updates the global adapter cursor to reflect that.
     * Usage: When we update the database, but do not want to reload the entire activity at one time. This method just
     *        refreshes the RecyclerView.
     *
     * @param newCursor                 Cursor that we want to swap the current Cursor for.
     * @return                          A new Cursor object for the adapter.
     */
    public Cursor swapCursor(Cursor newCursor) {
        if (mExerciseDbCursor != null) {
            mExerciseDbCursor.close();
        }
        mExerciseDbCursor = newCursor;
        if (newCursor != null) {
            notifyDataSetChanged();
        }
        return mExerciseDbCursor;
    }
}
