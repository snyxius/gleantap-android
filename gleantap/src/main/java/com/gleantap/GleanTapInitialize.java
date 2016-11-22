package com.gleantap;

import android.content.Context;

import java.util.Hashtable;

/**
 * Created by Snyxius Technologies on 7/19/2016.
 */
public class GleanTapInitialize {

    static NotificationPresenter presenter; ;

  static String AppId = "";
    public static void initialize(Context contexts,String App_IDs){
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



}
