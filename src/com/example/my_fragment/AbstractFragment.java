package com.example.my_fragment;

import com.example.firstcomponents.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public abstract class AbstractFragment extends Fragment {

	private View view;
	private OnNewMessageListener myMessenger;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		// onCreateView() is a lifecycle event that is unique to a Fragment.
		// This is called when Android
		// needs the layout for this Fragment. The call to
		// LayoutInflater::inflate() simply takes the layout
		// ID for the layout file, the parent view that will hold the layout,
		// and an option to add the inflated
		// view to the parent. This should always be false or an exception will
		// be thrown. Android will add
		// the view to the parent when necessary.
		view = inflater.inflate(R.layout.fragment_basic, container, false);

		// This is how you access your layout views. Notice how we call the
		// findViewById() method
		// on our View directly. There is no method called findViewById()
		// defined on Fragments like
		// there is in an Activity.

		return view;
	}

	public interface OnNewMessageListener {
		public void onMessageNeeded(String msg);
	}
	
	
	
	public void sendMessage(){
		myMessenger.onMessageNeeded("Hello Listener");
	}

	public View onCreateView(String name) {

		return view;

	}

}
