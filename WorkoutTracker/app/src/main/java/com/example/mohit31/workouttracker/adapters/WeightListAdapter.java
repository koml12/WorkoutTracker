package com.example.mohit31.workouttracker.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.TextView;
import com.example.mohit31.workouttracker.R;
import com.example.mohit31.workouttracker.database.WeightContract;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by mohit31 on 10/9/17.
 */
public class WeightListAdapter extends RecyclerView.Adapter<WeightListAdapter.WeightListViewHolder> {

    private Context mContext;
    private Cursor mCursor;
    private String mExerciseKey;

    public WeightListAdapter(Context context, Cursor cursor, String exerciseKey) {
        mContext = context;
        mCursor = cursor;
        mExerciseKey = exerciseKey;
    }

    class WeightListViewHolder extends RecyclerView.ViewHolder {
        TextView mDateTextView;
        TextView mWeightTextView;

        public WeightListViewHolder(View itemView) {
            super(itemView);
            mDateTextView = (TextView) itemView.findViewById(R.id.tv_date_item);
            mWeightTextView = (TextView) itemView.findViewById(R.id.tv_weight_item);
        }
    }

    @Override
    public WeightListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View v = LayoutInflater.from(context).inflate(R.layout.weight_item, parent, false);
        return new WeightListViewHolder(v);
    }

    @Override
    public void onBindViewHolder(WeightListViewHolder holder, int position) {
        if(!mCursor.moveToPosition(position)) {
            return;
        }
        long unixTimeStamp = mCursor.getLong(mCursor.getColumnIndex(WeightContract.WeightEntry.COLUMN_DATE));
        double weight = mCursor.getDouble(mCursor.getColumnIndex(WeightContract.WeightEntry.COLUMN_WEIGHT));
        setDateText(holder.mDateTextView, unixTimeStamp);
        setWeightText(holder.mWeightTextView, weight);
    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    private void setDateText(TextView textView, long unixStamp) {
        String datePattern = "MM/dd/yyyy";
        SimpleDateFormat dateFormat = new SimpleDateFormat(datePattern);
        String date = dateFormat.format(new Date(unixStamp));
        textView.setText(date);
    }

    private void setWeightText(TextView textView, double weight) {
        String weightString = String.valueOf(weight) + " lbs";
        textView.setText(weightString);
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
