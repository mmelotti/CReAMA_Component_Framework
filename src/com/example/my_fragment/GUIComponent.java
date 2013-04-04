package com.example.my_fragment;

import android.app.Activity;
import android.support.v4.app.FragmentTransaction;

public abstract class GUIComponent extends GenericComponent {

	private MyActivity controlActivity;

	private RequestListener component;
	private String nick;

	private int relativeFragmentId = -1;

	public void reloadActivity() {
		Activity a = getActivity();
		a.finish();
		startActivity(a.getIntent());
	}

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

	public void addComponentToView(GUIComponent comp, int viewId) {
		FragmentTransaction ft = getChildFragmentManager().beginTransaction();
		ft.add(viewId, comp);
		ft.commit();
	}

	public void setControlActivity(MyActivity mya) {
		controlActivity = mya;
	}

	public MyActivity getControlActivity() {
		return controlActivity;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

}
