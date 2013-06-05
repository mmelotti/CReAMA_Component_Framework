package com.gw.android.my_components.binomio;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import android.annotation.SuppressLint;
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
import com.gw.android.database.DaoMaster;
import com.gw.android.database.DaoSession;
import com.gw.android.database.DaoMaster.DevOpenHelper;
import com.gw.android.my_components.tag.TagDao.Properties;
import com.gw.android.my_fragment.CRComponent;


@SuppressLint({ "ValidFragment", "NewApi" })
public class BinomioAverageGUI extends CRComponent {

	//private Binomio bin = new Binomio();
	private Long newTarget;
	//private int nBin = 5;
	//private View binView;
	private int fechada = 0, simples = 0, vertical = 0, simetrica = 0,
			opaca = 0;

	private BinomioDao binomioDao;
	private DaoSession daoSession;



	private static final String KEY_LEFT_TEXT_VIEW = "leftTextView";
	private static final String KEY_RIGHT_TEXT_VIEW = "rightTextView";
	private static final String KEY_BINOMIO = "binomio";

	public BinomioAverageGUI(Long t) {
		newTarget = t;
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.binomios_average, container, false);

		Log.i("create", "binomio gui");
		// Button save = (Button) view.findViewById(R.id.btnBrowse);
		LinearLayout lCon = (LinearLayout) view
				.findViewById(R.id.binomio_container_average);

		

		addVariosBinomios(lCon, inflater);

		return view;
	}

	private void addVariosBinomios(LinearLayout l, LayoutInflater inflater) {
		List<BinomiosArquigrafia> list = new ArrayList<BinomiosArquigrafia>();
		list.add(new BinomiosArquigrafia("Fechada", "Aberta"));
		list.add(new BinomiosArquigrafia("Simples", "Complexa"));
		list.add(new BinomiosArquigrafia("Vertical", "Horizontal"));
		list.add(new BinomiosArquigrafia("Simétrica", "Assimétrica"));
		list.add(new BinomiosArquigrafia("Opaca", "Translúcida"));

		int i = 0;
		int[] values = averageBinomios(newTarget);

		for (BinomiosArquigrafia binomio : list) {
			View v = inflater.inflate(R.layout.binomio, null);
			TextView left, right;
			left = (TextView) v.findViewById(R.id.label_left);
			right = (TextView) v.findViewById(R.id.label_right);
			left.setText(binomio.getLeft());
			right.setText(binomio.getRight());

			binomio.setLeftValue(100 - values[i]);
			binomio.setRightValue(values[i]);

			final TextView seekbarLeftValueText = (TextView) v
					.findViewById(R.id.seekbar_value_left);
			seekbarLeftValueText.setText(Integer.toString(binomio
					.getRightValue()) + "%");
			final TextView seekbarRightValueText = (TextView) v
					.findViewById(R.id.seekbar_value_right);
			seekbarRightValueText.setText(Integer.toString(binomio
					.getLeftValue()) + "%");

			l.addView(v);

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

	public int[] averageBinomios(Long idTarget) {
		List<Binomio> lista = getAllFromTarget(idTarget);

		for (Binomio b : lista) {
			fechada += b.getFechada();
			simples += b.getSimples();
			vertical += b.getVertical();
			simetrica += b.getSimetrica();
			opaca += b.getOpaca();
			Log.i("val!!!","valor= "+ opaca);
		}

		if (lista.size() != 0) {
			fechada = fechada / lista.size();
			simples = simples / lista.size();
			
			
			vertical = vertical / lista.size();
			simetrica = simetrica / lista.size();
			opaca = opaca / lista.size();
			Log.i("val!!!",lista.size()+" opaca media= "+ opaca);
		} else {
			fechada = 50;
			simples = 50;
			vertical = 50;
			simetrica = 50;
			opaca = 50;
		}

		int[] data = { fechada, simples, vertical, simetrica, opaca };
		return data;
	}

	public List<Binomio> getAllFromTarget(Long id) {
		initRatingDao();
		List<Binomio> lista = binomioDao.queryBuilder()
				.where(Properties.TargetId.eq(id)).build().list();
		closeDao();

		return lista;
	}

	public void initRatingDao() {
		DevOpenHelper helper = new DaoMaster.DevOpenHelper(getActivity(),
				"binomios-db", null);
		SQLiteDatabase db = helper.getWritableDatabase();
		DaoMaster daoMaster = new DaoMaster(db);
		daoSession = daoMaster.newSession();
		binomioDao = daoSession.getBinomioDao();
	}

	public void closeDao() {
		binomioDao.getDatabase().close();
	}

}
