package com.suroid.googlesignin;

import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.suroid.googlelibrary.GoogleSignIn;
import com.suroid.googlelibrary.interfaces.GoogleSignCallback;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainActivity extends AppCompatActivity implements GoogleSignCallback{

    GoogleSignCallback googleSignCallback;
    GoogleSignIn googleSignInAccount;
    private int googleRequestCode = 101;
    String email = "",name ="",socialId = "",image="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        googleSignCallback = this;

        googleSignInAccount = GoogleSignIn.getInstance(this, googleRequestCode, this);
        googleSignInAccount.doSignIn();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == googleRequestCode)
            googleSignInAccount.onActivityResult(data);
    }

    @Override
    public void googleSignInSuccessResult(GoogleSignInAccount googleSignInAccount) {
        email = googleSignInAccount.getEmail();
        name = googleSignInAccount.getDisplayName();
        socialId = googleSignInAccount.getId();
        if(googleSignInAccount.getPhotoUrl()!=null) {
            image = googleSignInAccount.getPhotoUrl().toString();
        }else{
            image = "";
        }
        //steerToOtp();
    }

    @Override
    public void googleSignInFailureResult(String message) {
        showToastLong("failed",false);
    }

    @Override
    public void googleSignOutSussessResult(String message) {
        showToastLong("success",false);
    }

    @Override
    public void googleSignOutFailureResult(String message) {
        showToastLong("signout error",false);
    }


    private void gethashKey()
    {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.e("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e)
        {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    private void showToastLong(String message,boolean show){
        Toast.makeText(this,message,Toast.LENGTH_LONG).show();
    }
}
