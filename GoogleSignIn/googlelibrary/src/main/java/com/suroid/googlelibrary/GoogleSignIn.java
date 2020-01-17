package com.suroid.googlelibrary;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.suroid.googlelibrary.interfaces.GoogleSignCallback;


public class GoogleSignIn {
    private Activity mActivity;
    private GoogleSignInOptions mGoogleSignInOptions;
    private int GOOGLE_SIGN_IN_REQUEST_CODE;
    private GoogleSignCallback mGoogleSignCallback;
    private GoogleSignInClient mGoogleSignInClient;


    /*
     *  Initialize activity instance
     */
    public void setActivity(Activity activity) {
        this.mActivity = activity;
    }

    /*
     *  Initialize Google callback
     */
    public void setCallback(GoogleSignCallback mGoogleSignCallback) {
        this.mGoogleSignCallback = mGoogleSignCallback;
    }

    /*
     *  Initialize Google request code
     */
    public void setRequestCode(int GOOGLE_SIGN_IN_REQUEST_CODE) {
        this.GOOGLE_SIGN_IN_REQUEST_CODE = GOOGLE_SIGN_IN_REQUEST_CODE;

    }

    /*
     * Configure google sign in request for contacts
     */
    public void setUpGoogleClientForGoogleLogin(){
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        mGoogleSignInOptions  = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestProfile()
                .build();

        // Build a GoogleSignInClient with the options specified by mGoogleSignInOptions.
        mGoogleSignInClient = com.google.android.gms.auth.api.signin.GoogleSignIn.getClient(mActivity, mGoogleSignInOptions);
    }


    public void doSignIn(){
        GoogleSignInAccount account = com.google.android.gms.auth.api.signin.GoogleSignIn.getLastSignedInAccount(mActivity);
        if(account!=null)
        {
            mGoogleSignCallback.googleSignInSuccessResult(account);
        }
        else if(mGoogleSignInClient!=null)
        {
            Intent signInIntent = mGoogleSignInClient.getSignInIntent();
            mActivity.startActivityForResult(signInIntent, GOOGLE_SIGN_IN_REQUEST_CODE);
        }
        else
            Toast.makeText(mActivity,"Google SignIn Client is not connected",Toast.LENGTH_SHORT).show();
    }

    public void onActivityResult(Intent data) {
        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (data!=null) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = com.google.android.gms.auth.api.signin.GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }


    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            if(account!=null) {
                // Signed in successfully, show authenticated UI.
                mGoogleSignCallback.googleSignInSuccessResult(account);
            }
            else
                Toast.makeText(mActivity,"Something went wrong",Toast.LENGTH_SHORT).show();
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            mGoogleSignCallback.googleSignInFailureResult( e.getStatusCode()+"");
        }
    }

    public void doSignout(){
        if(mGoogleSignInClient!=null) {
            mGoogleSignInClient.signOut()
                    .addOnCompleteListener(mActivity, new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(mActivity,"Sign Out Successfully",Toast.LENGTH_SHORT).show();
                        }
                    });
        }
        else
        {
            Toast.makeText(mActivity,"Please Login First",Toast.LENGTH_SHORT).show();
        }

    }

}
