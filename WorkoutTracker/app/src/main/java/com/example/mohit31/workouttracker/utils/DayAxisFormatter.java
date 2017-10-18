package com.example.mohit31.workouttracker.utils;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by mohit31 on 10/15/17.
 */
public class DayAxisFormatter implements IAxisValueFormatter {

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd", Locale.getDefault());
        Date d = new Date((long) value);
        String date = dateFormat.format(d);
        return date;
    }
}
