package com.creama.ideatoolkit.components2.idea;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.creama.interfaces.IList;
import com.gw.android.R;
import com.gw.android.components.connection_manager.AsyncRequestHandler;
import com.gw.android.components.request.Request;
import com.gw.android.first_components.my_fragment.CRComponent;

public abstract class IdeaListGUI extends CRComponent implements IList{

	private final static String urlBase = "http://conecteideias.com:8080/";
	private String finalUrl[];
	private String param = "";
	private int requestType = 0;
	private ViewGroup myContainer;
	private LayoutInflater myInflater;
	private ListView listview;
	private String titulo = "Lista de Ideias";
	private List<Idea> lista = new ArrayList<Idea>();

	public IdeaListGUI(String title) {
		titulo = title;
	}

	public IdeaListGUI(String title, int requestType, String param) {
		titulo = title;
		this.requestType = requestType;
		this.param = param;
	}

	private void createRequestTypes() {
		finalUrl = new String[4];
		finalUrl[0] = "ideias/getAll";
		finalUrl[1] = "mobile/latestIdeas?range=";
		finalUrl[2] = "ideias/getIdeiasRelacionadas?userid=";
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		createRequestTypes();
		View view = inflater.inflate(R.layout.conecte_list_ideias_comp,
				container, false);
		TextView listTitle = (TextView) view.findViewById(R.id.list_titulo);
		listTitle.setText(titulo);
		listview = (ListView) view.findViewById(R.id.ideaListView);
		listview.setOnTouchListener(new ListView.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					// Disallow ScrollView to intercept touch events.
					v.getParent().requestDisallowInterceptTouchEvent(true);
					break;
				case MotionEvent.ACTION_UP:
					// Allow ScrollView to intercept touch events.
					v.getParent().requestDisallowInterceptTouchEvent(false);
					break;
				}
				// Handle ListView touch events.
				v.onTouchEvent(event);
				return true;
			}
		});

		AsyncRequestHandler mHandler = new AsyncRequestHandler() {
			@Override
			public void onSuccess(String response, Request request) {
				Log.i("BEFORE AFTER SUCCES", " ...= ");
				atualizarAfterSucces(response);
				Log.i("INFLATE IDEIAS", " ...= ");
				// inflateIdeasGUI();
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
		Log.i("IDEA LIST ", "before request");
		getIdeasRequest();
	}

	void getIdeasRequest() {
		Request request = new Request(null, urlBase + finalUrl[requestType]
				+ param, "get", null);
		String header[] = new String[2];
		header[0] = "X-ApiKey";
		header[1] = "257F1D3C-57A0-4F34-A937-1538104E97FE";
		request.onlyOneHeader(header);
		// request.setKeyValuePairs(keyValuePairs);
		makeRequest(request);
		Log.i("IDEA LIST ", "after request");
	}

	public void inflateIdeasGUI() {
		ViewGroup layoutList = (ViewGroup) myContainer.findViewById(0);
		layoutList.removeAllViews();
		for (int i = 0; i < 3; i++) {
			View view = myInflater.inflate(R.layout.conecte_list_ideias_comp,
					null);
			((TextView) view.findViewById(R.id.body))
					.setText("" + "texto novo");
			layoutList.addView(view);
		}

	}

	public void atualizarAfterSucces(String r) {
		Log.i("IDEA LIST ", "dentro atualizar after succes");
		lista.clear();
		Idea empty = new Idea();
		empty.setTitle("Carregando...");
		empty.setText("");
		try {
			JSONArray ideasArray;
			Log.i("BEFORE NEW ARRAY em idea", " bug?????...= ");
			ideasArray = new JSONArray(r);
			Log.i("PASS - going to json object", " array length?...= "+ideasArray.length());
			for (int j = 0; j < ideasArray.length(); j++) {
				JSONObject ideasObject = (JSONObject) ideasArray.get(j);
				String titulo, descricao;
				titulo = ideasObject.getString("titulo");
				descricao = ideasObject.getString("descricao");
	
				Log.i("entrando names array", " ...= ");
				/*
				for (int i = 0; i < namesArray.length(); i++) {
					String string = (String) namesArray.get(i);
					Log.i("dentro IDEA names = ", string + " ...= ");
				}*/
				Idea idea = new Idea();
				idea.setText(descricao);
				idea.setTitle(titulo);
				if(requestType!=2){
					Long idServer = ideasObject.getLong("id");
					idea.setServerId(idServer);
				}
				
				lista.add(idea);
				Log.i("Titulo = ", titulo);
				Log.i("Descricao = ", descricao);
				Log.i("before break", " ...= ");
			}
			IdeaListAdapter myAdapter = new IdeaListAdapter(getActivity(),
					lista) {

				@Override
				public void onClickListAdapterItem(View v, Long id) {
					Log.e("ONCLICK", "dentro component IDEA LIST");
					onClickOneItensTitleApplication(v, id);
				}
			};
			listview.setAdapter(myAdapter);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public abstract void onClickOneItensTitleApplication(View v, Long id);

}
