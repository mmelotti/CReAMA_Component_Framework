package my_components.gps;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.example.firstcomponents.R;
import com.example.my_fragment.ComponentSimpleModel;
import com.example.my_fragment.CRComponent;
import com.gw.android.components.sensor_service.SensorManagerService;
import com.gw.android.components.sensor_service.SensorServiceListener;

@SuppressLint("ValidFragment")
public class GPSListener extends CRComponent {

	@SuppressLint("ValidFragment")
	String latV, lonV;
	TextView lat, lon;
	Long idTarget;
	private Coordinates coord;
	private int sensorType = SensorManagerService.TYPE_GPS;
	private SensorServiceListener sensorListener;
	private Intent startIntent;

	public GPSListener(Long t) {
		// TODO Auto-generated constructor stub

		idTarget = t;
	}

	public GPSListener(ComponentSimpleModel c) {
		// TODO Auto-generated constructor stub

		coord = (Coordinates) c;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater
				.inflate(R.layout.coordinate_view, container, false);

		lat = (TextView) view.findViewById(R.id.latitudeValue);
		lon = (TextView) view.findViewById(R.id.longitudeValue);

		sensorListener = new SensorServiceListener(getActivity());
		startIntent = new Intent(
				getActivity(),
				com.gw.android.components.sensor_service.SensorManagerService.class);
		getActivity().startService(startIntent);
		Log.d("start sefvice???", "true");

		// sensorListener.startSamplingSensor(sensorType);

		return view;
	}

	@Override
	public void onStop() {
		super.onStop();
		Log.d("on stop", "true");
		// sensorListener.stopListening();

	}

	@Override
	public void onPause() {
		super.onPause();
		Log.d("on pause", "true");
		sensorListener.stopListening();

	}

	@Override
	public void onResume() {
		super.onResume();
		Log.d("on resume", "true GPS");
		sensorListener.startListening();

	}

	@Override
	public void onDestroy() {
		Log.d("on destroy", "true");
		//getActivity().stopService(startIntent);
		super.onDestroy();
	}
	
	@Override
	public void submittedFrom(Long target){
		
		
	}
	
}
