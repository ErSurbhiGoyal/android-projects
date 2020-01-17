package com.suroid.googlelibrary;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.suroid.googlelibrary.interfaces.GoogleSignCallback;


public class GoogleSignIn implements GoogleApiClient.OnConnectionFailedListener,GoogleApiClient.ConnectionCallbacks{
    private static GoogleSignIn mGoogleSignIn;
    private AppCompatActivity mActivity;
    private GoogleSignInOptions mGoogleSignInOptions;
    private GoogleApiClient mGoogleApiClient;
    private boolean mSignInClicked;
    private int GOOGLE_SIGN_IN_REQUEST_CODE;
    private GoogleSignCallback mGoogleSignCallback;

    /*
     * @param - activity
     * @param - requestCode used to handle response in onActivity result
     * @param - googleSignCallback interface to return callback
     *
     */
    public static GoogleSignIn getInstance(AppCompatActivity activity, int requestCode, GoogleSignCallback googleSignCallback) {
        mGoogleSignIn = new GoogleSignIn(activity,requestCode,googleSignCallback);
        return mGoogleSignIn;
    }

    public GoogleSignIn(AppCompatActivity activity, int requestCode, GoogleSignCallback  googleSignCallback) {
        this.mActivity = activity;
        this.GOOGLE_SIGN_IN_REQUEST_CODE = requestCode;
        this.mGoogleSignCallback = googleSignCallback;
        setUpGoogleClientForGoogleLogin();
    }

    /*
     * Configure google sign in request for contacts
     */
    private void setUpGoogleClientForGoogleLogin(){
        mGoogleSignInOptions  = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestProfile()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(mActivity)
                .addApi(Auth.GOOGLE_SIGN_IN_API,mGoogleSignInOptions)
                .addOnConnectionFailedListener(this)
                .addConnectionCallbacks(this)
                .build();

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {
        if(mGoogleApiClient!=null){
            mGoogleApiClient.connect();
        }
    }

    public void doSignIn(){
        if(!mGoogleApiClient.isConnected()){
            mGoogleApiClient.connect();
        }
        mSignInClicked = true;
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        mActivity.startActivityForResult(signInIntent, GOOGLE_SIGN_IN_REQUEST_CODE);
    }

    public void onActivityResult(Intent data) {
        if(mSignInClicked) {
            GoogleSignInResult signInResult = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(signInResult);
        }
    }


    private void handleSignInResult(GoogleSignInResult googleSignInResult){
        if(googleSignInResult.isSuccess()){
            GoogleSignInAccount googleSignInAccount = googleSignInResult.getSignInAccount();
            getProfileInfo(googleSignInAccount);
        }else{
            //Failure Message
            mGoogleSignCallback.googleSignInFailureResult(googleSignInResult.getStatus().getStatusMessage());
        }
        mSignInClicked = false;

    }

    private void getProfileInfo(GoogleSignInAccount googleSignInAccount) {
        mGoogleSignCallback.googleSignInSuccessResult(googleSignInAccount);
    }

    public void doSignout(){
        if(mGoogleApiClient!=null&& mGoogleApiClient.isConnected()){
            Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(new ResultCallback<Status>() {
                @Override
                public void onResult(@NonNull Status status) {
                    if(status.isSuccess()){
                        mGoogleApiClient.disconnect();
                        mGoogleSignCallback.googleSignOutSussessResult(status.getStatusMessage());
                    }else{
                        mGoogleSignCallback.googleSignOutFailureResult(status.getStatusMessage());
                    }
                }
            });
        }
    }


}