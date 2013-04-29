package com.example.my_fragment;

public class Dependency {

	private ComponentNaming source;
	private ComponentNaming target;
	private boolean toMany;

	public Dependency(ComponentNaming source, ComponentNaming target,
			boolean toMany) {
		this.source = source;
		this.target = target;
		this.toMany = toMany;
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
