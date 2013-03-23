package com.example.my_fragment;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.app.Fragment;

public class MyComponent extends Fragment {

	private int generalGUIId = -1;
	private int componentTargetId = -1;
	private Long current = (long) 1;
	private MyComponent componentTarget;
	private List<Integer> instancesId = new ArrayList<Integer>();

	public int getComponentTargetId() {
		return componentTargetId;
	}

	public void setComponentTargetId(int componentTargetId) {
		this.componentTargetId = componentTargetId;
	}

	public Long getCurrent() {
		return current;
	}

	public void setCurrent(Long current) {
		this.current = current;
	}

	public void addInstanceId(int n) {
		instancesId.add(n);
	}

	public boolean removeInstanceId(int n) {
		return instancesId.remove((Object) n);
	}

	public MyComponent getComponentTarget() {
		return componentTarget;
	}

	public void setComponentTarget(MyComponent componentTarget) {
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
