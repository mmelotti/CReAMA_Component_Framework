package com.gw.android.first_components.my_components;

import com.gw.android.R;
import com.gw.android.first_components.my_fragment.CRComponent;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class BuscarGUI extends CRComponent {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.search, container, false);
		Button button = (Button) view.findViewById(R.id.button_find);

		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Activity activity = getActivity();

				if (activity != null) {
					Toast.makeText(activity, "do you found it?",
							Toast.LENGTH_LONG).show();
				}
			}

		});

		return view;
	}
}
