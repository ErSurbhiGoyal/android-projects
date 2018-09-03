package com.suroid.intent;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import butterknife.InjectView;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private static final int RESULT_LOAD_IMAGE = 1;

    @InjectView(R.id.btnExplicit)
    Button explicitIntent;

    @InjectView(R.id.btnImplicit)
    Button implicitIntent;

    @InjectView(R.id.imageView)
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //We need not to call this. Butterknife library will automatically detect this function on button click
    //You can also use onClickListener if you want.
    @OnClick(R.id.btnExplicit)
    public void clickExplicit(){
        Toast.makeText(this,"This will land you on new activity",Toast.LENGTH_SHORT).show();
        //This is the way of initiating a new activity from one activity.
        Intent intent  = new Intent(this, SecondActivity.class);
        startActivity(intent);
        //If you don't want the user to open this activity again you can kill or destroy this activity using finish function.
        finish();
    }

    @OnClick(R.id.btnImplicit)
    public void clickImplicit(){
        Toast.makeText(this,"This will open Gallery for you",Toast.LENGTH_SHORT).show();
        //This is the way of initiating a new activity from one activity.
        Intent i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, RESULT_LOAD_IMAGE);
        //If you don't want the user to open this activity again you can kill or destroy this activity using finish function.
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case RESULT_LOAD_IMAGE:
                if (resultCode == RESULT_OK) {
                    Uri uri = data.getData();
                    String[] projection = {MediaStore.Images.Media.DATA};

                    Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(projection[0]);
                    String filepath = cursor.getString(columnIndex);
                    cursor.close();

                    Bitmap bitmap = BitmapFactory.decodeFile(filepath);
                    Drawable drawable = new BitmapDrawable(bitmap);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        imageView.setBackground(drawable);
                    }else{
                        imageView.setImageBitmap(bitmap);
                    }
                }
                break;
            default:
                break;
        }
    }
}
