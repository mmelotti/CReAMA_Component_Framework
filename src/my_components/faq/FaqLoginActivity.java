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

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class FaqLoginActivity extends Activity {
	TextView resultTxt;
	HttpContext httpContext;
	
	String login = "admin";
	String password = "123";
	String urlLogin = FaqActivity.url + "/users/9/login";
	String urlList = FaqActivity.url + "/faq/4/list?_format=json";
	String urlSave = FaqActivity.url + "/faq/4";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.faq_login);
		new GetListAsyncTask(this).execute();
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
	public class GetListAsyncTask extends AsyncTask<Void, Void, String> {
		private Context context;
		private ProgressDialog progressDialog;

		public GetListAsyncTask(Context context) {
			this.context = context;
			progressDialog = new ProgressDialog(context);
		}

		@Override
		protected void onPreExecute() {
			progressDialog.setTitle("Espere");
			progressDialog.setMessage("Fazendo login...");
			progressDialog.show();
		}

		@Override
		protected String doInBackground(Void... p) {
			String result;
			httpContext = new BasicHttpContext();			
			loginRequest(login, password, httpContext);
			return "";
		}

		@Override
		protected void onPostExecute(String result) {
			if (progressDialog.isShowing())
				progressDialog.dismiss();

			Intent loginDone = new Intent(FaqLoginActivity.this, FaqActivity.class);
			CookieStore cookies = (CookieStore) httpContext.getAttribute(ClientContext.COOKIE_STORE);
			List<Cookie> list = cookies.getCookies();
		
			loginDone.putExtra("sessionInfo", new SerializableCookie(list.get(0)));
			FaqLoginActivity.this.startActivity(loginDone);
		}

	}
}
