package com.example.my_fragment;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public abstract class MyActivity extends FragmentActivity{

	
	public abstract void configurarTargets();
	public abstract void instanciarComponents();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		instanciarComponents();
		configurarTargets();
	}
		
	
	
}
