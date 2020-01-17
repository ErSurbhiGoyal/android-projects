package com.suroid.googlelibrary.interfaces;


import com.google.android.gms.auth.api.signin.GoogleSignInAccount;


public interface GoogleSignCallback {
     void googleSignInSuccessResult(GoogleSignInAccount googleSignInAccount);
     void googleSignInFailureResult(String message);
     void googleSignOutSussessResult(String message);
     void googleSignOutFailureResult(String message);
}
