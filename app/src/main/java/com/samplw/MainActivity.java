package com.samplw;

import android.content.Context;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

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
        GleanTapInitialize.initialize(this,"58464beefbb597ac3e8b4568");
    }


    @Override
    protected void onStop() {
        super.onStop();
        GleanTapInitialize.registerEvent(Keys.closeApp);
    }

}
