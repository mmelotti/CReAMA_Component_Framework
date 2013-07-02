package com.gw.android.first_components.my_components.faq;

import com.gw.android.R;
import com.gw.android.components.connection_manager.AsyncRequestHandler;
import com.gw.android.components.request.Request;
import com.gw.android.first_components.my_fragment.CRComponent;
import com.gw.android.perguntaserespostas.FaqActivity;

import android.app.Dialog;
import android.content.Intent;
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
	String urlLogin = FaqActivity.url + "/users/9/login"; 
	String urlList = FaqActivity.url + "/faq/4/list?_format=json";
	String urlSave = FaqActivity.url + "/faq/4";

	EditText editLogin, editPassword; 
	Button btnSubmit; 
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Dialog d = getDialog();
		if (d != null)	// componente está sendo mostrado como dialog
			d.setTitle("FAQ Login");
		
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
		//login = "admin";
		//password = "123";
		Request request = new Request(null, urlLogin, "post",
				"user.login--" + login + "__user.password--" + password);
		getConnectionManager().makeRequest(request, getActivity(),
				new AsyncRequestHandler() {
					@Override
					public void onSuccess(String response) {
						//Log.e("onsuccess", response); 
						Intent loginDone = new Intent(getActivity(), FaqActivity.class);		
						FaqLoginGUI.this.startActivity(loginDone);
					}
				});
		return "";
	}
}
