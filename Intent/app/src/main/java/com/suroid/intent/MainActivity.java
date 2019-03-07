package com.suroid.intent;

import android.Manifest;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private static final int RESULT_LOAD_IMAGE = 1;
    private static final int requestPermissionCode = 999;
    private static boolean permissionGranted = false;

    @InjectView(R.id.btnExplicit)
    Button explicitIntent;

    @InjectView(R.id.btnImplicit)
    Button implicitIntent;

    @InjectView(R.id.imageView)
    ImageView imageView;

    @InjectView(R.id.btnSendData)
    Button btnSendData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getPermission();
        ButterKnife.inject(this);
    }

    public void getPermission() {
        ActivityCompat.requestPermissions(MainActivity.this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                requestPermissionCode);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        if (requestPermissionCode == requestCode) {
            for (int i = 0; i < permissions.length; i++) {
                if (grantResults[i] != 0) {
                    /*Log.e(TAG, "Permission Denied for " + permissions[i]);*/
                    Toast.makeText(MainActivity.this, "Please grant all permissions", Toast.LENGTH_SHORT).show();
                    permissionGranted = false;
                    break;
                }
                permissionGranted = true;
            }

        } else super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    //We need not to call this. Butterknife library will automatically detect this function on button click
    //You can also use onClickListener if you want.
    @OnClick({R.id.btnExplicit, R.id.btnImplicit, R.id.btnSendData})
    public void clickListener(View view) {
        switch (view.getId()) {
            case R.id.btnExplicit:
                clickExplicit();
                break;
            case R.id.btnImplicit:
                if (permissionGranted) {
                    clickImplicit();
                } else {
                    Toast.makeText(MainActivity.this, "First grant the permission to access the storage", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btnSendData:
                sendData();
                break;
        }
    }

    public void clickExplicit() {
        Toast.makeText(this, "This will land you on new activity", Toast.LENGTH_SHORT).show();
        //This is the way of initiating a new activity from one activity.
        Intent intent = new Intent(this, SecondActivity.class);
        startActivity(intent);
        //If you don't want the user to open this activity again you can kill or destroy this activity using finish function.
        //finish();
    }

    public void clickImplicit() {
        Toast.makeText(this, "This will open Gallery for you", Toast.LENGTH_SHORT).show();
        //This is the way of initiating a new activity from one activity.
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, RESULT_LOAD_IMAGE);
    }

    public void sendData() {
        Intent intent = new Intent(this, SendDataActivityOne.class);
        startActivity(intent);
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
                    } else {
                        imageView.setImageBitmap(bitmap);
                    }
                }
                break;
            default:
                break;
        }
    }
}
