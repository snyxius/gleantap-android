package com.gleantap;

/**
 * Created by Snyxius Technologies on 12/14/2016.
 */

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;

/**
 * Created by johntsai on 2015/12/8.
 */
public class SingleShotLocationProvider{
    static final private int PERMISSION_LOCATION_REQUEST_CODE = 123;
    static Context mcontext;


    public SingleShotLocationProvider(Context context){
        mcontext=context;
    }




    public static interface LocationCallback {
        public void onNewLocationAvailable(GPSCoordinates location);
    }

    // calls back to calling thread, note this is for low grain: if you want higher precision, swap the
    // contents of the else and if. Also be sure to check gps permission/settings are allowed.
    // call usually takes <10ms
    public static void requestSingleUpdate(final Context context, final LocationCallback callback) {
        final LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        boolean isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (isNetworkEnabled) {
            Criteria criteria = new Criteria();
            criteria.setAccuracy(Criteria.ACCURACY_COARSE);
            //LOCATION PERMISSION

//            final Activity activity = (Activity) context;
//            if (!checkPermission(context)) {
//                activity.runOnUiThread(new Runnable() {
//                    public void run() {
//                        Permissions permissions = new Permissions(context);
//                        permissions.checkLocationPermission();
//                    }
//                });
//
//            }else {

                locationManager.requestSingleUpdate(criteria, new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        callback.onNewLocationAvailable(new GPSCoordinates(location.getLatitude(), location.getLongitude()));
                    }

                    @Override
                    public void onStatusChanged(String provider, int status, Bundle extras) {
                    }

                    @Override
                    public void onProviderEnabled(String provider) {
                    }

                    @Override
                    public void onProviderDisabled(String provider) {
                    }
                }, null);
          //  }
        } else {
            boolean isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            if (isGPSEnabled) {
                Criteria criteria = new Criteria();
                criteria.setAccuracy(Criteria.ACCURACY_FINE);

//                //LOCATION PERMISSION
//                final Activity activity = (Activity) context;
//                if (!checkPermission(context)) {
//                    activity.runOnUiThread(new Runnable() {
//                        public void run() {
//                            Permissions permissions = new Permissions(context);
//                            permissions.checkLocationPermission();
//                        }
//                    });
//                }else {
                    locationManager.requestSingleUpdate(criteria, new LocationListener() {
                        @Override
                        public void onLocationChanged(Location location) {
                            callback.onNewLocationAvailable(new GPSCoordinates(location.getLatitude(), location.getLongitude()));
                        }

                        @Override
                        public void onStatusChanged(String provider, int status, Bundle extras) {
                        }

                        @Override
                        public void onProviderEnabled(String provider) {
                        }

                        @Override
                        public void onProviderDisabled(String provider) {
                        }
                    }, null);
                }
            }

    }


    // consider returning Location instead of this dummy wrapper class
    public static class GPSCoordinates {
        public float longitude = -1;
        public float latitude = -1;

        //public GPSCoordinates(float theLongitude, float theLatitude) {
        public GPSCoordinates(float theLatitude, float theLongitude) {
            longitude = theLongitude;
            latitude = theLatitude;
        }

        //public GPSCoordinates(double theLongitude, double theLatitude) {
        public GPSCoordinates(double theLatitude, double theLongitude) {
            longitude = (float) theLongitude;
            latitude = (float) theLatitude;
        }
    }

//    public static boolean checkPermission(Context context) {
//        return ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
//                && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;
//    }

    private void showPermissionDialog(Context context) {

    }




}
