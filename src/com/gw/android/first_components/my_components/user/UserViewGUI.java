package com.gw.android.first_components.my_components.user;


import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import com.gw.android.R;
import com.gw.android.components.connection_manager.AsyncRequestHandler;
import com.gw.android.components.request.Request;

import com.gw.android.first_components.my_components.gps.Coordinates;
import com.gw.android.first_components.my_components.tracker.Trackable;
import com.gw.android.first_components.my_fragment.CRActivity;
import com.gw.android.first_components.my_fragment.CRComponent;

public class UserViewGUI extends CRComponent implements Trackable{

	// private LayoutInflater li;
	private String urlView;
	int userUrl;
	private boolean conectado = true;
	private TextView login, name, email, id;
	String uName = "";
	String uLogin = "";
	String uEmail = "";

	String ip = "200.137.66.94";
	String url = "http://" + ip
			+ ":8080/GW-Application-Arquigrafia/groupware-workbench";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		setControlActivity((CRActivity) getActivity());

		// li = inflater;
		View view = inflater.inflate(R.layout.user_view, container, false);

		// url = getUrl();
		userUrl = 1;
		urlView = getBaseUrl() + "/users/" + userUrl + "?_format=json";

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
		getConnectionManager().getCookiesInfo();
		if(isTrackable()){
			doTrackableRequest();
		}
		else{
			viewRequest();
		}
		
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

		
		Long uId = 0L;

		try {
			JSONObject us, userObject;
			us = new JSONObject(r);

			userObject = us.getJSONObject("user");
			uLogin = userObject.get("login").toString();
			uEmail = userObject.get("email").toString();
			uName = userObject.get("name").toString();

			uId = Long.parseLong(userObject.get("id").toString());

			Log.i("Parseando user", " name= " + uName + uId);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		name.setText(name.getText() + uName);
		email.setText(email.getText() + uEmail);
		login.setText(login.getText() + uLogin);
		id.setText(id.getText() + "" + uId);

	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return uName;
	}

	@Override
	public String getComponentType() {
		// TODO Auto-generated method stub
		return "User";
	}

	@Override
	public int getIcon() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Coordinates getCoordinates() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isTrackable(){
		return true;
	}

	@Override
	public void doTrackableRequest() {
		// TODO url deles, falta fazer
		Request request = new Request(null, urlView, "get", null);
		makeRequest(request);
	}
	
	public List<Trackable> getListTrackable(){
		 List<Trackable> li=new ArrayList();
		 User teste = new User();
		 teste.setUsuario("Joao");
		 li.add(teste);
		
		return li;
	}

}
