package com.gw.android.first_components.my_components.binomio;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.gw.android.R;
import com.gw.android.components.connection_manager.AsyncRequestHandler;
import com.gw.android.components.request.Request;
import com.gw.android.first_components.database.DaoMaster;
import com.gw.android.first_components.database.DaoSession;
import com.gw.android.first_components.database.DaoMaster.DevOpenHelper;
import com.gw.android.first_components.my_components.comment.Comment;
import com.gw.android.first_components.my_components.tag.TagDao.Properties;
import com.gw.android.first_components.my_fragment.CRComponent;
import com.gw.android.first_components.my_fragment.ComponentSimpleModel;

@SuppressLint({ "ValidFragment", "NewApi" })
public class BinomioAverageGUI extends CRComponent {

	// private Binomio bin = new Binomio();
	private Long newTarget;
	// private int nBin = 5;
	// private View binView;
	private int fechada = 0, simples = 0, vertical = 0, assimetrica = 0,
			opaca = 0, externa = 0;

	private BinomioDao binomioDao;
	private DaoSession daoSession;

	private List<BinomiosArquigrafia> list;
	private int[] values;
	private LinearLayout l;
	private LayoutInflater inflater;

	private boolean conectado = true, teste = true;
	private String urlGetBinomio = "http://valinhos.ime.usp.br:51080/evaluations/5/photo/73?_format=json";
	private String urlFinalJSON = "?_format=json";

	private static final String KEY_LEFT_TEXT_VIEW = "leftTextView";
	private static final String KEY_RIGHT_TEXT_VIEW = "rightTextView";
	private static final String KEY_BINOMIO = "binomio";

	public BinomioAverageGUI(Long t) {
		newTarget = t;
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.binomios_average, container,
				false);

		Log.i("create", "binomio gui");
		// Button save = (Button) view.findViewById(R.id.btnBrowse);
		LinearLayout lCon = (LinearLayout) view
				.findViewById(R.id.binomio_container_average);

		AsyncRequestHandler mHandler = new AsyncRequestHandler() {
			@Override
			public void onSuccess(String response, Request request) {
				parseBinomioJSON(response);
				preencherBinomios();

				Log.i("Onsucces ParserBINOMIO ", " id=");

			}

			@Override
			public void onFailure(Throwable t, String response, Request r) {

				preencherBinomios();

			}
		};
		setComponentRequestCallback(mHandler);

		addVariosBinomios(lCon, inflater);

		return view;
	}

	private void addVariosBinomios(LinearLayout l, LayoutInflater inflater) {
		list = new ArrayList<BinomiosArquigrafia>();
		list.add(new BinomiosArquigrafia("Fechada", "Aberta"));
		list.add(new BinomiosArquigrafia("Simples", "Complexa"));
		list.add(new BinomiosArquigrafia("Vertical", "Horizontal"));
		list.add(new BinomiosArquigrafia("Externa", "Interna"));
		list.add(new BinomiosArquigrafia("Assimétrica", "Simétrica"));
		list.add(new BinomiosArquigrafia("Opaca", "Translúcida"));

		if (conectado) {
			values = averageBinomiosFromServer();
		} else {
			values = averageBinomiosDatabase(newTarget);
		}

		this.l = l;
		this.inflater = inflater;
		// preencherBinomios();

	}

	private void preencherBinomios() {
		int i = 0;
		for (BinomiosArquigrafia binomio : list) {
			View v = this.inflater.inflate(R.layout.binomio, null);
			TextView left, right;
			left = (TextView) v.findViewById(R.id.label_left);
			right = (TextView) v.findViewById(R.id.label_right);
			left.setText(binomio.getLeft());
			right.setText(binomio.getRight());

			binomio.setLeftValue(values[i]);
			binomio.setRightValue(100 - values[i]);

			final TextView seekbarLeftValueText = (TextView) v
					.findViewById(R.id.seekbar_value_left);
			seekbarLeftValueText.setText(Integer.toString(binomio
					.getRightValue()) + "%");
			final TextView seekbarRightValueText = (TextView) v
					.findViewById(R.id.seekbar_value_right);
			seekbarRightValueText.setText(Integer.toString(binomio
					.getLeftValue()) + "%");

			this.l.addView(v);

			SeekBar seekbar = (SeekBar) v.findViewById(R.id.seekbar);
			seekbar.setHorizontalScrollBarEnabled(true);

			final HashMap<String, Object> tag = new HashMap<String, Object>(3);
			tag.put(KEY_LEFT_TEXT_VIEW, seekbarLeftValueText);
			tag.put(KEY_RIGHT_TEXT_VIEW, seekbarRightValueText);
			tag.put(KEY_BINOMIO, binomio);
			seekbar.setTag(tag);

			seekbar.setProgress(binomio.getLeftValue());
			seekbar.setEnabled(false);

			i++;
		}
	}

	public void deleteAllFromTarget() {

		List<Binomio> lista = getAllFromTarget(newTarget);
		for (Binomio b : lista) {
			deleteOne(b);
		}

	}

	public void parseBinomioJSON(String r) {
		Log.i("Parseando binomio", " inicio");
		JSONObject object;

		// got the right average, we can remove the older
		// deleteAllFromTarget();

		try {
			JSONObject binomiosObject;
			binomiosObject = new JSONObject(r);

			JSONArray nameArray = binomiosObject.names();
			JSONArray valArray = binomiosObject.toJSONArray(nameArray);
			JSONArray arrayResults = valArray.getJSONArray(0);
			Log.i("Parseando comentario antes for", " foto= ");

			for (int j = 0; j < arrayResults.length(); j++) {
				JSONObject oneBinomio = arrayResults.getJSONObject(j);
				JSONObject binomialObject = oneBinomio
						.getJSONObject("binomial");
				String firstName = binomialObject.get("firstName").toString();
				Long value = Long.parseLong(oneBinomio
						.get("evaluationPosition").toString());

				values[j] = Integer.parseInt(oneBinomio.get(
						"evaluationPosition").toString());
				// Log.i("Parseando comentario", " text= " + value+firstName);
			}

			// updating comments, adding on DB
			Long newI = ComponentSimpleModel.getUniqueId(getActivity());
			Binomio newBinomio = new Binomio(newI);

			// TODO save binomios on database

			initBinomioDao();
			// binomioDao.insert(newBinomio);
			closeDao();

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	protected void onBind() {
		getBinomioRequest();
		Log.i("ONBIND ", " request BINOMIO");
	}

	public void getBinomioRequest() {
		Request request = new Request(null, getBaseUrl() + "/evaluations/"
				+ getCollabletId() + "/photo/" + newTarget + urlFinalJSON,
				"get", null);
		makeRequest(request);
	}

	public int[] averageBinomiosFromServer() {

		fechada = 50;
		simples = 50;
		vertical = 50;
		assimetrica = 50;
		opaca = 50;
		externa = 50;

		int[] data = { fechada, simples, vertical, externa, assimetrica, opaca };
		return data;
	}

	public int[] averageBinomiosDatabase(Long idTarget) {
		List<Binomio> lista = getAllFromTarget(idTarget);

		for (Binomio b : lista) {
			fechada += b.getFechada();
			simples += b.getSimples();
			vertical += b.getVertical();
			assimetrica += b.getSimetrica();
			externa += b.getInterna();
			opaca += b.getOpaca();
			Log.i("val!!!", "valor= " + opaca);
		}

		if (lista.size() != 0) {
			fechada = fechada / lista.size();
			simples = simples / lista.size();

			vertical = vertical / lista.size();
			assimetrica = assimetrica / lista.size();
			externa = externa / lista.size();
			opaca = opaca / lista.size();
			Log.i("val!!!", lista.size() + " opaca media= " + opaca);
		} else {
			fechada = 50;
			simples = 50;
			vertical = 50;
			assimetrica = 50;
			opaca = 50;
			externa = 50;
		}

		int[] data = { fechada, simples, vertical, externa, assimetrica, opaca };
		return data;
	}

	public List<Binomio> getAllFromTarget(Long id) {
		initBinomioDao();
		List<Binomio> lista = binomioDao.queryBuilder()
				.where(Properties.TargetId.eq(id)).build().list();
		closeDao();

		return lista;
	}

	public void initBinomioDao() {
		DevOpenHelper helper = new DaoMaster.DevOpenHelper(getActivity(),
				"binomios-db", null);
		SQLiteDatabase db = helper.getWritableDatabase();
		DaoMaster daoMaster = new DaoMaster(db);
		daoSession = daoMaster.newSession();
		binomioDao = daoSession.getBinomioDao();
	}

	public void deleteOne(Binomio b) {
		initBinomioDao();
		binomioDao.delete(b);
		daoSession.delete(b);
		getControlActivity().deletarAlgo(b.getId(), this);
		closeDao();
	}

	public void closeDao() {
		binomioDao.getDatabase().close();
	}

}
