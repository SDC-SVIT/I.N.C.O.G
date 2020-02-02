package sih.hexclan.incog;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.OnNmeaMessageListener;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapFragment extends Fragment implements OnMapReadyCallback {
    private static final String TAG = "MapsFragment";
    private MapView mapView;
    private GoogleMap googleMap;
    private static final String MAP_VIEW_BUNDLE_KEY = "MapViewBundleKey";
    View root;

    private LocationManager mLocationManager;
    private static final int LOCATION_PERMISSION_REQUEST = 1;
    private static final String[] REQUIRED_PERMISSIONS = { Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.CAMERA };
    SharedPreferences sharedPreferences;

    private LocationListener mLocationListener = new LocationListener() {

        @Override
        public void onLocationChanged(Location location) {
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

    private OnNmeaMessageListener nmeaMessageListener = new OnNmeaMessageListener() {
        @Override
        public void onNmeaMessage(String message, long timestamp) {
            // Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
            String[] split = message.split(",");
            Log.d(TAG, "onNmeaMessage: " + message);

        }
    };

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_map, container, false);
        mapView = root.findViewById(R.id.mapView);
        mapView.getMapAsync(this);
        mapView.onCreate(savedInstanceState);
        sharedPreferences = getActivity().getApplicationContext().getSharedPreferences("perm", Context.MODE_PRIVATE);

        mLocationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        if (getActivity()
                .checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && getActivity().checkSelfPermission(
                        Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("perm", true);
            requestPermissions(REQUIRED_PERMISSIONS, LOCATION_PERMISSION_REQUEST);
        } else
            setNmeaMessageListener();
        return root;
    }

    public void setNmeaMessageListener() {
        Toast.makeText(getContext(), "setNmeaMessageListener: Listener added", Toast.LENGTH_SHORT).show();
        boolean isGpsProviderEnabled = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (getActivity()
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
        this.googleMap = googleMap;
        this.googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        this.googleMap.setMaxZoomPreference(1200);

        LatLng svit = new LatLng(22.4690, 73.0763);
        // this.googleMap.addMarker(new MarkerOptions()
        // .position(svit)
        // .title("INCOG")
        // );
        this.googleMap.moveCamera(CameraUpdateFactory.zoomTo(18));
        this.googleMap.moveCamera(CameraUpdateFactory.newLatLng(svit));
        this.googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                googleMap.addMarker(new MarkerOptions().position(latLng).title("Required Location"));
                MainActivity.requiredLocation = latLng;
                ((Button) root.findViewById(R.id.startSnapping)).setVisibility(View.VISIBLE);
            }
        });
    }

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