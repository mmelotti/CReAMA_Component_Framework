package com.gw.android.first_components.conecte.ideia;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gw.android.R;
import com.gw.android.components.connection_manager.AsyncRequestHandler;
import com.gw.android.components.request.Request;
import com.gw.android.first_components.my_fragment.CRComponent;
import com.gw.android.first_components.my_fragment.ComponentSimpleModel;

public class IdeiaListGUI extends CRComponent {

	private String urlTest = "http://apiconecteideias.azurewebsites.net/ideias/getAll";

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.conecte_list_ideias, container, false);
		
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
		//Log.e("TEST R","setado calback");
		
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
		
		
		try {
			
			JSONArray ideasArray;
			ideasArray = new JSONArray(r);
			Log.i("Pass - going to json object", " ...= ");
		
			for (int j = 0; j < ideasArray.length(); j++) {
				JSONObject ideasObject = (JSONObject) ideasArray.get(j);
				JSONArray namesArray = ideasObject.names();
				
				String titulo, descricao;
				titulo = ideasObject.getString("titulo");
				descricao = ideasObject.getString("descricao");
				
				Log.i("entrando names array", " ...= ");
					for (int i = 0; i < namesArray.length(); i++) {
					String string=(String) namesArray.get(i);
					Log.i("dentro names = ", string+" ...= ");
				}
				Log.i("Titulo = ", titulo);
				Log.i("Descricao = ", descricao);
				Log.i("Parseando login antes for", ideasObject.toString());
				Log.i("before break", " ...= ");
				//break;
			}
			
			Log.i("Pass for - going to bug down here", " ...= ");
			
			JSONObject ideasObject;
			ideasObject = new JSONObject(r);
			
			

			JSONArray nameArray = ideasObject.names();
			JSONArray valArray = ideasObject.toJSONArray(nameArray);
			JSONArray arrayResults = valArray.getJSONArray(0);
			Log.i("Parseando login antes for", " ...= ");
			for (int j = 0; j < arrayResults.length(); j++) {
				JSONObject oneIdea = arrayResults.getJSONObject(j);
				Log.i("Parseando login antes for", " ....= ");
				JSONObject userObject = oneIdea.getJSONObject("user");
				String userName = userObject.get("name").toString();
				Long idServ = Long.parseLong(oneIdea.get("id").toString());
				String text = oneIdea.get("text").toString();

				// updating comments, adding on DB
				Long newI = ComponentSimpleModel.getUniqueId(getActivity());
				
				//initCommentDao();
				//commentDao.insert(comment);
				//closeDao();

				
				Log.i("Parseando login", "text = " + text + idServ
						+ userName);
			}
			//listview.setAdapter(new CommentAdapter(getActivity(), lista));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Log.e("TEST R LOGIN",r);
		
	}

}
