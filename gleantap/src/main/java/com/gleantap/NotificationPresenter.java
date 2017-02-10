package com.gleantap;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.util.Hashtable;

/**
 * Created by Snyxius Technologies on 7/25/2016.
 */
public  class  NotificationPresenter implements INotificationPresenter,OnValidateFinishListner {



    AsyncHandlerInteraction interaction;
    IInitalizeVaildate view;
    static final String TAG = "GleanTap";
    private static LOG_LEVEL visualLogLevel = LOG_LEVEL.NONE;
    private static LOG_LEVEL logCatLevel = LOG_LEVEL.WARN;


    NotificationPresenter(){
        interaction = new AsyncHandlerInteraction();
    }


    @Override
    public void initialize(Context context, String App_ID) {
       interaction.validateInteraction(this,context,App_ID);
    }

    @Override
    public void registeredToken(String Token) {
        interaction.validateInteractionToken(this,Token);
    }

    @Override
    public void registeredToken(String Token, Hashtable<String, String> data) {
        interaction.validateInteractionTokenAndData(this,Token,data);
    }


    @Override
    public void registeredEvent(String data, String AppId) {
        interaction.validateInteractionData(this,data,AppId);
    }

    @Override
    public void registeredTag(String data, String AppId) {
        interaction.validateInteractionTagData(this,data,AppId);
    }

    @Override
    public void pushClick(String data, String AppId) {
        interaction.validatePushData(this,data,AppId);
    }

    @Override
    public void permissionCheck(int requestCode, String[] permissions, int[] grantResults) {
        interaction.onRequestPermissionsResult(requestCode,permissions,grantResults);
    }

//    @Override
//    public void onStart() {
//        interaction.onStart();
//    }


    public static void Log(final LOG_LEVEL level, String message, Throwable throwable) {
        if (level.compareTo(logCatLevel) < 1) {
            if (level == LOG_LEVEL.VERBOSE)
                Log.v(TAG, message, throwable);
            else if (level == LOG_LEVEL.DEBUG)
                Log.d(TAG, message, throwable);
            else if (level == LOG_LEVEL.INFO)
                Log.i(TAG, message, throwable);
            else if (level == LOG_LEVEL.WARN)
                Log.w(TAG, message, throwable);
            else if (level == LOG_LEVEL.ERROR || level == LOG_LEVEL.FATAL)
                Log.e(TAG, message, throwable);
        }

    }

    public static void Log(final LOG_LEVEL level, String message) {
        if (level.compareTo(logCatLevel) < 1) {
            if (level == LOG_LEVEL.VERBOSE)
                Log.v(TAG, message);
            else if (level == LOG_LEVEL.DEBUG)
                Log.d(TAG, message);
            else if (level == LOG_LEVEL.INFO)
                Log.i(TAG, message);
            else if (level == LOG_LEVEL.WARN)
                Log.w(TAG, message);
            else if (level == LOG_LEVEL.ERROR || level == LOG_LEVEL.FATAL)
                Log.e(TAG, message);
        }

        if (level.compareTo(visualLogLevel) < 1 && ActivityLifecycleHandler.curActivity != null) {
            try {
                String fullMessage = message + "\n";

                final String finalFullMessage = fullMessage;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (ActivityLifecycleHandler.curActivity != null)
                            new AlertDialog.Builder(ActivityLifecycleHandler.curActivity)
                                    .setTitle(level.toString())
                                    .setMessage(finalFullMessage)
                                    .show();
                    }
                });
            } catch(Throwable t) {
                Log.e(TAG, "Error showing logging message.", t);
            }
        }

    }

    @Override
    public void onError(String message) {
        Log(LOG_LEVEL.FATAL, "PushSignal onError.");
        return;
    }

    @Override
    public void onSuccess(String message) {
        Log(LOG_LEVEL.VERBOSE, "PushSignal onSuccess.");
        return;
    }

    @Override
    public void emptyAppId() {
        Log(LOG_LEVEL.FATAL, "PushSignal emptyAppId can not be null.");
        return;
    }

    @Override
    public void emptyToken() {
        Log(LOG_LEVEL.FATAL, "PushSignal emptyToken can not be null.");
        return;
    }

    @Override
    public void emptyData() {
        Log(LOG_LEVEL.FATAL, "PushSignal emptyData can not be null.");
        return;
    }

    @Override
    public void emptyContext() {
        Log(LOG_LEVEL.FATAL, "PushSignal emptyContext can not be null.");
        return;
    }

    @Override
    public void internetIssue() {
        Log(LOG_LEVEL.FATAL, "PushSignal internetIssue.");
        return;
    }

    @Override
    public void serverIssue() {
        Log(LOG_LEVEL.FATAL, "PushSignal serverIssue.");
        return;
    }

    public enum LOG_LEVEL {
        NONE, FATAL, ERROR, WARN, INFO, DEBUG, VERBOSE
    }

    static void runOnUiThread(Runnable action) {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(action);
    }
}
