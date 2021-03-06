package com.gw.android.first_components.my_components.user;

import com.github.johnpersano.supertoasts.SuperToast;
import com.gw.android.R;
import com.gw.android.Utils.SuperToastUtils;
import com.gw.android.components.request.Request;
import com.gw.android.first_components.my_fragment.CRComponent;

import android.app.Dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class DoLoginGUI extends CRComponent {
	TextView resultTxt;
	String urlLogin;

	EditText editLogin, editPassword;
	Button btnSubmit;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Dialog d = getDialog();
		if (d != null) // componente está sendo mostrado como dialog
			d.setTitle("Login");

		urlLogin = getBaseUrl() + "/users/" + getCollabletId() + "/login";

		View view = inflater.inflate(R.layout.do_login, container, false);
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
				SuperToastUtils.showSuperToast(getActivity(),
						SuperToast.BACKGROUND_GREENTRANSLUCENT,
						"Efetuando login...");
				// Toast.makeText(getActivity(), "Efetuando login...",
				// Toast.LENGTH_SHORT).show();
				loginRequest(login, password);
			}
		});

		return view;
	}

	String loginRequest(String login, String password) {
		Request request = new Request(null, urlLogin, "post", "user.login--"
				+ login + "__user.password--" + password);
		makeRequest(request);
		return "";
	}
}
