package com.gw.android.my_components.faq;

import org.apache.http.impl.client.DefaultHttpClient;
import com.gw.android.R;
import com.gw.android.database.DaoMaster;
import com.gw.android.database.DaoSession;
import com.gw.android.database.DaoMaster.DevOpenHelper;
import com.gw.android.my_activities.FaqActivity;
import com.gw.android.my_components.comment.CommentDao;
import com.gw.android.my_components.request.Request;
import com.gw.android.my_components.request.RequestUtils;
import com.gw.android.my_fragment.CRComponent;
import com.gw.android.my_fragment.ComponentSimpleModel;
import com.loopj.android.http.AsyncHttpResponseHandler;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
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
	private FaqDao faqDao;
	private DaoSession daoSession;
	private boolean conectado=false;

	public static FaqDao initFaqDao(Activity a) {
		DevOpenHelper helper = new DaoMaster.DevOpenHelper(a, "faqs-db", null);
		SQLiteDatabase db = helper.getWritableDatabase();
		DaoMaster daoMaster = new DaoMaster(db);
		DaoSession daoSession = daoMaster.newSession();
		return daoSession.getFaqDao();
	}
	
	public void setData(String id, String question, String answer) {
		this.id = id; //string vazia eh nova pergunta-resposta
		this.question = question;
		this.answer = answer;
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

	String saveRequest(String id, String pergunta, String resposta) {
		Request request = new Request(null, null, urlSave, "post", "faq.id--"
				+ id + "__faq.pergunta--" + pergunta + "__faq.resposta--"
				+ resposta);
		
		//poe na fila de request primeiro, para o servico consumir
		//se estiver conectado, vai tentar enviar pro servidor
		//de qualquer maneira, salva no cache
		if(conectado){
		RequestUtils.makeRequest(request, getActivity(),
				new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(String response) {
						Log.e("onsuccess", response);
						Toast.makeText(getActivity(), "Enviado!",
								Toast.LENGTH_SHORT).show();
						if (FaqSendGUI.this.getDialog() != null) 
							// Sendo mostrado como dialog
							FaqSendGUI.this.dismiss();
					}

					@Override
					public void onFailure(Throwable t) {
						Log.e("onfailure", "batata");
					}

					@Override
					public void onFinish() {
						Log.e("onfinish", "batata");
					}
				});
		}
		
		if(id.equals("")){
			Faq faq = new Faq();
			faq.setPergunta(pergunta);
			faq.setResposta(resposta);
			newOne(new Faq());
		}else{
			changeOne();
		}
		
		return "";
	}

	@Override
	public void onClick(View arg0) {
		question = editQuestion.getText().toString();
		answer = editAnswer.getText().toString();
		saveRequest(id, question, answer);
	}

	
	public void newOne(Faq faq){
		initFaqDao();
		//gera id unico
		Long newId = ComponentSimpleModel.getUniqueId(getActivity());
		faq.setId(newId);
		faqDao.insert(faq);
		closeDao();
	}
	
	public void changeOne(){
		
		
	}
	
	public void initFaqDao() {
		//Log.i("en initi", "aquiii");
		DevOpenHelper helper = new DaoMaster.DevOpenHelper(getActivity(),
				"faqs-db", null);
		SQLiteDatabase db = helper.getWritableDatabase();
		DaoMaster daoMaster = new DaoMaster(db);
		daoSession = daoMaster.newSession();
		faqDao = daoSession.getFaqDao();
	}
	
	public void closeDao() {
		faqDao.getDatabase().close();
	}
	
}
