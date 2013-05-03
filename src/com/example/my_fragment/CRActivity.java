package com.example.my_fragment;

import java.util.ArrayList;
import java.util.List;

import com.example.firstcomponents.R;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

public abstract class CRActivity extends FragmentActivity {

	public abstract void configurarTargets();

	public abstract void instanciarComponents();

	private List<CRComponent> componentes = new ArrayList<CRComponent>();
	private FragmentManager fragmentManager;
	private FragmentTransaction transaction;

	private List<Dependency> dependencies;

	private int relativeGUIIdCont = 55;

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
		c.setRelativeFragmentId(relativeGUIIdCont);
		componentes.add(c);
		transaction.add(id, c);
		upRelativeId();
	}

	public void addGUIComponentWithTag(int id, CRComponent c) {
		c.setRelativeFragmentId(relativeGUIIdCont);
		componentes.add(c);

		transaction.add(id, c, "" + relativeGUIIdCont);
		upRelativeId();
	}

	public void addComponent(CRComponent c) {
		componentes.add(c);
	}

	public void startTransaction() {
		fragmentManager = getSupportFragmentManager();
		transaction = fragmentManager.beginTransaction();
	}

	public void finishTransaction() {
		transaction.commit();
	}

	public List<CRComponent> getComponentes() {
		return componentes;
	}

	public void setComponentes(List<CRComponent> componentes) {
		this.componentes = componentes;
	}

	public void callbackAdd(Long target, String component) {
		Log.i("ADD!", "entrando no for");
		for (Dependency d : getDependencies()) {
			// tem alguem que depende do que vai ser addedado?
			// Log.i("Remov?", d.getTarget().getNickName()+" <-encontrou??? " +
			// component);
			if (d.getTarget().getNickName().equals(component)) {
				Log.i("ADD!", "encontrou source= "+d.getSource().getNickName());
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

	public void addOther(ComponentNaming s, ComponentSimpleModel c, int id) {

		// addOne(s,c);

		ComponentDefinitions cd = new ComponentDefinitions();
		CRComponent one = cd.getComponentToMany(c, s.getGuiName());

		one.setNick(s.getNickName());
		addGUIComponentWithTag(id, one);

	}

	public void addOther(ComponentNaming s, Long target, int id) {
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
						addOther(d.getSource(), model, R.id.menu_lin);
						verDependenciaString(d.getSource(), model.getId());
					}
				} else {
					addOther(d.getSource(), target, R.id.menu_lin);
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

}
