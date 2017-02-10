package com.gleantap;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.gleantap.api.Parse;
import com.gleantap.api.WebRequest;
import com.gleantap.extras.Constants;
import com.gleantap.extras.Keys;

import org.json.JSONObject;

import java.util.Hashtable;


/**
 * Created by Snyxius Technologies on 7/25/2016.
 */

public class AsyncHandlerInteraction implements IAsyncHandlerInteraction {


    OnValidateFinishListner onValidateFinishListner;
    String App_ID = "";
    Context context = null;
    String tokenMain;
    Hashtable<String, String> dataMain;
    String validInteraction="validateInteractionTokenAndData";
    String latitude,longitude;
    static final private int PERMISSION_LOCATION_REQUEST_CODE = 123;
    boolean location = false ;

    @Override
    public void validateInteraction(OnValidateFinishListner onValidateFinishListner, Context context, String App_Id) {
        this.onValidateFinishListner = onValidateFinishListner;
        this.App_ID = App_Id;
        this.context = context;
        if(App_Id.equals("")){
            onValidateFinishListner.emptyAppId();
        }else if(context == null){
            onValidateFinishListner.emptyContext();
        }else{
            onValidateFinishListner.onSuccess("Success");
         //   getTheLocation();
            new SendData().execute(Parse.sendSessionData(context,App_ID).toString(), Constants.USER_SESSION);
            new SendData().execute(Parse.sendEventData(context, Keys.openApp,App_ID).toString(), Constants.EVENT_DETAILS);

        }
    }

    @Override
    public void validateInteractionToken(OnValidateFinishListner onValidateFinishListner, String Token) {
        this.onValidateFinishListner = onValidateFinishListner;
        if(App_ID.equals("")){
            onValidateFinishListner.emptyAppId();
        }else if(context == null){
            onValidateFinishListner.emptyContext();
        }else if(Token.equals("")){
            onValidateFinishListner.emptyToken();
        }else{
         //   getTheLocation();

            tokenMain=Token;
            validInteraction="validateInteractionToken";
            Constants.saveToPreferences(context,Keys.device_id,tokenMain);
            Constants.saveToPreferences(context,Keys.appid,App_ID);
            new isLocationEnabled().execute(Parse.sendAppId(context, App_ID).toString(), Constants.IS_LOCATION_ENABLE);

//            new SendData().execute(Parse.sendData(context,App_ID,Token).toString(), Constants.REGISTER);
            //new isLocationEnabled().execute(Parse.sendAppId(context, App_ID).toString(), Constants.IS_LOCATION_ENABLE);
//            Activity activity=(Activity)context;
//            if (isStoragePermissionGranted(activity)) {
//                getTheLocation();
//                new isLocationEnabled().execute(Parse.sendAppId(context, App_ID).toString(), Constants.IS_LOCATION_ENABLE);
//            }

        }
    }

    @Override
    public void validateInteractionTokenAndData(OnValidateFinishListner onValidateFinishListner, String Token, Hashtable<String, String> data) {
        if(App_ID.equals("")){
            onValidateFinishListner.emptyAppId();
        }else if(context == null){
            onValidateFinishListner.emptyContext();
        }else if(Token.equals("")){
            onValidateFinishListner.emptyToken();
        }else if (data.isEmpty() || data == null){
            onValidateFinishListner.emptyData();
        }else{
            tokenMain=Token;
            dataMain=data;
            validInteraction="validateInteractionTokenAndData";
            Constants.saveToPreferences(context,Keys.device_id,tokenMain);
            Constants.saveToPreferences(context,Keys.appid,App_ID);
            new isLocationEnabled().execute(Parse.sendAppId(context, App_ID).toString(), Constants.IS_LOCATION_ENABLE);
//            Activity activity=(Activity)context;
//            if (isStoragePermissionGranted(activity)) {
//                getTheLocation();

//            }

        }
    }

    @Override
    public void validateInteractionData(OnValidateFinishListner onValidateFinishListner, String data, String AppId) {
        if (data.isEmpty() || data == null){
            onValidateFinishListner.emptyData();
        }else{

            new SendData().execute(Parse.sendEventData(context,data,AppId).toString(), Constants.EVENT_DETAILS);
        }
    }

    @Override
    public void validateInteractionTagData(OnValidateFinishListner onValidateFinishListner, String data, String AppId) {
        if (data.isEmpty() || data == null){
            onValidateFinishListner.emptyData();
        }else{

            new SendData().execute(Parse.sendTagData(context,data,AppId).toString(), Constants.TAG_DETAILS);
        }
    }

    @Override
    public void validatePushData(OnValidateFinishListner onValidateFinishListner, String data, String AppId) {
        if (data.isEmpty() || data == null){
            onValidateFinishListner.emptyData();
        }else{

            new SendData().execute(Parse.sendPushData(context,data,AppId).toString(), Constants.PUSH_CLICK);
        }
    }


    private class SendData extends AsyncTask<String, Void, JSONObject> {

        @Override
        protected JSONObject doInBackground(String... params) {
            JSONObject jsonObject = null;
            try {
                jsonObject = WebRequest.postData(params[0],params[1]);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return jsonObject;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            super.onPostExecute(jsonObject);
            onDone(jsonObject);
        }

        private void onDone(JSONObject jsonObject) {
            try {
                if (jsonObject != null) {
                    if(jsonObject.has(Keys.message) && jsonObject.has(Keys.status)){

                        if(jsonObject.getString(Keys.status).equalsIgnoreCase(Constants.success)){

                            onValidateFinishListner.onSuccess("as");
                            JSONObject jsonObject1 = jsonObject.getJSONObject(Keys.message);

                            Constants.saveToPreferences(context,Keys.user_id,jsonObject1.getString(Keys.$id));

                        }else{

                            onValidateFinishListner.serverIssue();
                        }
                    }else{
                        onValidateFinishListner.serverIssue();
                    }
                } else {
                    onValidateFinishListner.serverIssue();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }


    private class isLocationEnabled extends AsyncTask<String, Void, JSONObject> {

        @Override
        protected JSONObject doInBackground(String... params) {
            JSONObject jsonObject = null;
            try {
                jsonObject = WebRequest.postData(params[0],params[1]);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return jsonObject;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            super.onPostExecute(jsonObject);
            onDone(jsonObject);
        }

        private void onDone(JSONObject jsonObject) {
            try {
                if (jsonObject != null) {
                    if(jsonObject.has(Keys.message)){

                        if(jsonObject.getString(Keys.message).equalsIgnoreCase(Keys.No)){

                            if(validInteraction.equalsIgnoreCase("validateInteractionTokenAndData")){
                                new SendData().execute(Parse.sendData(context,App_ID,tokenMain,dataMain).toString(), Constants.REGISTER);
                            }else if(validInteraction.equalsIgnoreCase("validateInteractionToken")){
                                new SendData().execute(Parse.sendData(context,App_ID,tokenMain).toString(), Constants.REGISTER);
                            }

                        }else{
                            onStart();
                        }
                    }else{
                        onValidateFinishListner.serverIssue();
                    }
                } else {
                    onValidateFinishListner.serverIssue();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }



    private void getTheLocation(){
        SingleShotLocationProvider loc=new SingleShotLocationProvider(context);
        SingleShotLocationProvider.requestSingleUpdate(context, new SingleShotLocationProvider.LocationCallback() {
            @Override
            public void onNewLocationAvailable(SingleShotLocationProvider.GPSCoordinates location) {
                Log.d("Location", "my location is " + String.valueOf(location.latitude)+" "+location.longitude);
                latitude= String.valueOf(location.latitude);
                longitude= String.valueOf(location.longitude);

            }
        });
    }

    public void onStart() {
        Activity activity=(Activity)context;
        if (isStoragePermissionGranted(activity)) {
            System.out.println("onstart permission granted");
            getTheLocation();
            if(validInteraction.equalsIgnoreCase("validateInteractionTokenAndData")){
                new SendData().execute(Parse.sendDataWithLatLng(context,App_ID,tokenMain,dataMain,latitude,longitude).toString(), Constants.REGISTER);
            }else if(validInteraction.equalsIgnoreCase("validateInteractionToken")){
                new SendData().execute(Parse.sendDataWithoutData(context,App_ID,tokenMain,latitude,longitude).toString(), Constants.REGISTER);
            }
        }else{
            System.out.println("onstart permission not granted");
//            if(validInteraction.equalsIgnoreCase("validateInteractionTokenAndData")){
//                new SendData().execute(Parse.sendData(context,App_ID,tokenMain,dataMain).toString(), Constants.REGISTER);
//            }else if(validInteraction.equalsIgnoreCase("validateInteractionToken")){
//                new SendData().execute(Parse.sendData(context,App_ID,tokenMain).toString(), Constants.REGISTER);
//            }

        }
    }


    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        if (requestCode == PERMISSION_LOCATION_REQUEST_CODE) {

            if ((grantResults.length > 0) && grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                    grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                System.out.println(" permission granted");
                getTheLocation();
                if(validInteraction.equalsIgnoreCase("validateInteractionTokenAndData")){
                    new SendData().execute(Parse.sendDataWithLatLng(context,App_ID,tokenMain,dataMain,latitude,longitude).toString(), Constants.REGISTER);
                }else if(validInteraction.equalsIgnoreCase("validateInteractionToken")){
                    new SendData().execute(Parse.sendDataWithoutData(context,App_ID,tokenMain,latitude,longitude).toString(), Constants.REGISTER);
                }

            }else{
                System.out.println(" permission not granted");
                if(validInteraction.equalsIgnoreCase("validateInteractionTokenAndData")){
                    new SendData().execute(Parse.sendData(context,App_ID,tokenMain,dataMain).toString(), Constants.REGISTER);
                }else if(validInteraction.equalsIgnoreCase("validateInteractionToken")){
                    new SendData().execute(Parse.sendData(context,App_ID,tokenMain).toString(), Constants.REGISTER);
                }
            }
        }else{
            System.out.println("not entering");
        }
    }



    //For fragments
    public static boolean isStoragePermissionGranted(Activity activity) {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                    ActivityCompat.requestPermissions(activity,
                            new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.READ_PHONE_STATE},
                            PERMISSION_LOCATION_REQUEST_CODE);

                return false;
            }
        } else {
            return true;
        }



    }






}
