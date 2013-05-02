package com.example.my_fragment;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.support.v4.app.FragmentTransaction;

public abstract class CRComponent extends GenericComponent {

	private CRActivity controlActivity;

	private RequestListener component=null;
	private String nick;
	private static String MSG_ENVIOU="enviou";

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

	public void addComponentToView(CRComponent comp, int viewId) {
		FragmentTransaction ft = getChildFragmentManager().beginTransaction();
		ft.add(viewId, comp);
		ft.commit();
	}

	public void setControlActivity(CRActivity mya) {
		controlActivity = mya;
	}

	public CRActivity getControlActivity() {
		return controlActivity;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public void enviou(){
		if(component!=null){
			sendMessage(MSG_ENVIOU);
		}
		
	}
	
	
	public List<ComponentSimpleModel> getListSimple(Long target, Activity a) {
		ArrayList<ComponentSimpleModel> list = new ArrayList<ComponentSimpleModel>();

		return list;
	}
	
	public void submittedFrom(Long target){
		
	}

}
