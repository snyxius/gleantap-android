package com.gleantap;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.widget.Toast;

import com.gleantap.api.Parse;
import com.gleantap.api.WebRequest;
import com.gleantap.extras.Constants;
import com.gleantap.extras.Keys;

import org.json.JSONObject;

import java.util.Hashtable;
import java.util.Locale;


/**
 * Created by Snyxius Technologies on 7/25/2016.
 */

public class AsyncHandlerInteraction implements IAsyncHandlerInteraction{


    OnValidateFinishListner onValidateFinishListner;
    String App_ID = "";
    Context context = null;
    @Override
    public void validateInteraction(OnValidateFinishListner onValidateFinishListner,Context context, String App_Id) {
        this.onValidateFinishListner = onValidateFinishListner;
        this.App_ID = App_Id;
        this.context = context;
        if(App_Id.equals("")){
            onValidateFinishListner.emptyAppId();
        }else if(context == null){
            onValidateFinishListner.emptyContext();
        }else{
            onValidateFinishListner.onSuccess("Success");
            new SendData().execute(Parse.sendSessionData(context,App_ID).toString(), Constants.USER_SESSION);
            new SendData().execute(Parse.sendEventData(context,Keys.openApp,App_ID).toString(), Constants.EVENT_DETAILS);

        }
    }

    @Override
    public void validateInteractionToken(OnValidateFinishListner onValidateFinishListner,String Token) {
        this.onValidateFinishListner = onValidateFinishListner;
        if(App_ID.equals("")){
            onValidateFinishListner.emptyAppId();
        }else if(context == null){
            onValidateFinishListner.emptyContext();
        }else if(Token.equals("")){
            onValidateFinishListner.emptyToken();
        }else{
            new SendData().execute(Parse.sendData(context,App_ID,Token).toString(), Constants.REGISTER);
        }
    }

    @Override
    public void validateInteractionTokenAndData(OnValidateFinishListner onValidateFinishListner,String Token, Hashtable<String, String> data) {
        if(App_ID.equals("")){
            onValidateFinishListner.emptyAppId();
        }else if(context == null){
            onValidateFinishListner.emptyContext();
        }else if(Token.equals("")){
            onValidateFinishListner.emptyToken();
        }else if (data.isEmpty() || data == null){
            onValidateFinishListner.emptyData();
        }else{

            new SendData().execute(Parse.sendData(context,App_ID,Token,data).toString(), Constants.REGISTER);
        }
    }

    @Override
    public void validateInteractionData(OnValidateFinishListner onValidateFinishListner, String data,String AppId) {
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


}
