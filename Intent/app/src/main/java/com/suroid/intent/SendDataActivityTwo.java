package com.suroid.intent;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class SendDataActivityTwo extends AppCompatActivity {

    public static final String EXTRA_REPLY ="REPLY";

    @InjectView(R.id.text_message)
    TextView text_message;

    @InjectView(R.id.editText_second)
    TextView editText_second;

    @InjectView(R.id.button_second)
    Button button_second;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_data_two);
        ButterKnife.inject(this);
        //TODO: Do whatever task you want to do on this new activity
        if (getIntent() != null) {
            Intent intent = getIntent();
            String message = intent.getStringExtra(SendDataActivityOne.EXTRA_MESSAGE);
            text_message.setText(message);
        }
    }

    @OnClick({R.id.button_second})
    public void clickListener(View view) {
        switch (view.getId()) {
            case R.id.button_second:
                if(editText_second.getText()!=null && editText_second.getText().length()>0) {
                    returnReply();
                }else{
                    Toast.makeText(SendDataActivityTwo.this, "Please enter some reply", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    public void returnReply() {
        String reply = editText_second.getText().toString();
        Intent replyIntent = new Intent();
        replyIntent.putExtra(EXTRA_REPLY, reply);
        setResult(RESULT_OK, replyIntent);
        finish();
    }
}
