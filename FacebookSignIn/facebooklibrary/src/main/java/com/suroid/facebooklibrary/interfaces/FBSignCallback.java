package com.suroid.facebooklibrary.interfaces;


import com.facebook.FacebookException;

import org.json.JSONObject;


public interface FBSignCallback {
    void fbSignInSuccessResult(JSONObject jsonObject);
    void fbSignOutSuccessResult();
    void fbSignInFailure(FacebookException exception);
    void fbSignInCancel();
}
