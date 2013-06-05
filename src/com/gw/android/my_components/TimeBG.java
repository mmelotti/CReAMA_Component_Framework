package com.gw.android.my_components;

import java.util.Calendar;

import com.gw.android.my_fragment.BGComponent;

public class TimeBG extends BGComponent {

	public String getHours() {
		Calendar c = Calendar.getInstance();
		String hora = Integer.toString(c.get(Calendar.HOUR));

		return "numsei";
	}

	@Override
	public String onRequest(String msg) {
		String resposta = "";

		if (msg.equals("hora"))
			resposta = getHours();

		return resposta;
	}

}
