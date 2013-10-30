package com.gw.android.first_components.my_components.arquigrafia;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gw.android.R;

import com.gw.android.first_components.my_fragment.CRActivity;
import com.gw.android.first_components.my_fragment.CRComponent;

public class ArquigrafiaLogoGUI extends CRComponent {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		setControlActivity((CRActivity) getActivity());

		// li = inflater;
		View view = inflater.inflate(R.layout.arquigrafia_logo, container,
				false);

		return view;
	}
}
