package com.gleantap.extras;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Snyxius Technologies on 7/25/2016.
 */
public class Constants {


    public static final String URL = "http://45.55.245.79:81/";
   /// public static final String API = "projects/funnelup/branches/sumana/index.php/";
//    public static final String API = "projects/gleantap/users/api/";
   public static final String API = "projects/gleantap/v1/api/";

//    public static final String REGISTER = URL + API+"userdetails";
//    public static final String EVENT_DETAILS = URL + API+"eventdetails";
//    public static final String TAG_DETAILS = URL + API+"tagdetails";
//    public static final String SEARCH_DETAILS = URL + API+"searchdetails";
//    public static final String USER_SESSION = URL + API+"userSession";
//    public static final String PUSH_CLICK = URL + API+"pushClick";

 public static final String REGISTER = URL + API+"saveUser";
 public static final String EVENT_DETAILS = URL + API+"saveEvent";
 public static final String TAG_DETAILS = URL + API+"saveTag";
 public static final String SEARCH_DETAILS = URL + API+"searchUser";
 public static final String USER_SESSION = URL + API+"userSession";
 public static final String PUSH_CLICK = URL + API+"pushClick";
 public static final String IS_LOCATION_ENABLE = URL + API+"isLocationTrackingEnabled";

    public static final String Failed = "Failed";
    public static final String Default_String = "";
    public static final int SUCCESS = 200;
    public static final String success = "success";
    public static final String failed = "failed";


    public static void saveToPreferences(Context context, String preferenceName, String preferenceValue) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(preferenceName, preferenceValue);
        editor.apply();
    }

    public static String readFromPreferences(Context context, String preferenceName, String defaultValue) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        return sharedPreferences.getString(preferenceName, defaultValue);
    }
}
