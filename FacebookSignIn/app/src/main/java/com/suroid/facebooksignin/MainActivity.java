package com.suroid.facebooksignin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.facebook.FacebookException;
import com.suroid.facebooklibrary.FacebookSignIn;
import com.suroid.facebooklibrary.interfaces.FBSignCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainActivity extends AppCompatActivity implements FBSignCallback {

    FBSignCallback fbSignCallback;
    FacebookSignIn fbSignInAI;
    String email = "",name ="",socialId = "",image="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gethashKey();
        fbSignCallback = this;

        fbSignInAI = FacebookSignIn.getInstance(this, fbSignCallback);
        fbSignInAI.doSignIn();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (64206 == requestCode)
            fbSignInAI.setActivityResult(requestCode, resultCode, data);
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

    @Override
    public void fbSignInSuccessResult(JSONObject jsonObject) {
        try {
            if (jsonObject.getString("email") != null) {
                email = jsonObject.getString("email");
                name = jsonObject.getString("name");
                socialId = jsonObject.getString("id");
                image = jsonObject.getJSONObject("picture").getJSONObject("data").getString("url");
            }
        } catch (JSONException e) {
            fbSignInAI.doSignOut();
            showToastLong("Unable to fetch email address",false);
        }
        /*showToastLong(jsonObject.toString());
        Log.e("json",jsonObject.toString());*/
    }

    @Override
    public void fbSignOutSuccessResult() {
        showToastLong("signout",false);
    }

    @Override
    public void fbSignInFailure(FacebookException exception) {
        fbSignInAI.doSignOut();
        showToastLong("failed",false);
    }

    @Override
    public void fbSignInCancel() {
        fbSignInAI.doSignOut();
        showToastLong("cancel",false);
    }

    private void showToastLong(String message,boolean show){
        Toast.makeText(this,message,Toast.LENGTH_LONG).show();
    }

}
