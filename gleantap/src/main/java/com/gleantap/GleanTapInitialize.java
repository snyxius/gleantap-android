package com.gleantap;

import android.content.Context;
import android.content.pm.PackageManager;

import com.gleantap.api.Parse;
import com.gleantap.extras.Constants;

import java.util.Hashtable;

/**
 * Created by Snyxius Technologies on 7/19/2016.
 */
public class GleanTapInitialize {
    static final private int PERMISSION_LOCATION_REQUEST_CODE = 123;
    static NotificationPresenter presenter; ;
    static String AppId = "";

    public static void initialize(final Context contexts, final String App_IDs){
       // Intent intent = new Intent(contexts, TestService.class);
       //  intent.putExtra(Keys.appid,App_IDs);
       // contexts.startService(intent);
        presenter = new NotificationPresenter();
        presenter.initialize(contexts,App_IDs);
        AppId = App_IDs;

    }

    public static void checkLocationPermission(int requestCode, String[] permissions, int[] grantResults){
        presenter.permissionCheck(requestCode,permissions,grantResults);}


   // public static void onStart(){presenter.onStart();};

    public static void registerToken(String token){
        presenter.registeredToken(token);
    }

    public static void registerToken(String token, Hashtable<String,String> data){
        presenter.registeredToken(token,data);
    }

       public static void registerEvent(String data){
        presenter.registeredEvent(data,AppId);
    }

    public static void pushClick(String data){
        presenter.pushClick(data,AppId);
    }

    public static void registerTag(String data){
        presenter.registeredTag(data,AppId);
    }

    public static void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        if (requestCode == PERMISSION_LOCATION_REQUEST_CODE) {

            if ((grantResults.length > 0) && grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                    grantResults[1] == PackageManager.PERMISSION_GRANTED) {

            }
        }
    }
}
