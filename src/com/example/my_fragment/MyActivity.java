package com.example.my_fragment;

import java.util.ArrayList;
import java.util.List;

import my_components.comment.CommentListGUI;

import com.example.firstcomponents.R;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

public abstract class MyActivity extends FragmentActivity {

	public abstract void configurarTargets();

	public abstract void instanciarComponents();

	private List<GenericComponent> componentes = new ArrayList<GenericComponent>();
	private FragmentManager fragmentManager;
	private FragmentTransaction transaction;
	private int[] dependenciesInt;
	private List<Dependency> dependencies;

	private int relativeGUIIdCont = 55;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		instanciarComponents();
		configurarTargets();
	}

	public void deletarAlgo(Long t, GenericComponent m) {

	}

	public void addGUIComponent(int id, GUIComponent c) {
		c.setRelativeFragmentId(relativeGUIIdCont);
		componentes.add(c);
		transaction.add(id, c);
		upRelativeId();
	}

	public void addGUIComponentWithTag(int id, GUIComponent c) {
		c.setRelativeFragmentId(relativeGUIIdCont);
		componentes.add(c);
		transaction.add(id, c, "" + relativeGUIIdCont);
		upRelativeId();
	}
	
	public void addComponent(GUIComponent c){
		componentes.add(c);
	}
	

	public void startTransaction() {
		fragmentManager = getSupportFragmentManager();
		transaction = fragmentManager.beginTransaction();
	}

	public void finishTransaction() {
		transaction.commit();
	}

	public List<GenericComponent> getComponentes() {
		return componentes;
	}

	public void setComponentes(List<GenericComponent> componentes) {
		this.componentes = componentes;
	}

	public int[] getDependenciesInt() {
		return dependenciesInt;
	}

	public void setDependenciesInt(int[] dependencies) {
		this.dependenciesInt = dependencies;
	}

	public void callbackRemove(Long target, GenericComponent component) {
		boolean foundIt = false;

		for (int i : getDependenciesInt()) {
			// tem alguem que depende do que vai ser deletado?
			if (i == component.getGeneralGUIId()) {
				Log.i("Remover!", "encontrou " + i);
				foundIt = true;
				break;
			}
		}

		if (foundIt) {
			for (GenericComponent c : getComponentes()) {
				if (c.getComponentTargetId() == component.getGeneralGUIId()) {
					c.deleteAllFrom(target);
					Log.i("Remover!", "from " + target);
					break;
				}
			}
		}
	}

	public void upRelativeId() {
		relativeGUIIdCont++;
	}

	public int getRelativeGUIIdCont() {
		return relativeGUIIdCont;
	}

	public void setRelativeGUIIdCont(int relativeGUIIdCont) {
		this.relativeGUIIdCont = relativeGUIIdCont;
	}

	

	public List<Dependency> getDependencies() {
		return dependencies;
	}

	public void setDependencies(List<Dependency> dependencies) {
		this.dependencies = dependencies;
	}
	
	public void addDependencie(Dependency d){
		dependencies.add(d);
	}
	

	public void addOther(String s, ComponentSimpleModel c, int id) {

		// addOne(s,c);
		Log.i("criar comentario", " com id " + c.getId());
		ComponentDefinitions cd = new ComponentDefinitions();
		GUIComponent one = cd.getComponent(c, s);
		addGUIComponentWithTag(id, one);

	}

	public void addOther(String s, Long target, int id) {

		// addOne(s,c);
		// Log.i("criar comentario", " com id " + c.getId());
		ComponentDefinitions cd = new ComponentDefinitions();
		GUIComponent one = cd.getComponent(target, s);
		addGUIComponentWithTag(id, one);

	}

	public void verDependenciaString(String s, Long target) {
		for (Dependency d : getDependencies()) {
			if (d.getTarget().equals(s)) {
				Log.i("achou dependencia", "  com-> target " + s + " source "
						+ d.getSource());
				if (d.isToMany()) {
					List<ComponentSimpleModel> m =  new 
							CommentListGUI(target).getListSimple(target, this);
					
					
					for (ComponentSimpleModel model : m) {
						addOther(d.getSource(), model,R.id.menu_lin);
						verDependenciaString(d.getSource(), model.getId());
					}
				} else {
					addOther(d.getSource(), target,R.id.menu_lin);
					verDependenciaString(d.getSource(), target);
				}

			}

		}
	}


}
