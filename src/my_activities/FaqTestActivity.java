package my_activities;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.client.CookieStore;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import com.example.firstcomponents.R;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class FaqTestActivity extends Activity {
	TextView resultTxt;
	String ip = "192.168.1.135";
	String login = "admin";
	String password = "123";
	String urlLogin = "http://" + ip + ":8080/GW-Application-FAQ/groupware-workbench/users/9/login";
	String urlList = "http://" + ip + ":8080/GW-Application-FAQ/groupware-workbench/faq/4/list";
	String urlCreate = "http://" + ip + ":8080/GW-Application-FAQ/groupware-workbench/faq/4/create";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.faq_test);

		resultTxt = (TextView) findViewById(R.id.txt);
		new GetListAsyncTask(this).execute();
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
			progressDialog.setMessage("Recebendo lista de perguntas...");
			progressDialog.show();
		}

		@Override
		protected String doInBackground(Void... p) {
			HttpClient client = new DefaultHttpClient();
			HttpContext httpContext = new BasicHttpContext();
			CookieStore cookieStore = new BasicCookieStore();
			httpContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);

			HttpPost requestLogin = new HttpPost(urlLogin);
			List<NameValuePair> paramsLogin = new ArrayList<NameValuePair>();
			paramsLogin.add(new BasicNameValuePair("user.login", login));
			paramsLogin.add(new BasicNameValuePair("user.password", password));

			try {
				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramsLogin, "UTF-8");
				requestLogin.setEntity(entity);
				HttpResponse responseLogin = client.execute(requestLogin, httpContext);
				String resultLogin = EntityUtils.toString(responseLogin.getEntity());
				Log.e("resposta do login:", resultLogin);
				
				
				HttpGet requestList = new HttpGet(urlList);
				HttpResponse response = client.execute(requestList, httpContext);
				String result = EntityUtils.toString(response.getEntity());
				Log.e("resultado do list", result);
				
				HttpPost requestCreate = new HttpPost(urlCreate);
				List<NameValuePair> paramsCreate = new ArrayList<NameValuePair>();
				//paramsCreate.add(new BasicNameValuePair("faq.id", ""));
				paramsCreate.add(new BasicNameValuePair("faq.pergunta", "1111111"));
				paramsCreate.add(new BasicNameValuePair("faq.resposta", "2222222"));
				UrlEncodedFormEntity entityCreate = new UrlEncodedFormEntity(paramsCreate, "UTF-8");
				requestCreate.setEntity(entity);
				HttpResponse responseCreate = client.execute(requestCreate, httpContext);
				String resultCreate = EntityUtils.toString(responseCreate.getEntity());
				Log.e("resposta do create:", resultCreate);

				return resultCreate;
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			if (progressDialog.isShowing())
				progressDialog.dismiss();

			resultTxt.setText(result);
		}

	}
}
