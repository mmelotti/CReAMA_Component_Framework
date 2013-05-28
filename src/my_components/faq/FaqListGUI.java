package my_components.faq;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.json.JSONArray;
import org.json.JSONObject;

import com.example.firstcomponents.R;
import com.example.my_fragment.CRComponent;
import database.DaoMaster;
import database.DaoMaster.DevOpenHelper;
import database.DaoSession;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

@SuppressLint("ValidFragment")
public class FaqListGUI extends CRComponent {
	String ip = "192.168.1.106";
	String url = "http://" + ip
			+ ":8080/GW-Application-FAQ/groupware-workbench";
	String urlList = url + "/faq/4/list?_format=json";
	DefaultHttpClient client = new DefaultHttpClient();
	TextView tv;

	public static FaqDao initFaqDao(Activity a) {
		DevOpenHelper helper = new DaoMaster.DevOpenHelper(a, "faqs-db", null);
		SQLiteDatabase db = helper.getWritableDatabase();
		DaoMaster daoMaster = new DaoMaster(db);
		DaoSession daoSession = daoMaster.newSession();
		return daoSession.getFaqDao();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		List<Cookie> cookies = client.getCookieStore().getCookies();
		if (!cookies.isEmpty()) {
			Cookie sessionInfo = cookies.get(0);
			outState.putSerializable("sessionInfo", new MyCookie(sessionInfo));
		}
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		MyCookie cookie = null;

		if (savedInstanceState == null) {
			cookie = (MyCookie) getActivity().getIntent().getExtras()
					.getSerializable("sessionInfo");
		} else if (client.getCookieStore().getCookies().isEmpty()
				&& savedInstanceState.containsKey("sessionInfo")) {
			cookie = (MyCookie) savedInstanceState
					.getSerializable("sessionInfo");
		} else
			return;

		BasicClientCookie newCookie = new BasicClientCookie(cookie.getName(),
				cookie.getValue());
		newCookie.setDomain(cookie.getDomain());
		client.getCookieStore().addCookie(newCookie);

		new GetListAsyncTask().execute();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.faq_list, container, false);
		tv = (TextView) view.findViewById(R.id.result);
		return view;
	}

	String convertStreamToString(InputStream is) {
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

	String listRequest() {
		HttpGet requestList = new HttpGet(urlList);
		Log.e("list request", "entrou ");

		try {
			String result = "";
			HttpResponse response = client.execute(requestList);
			HttpEntity entity = response.getEntity();

			InputStream instream = entity.getContent();
			JSONObject json = new JSONObject(convertStreamToString(instream));
			JSONArray nameArray = json.names();
			JSONArray valArray = json.toJSONArray(nameArray);
			JSONArray arrayResults = valArray.getJSONArray(0);

			for (int j = 0; j < arrayResults.length(); j++) {
				JSONObject object = arrayResults.getJSONObject(j);
				result += "ID: " + object.get("id") + "\n";
				result += "PERGUNTA: " + object.get("pergunta") + "\n";
				result += "RESPOSTA: " + object.get("resposta") + "\n\n";
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// ASyncTask
	public class GetListAsyncTask extends AsyncTask<Void, Void, String> {

		@Override
		protected String doInBackground(Void... p) {
			String result;

			result = listRequest();
			return result;
		}

		@Override
		protected void onPostExecute(String result) {
			tv.setText(result);
		}

	}

}
