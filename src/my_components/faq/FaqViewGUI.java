package my_components.faq;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import my_activities.FaqActivity;

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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

@SuppressLint("ValidFragment")
public class FaqViewGUI extends CRComponent implements OnItemClickListener {
	String urlList = FaqActivity.url + "/faq/4/list?_format=json";
	DefaultHttpClient client = new DefaultHttpClient();
	ListView listView;
	List<Faq> list = new ArrayList<Faq>();
	List<String> mQuestions = new ArrayList<String>();

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
			outState.putSerializable("sessionInfo", new SerializableCookie(sessionInfo));
		}
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		SerializableCookie cookie = null;

		if (savedInstanceState == null) {
			cookie = (SerializableCookie) getActivity().getIntent().getExtras()
					.getSerializable("sessionInfo");
		} else if (client.getCookieStore().getCookies().isEmpty()
				&& savedInstanceState.containsKey("sessionInfo")) {
			cookie = (SerializableCookie) savedInstanceState
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
		listView = (ListView) view.findViewById(R.id.listView);
		listView.setOnItemClickListener(this);
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

	void listRequest() {
		HttpGet requestList = new HttpGet(urlList);
		Log.e("list request", "entrou ");

		try {
			HttpResponse response = client.execute(requestList);
			HttpEntity entity = response.getEntity();

			InputStream instream = entity.getContent();
			JSONObject json = new JSONObject(convertStreamToString(instream));
			JSONArray nameArray = json.names();
			JSONArray valArray = json.toJSONArray(nameArray);
			JSONArray arrayResults = valArray.getJSONArray(0);

			for (int j = 0; j < arrayResults.length(); j++) {
				JSONObject object = arrayResults.getJSONObject(j);			
				Long id = Long.parseLong(object.get("id").toString());
				String pergunta = object.get("pergunta").toString();
				String resposta = object.get("resposta").toString();
				list.add(new Faq(id, null, pergunta, resposta));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// ASyncTask
	public class GetListAsyncTask extends AsyncTask<Void, Void, String> {

		@Override
		protected String doInBackground(Void... p) {
			listRequest();

			mQuestions.clear();
			for (Faq f : list)
				mQuestions.add(f.getPergunta());
			
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			//tv.setText(result);
			ArrayAdapter adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_list_item_1, mQuestions);
			adapter.notifyDataSetChanged();
			listView.setAdapter(adapter);	
		}

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		// TODO Auto-generated method stub
		
	}

}
