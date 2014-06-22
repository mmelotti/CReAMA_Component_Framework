package com.creama.conecte.components.action;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;


import com.creama.conecte.components.idea.Idea;
import com.creama.interfaces.IList;
import com.github.johnpersano.supertoasts.SuperToast;
import com.gw.android.R;
import com.gw.android.Utils.SuperToastUtils;
import com.gw.android.components.connection_manager.AsyncRequestHandler;
import com.gw.android.components.request.Request;
import com.gw.android.first_components.my_fragment.CRComponent;

@SuppressLint("ValidFragment")
public abstract class ActionListGUI extends CRComponent implements IList{

	private final static String urlBase = "http://apiconecteideias.azurewebsites.net/";
	private ListView listview;
	private final static String urlFinal = "feed/lastestActivitiesbyId?Range=";
	private final static String urlFinal2 = "&id=";
	private List<Action> lista = new ArrayList<Action>();
	private Long userId;

	public ActionListGUI(Long serverId) {
		this.userId = serverId;
		
		Log.e("Request??", "after construtor");
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.conecte_list_actions_comp,
				container, false);

		// title=(TextView) view.findViewById(R.id.idea_titulo_one);
		// descricao=(TextView)view.findViewById(R.id.idea_body_one);

		listview = (ListView) view.findViewById(R.id.listActions);
		AsyncRequestHandler mHandler = new AsyncRequestHandler() {
			@Override
			public void onSuccess(String response, Request request) {
				atualizarAfterSucces(response);
			}

			@Override
			public void onFailure(Throwable arg0, String arg1, Request request) {
				// atualizarAfterSucces("erro");
				if (arg1.contains("não encontrado")) {
					SuperToastUtils.showSuperToast(getActivity(),
							SuperToast.BACKGROUND_GREENTRANSLUCENT,
							"Nenhuma atividade recente.");
				}
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

		Request request = new Request(null, urlBase + urlFinal + 5 + urlFinal2
				+ userId, "get", null);
		String header[] = new String[2];
		header[0] = "X-ApiKey";
		header[1] = "257F1D3C-57A0-4F34-A937-1538104E97FE";
		request.onlyOneHeader(header);
		// request.setKeyValuePairs(keyValuePairs);
		makeRequest(request);

	}

	public void atualizarAfterSucces(String r) {

		lista.clear();
		Idea empty = new Idea();
		empty.setTitle("Carregando...");
		empty.setText("");
		try {
			JSONArray ideasArray;
			ideasArray = new JSONArray(r);
			Log.i("PASS - going to json object", " bug?????...= ");
			for (int j = 0; j < ideasArray.length(); j++) {
				JSONObject ideasObject = (JSONObject) ideasArray.get(j);
				JSONArray namesArray = ideasObject.names();
				String texto, dataHora,nomeUsuario;
				texto = ideasObject.getString("texto");
				nomeUsuario = ideasObject.getString("nomeUsuario");
				
				dataHora = ideasObject.getString("dataHora");
				Log.i("entrando names array", " ...= ");
			
				Action action = new Action();
				action.setTexto(texto);
				action.setTitulo(texto);
				action.setNomeUsuario(nomeUsuario);
				//TODO acertar hora
				action.setDataHora(null);
				lista.add(action);
				Log.i("texto = ", texto);
				Log.i("data hora = ", dataHora);
				Log.i("before break", " ...= ");
			}

			ActionListAdapter myAdapter = new ActionListAdapter(lista) {
				@Override
				public void onClickOneItensTitleComponent(View v, Long id) {
					Log.e("ONCLICK", "dentro component ACTION LIST impadater");
					onClickListItem(v, id);
				}

				@Override
				public void onClickListAdapterItem(View v, Long id) {
					// TODO Auto-generated method stub
					
				}
			};
			listview.setAdapter(myAdapter);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	
	public void onClickListItem(View v,Long id){
		Log.e("ONCLICK", "dentro component ACTION LIST");
	}

}