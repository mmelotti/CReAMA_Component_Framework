package com.gw.android.my_components.request;

import android.content.Intent;

public interface RequestDoneListener {
	
	void onRequestDone(Intent data, boolean success);
	
}