package com.gw.android.first_components.my_components.tracker;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnCameraChangeListener;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.gw.android.R;
import com.gw.android.first_components.database.DaoMaster;
import com.gw.android.first_components.database.DaoSession;
import com.gw.android.first_components.database.DaoMaster.DevOpenHelper;
import com.gw.android.first_components.my_components.gps.Coordinates;
import com.gw.android.first_components.my_components.gps.CoordinatesDao;
import com.gw.android.first_components.my_fragment.CRComponent;

@SuppressLint("ValidFragment")
public class TrackerGUI extends CRComponent {

	List<Coordinates> l;
	List<Trackable> li;
	float lastZoom;
	private MapView mMapView;
	private GoogleMap mMap;
	private Bundle mBundle;
	CoordinatesDao coordDao;

	public TrackerGUI(List<Trackable> li) {
		this.li = li;
	}

	public TrackerGUI() {

	}

	public static CoordinatesDao initCoordinatesDao(Context context) {
		DevOpenHelper helper = new DaoMaster.DevOpenHelper(context,
				"coordinatess-db", null);
		SQLiteDatabase db = helper.getWritableDatabase();
		DaoMaster daoMaster = new DaoMaster(db);
		DaoSession daoSession = daoMaster.newSession();
		return daoSession.getCoordinatesDao();
	}

	public void populateMap(float zoom) {
		mMap.clear();
		int iconResource;

		if (zoom <= 6)
			iconResource = R.drawable.picture_small;
		else if (zoom <= 12)
			iconResource = R.drawable.picture_medium;
		else
			iconResource = R.drawable.picture_large;

		for (Coordinates c : l) {
			mMap.addMarker(new MarkerOptions()
					.position(new LatLng(c.getLatitude(), c.getLongitude()))
					.title("Teste").snippet("Marker teste!")
					.icon(BitmapDescriptorFactory.fromResource(iconResource)));
		}

		for (Trackable t : li) {
			mMap.addMarker(new MarkerOptions()
					.position(
							new LatLng(t.getCoordinates().getLatitude(), t
									.getCoordinates().getLongitude()))
					.title(t.getComponentType())
					.snippet(t.getName())
					.icon(BitmapDescriptorFactory.fromResource(t
							.getIconResource())));
		}

	}

	public OnCameraChangeListener getCameraChangeListener() {
		return new OnCameraChangeListener() {
			@Override
			public void onCameraChange(CameraPosition position) {
				Log.e("ZOOM", "LEVEL: " + position.zoom);
				if (lastZoom <= 6 && position.zoom > 6 || lastZoom <= 12
						&& lastZoom > 6
						&& !(position.zoom <= 12 && position.zoom > 6)
						|| lastZoom > 12 && position.zoom < 12)
					populateMap(position.zoom);
				lastZoom = position.zoom;
			}
		};
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View inflatedView = inflater.inflate(R.layout.tracker_layout,
				container, false);

		mMapView = (MapView) inflatedView.findViewById(R.id.map);
		mMapView.onCreate(mBundle);
		mMap = ((MapView) inflatedView.findViewById(R.id.map)).getMap();
		mMap.addMarker(new MarkerOptions().position(
				new LatLng(-20.27324080467165, -40.30574798583867)).title(
				"UFES"));
		mMap.setOnCameraChangeListener(getCameraChangeListener());

		coordDao = initCoordinatesDao(getActivity());
		coordDao.deleteAll();
		Coordinates c = new Coordinates(null, null, null, -20.4, -40.30, null,
				null, null);
		coordDao.insert(c);
		c = new Coordinates(null, null, null, -20.2, -40.1, null, null, null);
		coordDao.insert(c);

		try {
			MapsInitializer.initialize(getActivity());
		} catch (GooglePlayServicesNotAvailableException e) {
			e.printStackTrace();
		}
		l = coordDao.loadAll();
		lastZoom = 1l;
		populateMap(1l);

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
