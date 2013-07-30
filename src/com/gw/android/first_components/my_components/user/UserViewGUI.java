package com.gw.android.first_components.my_components.user;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.gw.android.R;
import com.gw.android.components.request.Request;
import com.gw.android.first_components.my_components.faq.Faq;
import com.gw.android.first_components.my_fragment.CRActivity;
import com.gw.android.first_components.my_fragment.CRComponent;

public class UserViewGUI extends CRComponent {

	private LayoutInflater li;
	private String url, urlView;
	int userUrl;
	private boolean conectado = true;

	
	private String getUrl() {
		SharedPreferences testPrefs = getActivity()
				.getApplication()
				.getSharedPreferences("test_prefs", Context.MODE_PRIVATE);
		return testPrefs.getString("arquigrafia_base_url", "");
	}
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		setControlActivity((CRActivity) getActivity());

		li = inflater;
		View view = inflater.inflate(R.layout.user_view, container, false);

		
		url = getUrl();
		userUrl = 1;
		urlView = url + "/faq/" + userUrl + "?_format=json";
		
		return view;
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

}
