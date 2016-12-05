package com.gleantap.service;

/**
 * Created by Snyxius Technologies on 11/25/2016.
 */

import android.Manifest;
import android.app.Activity;
import android.app.IntentService;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.os.ResultReceiver;
import android.widget.Toast;

import com.gleantap.R;
import com.gleantap.com.permissioneverywhere.Const;
import com.gleantap.com.permissioneverywhere.PermissionEverywhere;
import com.gleantap.com.permissioneverywhere.PermissionResponse;
import com.gleantap.com.permissioneverywhere.PermissionResultCallback;
import com.gleantap.extras.Keys;

/**
 * Created by Farruxx on 30.04.2016.
 */
public class TestService extends Service {

    public static final String RESULT = "result";
    public String appId = "";
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public int onStartCommand(Intent intent, int flags, int startId) {

      appId = intent.getStringExtra(Keys.appid);

        //do something

        return START_STICKY;

    }
    @Override
    public void onCreate() {
        super.onCreate();
        boolean asyncTest = true;

        if(asyncTest) {

            new AsyncTask<Void, Void, Boolean>() {

                @Override
                protected Boolean doInBackground(Void... params) {
                    PermissionResponse response = null;
                    try {
                        response = PermissionEverywhere.getPermission(getApplicationContext(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                123, "My Awesome App", "This app needs a write permission", R.mipmap.ic_launcher).call();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    boolean isGranted = response.isGranted();

                    return isGranted;
                }

                @Override
                protected void onPostExecute(Boolean aBoolean) {
                    super.onPostExecute(aBoolean);
                    Toast.makeText(TestService.this, "is Granted " + aBoolean, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Const.NOTIFICATION);
                    intent.putExtra("Grant",aBoolean);
                    intent.putExtra(Keys.appid,appId);
                    intent.putExtra(RESULT, Activity.RESULT_OK);
                    sendBroadcast(intent);

                }
            }.execute();


        }else {


            PermissionEverywhere.getPermission(getApplicationContext(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    123, "My Awesome App", "This app needs a permission", R.mipmap.ic_launcher).enqueue(new PermissionResultCallback() {
                @Override
                public void onComplete(PermissionResponse permissionResponse) {
                    Toast.makeText(TestService.this, "is Granted " + permissionResponse.isGranted(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Const.NOTIFICATION);
                    intent.putExtra("Grant",permissionResponse.isGranted());
                    intent.putExtra(Keys.appid,appId);
                    intent.putExtra(RESULT, Activity.RESULT_OK);
                    sendBroadcast(intent);
                }
            });
        }

    }
}
