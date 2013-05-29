package my_activities;

import my_components.faq.FaqListGUI;
import my_components.faq.FaqSendGUI;

import com.example.firstcomponents.R;
import com.example.my_fragment.CRActivity;

import android.os.Bundle;

public class FaqActivity extends CRActivity {
	static String ip = "192.168.1.106";
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
