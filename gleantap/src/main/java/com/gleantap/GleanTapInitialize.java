package com.gleantap;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.gleantap.com.permissioneverywhere.PermissionEverywhere;
import com.gleantap.com.permissioneverywhere.PermissionResponse;
import com.gleantap.com.permissioneverywhere.PermissionResultCallback;
import com.gleantap.extras.Keys;
import com.gleantap.service.TestService;

import java.util.Hashtable;

/**
 * Created by Snyxius Technologies on 7/19/2016.
 */
public class GleanTapInitialize {

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


    public static void registerToken(String token){
        presenter.registeredToken(token);
    }

    public static void registerToken(String token, Hashtable<String,String> data){presenter.registeredToken(token,data);
    }

       public static void registerEvent(String data){
        presenter.registeredEvent(data,AppId);
    }

    public static void pushClick(String data){
        presenter.pushClick(data,AppId);
    }



}
