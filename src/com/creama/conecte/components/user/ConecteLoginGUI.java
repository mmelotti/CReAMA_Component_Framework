package com.creama.conecte.components.user;

import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.github.johnpersano.supertoasts.SuperToast;
import com.gw.android.R;
import com.gw.android.Utils.SuperToastUtils;
import com.gw.android.components.connection_manager.AsyncRequestHandler;
import com.gw.android.components.request.Request;
import com.gw.android.first_components.my_components.comment.Comment;
import com.gw.android.first_components.my_components.comment.CommentAdapter;
import com.gw.android.first_components.my_fragment.CRComponent;
import com.gw.android.first_components.my_fragment.ComponentSimpleModel;

public class ConecteLoginGUI extends CRComponent {

	TextView resultTxt;
	String urlLogin;

	EditText editLogin, editPassword;
	Button btnSubmit;

	// private String urlTest =
	// "http://apiconecteideias.azurewebsites.net/ideias/getAll";
	private String urlTest = "http://apiconecteideias.azurewebsites.net/ideias/searchById?id=8107";

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.conecte_login, container, false);

		btnSubmit = (Button) view.findViewById(R.id.loginConecteSubmit);
		editLogin = (EditText) view.findViewById(R.id.editConecteUser);
		editPassword = (EditText) view.findViewById(R.id.editConectePass);

		// TODO apagar isso aqui, é só pra teste
		editLogin.setText("admin");
		editPassword.setText("123");

		btnSubmit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String login = editLogin.getText().toString();
				String password = editPassword.getText().toString();
				SuperToastUtils.showSuperToast(getActivity(),
						SuperToast.BACKGROUND_GREENTRANSLUCENT,
						"Efetuando login...");
				// Toast.makeText(getActivity(), "Efetuando login...",
				// Toast.LENGTH_SHORT).show();
				// loginRequest(login, password);
				testRequest();
			}
		});

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
		Log.e("TEST LOGIN", "setado calback");

		return view;
	}

	// http://conecteideias.azurewebsites.net/Ideia/Create

	// http://apiconecteideias.azurewebsites.net/ideias/searchById?id=1067

	// "X-ApiKey" e o value dele é 257F1D3C-57A0-4F34-A937-1538104E97FE

	@Override
	protected void onBind() {
		// getConnectionManager().getCookiesInfo();
		// testRequest();

	}

	void testRequest() {

		Log.e("TEST R LOGIN", "fazendo request....");
		Request request = new Request(null, urlTest, "get", null);
		String header[] = new String[2];
		header[0] = "X-ApiKey";
		header[1] = "257F1D3C-57A0-4F34-A937-1538104E97FE";
		request.onlyOneHeader(header);
		// request.setKeyValuePairs(keyValuePairs);

		makeRequest(request);

	}

	String loginRequest(String login, String password) {
		Request request = new Request(null, urlTest, "post", "user.login--"
				+ login + "__user.password--" + password);
		makeRequest(request);
		return "";
	}

	public void atualizarAfterSucces(String r) {

		try {

			Log.i("onde ideia", " ...= ");

			JSONObject ideasObject;
			ideasObject = new JSONObject(r);

			JSONArray nameArray = ideasObject.names();
			JSONArray valArray = ideasObject.toJSONArray(nameArray);
			JSONArray arrayResults = valArray.getJSONArray(0);
			Log.i("Parseando login antes for", " ...= "+ ideasObject.toString());
			for (int j = 0; j < arrayResults.length(); j++) {
				JSONObject oneIdea = arrayResults.getJSONObject(j);
				Log.i("Parseando ideia dentro for", " ....= " + oneIdea.toString());
				/*
				JSONObject userObject = oneIdea.getJSONObject("user");
				String userName = userObject.get("name").toString();
				Long idServ = Long.parseLong(oneIdea.get("id").toString());
				String text = oneIdea.get("text").toString();

				// updating comments, adding on DB
				Long newI = ComponentSimpleModel.getUniqueId(getActivity());

				// initCommentDao();
				// commentDao.insert(comment);
				// closeDao();

				Log.i("Parseando login", "text = " + text + idServ + userName);
				*/
			}
			// listview.setAdapter(new CommentAdapter(getActivity(), lista));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Log.e("TEST R LOGIN",r);

	}

}