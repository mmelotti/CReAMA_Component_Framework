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
	private boolean loginVerified=false;
	EditText editLogin, editPassword;
	Button btnSubmit;

	// private String urlTest =
	// "http://apiconecteideias.azurewebsites.net/ideias/getAll";
	private String urlVerifyUser = "http://apiconecteideias.azurewebsites.net/usuarios/searchByEmail?email=";

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.conecte_login_comp, container,
				false);

		btnSubmit = (Button) view.findViewById(R.id.loginConecteSubmit);
		editLogin = (EditText) view.findViewById(R.id.editConecteUser);
		editPassword = (EditText) view.findViewById(R.id.editConectePass);

		// TODO apagar isso aqui, é só pra teste
		editLogin.setText("maisonmelotti@gmail.com");
		editPassword.setText("minhasenha");

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
		Request request = new Request(null, urlVerifyUser
				+ "maisonmelotti@gmail.com", "get", null);
		String header[] = new String[2];
		header[0] = "X-ApiKey";
		header[1] = "257F1D3C-57A0-4F34-A937-1538104E97FE";
		request.onlyOneHeader(header);
		// request.setKeyValuePairs(keyValuePairs);

		makeRequest(request);

	}

	String loginRequest(String login, String password) {
		Request request = new Request(null, urlVerifyUser, "post",
				"user.login--" + login + "__user.password--" + password);
		makeRequest(request);
		return "";
	}

	public void atualizarAfterSucces(String r) {
		if (r != null ) {
			
			try {

				Log.i("onde ideia", " ...= ");

				JSONObject userObject;
				userObject = new JSONObject(r);

				JSONArray nameArray = userObject.names();
				JSONArray valArray = userObject.toJSONArray(nameArray);
				JSONArray arrayResults = valArray.getJSONArray(0);

				User user;
				String email = "";
				String pass = null;

				// TODO i will get just the first email, should crash with more
				// than one users email
				JSONArray emailArray = userObject.getJSONArray("emails");
				JSONObject emailObject = emailArray.getJSONObject(0);
				Log.i("Parseando dentro emails!!!",
						" ...= " + emailObject.toString());
				email = emailObject.getString("enderecoEmail");
				pass = userObject.getString("senha");

				Log.i("Parseando login antes for",
						" ...= " + userObject.toString());
				for (int j = 0; j < nameArray.length(); j++) {
					// JSONObject oneIdea = arrayResults.getJSONObject(j);
					String name = nameArray.getString(j);

					if (nameArray.getString(j).equals("emails")) {

					}
					Log.i("LOGIN DENro names", " ....= " + name);

				}
				user = new User();
				user.setName(userObject.getString("nome"));
				user.setNascimento(userObject.getString("dataNascimento")
						.substring(0, 10));
				user.setEmail(email);
				if (pass != null) {
					verifyPassword(pass);
				}

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else { // a resposta veio vazia, nao tem user com o email

		}

	}

	private void verifyPassword(String s) {
		if (s.equals("minhasenha")) {
			Log.i("TESTANDO LOGIN after senha", " ....= " + s);
			loginVerified=true;
		}
	}

	public boolean isLoginVerified(){
		return loginVerified;
	}
	
}