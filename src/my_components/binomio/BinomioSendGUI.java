package my_components.binomio;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import my_components.tag.TagDao.Properties;

import android.annotation.SuppressLint;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.firstcomponents.R;
import com.example.my_fragment.ComponentSimpleModel;
import com.example.my_fragment.CRComponent;

import database.DaoMaster;
import database.DaoSession;
import database.DaoMaster.DevOpenHelper;

@SuppressLint({ "ValidFragment", "NewApi" })
public class BinomioSendGUI extends CRComponent {

	private Long newTarget;

	private BinomioDao binomioDao;
	private DaoSession daoSession;
	
	
	private int[] values = iniciarBinomios();

	private Button button;

	private static final String KEY_LEFT_TEXT_VIEW = "leftTextView";
	private static final String KEY_RIGHT_TEXT_VIEW = "rightTextView";
	private static final String KEY_BINOMIO = "binomio";

	

	public BinomioSendGUI() {

	}

	public BinomioSendGUI(Long t) {
		newTarget = t;
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.binomios_view, container, false);

		Log.i("create", "binomio gui");
		// Button save = (Button) view.findViewById(R.id.btnBrowse);
		LinearLayout lCon = (LinearLayout) view
				.findViewById(R.id.binomio_container);

		button = (Button) view.findViewById(R.id.button_binomio);

		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Long newId = ComponentSimpleModel.getUniqueId(getActivity());

				Binomio b = new Binomio();
				b.setId(newId);
				b.setTargetId(newTarget);
				
				b.setFechada(100-values[0]);
				b.setAberta(values[0]);
				b.setSimples(100-values[1]);
				b.setComplexa(values[1]);
				
				Log.i("val","valor 0 1 2="+values[0]+" "+values[1]+" "+values[2]);
				
				b.setVertical(100-values[2]);
				b.setHorizontal(values[2]);
				b.setSimetrica(100-values[3]);
				b.setAssimetrica(values[3]);
				b.setOpaca(100-values[4]);
				b.setTranslucida(values[4]);
				
				
				initRatingDao();
				binomioDao.insert(b);

				closeDao();
				reloadActivity();
			}
		});

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

		for (BinomiosArquigrafia binomio : list) {
			
			View v = inflater.inflate(R.layout.binomio, null);
			TextView left, right;
			left = (TextView) v.findViewById(R.id.label_left);
			right = (TextView) v.findViewById(R.id.label_right);
			left.setText(binomio.getLeft());
			right.setText(binomio.getRight());

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
			tag.put("nbinomio", i);
			seekbar.setTag(tag);

			seekbar.setProgress(binomio.getLeftValue());
			i++;
			seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
				@Override
				public void onStopTrackingTouch(SeekBar seekBar) {
				}

				@Override
				public void onStartTrackingTouch(SeekBar seekBar) {
				}

				@Override
				public void onProgressChanged(SeekBar seekBar, int progress,
						boolean fromUser) {
					@SuppressWarnings("unchecked")
					final HashMap<String, Object> tag = (HashMap<String, Object>) seekBar
							.getTag();
					
					final BinomiosArquigrafia binomio = (BinomiosArquigrafia) tag
							.get(KEY_BINOMIO);

					final int numero = (Integer) tag.get("nbinomio");
					values[numero] = progress;

					binomio.setLeftValue(progress);
					binomio.setRightValue(100 - progress);
					((TextView) tag.get(KEY_LEFT_TEXT_VIEW)).setText(Integer
							.toString(binomio.getRightValue()) + "%");
					((TextView) tag.get(KEY_RIGHT_TEXT_VIEW)).setText(Integer
							.toString(binomio.getLeftValue()) + "%");
				}
			});

		}
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

	public int[] iniciarBinomios() {

		int[] data = { 50, 50,50,50,50 };
		return data;
	}

}
