package com.gleantap.api;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.format.Formatter;
import android.util.Log;

import com.gleantap.extras.Keys;

import org.json.JSONObject;

import java.lang.reflect.Field;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
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
            jsonObject1.accumulate(Keys.country, getLocalIpAddress());
            jsonObject1.accumulate(Keys.city,getLocalIpAddress());
            jsonObject1.accumulate(Keys.language, Locale.getDefault().getDisplayLanguage());
            jsonObject1.accumulate(Keys.os,getOSDetails());
            jsonObject1.accumulate(Keys.ip,getLocalIpAddress());
            jsonObject.accumulate(Keys.data,jsonObject1);
        }catch (Exception e){
                e.printStackTrace();
        }

        return  jsonObject;
    }

    public static String getOSDetails(){
        StringBuilder builder = new StringBuilder();
        builder.append("android : ").append(Build.VERSION.RELEASE);

        Field[] fields = Build.VERSION_CODES.class.getFields();
        for (Field field : fields) {
            String fieldName = field.getName();
            int fieldValue = -1;

            try {
                fieldValue = field.getInt(new Object());
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }

            if (fieldValue == Build.VERSION.SDK_INT) {
                builder.append(" : ").append(fieldName).append(" : ");
                builder.append("sdk=").append(fieldValue);
            }
        }

        return builder.toString();
    }

    public static String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        String ip = Formatter.formatIpAddress(inetAddress.hashCode());
                        Log.i("***** IP=",ip);
                        return ip;
                    }
                }
            }
        } catch (SocketException ex) {
            Log.e("Ex", ex.toString());
        }
        return null;
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

    public static  JSONObject sendEventData(Context context,String event_name,String AppId){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.accumulate(Keys.event_name,event_name);
            jsonObject.accumulate(Keys.appid,AppId);
            jsonObject.accumulate(Keys.userId,getDeviceUniqueID(context));
            jsonObject.accumulate(Keys.platform,Keys.Android);

        }catch (Exception e){
            e.printStackTrace();
        }
        return  jsonObject;
    }

    public static  JSONObject sendPushData(Context context,String event_name,String AppId){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.accumulate(Keys.campaign_id,event_name);
            jsonObject.accumulate(Keys.appid,AppId);
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

            jsonObject1.accumulate(Keys.country, getLocalIpAddress());
            jsonObject1.accumulate(Keys.city,getLocalIpAddress());
            jsonObject1.accumulate(Keys.language, Locale.getDefault().getDisplayLanguage());
            jsonObject1.accumulate(Keys.os,getOSDetails());
            jsonObject1.accumulate(Keys.ip,getLocalIpAddress());
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
