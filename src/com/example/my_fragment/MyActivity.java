package com.example.my_fragment;

import java.util.ArrayList;
import java.util.List;

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
	private int[] dependencies;

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

	public int[] getDependencies() {
		return dependencies;
	}

	public void setDependencies(int[] dependencies) {
		this.dependencies = dependencies;
	}

	public void callbackRemove(Long target, GenericComponent component) {
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

	public void addDependentList(List<ComponentSimpleModel> lista, String s1,
			int target, List<String> list2, int fragmentId, MyActivity control) {

		ComponentDefinitions def = new ComponentDefinitions();

		for (int i = 0; i < lista.size(); i++) {

			GUIComponent com = def.getComponent(lista.get(i), s1);
			com.setComponentTargetId(target);
			com.setControlActivity(control);
			addGUIComponentWithTag(fragmentId, com);

			// para cada component, outros mais!
			for (int j = 0; j < list2.size(); j++) {

				GUIComponent rat = def.getComponent(lista.get(i).getId(),
						list2.get(j));
				rat.setComponentTargetId(1);
				rat.setControlActivity(control);
				addGUIComponentWithTag(fragmentId, rat);
			}

		}
	}

	public void addListInsideList(List<ComponentSimpleModel> lista, String s1,
			int target, List<String> list2, int fragmentId, MyActivity control,
			String before,String newList, int newTarget) {

		ComponentDefinitions def = new ComponentDefinitions();

		for (int i = 0; i < lista.size(); i++) {

			GUIComponent com = def.getComponent(lista.get(i), s1);
			com.setComponentTargetId(target);
			com.setControlActivity(control);
			addGUIComponentWithTag(fragmentId, com);

			// para cada component, outros mais!
			for (int j = 0; j < list2.size(); j++) {

				
				if(list2.get(j).equals(before)){
					addListForOne(lista, newList, newTarget, fragmentId, control);
				}
				GUIComponent rat = def.getComponent(lista.get(i).getId(),
						list2.get(j));
				rat.setComponentTargetId(1);
				rat.setControlActivity(control);
				addGUIComponentWithTag(fragmentId, rat);
			}

		}
	}

	public void addListForOne(List<ComponentSimpleModel> lista, String s1,
			int target, int fragmentId, MyActivity control) {

		ComponentDefinitions def = new ComponentDefinitions();

		for (int i = 0; i < lista.size(); i++) {

			GUIComponent com = def.getComponent(lista.get(i), s1);
			com.setComponentTargetId(target);
			com.setControlActivity(control);
			addGUIComponentWithTag(fragmentId, com);

			// para cada component, outros mais!

		}
	}

}
