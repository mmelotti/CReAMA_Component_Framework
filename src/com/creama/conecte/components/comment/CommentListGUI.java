package com.creama.conecte.components.comment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.creama.conecte.components.idea.Idea;
import com.gw.android.R;
import com.gw.android.components.connection_manager.AsyncRequestHandler;
import com.gw.android.components.request.Request;
import com.gw.android.first_components.my_fragment.CRComponent;

@SuppressLint("ValidFragment")
public class CommentListGUI extends CRComponent {

	TextView title,descricao;
	private String urlTest = "http://apiconecteideias.azurewebsites.net/ideias/searchById?id=";

	
	private String urlFinal = "/feed/lastestActivitiesbyId?Range=";
	private String urlFinal2 = "&id=";
	
	
	Long serverId;
	
	public CommentListGUI(Long serverId){
		this.serverId=serverId;
		Log.e("Request??","after construtor");
	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.conecte_one_idea_comp, container,
				false);
		
		
		title=(TextView) view.findViewById(R.id.idea_titulo_one);
		descricao=(TextView)view.findViewById(R.id.idea_body_one);
		

		AsyncRequestHandler mHandler = new AsyncRequestHandler() {
			@Override
			public void onSuccess(String response, Request request) {
				atualizarAfterSucces(response);
			}

			@Override
			public void onFailure(Throwable arg0, String arg1, Request request) {
				//atualizarAfterSucces("erro");
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

		Request request = new Request(null, urlTest+serverId, "get", null);
		String header[] = new String[2];
		header[0] = "X-ApiKey";
		header[1] = "257F1D3C-57A0-4F34-A937-1538104E97FE";
		request.onlyOneHeader(header);
		// request.setKeyValuePairs(keyValuePairs);

		 makeRequest(request);

	}

	public void atualizarAfterSucces(String r) {
		try {

			Log.i("onde ideia", " ...= ");

			JSONObject ideasObject;
			ideasObject = new JSONObject(r);

			JSONArray nameArray = ideasObject.names();
			JSONArray valArray = ideasObject.toJSONArray(nameArray);
			JSONArray arrayResults = valArray.getJSONArray(0);
			
			Idea idea;
			
			Log.i("Parseando login antes for",
					" ...= " + ideasObject.toString());
			for (int j = 0; j < nameArray.length(); j++) {
				//JSONObject oneIdea = arrayResults.getJSONObject(j);
				String name=nameArray.getString(j);
				Log.i("Parseando ideia dentro for",
						" ....= " + name);
				/*
				 * JSONObject userObject = oneIdea.getJSONObject("user"); String
				 * userName = userObject.get("name").toString(); Long idServ =
				 * Long.parseLong(oneIdea.get("id").toString()); String text =
				 * oneIdea.get("text").toString();
				 * 
				 * // updating comments, adding on DB Long newI =
				 * ComponentSimpleModel.getUniqueId(getActivity());
				 * 
				 * // initCommentDao(); // commentDao.insert(comment); //
				 * closeDao();
				 * 
				 * Log.i("Parseando login", "text = " + text + idServ +
				 * userName);
				 */
			}
			idea=new Idea();
			idea.setTitle(ideasObject.getString("titulo"));
			idea.setText(ideasObject.getString("descricao"));
			//setComponentGUI(idea);
			// listview.setAdapter(new CommentAdapter(getActivity(), lista));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Log.e("TEST R LOGIN",r);

	}

	
	private void setComponentGUI(Idea idea){
		
		title.setText(idea.getTitle());
		descricao.setText(idea.getText());
	}
	
}