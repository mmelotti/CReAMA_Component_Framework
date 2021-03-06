package com.gw.android.first_components.my_fragment;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.app.DialogFragment;

public class GenericComponent extends DialogFragment {

	private int generalGUIId = -1;
	private int componentTargetId = -1;
	private Long current = (long) 1;

	private GenericComponent componentTarget;
	private List<Integer> instancesId = new ArrayList<Integer>();

	public int getComponentTargetId() {
		return componentTargetId;
	}

	public void setComponentTargetId(int componentTargetId) {
		this.componentTargetId = componentTargetId;
	}

	public Long getCurrentInstanceId() {
		return current;
	}

	public void setCurrentId(Long current) {
		this.current = current;
	}

	public void addInstanceId(int n) {
		instancesId.add(n);
	}

	public boolean removeInstanceId(int n) {
		return instancesId.remove((Object) n);
	}

	public GenericComponent getComponentTarget() {
		return componentTarget;
	}

	public void setComponentTarget(GenericComponent componentTarget) {
		this.componentTarget = componentTarget;
	}

	public int getGeneralGUIId() {
		return generalGUIId;
	}

	public void setGeneralGUIId(int id) {
		this.generalGUIId = id;
	}

	public void deleteAllFrom(Long target) {

	}

}
