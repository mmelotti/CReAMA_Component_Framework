package my_components.rating2comment;

import java.util.ArrayList;
import java.util.List;

import my_components.Constants;
import my_components.comment.CommentListGUI;
import my_components.comment.CommentSendGUI;
import my_components.comment.CommentViewGUI;
import my_components.comment.CommentDao.Properties;
import my_components.rating.Rating;
import my_components.rating.RatingDao;
import my_components.rating.RatingViewGUI;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.firstcomponents.R;
import com.example.my_fragment.ComponentDefinitions;
import com.example.my_fragment.ComponentSimpleModel;
import com.example.my_fragment.Dependency;
import com.example.my_fragment.GUIComponent;
import com.example.my_fragment.GenericComponent;

import database.DaoMaster;
import database.DaoSession;
import database.DaoMaster.DevOpenHelper;

public class RatingToCommentGUI extends GUIComponent {

	CommentViewGUI comview;
	RatingViewGUI ratview;
	CommentSendGUI send;
	Long newTarget;
	private RatingToCommentDao rating2cDao;
	private DaoSession daoSession;
	private RatingToComment rating2c;

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

		View view = inflater.inflate(R.layout.composed, container, false);
		lookForTarget();
		configurarTargets();
		
		//botar na tela
		initTransaction();
		verDependenciaString(Constants.RatingToCommentGUIName, rating2c.getId());
		finishTransaction();
		
		
		//addComponent();
		return view;
	}

	public void lookForTarget() {

		initRatingToCommentDao();
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

		Dependency d;
		setDependencies(new ArrayList<Dependency>());
		d = new Dependency(Constants.CommentViewGUIName,
				Constants.RatingToCommentGUIName, true);
		addDependencie(d);
		d = new Dependency(Constants.RatingViewGUIName,
				Constants.CommentViewGUIName, false);
		addDependencie(d);
		d = new Dependency(Constants.CommentSendGUIName,
				Constants.RatingToCommentGUIName, false);
		addDependencie(d);

	}

	public void createNew() {
		Long newId = ComponentSimpleModel.getUniqueId(getActivity());

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
	private FragmentManager fragmentManager;
	private FragmentTransaction transaction;
	private int[] dependenciesInt;
	private List<Dependency> dependencies;

	private int relativeGUIIdCont = 55;

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
					List<ComponentSimpleModel> m = new CommentListGUI(target)
							.getListSimple(target, getActivity());

					for (ComponentSimpleModel model : m) {
						addOther(d.getSource(), model, R.id.rootComposed);
						verDependenciaString(d.getSource(), model.getId());
					}
				} else {
					addOther(d.getSource(), target, R.id.rootComposed);
					verDependenciaString(d.getSource(), target);
				}

			}

		}
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
