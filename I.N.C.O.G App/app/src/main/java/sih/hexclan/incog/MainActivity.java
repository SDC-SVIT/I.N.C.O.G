package sih.hexclan.incog;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;

import com.google.android.gms.maps.model.LatLng;

public class MainActivity extends AppCompatActivity {

    static LatLng requiredLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Fragment mapFragment = new MapFragment();
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout,mapFragment);
        fragmentTransaction.commit();
    }

    public void activateClick(View view) {
        Fragment cameraFrag = new CameraFragment();
        this.getSupportFragmentManager().beginTransaction().replace(R.id.cameraFragment,cameraFrag).commit();
    }
}
