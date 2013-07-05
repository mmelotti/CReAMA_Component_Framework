package com.gw.android.perguntaserespostas;

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
	static String ip = "200.137.66.94"; 
	
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
		
		
		Bundle extras = getIntent().getExtras(); 
		String faq;

		if (extras != null) {
		    faq = extras.getString("faq");
		    // and get whatever type user account id is
		}else{
			faq="4";//nao deve vir errado, mas...
		}
		
		
		
		startTransaction();
		FaqListGUI faqList = new FaqListGUI();
		faqList.setUrlVariable(faq);
		FaqSendGUI faqSend = new FaqSendGUI();
		faqSend.setUrlVariable(faq);
		
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
