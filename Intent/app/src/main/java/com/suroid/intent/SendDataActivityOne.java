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

public class SendDataActivityOne extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "MESSAGE";
    public static final int TEXT_REQUEST = 1;

    @InjectView(R.id.editText_main)
    TextView editText_main;

    @InjectView(R.id.text_message_reply)
    TextView text_message_reply;

    @InjectView(R.id.text_header_reply)
    TextView text_header_reply;

    @InjectView(R.id.button2)
    Button button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_data_one);
        ButterKnife.inject(this);
        //TODO: Do whatever task you want to do on this new activity
    }

    @OnClick({R.id.button2})
    public void clickListener(View view) {
        switch (view.getId()) {
            case R.id.button2:
                if(editText_main.getText()!=null && editText_main.getText().length()>0) {
                    launchSecondActivity();
                }else{
                    Toast.makeText(SendDataActivityOne.this, "Please enter some message", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    public void launchSecondActivity() {
        Intent intent = new Intent(this, SendDataActivityTwo.class);
        String message = editText_main.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivityForResult(intent, TEXT_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode,
                                 int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TEXT_REQUEST) {
            if (resultCode == RESULT_OK) {
                String reply =
                        data.getStringExtra(SendDataActivityTwo.EXTRA_REPLY);
                text_header_reply.setVisibility(View.VISIBLE);
                text_message_reply.setText(reply);
                text_message_reply.setVisibility(View.VISIBLE);
                editText_main.setText("");
            }
        }
    }
}
