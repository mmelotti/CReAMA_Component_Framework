package my_components.binomio;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.annotation.SuppressLint;
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
import com.example.my_fragment.GUIComponent;

@SuppressLint({ "ValidFragment", "NewApi" })
public class BinomioGUI extends GUIComponent {

	private Binomio bin = new Binomio();
	private Long newTarget;
	private int nBin = 5;
	private View binView;
	
	private Button button;
	
	private static final String KEY_LEFT_TEXT_VIEW = "leftTextView";
	private static final String KEY_RIGHT_TEXT_VIEW = "rightTextView";
	private static final String KEY_BINOMIO = "binomio";

	public BinomioGUI() {

	}

	public BinomioGUI(Long t) {
		newTarget = t;
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.binomios, container, false);

		Log.i("create", "binomio gui");
		// Button save = (Button) view.findViewById(R.id.btnBrowse);
		LinearLayout lCon = (LinearLayout) view
				.findViewById(R.id.binomio_container);

		button = (Button) view.findViewById(R.id.button_binomio);
		
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
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

		for (BinomiosArquigrafia binomio : list) {
			View v = inflater.inflate(R.layout.binomio, null);
			TextView left, right;
			left = (TextView) v.findViewById(R.id.label_left);
			right = (TextView) v.findViewById(R.id.label_right);
			left.setText(binomio.getLeft());
			right.setText(binomio.getRight());
			
			
			final TextView seekbarLeftValueText = (TextView) v.findViewById(R.id.seekbar_value_left);
			seekbarLeftValueText.setText(Integer.toString(binomio.getRightValue()) + "%");
			final TextView seekbarRightValueText = (TextView) v.findViewById(R.id.seekbar_value_right);
			seekbarRightValueText.setText(Integer.toString(binomio.getLeftValue()) + "%");
			

			l.addView(v);
			
			
			SeekBar seekbar = (SeekBar) v.findViewById(R.id.seekbar);
			seekbar.setHorizontalScrollBarEnabled(true);
			
			final HashMap<String, Object> tag = new HashMap<String, Object>(3);
			tag.put(KEY_LEFT_TEXT_VIEW, seekbarLeftValueText);
			tag.put(KEY_RIGHT_TEXT_VIEW, seekbarRightValueText);
			tag.put(KEY_BINOMIO, binomio);
			seekbar.setTag(tag);
			
			seekbar.setProgress(binomio.getLeftValue());
			seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
				@Override
				public void onStopTrackingTouch(SeekBar seekBar) { }
				
				@Override
				public void onStartTrackingTouch(SeekBar seekBar) { }
				
				@Override
				public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
					final HashMap<String, Object> tag = (HashMap<String, Object>) seekBar.getTag();
					final BinomiosArquigrafia binomio = (BinomiosArquigrafia) tag.get(KEY_BINOMIO);
					binomio.setLeftValue(progress);
					binomio.setRightValue(100-progress);
					((TextView) tag.get(KEY_LEFT_TEXT_VIEW)).setText(Integer.toString(binomio.getRightValue()) + "%");
					((TextView) tag.get(KEY_RIGHT_TEXT_VIEW)).setText(Integer.toString(binomio.getLeftValue()) + "%");
				}
			});
			
			
		}
	}

}
