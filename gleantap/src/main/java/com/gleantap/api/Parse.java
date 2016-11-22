package com.gleantap.api;

import android.app.Activity;
import android.content.Context;
import android.provider.Settings;
import android.telephony.TelephonyManager;

import com.gleantap.extras.Keys;

import org.json.JSONObject;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

/**
 * Created by Snyxius Technologies on 6/7/2016.
 */
public class Parse {



    public static  JSONObject sendData(Context context,String App_Id,String token){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.accumulate(Keys.appid,App_Id) ;
            jsonObject.accumulate(Keys.devicetoken,token);
            jsonObject.accumulate(Keys.platform,Keys.Android);
            jsonObject.accumulate(Keys.userId,getDeviceUniqueID(context));
            JSONObject jsonObject1 = new JSONObject();
            jsonObject1.accumulate("country", Locale.getDefault().getDisplayCountry());
            jsonObject1.accumulate("language", Locale.getDefault().getDisplayLanguage());
            jsonObject1.accumulate("os model", android.os.Build.MODEL);
            jsonObject1.accumulate("os brand",android.os.Build.BRAND);
            jsonObject1.accumulate("os version",android.os.Build.VERSION.RELEASE );
            jsonObject1.accumulate("os",android.os.Build.VERSION.SDK_INT);
            jsonObject.accumulate(Keys.data,jsonObject1);
        }catch (Exception e){
                e.printStackTrace();
        }

        return  jsonObject;
    }

    public static  JSONObject sendSessionData(Context context,String App_Id){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.accumulate(Keys.platform,Keys.Android);
            jsonObject.accumulate(Keys.appid,App_Id);
            jsonObject.accumulate(Keys.userId,getDeviceUniqueID(context));
            jsonObject.accumulate(Keys.devicetoken,"");

        }catch (Exception e){
            e.printStackTrace();
        }

        return  jsonObject;
    }

    public static  JSONObject sendEventData(Context context,String event_name){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.accumulate(Keys.event_name,event_name);
            jsonObject.accumulate(Keys.user_id,getDeviceUniqueID(context));
            jsonObject.accumulate(Keys.platform,Keys.Android);

        }catch (Exception e){
            e.printStackTrace();
        }
        return  jsonObject;
    }
    public static  String getDeviceUniqueID(Context activity){
        String device_unique_id = Settings.Secure.getString(activity.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        return device_unique_id;
    }

    public static  JSONObject sendData(Context context, String App_Id, String token, Hashtable<String,String> data){
        JSONObject jsonObject = new JSONObject();
        try {
            JSONObject jsonObject1 = new JSONObject();

            jsonObject1.accumulate("country", Locale.getDefault().getDisplayCountry());
            jsonObject1.accumulate("language", Locale.getDefault().getDisplayLanguage());
            jsonObject1.accumulate("os model", android.os.Build.MODEL);
            jsonObject1.accumulate("os brand",android.os.Build.BRAND);
            jsonObject1.accumulate("OS version",android.os.Build.VERSION.RELEASE );
            jsonObject1.accumulate("os",android.os.Build.VERSION.SDK_INT);

            Enumeration e = data.keys();
            while (e.hasMoreElements()) {
                String key = (String) e.nextElement();
                jsonObject1.accumulate(key,data.get(key));
            }
            jsonObject.accumulate(Keys.appid,App_Id) ;
            jsonObject.accumulate(Keys.devicetoken,token);
            jsonObject.accumulate(Keys.platform,Keys.Android);
            jsonObject.accumulate(Keys.userId,getDeviceUniqueID(context));
            jsonObject.accumulate(Keys.data,jsonObject1);

        }catch (Exception e){
                e.printStackTrace();
        }
        return  jsonObject;
    }

}
