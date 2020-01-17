package com.suroid.facebooklibrary;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.suroid.facebooklibrary.interfaces.FBSignCallback;

import org.json.JSONObject;

import java.util.Arrays;

public class FacebookSignIn {
    private static FacebookSignIn mFacebookSignIn;
    private AppCompatActivity mActivity;
    private CallbackManager callbackManager;
    private FBSignCallback fbSignCallback;


    /*
     * @param - activity
     * @param - fbSignCallback interface to return callback
     *
     */
    public static FacebookSignIn getInstance(AppCompatActivity activity, FBSignCallback fbSignCallback) {
        mFacebookSignIn = new FacebookSignIn(activity,fbSignCallback);
        FacebookSdk.sdkInitialize(activity);
        return mFacebookSignIn;
    }

    /*
     * Initialize global variable
     */
    public FacebookSignIn(AppCompatActivity activity, FBSignCallback fbSignCallback) {
        this.mActivity = activity;
        this.fbSignCallback = fbSignCallback;
    }

    /*
     *  Sign In Method
     */
    public void doSignIn() {
        callbackManager = CallbackManager.Factory.create();
        LoginViaFacebook();
        LoginManager.getInstance().logInWithReadPermissions(mActivity,
                Arrays.asList("public_profile", "email"
                        /*, "user_friends", "user_location", "email", "user_birthday"*/));
    }

    /*
     *  Sign out Method
     */
    public void doSignOut() {
        LoginManager.getInstance().logOut();
        fbSignCallback.fbSignOutSuccessResult();
    }

    /*
     * To get user profile information from facebook
     */
    public void LoginViaFacebook() {
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                final GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(
                                    JSONObject jsonObject,
                                    GraphResponse response) {
                                if (jsonObject != null) {
                                    try {
                                        fbSignCallback.fbSignInSuccessResult(jsonObject);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id, name, email, picture"
                        /*, gender, picture, birthday"*/);
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {
                fbSignCallback.fbSignInCancel();
            }

            @Override
            public void onError(FacebookException exception) {
                fbSignCallback.fbSignInFailure(exception);
            }
        });
    }

    /*
     *  return callback to facebook using callbackmanager
     */
    public void setActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
