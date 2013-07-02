package com.gw.android.first_components.my_components.faq;

import java.util.List;

import org.apache.http.impl.client.DefaultHttpClient;
import com.gw.android.R;
import com.gw.android.components.connection_manager.AsyncRequestHandler;
import com.gw.android.components.request.Request;
import com.gw.android.first_components.database.DaoMaster;
import com.gw.android.first_components.database.DaoSession;
import com.gw.android.first_components.database.DaoMaster.DevOpenHelper;
import com.gw.android.first_components.my_components.faq.FaqDao.Properties;

import com.gw.android.first_components.my_fragment.CRComponent;
import com.gw.android.first_components.my_fragment.ComponentSimpleModel;
import com.gw.android.perguntaserespostas.FaqActivity;

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
	private String question = "", answer = "", idLocal = "", idServer = "";
	Long idlocal = 0L;
	DefaultHttpClient client = new DefaultHttpClient();
	Button btnSubmit;
	EditText editQuestion, editAnswer;
	private FaqDao faqDao;
	private DaoSession daoSession;
	private boolean conectado = true;

	public static FaqDao initFaqDao(Activity a) {
		DevOpenHelper helper = new DaoMaster.DevOpenHelper(a, "faqs-db", null);
		SQLiteDatabase db = helper.getWritableDatabase();
		DaoMaster daoMaster = new DaoMaster(db);
		DaoSession daoSession = daoMaster.newSession();
		return daoSession.getFaqDao();
	}

	public void setData(String id, String question, String answer,
			String idServer) {
		this.idLocal = id; // string vazia eh nova pergunta-resposta
		this.question = question;
		this.answer = answer;
		this.idServer = idServer;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		Dialog d = getDialog();
		if (d != null) // componente está sendo mostrado como dialog
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

	String saveRequest(String idLocal, String pergunta, String resposta) {
		Request request = new Request(null, urlSave, "post", "faq.id--"
				+ idServer + "__faq.pergunta--" + pergunta + "__faq.resposta--"
				+ resposta);

		// poe na fila de request primeiro, para o servico consumir
		// se estiver conectado, vai tentar enviar pro servidor
		// de qualquer maneira, salva no cache
		if (conectado) {
			getConnectionManager().makeRequest(request, getActivity(),
					new AsyncRequestHandler(true) {
						@Override
						public void onSuccess(String response) {
							Log.e("onsuccess", response);
							Toast.makeText(getActivity(), "Enviado!",
									Toast.LENGTH_SHORT).show();
							if (FaqSendGUI.this.getDialog() != null)
								// Sendo mostrado como dialog
								FaqSendGUI.this.dismiss();
							reloadActivity();
						}
					});
		}
		Faq faq = new Faq();
		faq.setPergunta(pergunta);
		faq.setResposta(resposta);
		// operacoes no cache
		if (idServer.equals("")) {// nao tem no server, pode ser novo, mas tem
									// que ver se ja nao ta no cache!

			if (idLocal.equals("")) {//nao tem no cache
				
				newOnePersistence(faq);
			} else {//tem, entao update
				changeOne(idLocal, faq);
			}

			// mesmo se nao tiver conectado salva no cache, se cair conexao ele
			// ainda pode alterar

		} else {// nao eh um novo
			changeOne(idLocal, faq);
		}

		return "";
	}

	@Override
	public void onClick(View arg0) {
		question = editQuestion.getText().toString();
		answer = editAnswer.getText().toString();
		saveRequest(idLocal, question, answer);// 
												
	}

	public void newOnePersistence(Faq faq) {
		initFaqDao();
		// gera id unico
		Long newId = ComponentSimpleModel.getUniqueId(getActivity());
		faq.setId(newId);
		faqDao.insert(faq);
		closeDao();
	}

	public void changeOne(String idL, Faq faq) {
		// busca o que ta no BD para atualizar
		// parei aqui, fui pro treino, falta deletar!!
		Long id = Long.parseLong(idL);
		Faq antigo = null;
		Faq atualizado = faq;
		initFaqDao();
		List<Faq> lista = faqDao.queryBuilder()
				.where(Properties.Id.eq(id)).build().list();
		closeDao();

		for (Faq f : lista) {
			antigo = f; // eh pra ter soh um na lista
		}

		if (antigo != null) {// ele ta mexendo online e ja tem id do server
			atualizado.setServerId(antigo.getServerId());
		}

		// de qualquer maneira insere o atualizado e deleta o antigo
		initFaqDao();
		faqDao.insert(atualizado);
		faqDao.delete(antigo);
		closeDao();
	}

	public void faqDelete(Faq faq) {
		initFaqDao();

		closeDao();
	}

	public void initFaqDao() {
		// Log.i("en initi", "aquiii");
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
