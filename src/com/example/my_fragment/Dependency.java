package com.example.my_fragment;

public class Dependency {

	
	private ComponentNaming source;
	private ComponentNaming target;
	
	private boolean toMany;
	
	
	public Dependency(ComponentNaming s,ComponentNaming t, boolean b){
		source = s;
		target = t;
		toMany = b;
		
		
	}


	public ComponentNaming getSource() {
		return source;
	}


	public void setSource(ComponentNaming source) {
		this.source = source;
	}


	public ComponentNaming getTarget() {
		return target;
	}


	public void setTarget(ComponentNaming target) {
		this.target = target;
	}


	public boolean isToMany() {
		return toMany;
	}


	public void setToMany(boolean toMany) {
		this.toMany = toMany;
	}
	
	
	
}
