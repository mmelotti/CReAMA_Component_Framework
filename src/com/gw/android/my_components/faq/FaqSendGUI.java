package com.gw.android.my_components.faq;

import java.util.ArrayList;
import java.util.List;


import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.gw.android.R;
import com.gw.android.database.DaoMaster;
import com.gw.android.database.DaoSession;
import com.gw.android.database.DaoMaster.DevOpenHelper;
import com.gw.android.my_activities.FaqActivity;
import com.gw.android.my_fragment.CRComponent;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

@SuppressLint("ValidFragment")
public class FaqSendGUI extends CRComponent implements OnClickListener {
	static String urlSave = FaqActivity.url + "/faq/4";
	String question = "", answer = "", id = "";
	DefaultHttpClient client = new DefaultHttpClient();
	Button btnSubmit;
	EditText editQuestion, editAnswer;

	public static FaqDao initFaqDao(Activity a) {
		DevOpenHelper helper = new DaoMaster.DevOpenHelper(a, "faqs-db", null);
		SQLiteDatabase db = helper.getWritableDatabase();
		DaoMaster daoMaster = new DaoMaster(db);
		DaoSession daoSession = daoMaster.newSession();
		return daoSession.getFaqDao();
	}
	
	public void setData(String id, String question, String answer) {
		this.id = id;
		this.question = question;
		this.answer = answer;
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		List<Cookie> cookies = client.getCookieStore().getCookies();
		if (!cookies.isEmpty()) {
			Cookie sessionInfo = cookies.get(0);
			outState.putSerializable("sessionInfo", new SerializableCookie(
					sessionInfo));
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
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		Dialog d = getDialog();
		if (d != null)	// componente está sendo mostrado como dialog
			d.setTitle("FAQ");
		
		View view = inflater.inflate(R.layout.faq_send, container, false);
		editQuestion = (EditText) view.findViewById(R.id.editQuestion);
		editAnswer = (EditText) view.findViewById(R.id.editAnswer);
		btnSubmit = (Button) view.findViewById(R.id.btnSubmit);
		btnSubmit.setOnClickListener(this);
		
		editQuestion.setText(question);
		editAnswer.setText(answer);
		return view;
	}

	// ASyncTask
	public class SendFaqAsyncTask extends AsyncTask<Void, Void, String> {

		@Override
		protected String doInBackground(Void... p) {
			String result;

			result = saveRequest(id, question, answer);
			return result;
		}

		@Override
		protected void onPostExecute(String result) {
			Toast.makeText(getActivity(), "Enviado!", Toast.LENGTH_SHORT)
					.show();
			if (FaqSendGUI.this.getDialog() != null) // Sendo mostrado como dialog
				FaqSendGUI.this.dismiss();
		}

	}

	public String saveRequest(String id, String pergunta, String resposta) {
		HttpPost request = new HttpPost(urlSave);
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("faq.id", id));
		params.add(new BasicNameValuePair("faq.pergunta", pergunta));
		params.add(new BasicNameValuePair("faq.resposta", resposta));

		try {
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params,
					"UTF-8");
			request.setEntity(entity);
			HttpResponse responseCreate = client.execute(request);
			return EntityUtils.toString(responseCreate.getEntity());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public void onClick(View arg0) {
		question = editQuestion.getText().toString();
		answer = editAnswer.getText().toString();
		new SendFaqAsyncTask().execute();
	}

}
