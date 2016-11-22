package com.gleantap;

/**
 * Created by Snyxius Technologies on 7/25/2016.
 */
public  interface IInitalizeVaildate {
    void onError(String message);
    void onSuccess(String message);
    void emptyAppId();
    void emptyToken();
    void emptyData();
    void emptyContext();
    void internetIssue();
    void serverIssue();
}
