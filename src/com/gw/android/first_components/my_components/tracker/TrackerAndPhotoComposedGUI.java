package com.gw.android.first_components.my_components.tracker;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gw.android.R;
import com.gw.android.components.connection_manager.AsyncRequestHandler;
import com.gw.android.components.request.Request;
import com.gw.android.first_components.my_components.photo.PhotoViewGUI;

import com.gw.android.first_components.my_fragment.CRComposedComponent;

public class TrackerAndPhotoComposedGUI extends CRComposedComponent {
	TrackerGUI tracker;
	PhotoViewGUI photo;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.blank);
		if (savedInstanceState == null)
			initComponents();
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.cr_composed, container, false);

		return view;
	}

	private void initComponents() {
		Log.e("ACTIVITY", "init components");
		initTransaction();

		photo = new PhotoViewGUI();

		photo.setTrackable(true);

		AsyncRequestHandler mHandler = new AsyncRequestHandler() {
			@Override
			public void onSuccess(String arg1, Request r) {
				initTransaction();

				tracker = new TrackerGUI(photo);
				addGUIComponent(R.id.rootComposed, tracker);

				finishTransaction();
			}

			@Override
			public void onFailure(Throwable arg0, String arg1, Request request) {
			}
		};

		photo.setApplicationRequestCallback(mHandler);
		Log.e("ACTIVITY", "TRACKER");

		addGUIComponent(R.id.rootComposed, photo);
		finishTransaction();
	}

}