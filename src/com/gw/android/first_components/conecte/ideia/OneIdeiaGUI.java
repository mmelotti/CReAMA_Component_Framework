package com.gw.android.first_components.conecte.ideia;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gw.android.R;
import com.gw.android.components.connection_manager.AsyncRequestHandler;
import com.gw.android.components.request.Request;
import com.gw.android.first_components.my_fragment.CRComponent;

public class OneIdeiaGUI extends CRComponent {

	private String urlTest = "http://apiconecteideias.azurewebsites.net/ideias/getAll";

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.conecte_one_ideia, container, false);
		
		AsyncRequestHandler mHandler = new AsyncRequestHandler() {
			@Override
			public void onSuccess(String response, Request request) {
				atualizarAfterSucces(response);
			}
			@Override
			public void onFailure(Throwable arg0, String arg1, Request request) {
				atualizarAfterSucces("erro");
			}
		};
		setComponentRequestCallback(mHandler);
	
		
		return view;
	}

	// http://conecteideias.azurewebsites.net/Ideia/Create

	// http://apiconecteideias.azurewebsites.net/ideias/searchById?id=1067

	// "X-ApiKey" e o value dele é 257F1D3C-57A0-4F34-A937-1538104E97FE

	@Override
	protected void onBind() {
		// getConnectionManager().getCookiesInfo();
		testRequest();

	}

	void testRequest() {
		
	
		Request request = new Request(null, urlTest, "get", null);
		String header[]=new String[2];
		header[0]="X-ApiKey";
		header[1]="257F1D3C-57A0-4F34-A937-1538104E97FE";
		request.onlyOneHeader(header);
		//request.setKeyValuePairs(keyValuePairs);
		
		//makeRequest(request);

	}
	
	public void atualizarAfterSucces(String r) {
		//Log.e("TEST R",r);
		
	}

}
