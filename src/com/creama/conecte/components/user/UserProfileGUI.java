package com.creama.conecte.components.user;

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

import com.gw.android.R;
import com.gw.android.components.connection_manager.AsyncRequestHandler;
import com.gw.android.components.request.Request;
import com.gw.android.first_components.my_fragment.CRComponent;

@SuppressLint("ValidFragment")
public class UserProfileGUI extends CRComponent {

	TextView userName, userEmail, userNascimento;
	// private String urlTest =
	// "http://apiconecteideias.azurewebsites.net/ideias/searchById?id=";
	private String urlUser = "http://apiconecteideias.azurewebsites.net/usuarios/searchByEmail?email=";
	/*
	 * Raft: ".../ideias/getIdeiasRelacionadas?userid=XXXX" Raft:
	 * ".../imagens/lastestByUser?Range=XX&userID=XXXX" Raft:
	 * ".../feed/lastestActivitiesbyId?Range=XX&id=XXXX" Raft: essas tres url
	 */
	Long serverId;

	public UserProfileGUI(Long serverId) {
		this.serverId = serverId;
		Log.e("Request??", "after construtor");
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.conecte_user_comp, container,
				false);
		userName = (TextView) view.findViewById(R.id.user_name);
		userEmail = (TextView) view.findViewById(R.id.user_email);
		userNascimento = (TextView) view.findViewById(R.id.user_nascimento);
		AsyncRequestHandler mHandler = new AsyncRequestHandler() {
			@Override
			public void onSuccess(String response, Request request) {
				atualizarAfterSucces(response);
			}

			@Override
			public void onFailure(Throwable arg0, String arg1, Request request) {
				if(arg1.contains("Usuário não encontrado")){
					
				}
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
		Request request = new Request(null,
				urlUser + "victoraft@gmail.com", "get", null);
		String header[] = new String[2];
		header[0] = "X-ApiKey";
		header[1] = "257F1D3C-57A0-4F34-A937-1538104E97FE";
		request.onlyOneHeader(header);
		// request.setKeyValuePairs(keyValuePairs);
		makeRequest(request);
	}

	public void atualizarAfterSucces(String r) {
		try {
			Log.i("USER!!", " ...= ");
			JSONObject userObject;
			userObject = new JSONObject(r);
			JSONArray nameArray = userObject.names();
			User user;
			String email = "";
			// TODO i will get just the first email, should crash with more than
			// one users email
			JSONArray emailArray = userObject.getJSONArray("emails");
			JSONObject emailObject = emailArray.getJSONObject(0);
			Log.i("Parseando dentro emails!!!",
					" ...= " + emailObject.toString());
			email = emailObject.getString("enderecoEmail");
			Log.i("Parseando user antes for", " ...= " + userObject.toString());
			for (int j = 0; j < nameArray.length(); j++) {
				// JSONObject oneIdea = arrayResults.getJSONObject(j);
				String name = nameArray.getString(j);
				if (nameArray.getString(j).equals("emails")) {
				}
				Log.i("Parseando USER dentro for", " ....= " + name);
			}
			user = new User();
			user.setName(userObject.getString("nome"));
			user.setNascimento(userObject.getString("dataNascimento")
					.substring(0, 10));
			user.setEmail(email);
			setComponentGUI(user);
			// listview.setAdapter(new CommentAdapter(getActivity(), lista));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void setComponentGUI(User idea) {
		userName.setText(idea.getName());
		userEmail.setText(idea.getEmail());
		userNascimento.setText(idea.getNascimento());
	}

}