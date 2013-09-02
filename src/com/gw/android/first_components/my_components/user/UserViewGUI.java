package com.gw.android.first_components.my_components.user;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.gw.android.R;
import com.gw.android.components.connection_manager.AsyncRequestHandler;
import com.gw.android.components.request.Request;
import com.gw.android.first_components.my_components.faq.Faq;
import com.gw.android.first_components.my_fragment.CRActivity;
import com.gw.android.first_components.my_fragment.CRComponent;

public class UserViewGUI extends CRComponent {

	private LayoutInflater li;
	private String urlView;
	int userUrl;
	private boolean conectado = true;
	private TextView login, name, email, id;

	String ip = "200.137.66.94";
	String url = "http://" + ip
			+ ":8080/GW-Application-Arquigrafia/groupware-workbench";

	private String getUrl() {
		SharedPreferences testPrefs = getActivity().getApplication()
				.getSharedPreferences("test_prefs", Context.MODE_PRIVATE);
		return testPrefs.getString("arquigrafia_base_url", "");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		setControlActivity((CRActivity) getActivity());

		li = inflater;
		View view = inflater.inflate(R.layout.user_view, container, false);

		// url = getUrl();
		userUrl = 1;
		urlView = url + "/users/" + userUrl + "?_format=json";

		name = (TextView) view.findViewById(R.id.userName);
		email = (TextView) view.findViewById(R.id.userEmail);
		login = (TextView) view.findViewById(R.id.userLogin);
		id = (TextView) view.findViewById(R.id.userId);

		AsyncRequestHandler mHandler = new AsyncRequestHandler() {
			@Override
			public void onSuccess(String response, Request request) {
				atualizarAfterSucces(response);
			}
		};
		setComponentRequestCallback(mHandler);
		return view;
	}

	@Override
	protected void onBind() {
		viewRequest();
	}

	void viewRequest() {
		Request request = new Request(null, urlView, "get", null);
		// se nao estiver conectado, nem vale ir para a fila de request
		// se estiver conectado, vai tentar buscar no servidor as

		// depois salva no cache para acesso offline
		if (conectado)
			makeRequest(request);
		else {// pega no cache
			preencheCampos();
		}
	}

	public void preencheCampos() {
		// preenche com oq ta no cache
	}

	public void atualizarAfterSucces(String r) {
		// TODO parser xml
		name.setText(name.getText() + "Administrador");
		email.setText(email.getText() + "admin@arquigrafia.com.br");
		login.setText(login.getText() + "admin");
		id.setText(id.getText() + "1");
		
		
		
	}

}
