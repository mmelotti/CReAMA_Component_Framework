package com.gw.android.first_components.my_components.binomio;

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
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.gw.android.R;import com.gw.android.components.connection_manager.AsyncRequestHandler;
import com.gw.android.components.request.Request;
import com.gw.android.first_components.database.DaoMaster;
import com.gw.android.first_components.database.DaoSession;
import com.gw.android.first_components.database.DaoMaster.DevOpenHelper;
import com.gw.android.first_components.my_components.faq.FaqSendGUI;
import com.gw.android.first_components.my_components.tag.TagDao.Properties;
import com.gw.android.first_components.my_fragment.CRComponent;
import com.gw.android.first_components.my_fragment.ComponentSimpleModel;


@SuppressLint({ "ValidFragment", "NewApi" })
public class BinomioSendGUI extends CRComponent {

	private Long newTarget;

	private BinomioDao binomioDao;
	private DaoSession daoSession;
	private boolean conectado = true;
	
	private int[] values = iniciarBinomios();

	private Button button;

	private static final String KEY_LEFT_TEXT_VIEW = "leftTextView";
	private static final String KEY_RIGHT_TEXT_VIEW = "rightTextView";
	private static final String KEY_BINOMIO = "binomio";
	private String urlPostAvaliation = "http://www.arquigrafia.org.br/18/photo_avaliation/";

	/*
	 * 
	 * 
	 * http://www.arquigrafia.org.br/18/photo_avaliation/1821
	 * 
	 * 
	 *
	 * binomialMgr.binomialFirst...	Aberta
binomialMgr.binomialSecon...	Fechada
binomialMgr.binomialValue...	50
binomialMgr.binomialIdUse...	1
binomialMgr.binomialFirst...	Complexa
binomialMgr.binomialSecon...	Simples
binomialMgr.binomialValue...	50
binomialMgr.binomialIdUse...	1
binomialMgr.binomialFirst...	Horizontal
binomialMgr.binomialSecon...	Vertical
binomialMgr.binomialValue...	50
binomialMgr.binomialIdUse...	1
binomialMgr.binomialFirst...	Interna
binomialMgr.binomialSecon...	Externa
binomialMgr.binomialValue...	50
binomialMgr.binomialIdUse...	1
binomialMgr.binomialFirst...	Simétrica
binomialMgr.binomialSecon...	Assimétrica
binomialMgr.binomialValue...	50
binomialMgr.binomialIdUse...	1
binomialMgr.binomialFirst...	Translúcida
binomialMgr.binomialSecon...	Opaca
binomialMgr.binomialValue...	50
binomialMgr.binomialIdUse...	1
saveBinomial	
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * Content-Disposition: form-data; name="binomialMgr.binomialFirst[21]"
	 * Aberta -----------------------------11311232393095 Content-Disposition:
	 * form-data; name="binomialMgr.binomialSecond[21]" Fechada
	 * -----------------------------11311232393095 Content-Disposition:
	 * form-data; name="binomialMgr.binomialValue[21]" 50
	 * -----------------------------11311232393095 Content-Disposition:
	 * form-data; name="binomialMgr.binomialIdUser[21]" 1
	 * -----------------------------11311232393095 Content-Disposition:
	 * form-data; name="binomialMgr.binomialFirst[19]" Complexa
	 * -----------------------------11311232393095 Content-Disposition:
	 * form-data; name="binomialMgr.binomialSecond[19]" Simples
	 * -----------------------------11311232393095 Content-Disposition:
	 * form-data; name="binomialMgr.binomialValue[19]" 50
	 * -----------------------------11311232393095 Content-Disposition:
	 * form-data; name="binomialMgr.binomialIdUser[19]" 1
	 * -----------------------------11311232393095 Content-Disposition:
	 * form-data; name="binomialMgr.binomialFirst[13]" Horizontal
	 * -----------------------------11311232393095 Content-Disposition:
	 * form-data; name="binomialMgr.binomialSecond[13]" Vertical
	 * -----------------------------11311232393095 Content-Disposition:
	 * form-data; name="binomialMgr.binomialValue[13]" 50
	 * -----------------------------11311232393095 Content-Disposition:
	 * form-data; name="binomialMgr.binomialIdUser[13]" 1
	 * -----------------------------11311232393095 Content-Disposition:
	 * form-data; name="binomialMgr.binomialFirst[20]" Interna
	 * -----------------------------11311232393095 Content-Disposition:
	 * form-data; name="binomialMgr.binomialSecond[20]" Externa
	 * -----------------------------11311232393095 Content-Disposition:
	 * form-data; name="binomialMgr.binomialValue[20]" 50
	 * -----------------------------11311232393095 Content-Disposition:
	 * form-data; name="binomialMgr.binomialIdUser[20]" 1
	 * -----------------------------11311232393095 Content-Disposition:
	 * form-data; name="binomialMgr.binomialFirst[16]" Simétrica
	 * -----------------------------11311232393095 Content-Disposition:
	 * form-data; name="binomialMgr.binomialSecond[16]" Assimétrica
	 * -----------------------------11311232393095 Content-Disposition:
	 * form-data; name="binomialMgr.binomialValue[16]" 50
	 * -----------------------------11311232393095 Content-Disposition:
	 * form-data; name="binomialMgr.binomialIdUser[16]" 1
	 * -----------------------------11311232393095 Content-Disposition:
	 * form-data; name="binomialMgr.binomialFirst[14]" Translúcida
	 * -----------------------------11311232393095 Content-Disposition:
	 * form-data; name="binomialMgr.binomialSecond[14]" Opaca
	 * -----------------------------11311232393095 Content-Disposition:
	 * form-data; name="binomialMgr.binomialValue[14]" 50
	 * -----------------------------11311232393095 Content-Disposition:
	 * form-data; name="binomialMgr.binomialIdUser[14]" 1
	 * -----------------------------11311232393095 Content-Disposition:
	 * form-data; name="saveBinomial"
	 */

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
				if(conectado){
					sendToServer(b);
				}
				
				
				reloadActivity();
			}
		});

		addVariosBinomios(lCon, inflater);

		return view;
	}

	
	private void initializeCallback() {
		// Seta callback para quando terminar a requisição de envio
		AsyncRequestHandler mHandler = new AsyncRequestHandler(true) {
			@Override
			public void onSuccess(String response, Request r) {
				
				reloadActivity();
			}
		};
		setComponentRequestCallback(mHandler);
	}
	
	private void sendToServer(Binomio binomio){
		//o ultimo parece ser vazio sempre
		
		//TODO FALTA INTERNA EXTERNA!!!!
		Request request = new Request(null, urlPostAvaliation, "post",
				"binomialMgr.binomialFirst[21]--" + "Aberta" + 
		"__binomialMgr.binomialSecond[21]--" + "Fechada"
						+ "__binomialMgr.binomialValue[21]--" + binomio.getAberta()+
						"__binomialMgr.binomialIdUser[21]--"+ "1"+
						
						"__binomialMgr.binomialFrist[19]--" +"Complexa"+
						"__binomialMgr.binomialSecond[19]--" + "Simples"+
						"__binomialMgr.binomialValue[19]--" +binomio.getComplexa() +
						"__binomialMgr.binomialIdUser[19]--" +"1" +
						
						"__binomialMgr.binomialFrist[13]--" +"Horizontal"+
						"__binomialMgr.binomialSecond[13]--" + "Vertical"+
						"__binomialMgr.binomialValue[13]--" +binomio.getHorizontal() +
						"__binomialMgr.binomialIdUser[13]--" +"1" +
						
						"__binomialMgr.binomialFrist[20]--" +"Interna"+
						"__binomialMgr.binomialSecond[20]--" + "Externa"+
						"__binomialMgr.binomialValue[20]--" +binomio.getHorizontal() +
						"__binomialMgr.binomialIdUser[20]--" +"1" +
						
						"__binomialMgr.binomialFrist[16]--" +"Simetrica"+
						"__binomialMgr.binomialSecond[16]--" + "Assimetrica"+
						"__binomialMgr.binomialValue[16]--" +"" +
						"__binomialMgr.binomialIdUser[16]--" +"1" +
						
"__binomialMgr.binomialFrist[14]--" +"Translucida"+
"__binomialMgr.binomialSecond[14]--" + "Opaca"+
"__binomialMgr.binomialValue[14]--" +binomio.getTranslucida() +
"__binomialMgr.binomialIdUser[14]--" +"1" + "__saveBinomial--"+"");
				
		
		makeRequest(request);		
		
		
		
		
	}
	
	private void addVariosBinomios(LinearLayout l, LayoutInflater inflater) {

		List<BinomiosArquigrafia> list = new ArrayList<BinomiosArquigrafia>();
		list.add(new BinomiosArquigrafia("Fechada", "Aberta"));
		list.add(new BinomiosArquigrafia("Simples", "Complexa"));
		list.add(new BinomiosArquigrafia("Vertical", "Horizontal"));
		
		list.add(new BinomiosArquigrafia("Simétrica", "Assimétrica"));
		list.add(new BinomiosArquigrafia("Opaca", "Translúcida"));
		list.add(new BinomiosArquigrafia("Externa", "Interna"));

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
