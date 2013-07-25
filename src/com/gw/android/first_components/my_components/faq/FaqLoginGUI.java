package com.gw.android.first_components.my_components.faq;

import com.gw.android.R;
import com.gw.android.components.request.Request;
import com.gw.android.first_components.my_fragment.CRComponent;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class FaqLoginGUI extends CRComponent { 
	TextView resultTxt;
	String urlLogin; 

	EditText editLogin, editPassword; 
	Button btnSubmit; 
	
	private String getUrl() {
		SharedPreferences testPrefs = getActivity()
				.getApplication()
				.getSharedPreferences("test_prefs", Context.MODE_PRIVATE);
		return testPrefs.getString("faq_base_url", "");
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Dialog d = getDialog();
		if (d != null)	// componente está sendo mostrado como dialog
			d.setTitle("FAQ Login");

		urlLogin = getUrl() + "/users/"+ getCollabletId() +"/login"; 
		
		View view = inflater.inflate(R.layout.faq_login, container, false);
		btnSubmit = (Button) view.findViewById(R.id.btnSubmit);
		editLogin = (EditText) view.findViewById(R.id.editLogin);
		editPassword = (EditText) view.findViewById(R.id.editPassword);
		
		// TODO apagar isso aqui, é só pra teste
		editLogin.setText("admin");
		editPassword.setText("123");
		
		btnSubmit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String login = editLogin.getText().toString();
				String password = editPassword.getText().toString();
				loginRequest(login, password);
			}
		});
		
		return view;
	}

	String loginRequest(String login, String password) {
		Request request = new Request(null, urlLogin, "post",
				"user.login--" + login + "__user.password--" + password);
		makeRequest(request);
		return "";
	}
}
