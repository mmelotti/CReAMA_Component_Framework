package com.gw.android.first_components.my_fragment;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.gw.android.R;

public class CRComposedComponent extends CRComponent {

	public void addComponent() {
		initTransaction();
		// put components here
		finishTransaction();
	}

	public void initTransaction() {

		transaction = getChildFragmentManager().beginTransaction();

	}

	public void finishTransaction() {
		transaction.commit();
	}

	// PARTE PARA COMPONENTES COMPOSTOS

	private List<GenericComponent> componentes = new ArrayList<GenericComponent>();
	private FragmentTransaction transaction;
	private List<Dependency> dependencies;

	private int relativeGUIIdCont = 55;

	public void addOther(String s, ComponentSimpleModel c, int id) {

		// addOne(s,c);

		ComponentDefinitions cd = new ComponentDefinitions();
		CRComponent one = cd.getComponentToMany(c, s);
		addGUIComponentWithTag(id, one);
	}

	public void addOther(String s, Long target, int id) {
		// addOne(s,c);
		// Log.i("criar comentario", " com id " + c.getId());
		ComponentDefinitions cd = new ComponentDefinitions();
		CRComponent one = cd.getComponent(target, s);
		addGUIComponentWithTag(id, one);
	}

	public void verDependenciaString(ComponentNaming s, Long target) {
		for (Dependency d : getDependencies()) {
			if (d.getTarget().equals(s)) {
				Log.i("achou dependencia", "  com-> target " + s + " source "
						+ d.getSource());
				if (d.isToMany()) {
					List<ComponentSimpleModel> m = listToMany(d.getSource()
							.getGuiName(), target);

					for (ComponentSimpleModel model : m) {
						addOther(d.getSource().getGuiName(), model,
								R.id.rootComposed);
						verDependenciaString(d.getSource(), model.getId());
					}
				} else {
					addOther(d.getSource().getGuiName(), target,
							R.id.rootComposed);
					verDependenciaString(d.getSource(), target);
				}

			}

		}
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

	public void addDependencie(Dependency d) {
		dependencies.add(d);
	}

	public List<ComponentSimpleModel> listToMany(String name, Long target) {
		ComponentDefinitions c = new ComponentDefinitions();
		List<ComponentSimpleModel> list;
		list = c.listToMany(name, target, getActivity());

		return list;
	}

}
