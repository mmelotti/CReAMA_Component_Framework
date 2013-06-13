package com.gw.android.my_components.faq;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import com.gw.android.R;
import com.gw.android.database.DaoMaster;
import com.gw.android.database.DaoSession;
import com.gw.android.database.DaoMaster.DevOpenHelper;
import com.gw.android.my_activities.FaqActivity;
import com.gw.android.my_components.request.Request;
import com.gw.android.my_components.request.RequestUtils;
import com.gw.android.my_fragment.CRComponent;
import com.loopj.android.http.AsyncHttpResponseHandler;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

@SuppressLint("ValidFragment")
public class FaqListGUI extends CRComponent implements OnItemClickListener {
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
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		listRequest();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.faq_list, container, false);
		listView = (ListView) view.findViewById(R.id.listView);
		listView.setOnItemClickListener(this);
		return view;
	}

	void listRequest() {
		Request request = new Request(null, null, urlList, "get", null);
		RequestUtils.makeRequest(request, getActivity(),
				new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(String response) {
						Log.e("list onsuccess", response);

						parseJSON(response);
						mQuestions.clear();
						for (Faq f : list)
							mQuestions.add(f.getPergunta());

						ArrayAdapter<String> adapter = new ArrayAdapter<String>(
								getActivity().getApplicationContext(),
								android.R.layout.simple_list_item_1, mQuestions);
						adapter.notifyDataSetChanged();
						listView.setAdapter(adapter);
					}

					@Override
					public void onFailure(Throwable t) {
						Log.e("list onfailure", "batata");
					}

					@Override
					public void onFinish() {
						Log.e("list onfinish", "batata");
					}
				});
	}

	void parseJSON(String response) {
		try {
			JSONObject json = new JSONObject(response);

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

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Faq f = list.get(position);
		FaqSendGUI faqView = new FaqSendGUI();
		faqView.setData(f.getId().toString(), f.getPergunta(), f.getResposta());
		faqView.show(getFragmentManager(), "faqView");
	}

}
