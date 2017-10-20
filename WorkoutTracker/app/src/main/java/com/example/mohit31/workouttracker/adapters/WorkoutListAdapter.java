package com.example.mohit31.workouttracker.adapters;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.mohit31.workouttracker.activities.WorkoutViewActivity;
import com.example.mohit31.workouttracker.database.WorkoutListContract;
import com.example.mohit31.workouttracker.R;

/**
 * Created by mohit31 on 9/11/17.
 */
public class WorkoutListAdapter extends RecyclerView.Adapter<WorkoutListAdapter.WorkoutListViewHolder> {

    private Context mContext;
    private Cursor mCursor;

    public WorkoutListAdapter(Context context, Cursor cursor) {
        mContext = context;
        mCursor = cursor;
    }


    class WorkoutListViewHolder extends RecyclerView.ViewHolder {
        TextView mWorkoutNameTextView;

        WorkoutListViewHolder(final View itemView) {
            super(itemView);
            mWorkoutNameTextView = (TextView) itemView.findViewById(R.id.tv_workout_name);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!mCursor.moveToPosition(getAdapterPosition()))
                        return;
                    int key = mCursor.getInt(mCursor.getColumnIndex(WorkoutListContract.WorkoutListEntry.COLUMN_WORKOUT_KEY));

                    Intent intent = new Intent(view.getContext(), WorkoutViewActivity.class);
                    intent.putExtra("WORKOUT_KEY", key);

                    mContext.startActivity(intent);
                }
            });
        }
    }


    @Override
    public WorkoutListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View v = LayoutInflater.from(context).inflate(R.layout.workout_item, parent, false);
        return new WorkoutListViewHolder(v);
    }


    @Override
    public void onBindViewHolder(WorkoutListViewHolder holder, int position) {
        if (!mCursor.moveToPosition(position)) {
            return;
        }
        setWorkoutName(holder.mWorkoutNameTextView);
    }


    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }


    private void setWorkoutName(TextView textView) {
        String workoutName = mCursor.getString(mCursor.getColumnIndex(WorkoutListContract.WorkoutListEntry.COLUMN_WORKOUT_NAME));
        textView.setText(workoutName);
    }


    public Cursor swapCursor(Cursor newCursor) {
        if (mCursor != null) {
            mCursor.close();
        }
        mCursor = newCursor;
        if (newCursor != null) {
            notifyDataSetChanged();
        }
        return mCursor;
    }



}
