package com.example.mohit31.workouttracker.activities;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.mohit31.workouttracker.R;

public class TimerActivity extends AppCompatActivity {
    private TextView mTimerTextView;
    private Button mStartTimerButton;
    private EditText mSetTime;
    private int numberOfTimers;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        mTimerTextView = (TextView) findViewById(R.id.tv_timer);
        mStartTimerButton = (Button) findViewById(R.id.btn_start_timer);
        mSetTime = (EditText) findViewById(R.id.et_timer_set);


        mStartTimerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int timerSeconds = Integer.parseInt(mSetTime.getText().toString());
                new CountDownTimer(timerSeconds * 1000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        mTimerTextView.setText(millisUntilFinished / 1000 + " seconds remaining.");
                    }

                    @Override
                    public void onFinish() {
                        mTimerTextView.setText("Finished");
                        numberOfTimers++;
                        Toast.makeText(getApplicationContext(), "You have clicked the timer " + numberOfTimers + " times",
                                Toast.LENGTH_LONG).show();
                    }
                }.start();
            }
        });


    }
}
