package com.gleantap;

import android.content.Context;

import java.util.HashMap;
import java.util.Hashtable;

/**
 * Created by Snyxius Technologies on 7/25/2016.
 */
public interface INotificationPresenter {
    void initialize(Context context, String App_ID);
    void registeredToken(String Token);
    void registeredToken(String Token, Hashtable<String, String> data);
    void registeredEvent(String data,String AppId);
    void registeredTag(String data,String AppId);
    void pushClick(String data,String AppId);
}
