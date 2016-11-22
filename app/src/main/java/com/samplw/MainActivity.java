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
        GleanTapInitialize.initialize(this,"5833fb09fbb5971f3ea36be7");
        System.out.println("Un "+getDeviceUniqueID(this));
        GleanTapInitialize.registerEvent(Keys.openApp);
    }

    public static  String getDeviceUniqueID(Context activity){
        String device_unique_id = Settings.Secure.getString(activity.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        return device_unique_id;
    }
}
