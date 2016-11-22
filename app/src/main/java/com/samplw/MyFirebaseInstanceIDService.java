package com.samplw;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.gleantap.GleanTapInitialize;
import com.gleantap.extras.Keys;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import java.util.Hashtable;

/**
 * Created by amanjham on 27/10/16.
 */

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {


        GleanTapInitialize.registerToken(FirebaseInstanceId.getInstance().getToken());

        // Advanced features
        Hashtable<String,String> data = new Hashtable<>();
        data.put("location","bangalore");
        data.put("gender","male");
        GleanTapInitialize.registerToken(FirebaseInstanceId.getInstance().getToken(),data);


    }

}
