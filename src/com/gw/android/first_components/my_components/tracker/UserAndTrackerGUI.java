package com.gw.android.first_components.my_components.tracker;

import android.os.Bundle;
import android.util.Log;

import com.gw.android.R;
import com.gw.android.components.connection_manager.AsyncRequestHandler;
import com.gw.android.components.request.Request;
import com.gw.android.first_components.my_components.user.UserViewGUI;
import com.gw.android.first_components.my_fragment.CRComposedComponent;

public class UserAndTrackerGUI extends CRComposedComponent{
	TrackerGUI tracker;
	UserViewGUI user;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.blank);
		if (savedInstanceState == null)
			initComponents();
	}

	private void initComponents() {
		initTransaction();

		user = new UserViewGUI();
		user.setNick("user_nick");
		user.setTrackable(true);

		AsyncRequestHandler mHandler = new AsyncRequestHandler() {
			@Override
			public void onSuccess(String arg1, Request r) {
				initTransaction();
				Log.e("ACTIVITY", "START TRANSACTION TRACKER");
				tracker = new TrackerGUI(user);
				addGUIComponent(R.id.root, tracker);

				finishTransaction();
			}

			@Override
			public void onFailure(Throwable arg0, String arg1, Request request) {
			}
		};

		user.setApplicationRequestCallback(mHandler);
		Log.e("ACTIVITY", "TRACKER");

		addGUIComponent(R.id.root, user);
		finishTransaction();
	}

}
