package com.gw.android.first_components.my_components.tracker;

import java.util.List;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.gw.android.R;
import com.gw.android.first_components.database.DaoMaster;
import com.gw.android.first_components.database.DaoSession;
import com.gw.android.first_components.database.DaoMaster.DevOpenHelper;
import com.gw.android.first_components.my_components.gps.Coordinates;
import com.gw.android.first_components.my_components.gps.CoordinatesDao;
import com.gw.android.first_components.my_fragment.CRComponent;

public class TrackerGUI extends CRComponent {

	private MapView mMapView;
    private GoogleMap mMap;
    private Bundle mBundle;  
    CoordinatesDao coordDao;
	public static CoordinatesDao initCoordinatesDao(Context context) {
		DevOpenHelper helper = new DaoMaster.DevOpenHelper(context,
				"coordinatess-db", null);
		SQLiteDatabase db = helper.getWritableDatabase();
		DaoMaster daoMaster = new DaoMaster(db);
		DaoSession daoSession = daoMaster.newSession();
		return daoSession.getCoordinatesDao();
	}
	
	private void populateMap() {
		List<Coordinates> l = coordDao.loadAll();
		for (Coordinates c : l) {
			mMap.addMarker(new MarkerOptions()
	        .position(new LatLng(c.getLatitude(), c.getLongitude()))
	        .title("Teste")
	        .snippet("Marker teste!")
	        );
		}
	}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflatedView = inflater.inflate(R.layout.tracker_layout, container, false);       

        mMapView = (MapView) inflatedView.findViewById(R.id.map);
        mMapView.onCreate(mBundle);
        mMap = ((MapView) inflatedView.findViewById(R.id.map)).getMap();
        mMap.addMarker(new MarkerOptions().position(new LatLng(-20.27324080467165, -40.30574798583867)).title("UFES"));
        
        coordDao = initCoordinatesDao(getActivity());
        coordDao.deleteAll();
        Coordinates c = new Coordinates(null, null, null, -20.4, -40.30, null, null, null);
        coordDao.insert(c);
        c = new Coordinates(null, null, null, -20.2, -40.1, null, null, null);
        coordDao.insert(c);
        populateMap();
        coordDao.getDatabase().close();
        
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
