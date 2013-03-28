package com.example.my_fragment;

public class Dependency {

	
	private String source;
	private String target;
	private boolean toMany;
	
	
	public Dependency(String s,String t, boolean b){
		source = s;
		target = t;
		toMany = b;
		
	}


	public String getSource() {
		return source;
	}


	public void setSource(String source) {
		this.source = source;
	}


	public String getTarget() {
		return target;
	}


	public void setTarget(String target) {
		this.target = target;
	}


	public boolean isToMany() {
		return toMany;
	}


	public void setToMany(boolean toMany) {
		this.toMany = toMany;
	}
	
	
	
}
