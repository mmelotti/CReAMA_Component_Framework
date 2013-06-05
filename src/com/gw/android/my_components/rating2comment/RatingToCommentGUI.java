package com.gw.android.my_components.rating2comment;

import java.util.ArrayList;
import java.util.List;


import android.annotation.SuppressLint;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gw.android.R;
import com.gw.android.database.DaoMaster;
import com.gw.android.database.DaoSession;
import com.gw.android.database.DaoMaster.DevOpenHelper;
import com.gw.android.my_components.Constants;
import com.gw.android.my_components.comment.CommentListGUI;
import com.gw.android.my_components.comment.CommentSendGUI;
import com.gw.android.my_components.comment.CommentViewGUI;
import com.gw.android.my_components.comment.CommentDao.Properties;
import com.gw.android.my_components.rating.RatingViewGUI;
import com.gw.android.my_fragment.CRComponent;
import com.gw.android.my_fragment.ComponentDefinitions;
import com.gw.android.my_fragment.ComponentNaming;
import com.gw.android.my_fragment.ComponentSimpleModel;
import com.gw.android.my_fragment.Dependency;
import com.gw.android.my_fragment.GenericComponent;


@SuppressLint("ValidFragment")
public class RatingToCommentGUI extends CRComponent {
	CommentViewGUI comview;
	RatingViewGUI ratview;
	CommentSendGUI send;
	Long newTarget;
	private RatingToCommentDao rating2cDao;
	private DaoSession daoSession;
	private RatingToComment rating2c;
	private ComponentNaming commentView, commentSend, rating2commentView, rating;

	public RatingToCommentGUI(Long target) {
		newTarget = target;
	}

	public RatingToCommentGUI(GenericComponent target) {
		setComponentTarget(target);
	}

	public RatingToCommentGUI() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.cr_composed, container, false);
		lookForTarget();
		configurarTargets();
		
		//botar na tela
		initTransaction();
		verDependenciaString(rating2commentView, rating2c.getId());
		finishTransaction();	
		
		//addComponent();
		return view;
	}

	public void lookForTarget() {

		initRatingToCommentDao();
		Log.e("look", " target " + newTarget);
		List<RatingToComment> lista = rating2cDao.queryBuilder()
				.where(Properties.TargetId.eq(newTarget)).build().list();

		if (lista.size() == 0) {
			createNew();
			closeDao();
			return;
		}

		rating2c = lista.get(0);
		closeDao();
	}

	public void configurarTargets() {
		setDependencies(new ArrayList<Dependency>());

		commentView = new ComponentNaming(Constants.CommentViewGUIName, Constants.CommentViewGUIName+"inside");
		commentSend = new ComponentNaming(Constants.CommentSendGUIName, Constants.CommentSendGUIName+"inside");
		rating2commentView = new ComponentNaming(Constants.RatingToCommentGUIName, Constants.RatingToCommentGUIName+"inside");
		rating = new ComponentNaming(Constants.RatingViewGUIName,Constants.RatingViewGUIName+"inside");

		addDependencie(new Dependency(commentView, rating2commentView, true));
		addDependencie(new Dependency(rating, commentView, false));
		addDependencie(new Dependency(commentSend, rating2commentView, false));
	}

	public void createNew() {
		Long newId = ComponentSimpleModel.getUniqueId(getActivity());
		Log.i("composed", " com target " + newTarget);
		rating2c = new RatingToComment(newId, newTarget);
		rating2cDao.insert(rating2c);
	}

	public void initRatingToCommentDao() {
		DevOpenHelper helper = new DaoMaster.DevOpenHelper(getActivity(),
				"ratingtocomments-db", null);
		SQLiteDatabase db = helper.getWritableDatabase();
		DaoMaster daoMaster = new DaoMaster(db);
		daoSession = daoMaster.newSession();
		rating2cDao = daoSession.getRatingToCommentDao();
	}

	public void closeDao() {
		rating2cDao.getDatabase().close();
	}

	public void addComponent() {
		initTransaction();
		send = new CommentSendGUI(rating2c.getId());
		transaction.add(R.id.rootComposed, send);
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
					List<ComponentSimpleModel> m =  new 
							CommentListGUI(target).getListSimple(target, getActivity());
					
					
					for (ComponentSimpleModel model : m) {
						addOther(d.getSource().getGuiName(), model,R.id.rootComposed);
						verDependenciaString(d.getSource(), model.getId());
					}
				} else {
					addOther(d.getSource().getGuiName(), target,R.id.rootComposed);
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

}
