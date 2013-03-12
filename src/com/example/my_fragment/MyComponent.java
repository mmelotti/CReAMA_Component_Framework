package com.example.my_fragment;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.app.Fragment;

public class MyComponent extends Fragment{

	private int componentId=-1;
	
	private Long current = (long) 1;
	
	public Long getCurrent() {
		return current;
	}

	public void setCurrent(Long current) {
		this.current = current;
	}

	private MyComponent componentTarget;
	
	private List<Integer> instancesId = new ArrayList<Integer>();
	
	public void addInstanceId(int n){
		instancesId.add(n);
	}
	
	public boolean removeInstanceId(int n){
		return instancesId.remove((Object) n);
	}
	
	public MyComponent getComponentTarget() {
		return componentTarget;
	}

	public void setComponentTarget(MyComponent componentTarget) {
		this.componentTarget = componentTarget;
	}

	public int getComponentId() {
		return componentId;
	}

	public void setId(int id) {
		this.componentId = id;
	}
	
	
	
	
	
}
