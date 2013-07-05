package com.gw.android.first_components.my_components.faq;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import com.gw.android.R;
import com.gw.android.components.connection_manager.AsyncRequestHandler;
import com.gw.android.components.request.Request;
import com.gw.android.first_components.database.DaoMaster;
import com.gw.android.first_components.database.DaoSession;
import com.gw.android.first_components.database.DaoMaster.DevOpenHelper;
import com.gw.android.first_components.my_fragment.CRComponent;
import com.gw.android.first_components.my_fragment.ComponentSimpleModel;
import com.gw.android.perguntaserespostas.FaqActivity;

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

	private String faqUrl = "4";
	String urlList = FaqActivity.url + "/faq/" + faqUrl + "/list?_format=json";

	DefaultHttpClient client = new DefaultHttpClient();
	ListView listView;
	List<Faq> list = new ArrayList<Faq>();
	List<String> mQuestions = new ArrayList<String>();
	private boolean conectado = true;
	private FaqDao faqDao;
	private DaoSession daoSession;

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
	}

	@Override
	protected void onBind() {
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
		Request request = new Request(null, urlList, "get", null);
		
		Log.i("fazendo request list", urlList);
		// se nao estiver conectado, nem vale ir para a fila de request
		// se estiver conectado, vai tentar buscar no servidor as
		// perguntas/respostas
		// depois salva no cache para acesso offline
		if (conectado) {
			getConnectionManager().makeRequest(request, getActivity(),
					new AsyncRequestHandler() {
						@Override
						public void onSuccess(String response) {
							Log.e("list onsuccess", response);

							parseJSON(response);
							mQuestions.clear();
							for (Faq f : list)
								mQuestions.add(f.getPergunta());

							ArrayAdapter<String> adapter = new ArrayAdapter<String>(
									getActivity().getApplicationContext(),
									android.R.layout.simple_list_item_1,
									mQuestions);
							adapter.notifyDataSetChanged();
							listView.setAdapter(adapter);
						}
					});
		} else {// pega no cache
			Log.e("list onsuccess", " sem conexao");

			// preenche list (from cache) para mostrar no view
			fillList();
			mQuestions.clear();
			for (Faq f : list)
				mQuestions.add(f.getPergunta());

			ArrayAdapter<String> adapter = new ArrayAdapter<String>(
					getActivity().getApplicationContext(),
					android.R.layout.simple_list_item_1, mQuestions);
			adapter.notifyDataSetChanged();
			listView.setAdapter(adapter);
		}

	}

	void parseJSON(String response) {
		try {
			JSONObject json = new JSONObject(response);

			JSONArray nameArray = json.names();
			JSONArray valArray = json.toJSONArray(nameArray);
			JSONArray arrayResults = valArray.getJSONArray(0);

			// apaga tudo no cache para reecriar...por enquanto fazemos isso
			apagaNoCache();
			// so pode executar isso se tiver conectado

			for (int j = 0; j < arrayResults.length(); j++) {
				JSONObject object = arrayResults.getJSONObject(j);
				Long idServ = Long.parseLong(object.get("id").toString());
				String pergunta = object.get("pergunta").toString();
				String resposta = object.get("resposta").toString();

				// cria novo faq para mandar pro cache
				Faq novo = new Faq(0L, 0L, idServ, pergunta, resposta);
				novo = newOnePersistence(novo); // gera Id unico, entao retorna
												// atualizado
				list.add(novo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void apagaNoCache() {
		initFaqDao();
		List<Faq> lista = faqDao.queryBuilder().build().list();
		// faz uma busca e apaga um por um
		for (Faq f : lista) {
			faqDao.delete(f);
		}
		closeDao();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Faq f = list.get(position);
		FaqSendGUI faqView = new FaqSendGUI();
		faqView.setData(f.getId().toString(), f.getPergunta(), f.getResposta(),
				f.getServerId() + "");
		faqView.show(getFragmentManager(), "faqView");
	}

	public Faq newOnePersistence(Faq faq) {
		initFaqDao();
		Long newId = ComponentSimpleModel.getUniqueId(getActivity());
		faq.setId(newId);
		faqDao.insert(faq);
		closeDao();
		return faq;
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

	public void fillList() {
		initFaqDao();
		list = faqDao.queryBuilder().build().list();
		closeDao();
	}

	public void setUrlVariable(String v) {
		faqUrl = v;
		urlList = FaqActivity.url + "/faq/" + faqUrl + "/list?_format=json";
	}
}
