package com.gw.android.first_components.my_fragment;

import java.util.ArrayList;
import java.util.List;

import com.gw.android.R;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

public abstract class CRActivity extends FragmentActivity {

	public abstract void configurarTargets();

	public abstract void instanciarComponents();

	private List<CRComponent> allComponentsList = new ArrayList<CRComponent>();
	private FragmentManager fragmentManager;
	private FragmentTransaction transaction;

	private List<CRComponent> activeComponentsList = new ArrayList<CRComponent>();
	private List<Dependency> dependencies;

	private int relativeGUIIdCont = 55;

	public CRActivity() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		instanciarComponents();
		configurarTargets();
	}

	public void deletarAlgo(Long t, CRComponent m) {

	}

	public void inserirAlgo(Long t, CRComponent m) {

	}

	public void addGUIComponent(int id, CRComponent c) {
		// add a CRComponent, only if this component is not active
		c.setRelativeFragmentId(relativeGUIIdCont);
		if (allComponentsList.contains(c)) {
			if (activeComponentsList.contains(c)) {

			} else {
				transaction.show(c);
				activeComponentsList.add(c);
			}
		} else {
			allComponentsList.add(c);
			transaction.add(id, c);
			activeComponentsList.add(c);
		}

		upRelativeId();
	}

	public void hideGUIComponent(CRComponent c) {
		// hide one active component
		if (activeComponentsList.contains(c)) {
			transaction.hide(c);
			activeComponentsList.remove(c);
		}
	}

	public void addMultipleGUIComponentsHidingOthers(int rId,
			CRComponent[] array) {
		// add multiple CRComponents, hiding others active components
		for (int i = 0; i < activeComponentsList.size(); i++) {
			transaction.hide(activeComponentsList.get(i));
		}
		activeComponentsList.clear();

		for (int i = 0; i < array.length; i++) {
			addGUIComponent(rId, array[i]);
		}
	}

	public void addMultipleGUIComponents(int rId, CRComponent[] array) {
		// add multiple CRComponents, NOT hiding others active components

		for (int i = 0; i < array.length; i++) {
			addGUIComponent(rId, array[i]);
		}
	}

	public void addGUIComponentHidingOthers(int rId, CRComponent c) {
		// add a CRComponent, hiding others active components
		for (int i = 0; i < activeComponentsList.size(); i++) {
			transaction.hide(activeComponentsList.get(i));
		}
		activeComponentsList.clear();

		addGUIComponent(rId, c);

	}

	public void removeGUIComponent(int id, CRComponent c) {
		// remove a CRComponent
		allComponentsList.remove(c);
		transaction.remove(c);

		// upRelativeId();
	}

	public void addGUIComponentWithTag(int id, CRComponent c) {
		c.setRelativeFragmentId(relativeGUIIdCont);
		allComponentsList.add(c);

		transaction.add(id, c, "" + relativeGUIIdCont);
		upRelativeId();
	}

	public void addComponent(CRComponent c) {
		allComponentsList.add(c);
	}

	public void startTransaction() {
		fragmentManager = getSupportFragmentManager();
		transaction = fragmentManager.beginTransaction();
	}

	public void finishTransaction() {
		transaction.commit();
	}

	public List<CRComponent> getComponentes() {
		return allComponentsList;
	}

	public void setComponentes(List<CRComponent> componentes) {
		this.allComponentsList = componentes;
	}

	public void callbackAdd(Long target, String component) {
		Log.i("ADD!", "entrando no for");
		for (Dependency d : getDependencies()) {
			// tem alguem que depende do que vai ser addedado?
			// Log.i("Remov?", d.getTarget().getNickName()+" <-encontrou??? " +
			// component);
			if (d.getTarget().getNickName().equals(component)) {
				Log.i("ADD!", "encontrou source= "
						+ d.getSource().getNickName());
				for (CRComponent c : getComponentes()) {
					if (c.getNick().equals(d.getSource().getNickName())) {
						c.submittedFrom(target);
						Log.i("ADD!", "callback from= " + component);
						break;
					}
				}

			}
		}

	}

	public void callbackRemove(Long target, String component) {
		boolean foundIt = false;

		for (Dependency d : getDependencies()) {
			// tem alguem que depende do que vai ser deletado?
			// Log.i("Remov?", d.getTarget().getNickName()+" <-encontrou??? " +
			// component);
			if (d.getTarget().getNickName().equals(component)) {
				Log.i("Remover!", "encontrou " + component);
				foundIt = true;
				break;
			}
		}

		if (foundIt) {
			for (CRComponent c : getComponentes()) {
				if (c.getNick().equals(component)) {
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

	public void addDependency(Dependency d) {
		dependencies.add(d);
	}

	public void addGuiComponentToMany(ComponentNaming s,
			ComponentSimpleModel c, int id) {

		// addOne(s,c);

		ComponentDefinitions cd = new ComponentDefinitions();
		CRComponent one = cd.getComponentToMany(c, s.getGuiName());

		one.setNick(s.getNickName());
		addGUIComponentWithTag(id, one);

	}

	public void addGuiComponent(ComponentNaming s, Long target, int id) {
		ComponentDefinitions cd = new ComponentDefinitions();
		CRComponent one = cd.getComponent(target, s.getGuiName());
		one.setNick(s.getNickName());
		addGUIComponentWithTag(id, one);
	}

	public void verDependenciaString(ComponentNaming s, Long target) {
		for (Dependency d : getDependencies()) {
			if (d.getTarget().equals(s)) {

				if (d.isToMany()) {
					List<ComponentSimpleModel> m = listToMany(d.getSource()
							.getGuiName(), target);

					for (ComponentSimpleModel model : m) {
						addGuiComponentToMany(d.getSource(), model,
								R.id.menu_lin);
						verDependenciaString(d.getSource(), model.getId());
					}
				} else {
					addGuiComponent(d.getSource(), target, R.id.menu_lin);
					verDependenciaString(d.getSource(), target);
				}

			}

		}
	}

	public List<ComponentSimpleModel> listToMany(String name, Long target) {
		ComponentDefinitions c = new ComponentDefinitions();
		List<ComponentSimpleModel> list;
		list = c.listToMany(name, target, this);

		return list;
	}

	public void putSharedData(String key, String value) {
		SharedPreferences testPrefs = getSharedPreferences("test_prefs",
				Context.MODE_PRIVATE);
		Editor edit = testPrefs.edit();
		edit.putString(key, value).commit();
	}

	public String getSharedData(String key) {
		SharedPreferences testPrefs = getSharedPreferences("test_prefs",
				Context.MODE_PRIVATE);
		return testPrefs.getString(key, "");
	}

}
