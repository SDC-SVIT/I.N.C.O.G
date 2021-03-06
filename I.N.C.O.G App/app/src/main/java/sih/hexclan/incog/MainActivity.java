package sih.hexclan.incog;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.ImageFormat;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.OnNmeaMessageListener;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.maps.model.LatLng;
import android.util.Log;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private LocationManager mLocationManager;
    private static final int LOCATION_PERMISSION_REQUEST = 1;
    private static final String[] REQUIRED_PERMISSIONS = {Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.CAMERA};
    static LatLng requiredLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Fragment mapFragment = new MapFragment();
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, mapFragment);
        fragmentTransaction.commit();
        // mLocationManager = (LocationManager)
        // getSystemService(Context.LOCATION_SERVICE);
        // requestPermissions(REQUIRED_PERMISSIONS, LOCATION_PERMISSION_REQUEST);
        // setNmeaMessageListener();

    }
//    public void activateClick(View view) {
////        Fragment cameraFrag = new CameraFragment();
//        Toast.makeText(this, "Click Activated!! Latlong are:"+MainActivity.requiredLocation.latitude+" "+MainActivity.requiredLocation.longitude, Toast.LENGTH_SHORT).show();
//        Log.d(TAG, "activateClick: Latlong are : "+MainActivity.requiredLocation.latitude+" "+MainActivity.requiredLocation.longitude);
//        try {
//            Navigation.findNavController(this,R.id.frame_layout).navigate(MapFragmentDirections.actionMapFragmentToCameraFragment(((CameraManager) getActivity().getSystemService(Context.CAMERA_SERVICE)).getCameraIdList()[0], ImageFormat.JPEG));
//
//        } catch (CameraAccessException e) {
//            e.printStackTrace();
//        }
////        this.getSupportFragmentManager().beginTransaction().replace(R.id.cameraFragment, cameraFrag).commit();
//    }
}



    //    private LocationListener mLocationListener = new LocationListener() {
//
//        @Override
//        public void onLocationChanged(Location location) {
//            Log.d(TAG, "onLocationChanged: " + location.getLatitude() + " " + location.getLongitude());
//            Toast.makeText(MainActivity.this,
//                    "onLocationChanged: " + location.getLatitude() + " " + location.getLongitude(), Toast.LENGTH_SHORT)
//                    .show();
//        }
//
//        @Override
//        public void onStatusChanged(String provider, int status, Bundle extras) {
//
//        }
//
//        @Override
//        public void onProviderEnabled(String provider) {
//
//        }
//
//        @Override
//        public void onProviderDisabled(String provider) {
//
//        }
//    };

    // private OnNmeaMessageListener nmeaMessageListener = new
    // OnNmeaMessageListener() {
    // @Override
    // public void onNmeaMessage(String message, long timestamp) {
    //// Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
    // String[] split = message.split(",");
    // Log.d(TAG, "onNmeaMessage: " + message);
    //
    // }
    // };

    //
    // public void setNmeaMessageListener() {
    // Toast.makeText(this, "setNmeaMessageListener: Listener added",
    // Toast.LENGTH_SHORT).show();
    // boolean isGpsProviderEnabled =
    // mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    // if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) !=
    // PackageManager.PERMISSION_GRANTED &&
    // checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) !=
    // PackageManager.PERMISSION_GRANTED)
    // return;
    //
    // if (isGpsProviderEnabled) {
    // mLocationManager.requestLocationUpdates(
    // LocationManager.GPS_PROVIDER,
    // 0,
    // 0.0f /* minDistance */,
    // mLocationListener);
    // mLocationManager.requestLocationUpdates(
    // LocationManager.GPS_PROVIDER,
    // 0,
    // 0.0f /* minDistance */,
    // mLocationListener);
    // mLocationManager.addNmeaListener(nmeaMessageListener);
    // }
    //
    // }

    // @Override
    // public void onRequestPermissionsResult(int requestCode, @NonNull String[]
    // permissions, @NonNull int[] grantResults) {
    // super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    // setNmeaMessageListener();
    // }

