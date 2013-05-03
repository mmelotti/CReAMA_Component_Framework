package my_activities;

import com.example.firstcomponents.R;
import com.gw.android.components.sensor_service.SensorManagerService;
import com.gw.android.components.sensor_service.SensorServiceListener;

import android.app.Activity;
import android.content.Intent;
import android.hardware.Sensor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

public class SensoresActivity extends Activity implements OnClickListener {
	SensorServiceListener sensorListener;
	Intent startIntent;
	boolean acelOn = false, lumOn = false, magOn = false, GPSOn = false;

	@Override
	public void onClick(View v) {
		int sensorType = 0;
		boolean turnOn = false;

		switch (v.getId()) {
		case R.id.btnAcel:
			sensorType = Sensor.TYPE_ACCELEROMETER;
			acelOn = !acelOn;
			turnOn = acelOn;
			break;

		case R.id.btnLum:
			sensorType = SensorManagerService.TYPE_NETWORK;
			lumOn = !lumOn;
			turnOn = lumOn;
			break;

		case R.id.btnMag:
			sensorType = Sensor.TYPE_MAGNETIC_FIELD;
			magOn = !magOn;
			turnOn = magOn;
			break;

		case R.id.btnGPS:
			sensorType = SensorManagerService.TYPE_GPS;
			GPSOn = !GPSOn;
			turnOn = GPSOn;
			break;

		case R.id.btnOff:
			//stopService(startIntent);
			break;

		default:
			sensorType = 0;
			break;
		}

		sendBroadcast(sensorType, turnOn);

		double[] array = sensorListener.getSensorValues(sensorType);
		if (array != null)
			Log.e("valor do sensor " + sensorType, array[0] + "  " + array[1]
					+ "  " + array[2]);
		else
			Log.e("array nulo", "o sensor ainda nao registrou nenhum valor");
	}

	protected void onPause() {
		super.onPause();
		sensorListener.stopListening();
	}

	@Override
	protected void onResume() {
		super.onResume();
		sensorListener.startListening();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sensores_test);

		sensorListener = new SensorServiceListener(this);
		startIntent = new Intent(
				this,
				com.gw.android.components.sensor_service.SensorManagerService.class);
		startService(startIntent);
		Log.d("start sefvice???", "true");
		
		
		findViewById(R.id.btnAcel).setOnClickListener(this);
		findViewById(R.id.btnLum).setOnClickListener(this);
		findViewById(R.id.btnMag).setOnClickListener(this);
		findViewById(R.id.btnOff).setOnClickListener(this);
		findViewById(R.id.btnGPS).setOnClickListener(this);
	}

	@Override
	public void onDestroy() {
		//stopService(startIntent);
		super.onDestroy();
	}

	public void sendBroadcast(int type, boolean turnOn) {
		if (turnOn)
			sensorListener.startSamplingSensor(type);
		else
			sensorListener.stopSamplingSensor(type);
	}

}
