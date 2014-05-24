package com.creama.conecte.components.idea;

import java.text.DateFormat;
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
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.gw.android.R;
import com.gw.android.components.connection_manager.AsyncRequestHandler;
import com.gw.android.components.request.Request;
import com.gw.android.first_components.my_components.comment.Comment;
import com.gw.android.first_components.my_fragment.CRComponent;
import com.gw.android.first_components.my_fragment.ComponentSimpleModel;

public abstract class IdeaListGUI extends CRComponent {

	private String urlBase = "http://apiconecteideias.azurewebsites.net/";
	private String finalUrl[];
	private String param="";
	private int requestType=0;
	
	private ViewGroup myContainer;
	private LayoutInflater myInflater;
	private ListView listview;

	private String titulo="Lista de Ideias";
	
	
	
	private List<Idea> lista = new ArrayList<Idea>();

	public IdeaListGUI(String title){
		titulo=title;
		
	}
	
	public IdeaListGUI(String title, int requestType, String param){
		titulo=title;
		this.requestType=requestType;
		this.param=param;
		
	}
	
	private void createRequestTypes(){
		finalUrl = new String[4];

		finalUrl[0]="ideias/getAll";
		finalUrl[1]="mobile/latestIdeas?range=";	
		finalUrl[2]="ideas/getIdeiasRelacionadas?userid=";				
	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		createRequestTypes();
		View view = inflater.inflate(R.layout.conecte_list_ideias_act, container,
				false);

		
		TextView listTitle= (TextView) view.findViewById(R.id.list_titulo);
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
		// Log.e("TEST R","setado calback");

		return view;
	}

	// http://conecteideias.azurewebsites.net/Ideia/Create

	// http://apiconecteideias.azurewebsites.net/ideias/searchById?id=1067

	// "X-ApiKey" e o value dele é 257F1D3C-57A0-4F34-A937-1538104E97FE

	@Override
	protected void onBind() {
		// getConnectionManager().getCookiesInfo();
		getIdeasRequest();

	}

	void getIdeasRequest() {

		Request request = new Request(null, urlBase+finalUrl[requestType]+param, "get", null);
		String header[] = new String[2];
		header[0] = "X-ApiKey";
		header[1] = "257F1D3C-57A0-4F34-A937-1538104E97FE";
		request.onlyOneHeader(header);
		// request.setKeyValuePairs(keyValuePairs);

		makeRequest(request);

	}

	public void inflateIdeasGUI() {
		ViewGroup layoutList = (ViewGroup) myContainer.findViewById(0);
		layoutList.removeAllViews();

		for (int i = 0; i < 3; i++) {
			View view = myInflater.inflate(R.layout.conecte_list_ideias_act, null);
			// Comment comm = (Comment) lista.get(i);

			((TextView) view.findViewById(R.id.body))
					.setText("" + "texto novo");

			layoutList.addView(view);
		}

	}

	public void atualizarAfterSucces(String r) {

		lista.clear();
		Idea empty = new Idea();
		empty.setTitle("Carregando...");
		empty.setText("");
		//lista.add(empty);
		try {

			JSONArray ideasArray;
			ideasArray = new JSONArray(r);
			Log.i("PASS - going to json object", " bug?????...= ");

			for (int j = 0; j < ideasArray.length(); j++) {
				JSONObject ideasObject = (JSONObject) ideasArray.get(j);
				JSONArray namesArray = ideasObject.names();

				String titulo, descricao;
				titulo = ideasObject.getString("titulo");
				descricao = ideasObject.getString("descricao");
				Long idServer=ideasObject.getLong("id");

				Log.i("entrando names array", " ...= ");
				for (int i = 0; i < namesArray.length(); i++) {
					String string = (String) namesArray.get(i);
					Log.i("dentro names = ", string + " ...= ");
				}
				Idea idea = new Idea();
				idea.setText(descricao);
				idea.setTitle(titulo);
				idea.setServerId(idServer);
				lista.add(idea);
				Log.i("Titulo = ", titulo);
				Log.i("Descricao = ", descricao);
				// Log.i("Parseando login antes for", ideasObject.toString());
				Log.i("before break", " ...= ");
				// break;
			}

			/*
			 * Log.i("Pass for - going to bug down here", " ...= ");
			 * 
			 * JSONObject ideasObject; ideasObject = new JSONObject(r);
			 * 
			 * JSONArray nameArray = ideasObject.names(); JSONArray valArray =
			 * ideasObject.toJSONArray(nameArray); JSONArray arrayResults =
			 * valArray.getJSONArray(0); Log.i("Parseando login antes for",
			 * " ...= "); for (int j = 0; j < arrayResults.length(); j++) {
			 * JSONObject oneIdea = arrayResults.getJSONObject(j);
			 * Log.i("Parseando login antes for", " ....= "); JSONObject
			 * userObject = oneIdea.getJSONObject("user"); String userName =
			 * userObject.get("name").toString(); Long idServ =
			 * Long.parseLong(oneIdea.get("id").toString()); String text =
			 * oneIdea.get("text").toString();
			 * 
			 * // updating comments, adding on DB Long newI =
			 * ComponentSimpleModel.getUniqueId(getActivity());
			 * 
			 * // initCommentDao(); // commentDao.insert(comment); //
			 * closeDao();
			 * 
			 * Log.i("Parseando login", "text = " + text + idServ + userName); }
			 */
			IdeaListAdapter myAdapter = new IdeaListAdapter(getActivity(),
					lista){

						@Override
						public void onClickOneItensTitleComponent(View v,Long id) {
							// TODO Auto-generated method stub
							Log.e("ONNon clic test","dentro component");
							onClickOneItensTitleApplication(v,id);
						}
				
			};
			listview.setAdapter(myAdapter);
			//myAdapter.clearEmptyItem();

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Log.e("TEST R LOGIN",r);

	}
	
	public abstract void onClickOneItensTitleApplication(View v,Long id);

}
