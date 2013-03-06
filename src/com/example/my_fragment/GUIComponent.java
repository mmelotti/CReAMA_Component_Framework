package com.example.my_fragment;



import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class GUIComponent extends MyComponent {

	public abstract View onCreateView(LayoutInflater inflater,
			ViewGroup container, Bundle savedInstanceState);

	private RequestListener component;

	public interface RequestListener {
		public String onRequest(String msg);
	}

	public String sendMessage(String msg) {
		String re = component.onRequest(msg);
		
		return re;
	}

	public RequestListener getComponent() {
		return component;
	}

	public void setComponent(RequestListener component) {
		this.component = component;
	}

	
	
	
	
}
