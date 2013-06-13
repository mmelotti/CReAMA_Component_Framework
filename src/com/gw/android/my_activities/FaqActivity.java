package com.gw.android.my_activities;

import com.gw.android.R;
import com.gw.android.my_components.faq.FaqListGUI;
import com.gw.android.my_components.faq.FaqSendGUI;
import com.gw.android.my_fragment.CRActivity;

import android.os.Bundle;

public class FaqActivity extends CRActivity {
	static String ip = "192.168.3.104";
	public static  String url = "http://" + ip
			+ ":8080/GW-Application-FAQ/groupware-workbench";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.blank);

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

}
