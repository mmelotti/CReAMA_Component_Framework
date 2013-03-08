package com.example.my_fragment;

public abstract class ComponentSimpleModel {

	private String instanceId="";
	private Long targetId = new Long(1); 
	
	
	public Long getTargetId() {
		return targetId;
	}

	public void setTargetId(Long targetId) {
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
