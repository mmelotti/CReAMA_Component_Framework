package com.example.my_fragment;



import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class GUIComponent extends MyComponent {

	public abstract View onCreateView(LayoutInflater inflater,
			ViewGroup container, Bundle savedInstanceState);

	private RequestListener component;
	
	private int relativeFragmentId=-1;

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

	public int getRelativeFragmentId() {
		return relativeFragmentId;
	}

	public void setRelativeFragmentId(int relativeFragmentId) {
		this.relativeFragmentId = relativeFragmentId;
	}

	
	
	
	
}
