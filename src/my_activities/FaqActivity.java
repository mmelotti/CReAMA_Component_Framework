package my_activities;

import my_components.faq.FaqListGUI;
import com.example.firstcomponents.R;
import com.example.my_fragment.CRActivity;

import android.os.Bundle;

public class FaqActivity extends CRActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.faq_test);

		startTransaction();
		FaqListGUI faqList = new FaqListGUI();
		
		addGUIComponent(R.id.root, faqList);
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
