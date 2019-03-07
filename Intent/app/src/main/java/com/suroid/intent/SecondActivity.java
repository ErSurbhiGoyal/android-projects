package com.suroid.intent;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import butterknife.ButterKnife;

import butterknife.InjectView;

public class SecondActivity extends AppCompatActivity {

    @InjectView(R.id.text_message)
    TextView text_message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        ButterKnife.inject(this);
        //TODO: Do whatever task you want to do on this new activity
        if (getIntent() != null) {
            Intent intent = getIntent();
            String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
            text_message.setText(message);
        }
    }
}
