package com.example.my_fragment;

public abstract class ComponentSimpleModel {

	private String instanceId="";
	private int targetId = 1; 
	
	
	public int getTargetId() {
		return targetId;
	}

	public void setTargetId(int targetId) {
		this.targetId = targetId;
	}

	public String getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}

	public abstract void save();
	
	public abstract void restore();
	
}
