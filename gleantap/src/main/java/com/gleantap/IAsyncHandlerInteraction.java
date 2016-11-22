package com.gleantap;

import android.content.Context;

import java.util.Hashtable;

/**
 * Created by Snyxius Technologies on 7/25/2016.
 */
public interface IAsyncHandlerInteraction {
    void validateInteraction(OnValidateFinishListner onValidateFinishListner, Context context, String App_Id);
    void validateInteractionToken(OnValidateFinishListner onValidateFinishListner, String Token);
    void validateInteractionTokenAndData(OnValidateFinishListner onValidateFinishListner, String Token, Hashtable<String, String> data);
    void validateInteractionData(OnValidateFinishListner onValidateFinishListner, String data,String AppId);
}
