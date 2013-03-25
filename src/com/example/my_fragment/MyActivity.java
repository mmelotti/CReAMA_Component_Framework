package com.example.my_fragment;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

public abstract class MyActivity extends FragmentActivity{

	
	public abstract void configurarTargets();
	public abstract void instanciarComponents();
	
	
	private List<MyComponent> componentes = new ArrayList<MyComponent>();
	private FragmentManager fragmentManager; 
	private FragmentTransaction transaction;
	private int[] dependencies; 
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		instanciarComponents();
		configurarTargets();
	}
		
	public void deletarAlgo(Long t, MyComponent m){
		
	}
	

	
	public void addGUIComponent(int id, GUIComponent c) {

		componentes.add(c);
		transaction.add(id, c);
	}

	public void addGUIComponent(int id, GUIComponent c,
			String tag) {

		componentes.add(c);
		transaction.add(id, c, tag);
	}

	
	public void startTransaction(){
		fragmentManager = getSupportFragmentManager();
		transaction = fragmentManager.beginTransaction();
	}
	
	public void finishTransaction(){
		transaction.commit();
	}
	public List<MyComponent> getComponentes() {
		return componentes;
	}
	public void setComponentes(List<MyComponent> componentes) {
		this.componentes = componentes;
	}
	public int[] getDependencies() {
		return dependencies;
	}
	public void setDependencies(int[] dependencies) {
		this.dependencies = dependencies;
	}
	
	public void callbackRemove(Long target, MyComponent component) {
		boolean foundIt = false;

		for (int i : getDependencies()) {
			// tem alguem que depende do que vai ser deletado?
			if (i == component.getGeneralGUIId()) {
				Log.i("Remover!", "encontrou " + i);
				foundIt = true;
				break;
			}
		}

		if (foundIt) {
			for (MyComponent c : getComponentes()) {
				if (c.getComponentTargetId() == component.getGeneralGUIId()) {
					c.deleteAllFrom(target);
					Log.i("Remover!", "from " + target);
					break;
				}
			}
		}
	}
	
	
}
