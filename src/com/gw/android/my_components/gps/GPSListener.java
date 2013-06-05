package com.gw.android.my_components.gps;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Address;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gw.android.R;
import com.gw.android.components.sensor_service.SensorManagerService;
import com.gw.android.components.sensor_service.SensorServiceListener;
import com.gw.android.my_fragment.CRComponent;
import com.gw.android.my_fragment.ComponentSimpleModel;

@SuppressLint("ValidFragment")
public class GPSListener extends CRComponent {

	@SuppressLint("ValidFragment")
	String latV, lonV;
	TextView lat, lon;
	Long idTarget;
	private Coordinates coord;
	private Context mCtx;
	private int sensorType = SensorManagerService.TYPE_GPS;
	private SensorServiceListener sensorListener;

	public GPSListener(Long t) {
		idTarget = t;
	}

	public GPSListener(ComponentSimpleModel c) {
		coord = (Coordinates) c;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater
				.inflate(R.layout.coordinate_view, container, false);

		lat = (TextView) view.findViewById(R.id.latitudeValue);
		lon = (TextView) view.findViewById(R.id.longitudeValue);

		mCtx = getActivity().getApplicationContext();
		sensorListener = new SensorServiceListener(mCtx);
		/*startIntent = new Intent(
				mCtx,
				com.gw.android.components.sensor_service.SensorManagerService.class);
		mCtx.startService(startIntent);*/
		Log.d("start service", "true");

		return view;
	}

	@Override
	public void onPause() {
		super.onPause();
		Log.d("on pause", "true");
		sensorListener.stopListening();
		sensorListener.stopSamplingSensor(sensorType);
	}

	@Override
	public void onResume() {
		super.onResume();
		Log.d("on resume", "true GPS"); 
		sensorListener.startListening();
		sensorListener.startSamplingSensor(sensorType);
	}

	@Override
	public void submittedFrom(Long target) {
		Coordinates c = getCoordinates(mCtx, target);
		CoordinatesDao coordDao = GPSViewGUI.initCoordDao(mCtx);
		coordDao.insert(c);
		coordDao.getDatabase().close();
	}

	public Coordinates getCoordinates(Context ctx, Long target) {
		double[] array = sensorListener.getSensorValues(sensorType);
		Address ad = GPSViewGUI.getAddress(ctx, array[0], array[1]);
		
		if (ad != null) 
			coord = new Coordinates(ComponentSimpleModel.getUniqueId(ctx), target, array[0], array[1], ad.getAddressLine(0), ad.getAddressLine(1), ad.getAddressLine(2));
		else
			coord = new Coordinates(ComponentSimpleModel.getUniqueId(ctx), target, array[0], array[1], null, null, null);
		
		return coord;
	}
}
