package com.gw.android.first_components.my_components.tracker;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.gw.android.R;
import com.gw.android.first_components.my_fragment.CRComponent;

public class TrackerGUI extends CRComponent {

	private MapView mMapView;
    private GoogleMap mMap;
    private Bundle mBundle;    

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflatedView = inflater.inflate(R.layout.tracker_layout, container, false);

        mMapView = (MapView) inflatedView.findViewById(R.id.map);
        mMapView.onCreate(mBundle);
        mMap = ((MapView) inflatedView.findViewById(R.id.map)).getMap();
        mMap.addMarker(new MarkerOptions().position(new LatLng(-20.27324080467165, -40.30574798583867)).title("UFES"));
        return inflatedView;
    }

    @Override 
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBundle = savedInstanceState;
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        mMapView.onDestroy();
        super.onDestroy();
    }

}
