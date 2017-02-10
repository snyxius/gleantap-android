package com.samplw;

import android.content.Context;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.gleantap.AsyncHandlerInteraction;
import com.gleantap.GleanTapInitialize;
import com.gleantap.extras.Keys;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(getIntent().getStringExtra(Keys.campaign_id) != null){
            GleanTapInitialize.pushClick(getIntent().getStringExtra(Keys.campaign_id));

        }

        GleanTapInitialize.initialize(this,"584c6234fbb59728688b4567");
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        GleanTapInitialize.checkLocationPermission(requestCode, permissions, grantResults);
    }


    @Override
    protected void onStop() {
        super.onStop();
        GleanTapInitialize.registerEvent(Keys.closeApp);
    }



}
