package com.gleantap;

/**
 * Created by nihas-mac on 14/12/2016.
 */

public class PermissionListener {

    public interface OnCustomStateListener {
        void stateChanged();
    }

    private static PermissionListener mInstance;
    private OnCustomStateListener mListener;
    private boolean mState=false;

    private PermissionListener() {}

    public static PermissionListener getInstance() {
        if(mInstance == null) {
            mInstance = new PermissionListener();
        }
        return mInstance;
    }

    public void setListener(OnCustomStateListener listener) {
        mListener = listener;
    }

    public void changeState(boolean state) {
        if(mListener != null) {
            mState = state;
            notifyStateChange();
        }
    }

    public boolean getState() {
        return mState;
    }

    private void notifyStateChange() {
        mListener.stateChanged();
    }
}
