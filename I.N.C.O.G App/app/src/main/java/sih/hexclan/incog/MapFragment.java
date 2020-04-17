package sih.hexclan.incog;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.ImageFormat;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.OnNmeaMessageListener;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Objects;

public class MapFragment extends Fragment implements OnMapReadyCallback {
    private static final String TAG = "MapsFragment";
    private MapView mapView;
    private static final String MAP_VIEW_BUNDLE_KEY = "MapViewBundleKey";
    private View root;
    private static Location location;

    private LocationManager mLocationManager;
    private static final int LOCATION_PERMISSION_REQUEST = 1;
    private static final String[] REQUIRED_PERMISSIONS = {Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.CAMERA};
    private Marker marker;
    private LocationListener mLocationListener = new LocationListener() {

        @Override
        public void onLocationChanged(Location location) {
            MapFragment.location = location;
            Log.d(TAG, "onLocationChanged: " + location.getLatitude() + " " + location.getLongitude());
            LatLng myLatLng = new LatLng(location.getLatitude(), location.getLongitude());
            // googleMap.moveCamera(CameraUpdateFactory.newLatLng(myLatLng));
            Toast.makeText(getContext(), "onLocationChanged: " + location.getLatitude() + " " + location.getLongitude(),
                    Toast.LENGTH_SHORT).show();
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
    };

    private OnNmeaMessageListener nmeaMessageListener = (message, timestamp) -> {
        // Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
        String[] split = message.split(",");
//            Log.d(TAG, "onNmeaMessage: " + message);

    };

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_map, container, false);
        mapView = root.findViewById(R.id.mapView);
        mapView.getMapAsync(this);
        mapView.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences = Objects.requireNonNull(getActivity()).getApplicationContext().getSharedPreferences("perm", Context.MODE_PRIVATE);

//        root.findViewById(R.id.startSnapping).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                nt cameraFrag = new CameraFragment();
//                    Toast.makeText(getActivity(), "Click Activated!! Latlong are:"+MainActivity.requiredLocation.latitude+" "+MainActivity.requiredLocation.longitude, Toast.LENGTH_SHORT).show();
//                    Log.d(TAG, "activateClick: Latlong are : "+MainActivity.requiredLocation.latitude+" "+MainActivity.requiredLocation.longitude);
//                    try {
//                        Navigation.findNavController(getActivity(),R.id.frame_layout).navigate(MapFragmentDirections.actionMapFragmentToCameraFragment(((CameraManager) getActivity().getSystemService(Context.CAMERA_SERVICE)).getCameraIdList()[0], ImageFormat.JPEG));
//
//                    } catch (CameraAccessException e) {
//                        e.printStackTrace();
//                    }
////        this.getSupportFragmentManager().beginTransaction().replace(R.id.cameraFragment, cameraFrag).commit();
//                }
//
//        });

        mLocationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        if (getActivity()
                .checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && getActivity().checkSelfPermission(
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("perm", true);
            requestPermissions(REQUIRED_PERMISSIONS, LOCATION_PERMISSION_REQUEST);
            editor.apply();
        } else
            setNmeaMessageListener();
        return root;
    }

    private void setNmeaMessageListener() {
        Toast.makeText(getContext(), "setNmeaMessageListener: Listener added", Toast.LENGTH_SHORT).show();
        boolean isGpsProviderEnabled = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (Objects.requireNonNull(getActivity())
                .checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && getActivity().checkSelfPermission(
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            return;

        if (isGpsProviderEnabled) {
            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0.0f /* minDistance */,
                    mLocationListener);
            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0.0f /* minDistance */,
                    mLocationListener);
            mLocationManager.addNmeaListener(nmeaMessageListener);
        }

    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        googleMap.setMaxZoomPreference(1200);

        LatLng svit = new LatLng(22.4690, 73.0763);
        // this.googleMap.addMarker(new MarkerOptions()
        // .position(svit)
        // .title("INCOG")
        // );

        googleMap.moveCamera(CameraUpdateFactory.zoomTo(18));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(svit));
        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                if (marker!=null){marker.remove();}
                marker = googleMap.addMarker(new MarkerOptions().position(latLng).title("Required Location"));
                MainActivity.requiredLocation = latLng;
                ((Button) root.findViewById(R.id.startSnapping)).setVisibility(View.VISIBLE);
            }
        });
    }

//    public void activateClick(View view) {
////        Fragment cameraFrag = new CameraFragment();
//        Toast.makeText(getActivity(), "Click Activated!! Latlong are:"+MainActivity.requiredLocation.latitude+" "+MainActivity.requiredLocation.longitude, Toast.LENGTH_SHORT).show();
//        Log.d(TAG, "activateClick: Latlong are : "+MainActivity.requiredLocation.latitude+" "+MainActivity.requiredLocation.longitude);
//        try {
////            Navigation.findNavController(getActivity(),R.id.frame_layout).navigate(MapFragmentDirections.actionMapFragmentToCameraFragment(((CameraManager) getActivity().getSystemService(Context.CAMERA_SERVICE)).getCameraIdList()[0], ImageFormat.JPEG));
//
//        } catch (CameraAccessException e) {
//            e.printStackTrace();
//        }
////        this.getSupportFragmentManager().beginTransaction().replace(R.id.cameraFragment, cameraFrag).commit();
//    }


    @Override
    public void onDestroy() {
        mapView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        mapView.onLowMemory();
        super.onLowMemory();
    }

    @Override
    public void onPause() {
        mapView.onPause();
        super.onPause();
    }

    @Override
    public void onResume() {

        mapView.onResume();
        super.onResume();
    }

    @Override
    public void onStart() {
        mapView.onStart();
        super.onStart();
    }

    @Override
    public void onStop() {
        mapView.onStop();
        super.onStop();
    }

}