package com.example.mohit31.workouttracker.Adapters;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.mohit31.workouttracker.Database.WorkoutViewContract;
import com.example.mohit31.workouttracker.R;

/**
 * Created by mohit31 on 8/30/17.
 */


// TODO: Click listener for list items; will take the user to the edit exercise activity,

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

        public WorkoutViewViewHolder(View itemView) {
            super(itemView);
            mExerciseNameTextView = (TextView) itemView.findViewById(R.id.tv_exercise_name);
            mRepsSetsTextView = (TextView) itemView.findViewById(R.id.tv_exercise_reps_sets);
            mRestTimeTextView = (TextView) itemView.findViewById(R.id.tv_exercise_rest);
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
    }


    /* Sets the text of the parameter TextView to an exercise name from the database.
     */
    public void setExerciseName(TextView textView) {
        String exerciseName = mExerciseDbCursor.getString(mExerciseDbCursor.getColumnIndex(WorkoutViewContract.WorkoutViewEntry.COLUMN_EXERCISE_NAME));
        textView.setText(exerciseName);
    }


    /* Sets the text of a TextView to the reps and sets of an exercise.
     *  Also changes the int values of reps and sets from the database to a String "sets x reps".
     */
    public void setRepsAndSets(TextView textView) {
        int reps = mExerciseDbCursor.getInt(mExerciseDbCursor.getColumnIndex(WorkoutViewContract.WorkoutViewEntry.COLUMN_REPS));
        int sets = mExerciseDbCursor.getInt(mExerciseDbCursor.getColumnIndex(WorkoutViewContract.WorkoutViewEntry.COLUMN_SETS));
        String repsAndSets = String.valueOf(sets) + "x" + String.valueOf(reps);
        textView.setText(repsAndSets);
    }


    /* Sets the text of a TextView to the rest time in seconds.
     */
    public void setRestTime(TextView textView) {
        int restTime = mExerciseDbCursor.getInt(mExerciseDbCursor.getColumnIndex(WorkoutViewContract.WorkoutViewEntry.COLUMN_REST_TIME));
        String restTimeString = String.valueOf(restTime) + " seconds";
        textView.setText(restTimeString);
    }


    /* Takes in a new cursor, and updates the global adapter cursor to reflect that.
     * Usage: When we update the database, but do not want to reload the entire activity at one time. This method jsut
     *        refreshes the RecyclerView
     */
    public void swapCursor(Cursor newCursor) {
        if (mExerciseDbCursor != null) {
            mExerciseDbCursor.close();
        }
        mExerciseDbCursor = newCursor;
        if (newCursor != null) {
            notifyDataSetChanged();
        }
    }
}
