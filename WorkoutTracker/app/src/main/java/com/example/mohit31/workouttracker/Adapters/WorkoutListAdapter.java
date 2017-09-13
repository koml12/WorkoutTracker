package com.example.mohit31.workouttracker.Adapters;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.example.mohit31.workouttracker.Activities.WorkoutViewActivity;
import com.example.mohit31.workouttracker.Database.WorkoutListContract;
import com.example.mohit31.workouttracker.R;
import org.w3c.dom.Text;

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

        public WorkoutListViewHolder(final View itemView) {
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


    public void setWorkoutName(TextView textView) {
        String workoutName = mCursor.getString(mCursor.getColumnIndex(WorkoutListContract.WorkoutListEntry.COLUMN_WORKOUT_NAME));
        textView.setText(workoutName);
    }



}
