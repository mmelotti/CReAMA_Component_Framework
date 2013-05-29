package my_components.faq;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import my_activities.FaqActivity;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.client.CookieStore;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import com.example.firstcomponents.R;
import com.example.my_fragment.CRComponent;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class FaqLoginGUI extends CRComponent {
	TextView resultTxt;
	HttpContext httpContext;
	String urlLogin = FaqActivity.url + "/users/9/login";
	String urlList = FaqActivity.url + "/faq/4/list?_format=json";
	String urlSave = FaqActivity.url + "/faq/4";

	EditText editLogin, editPassword;
	Button btnSubmit;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Dialog d = getDialog();
		if (d != null)	// componente está sendo mostrado como dialog
			d.setTitle("FAQ Login");
		
		View view = inflater.inflate(R.layout.faq_login, container, false);
		btnSubmit = (Button) view.findViewById(R.id.btnSubmit);
		editLogin = (EditText) view.findViewById(R.id.editLogin);
		editPassword = (EditText) view.findViewById(R.id.editPassword);
		
		btnSubmit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				new LoginAsyncTask(getActivity().getApplicationContext()).execute();		
			}
		});
		
		return view;
	}
	
	public static String convertStreamToString(InputStream is) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();
		String line = null;
		
		try {
			while ((line = reader.readLine()) != null)
				sb.append(line + "\n");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}

	String loginRequest(String login, String password,
			HttpContext httpContext) {
		HttpClient client = new DefaultHttpClient();
		CookieStore cookieStore = new BasicCookieStore();
		httpContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);

		HttpPost requestLogin = new HttpPost(urlLogin);
		List<NameValuePair> paramsLogin = new ArrayList<NameValuePair>();
		paramsLogin.add(new BasicNameValuePair("user.login", login));
		paramsLogin.add(new BasicNameValuePair("user.password", password));
		try {
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramsLogin,
					"UTF-8");
			requestLogin.setEntity(entity);
			HttpResponse responseLogin = client.execute(requestLogin,
					httpContext);
			
			Header[] h1 = responseLogin.getAllHeaders();
			for (Header ha : h1) 
				Log.e("headers", ha.toString());
			return EntityUtils.toString(responseLogin.getEntity());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	String retrieveRequest(String id, HttpContext httpContext) {
		HttpClient client = new DefaultHttpClient();
		HttpGet requestList = new HttpGet(FaqActivity.url + "/" + id);
		
		try {
			HttpResponse response = client.execute(requestList, httpContext);
			return EntityUtils.toString(response.getEntity());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	// ASyncTask
	public class LoginAsyncTask extends AsyncTask<Void, Void, String> {

		public LoginAsyncTask(Context context) {

		}

		@Override
		protected String doInBackground(Void... p) {
			httpContext = new BasicHttpContext();	
			String login = editLogin.getText().toString();
			String password = editPassword.getText().toString();
			loginRequest(login, password, httpContext);
			return "";
		}

		@Override
		protected void onPostExecute(String result) {
			Intent loginDone = new Intent(getActivity(), FaqActivity.class);
			CookieStore cookies = (CookieStore) httpContext.getAttribute(ClientContext.COOKIE_STORE);
			List<Cookie> list = cookies.getCookies();
		
			loginDone.putExtra("sessionInfo", new SerializableCookie(list.get(0)));
			FaqLoginGUI.this.startActivity(loginDone);
		}

	}
}
