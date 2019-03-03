package com.example.batterent.FCMServices;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

public class MyFirebaseInstanceIdService extends FirebaseInstanceIdService {


    private static final String TAG = "Hritik";
    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();

        Log.d(TAG, "onTokenRefresh: ");
        String t= FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "onTokenRefresh: "+t);
        sendNewTokenToServer(FirebaseInstanceId.getInstance().getToken());
    }

    private void sendNewTokenToServer(String token) {

        Log.d(TAG, "..................................... "+String.valueOf(token));


    }
}
