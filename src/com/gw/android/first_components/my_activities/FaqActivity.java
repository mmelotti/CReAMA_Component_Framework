package com.gw.android.first_components.my_activities;

import com.gw.android.R;
import com.gw.android.components.sensor_service.SensorManagerService;
import com.gw.android.components.sensor_service.SensorServiceListener;
import com.gw.android.first_components.my_components.faq.FaqListGUI;
import com.gw.android.first_components.my_components.faq.FaqSendGUI;
import com.gw.android.first_components.my_fragment.CRActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class FaqActivity extends CRActivity {
	static String ip = "192.168.1.119";
	private Intent startIntent;
	private SensorServiceListener sensorListener;
	
	public static  String url = "http://" + ip
			+ ":8080/GW-Application-FAQ/groupware-workbench";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.blank);

		
		sensorListener = new SensorServiceListener(this);
		startIntent = new Intent(
				this,
				com.gw.android.components.sensor_service.SensorManagerService.class);
		startService(startIntent);
		Log.d("start sefvice???", "true");
		sensorListener.startListening();
		ligar();
		
		startTransaction();
		FaqListGUI faqList = new FaqListGUI();
		FaqSendGUI faqSend = new FaqSendGUI();

		addGUIComponent(R.id.root, faqList);
		addGUIComponent(R.id.root, faqSend);
		finishTransaction();
	}

	@Override
	public void configurarTargets() {
		// TODO Auto-generated method stub

	}

	@Override
	public void instanciarComponents() {
		// TODO Auto-generated method stub

	}

	

	@Override
	protected void onPause() {
		super.onPause();
		sensorListener.stopListening();
	}

	@Override
	protected void onResume() {
		super.onResume();
		sensorListener.startListening();
	}
	
	private void ligar(){
		sensorListener.startSamplingSensor(SensorManagerService.TYPE_NETWORK);
	}
	
}
