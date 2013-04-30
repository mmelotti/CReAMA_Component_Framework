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

@SuppressLint("ValidFragment")
public class GPSViewGUI extends CRComponent {

	@SuppressLint("ValidFragment")
	String latV, lonV;
	TextView lat, lon;
	Long idTarget;
	private Coordinates coord;
	

	public GPSViewGUI(Long t) {
		// TODO Auto-generated constructor stub

		idTarget = t;
	}

	public GPSViewGUI(ComponentSimpleModel c) {
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

		return view;
	}

}
