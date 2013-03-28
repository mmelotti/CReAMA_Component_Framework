package my_activities;

import java.util.ArrayList;
import java.util.List;

import my_components.comment.CommentListGUI;
import my_components.comment.CommentSendGUI;
import my_components.comment.CommentViewGUI;
import my_components.photo.PhotoGUI;

import android.os.Bundle;

import android.util.Log;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.firstcomponents.R;
import com.example.my_fragment.ComponentDefinitions;
import com.example.my_fragment.ComponentSimpleModel;
import com.example.my_fragment.Dependency;
import com.example.my_fragment.GUIComponent;
import com.example.my_fragment.MyActivity;
import com.example.my_fragment.GenericComponent;

public class NewListActivity extends MyActivity {

	private PhotoGUI photo;
	private CommentListGUI comentario;
	private CommentSendGUI sendCom;
	private boolean gambiarraFlag = false;

	private int[] thisDependencies = new int[] { 1, 3 };
	// aqui existe dependencia de comentario, e foto

	private long commentTarget;
	Long photoId;
	int relativeGUIIdCont = 55;
	private List<ComponentSimpleModel> lista;
	private List<String> listDependents;

	private List<Dependency> dep;

	LinearLayout myview;

	protected void photoNotFound() {
		gambiarraFlag = true;
		Toast.makeText(this,
				"Foto não encontrada. Verifique se já existe alguma foto.",
				Toast.LENGTH_SHORT).show();
		finish();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (gambiarraFlag)
			return;

		setContentView(R.layout.activity_fragment_runtime);

		// set targets
		setDependencies(thisDependencies);
		setMyList();
		addSomething();
	}

	public void configurarTargets() {
		if (gambiarraFlag)
			return;
		// rating.setComponentTarget(photo);
		commentTarget = photo.getCurrentInstanceId();
		sendCom.setComponentTarget(photo);

		Dependency d;
		dep = new ArrayList<Dependency>();
		d = new Dependency("OneComment", "Photo", true);
		dep.add(d);
		d = new Dependency("Rating", "OneComment", false);
		dep.add(d);
		d = new Dependency("CommentSend", "Photo", false);
		dep.add(d);

	}

	public void instanciarComponents() {
		photoId = getIntent().getLongExtra("nImagem", -1L);
		if (photoId != -1L)
			photo = new PhotoGUI(photoId);
		else {
			photoNotFound();
			return;
		}

		photoId = photo.getImageId();
		if (photoId == -1L) {
			photoNotFound();
			return;
		}

		sendCom = new CommentSendGUI();
	}

	public void setMyList() {
		comentario = new CommentListGUI(commentTarget);
		// lista = comentario.getList(commentTarget, this);
		// o tipo diferente de lista
		lista = comentario.getListSimple(commentTarget, this);
		listDependents = new ArrayList<String>();
		// listDependents.add("Rating");
		// listDependents.add("Rating");
		// listDependents.add("CommentSend");

	}

	public void addOther(String s, ComponentSimpleModel c) {

		// addOne(s,c);
		Log.i("criar comentario", " com id " + c.getId());
		ComponentDefinitions cd = new ComponentDefinitions();
		GUIComponent one = cd.getComponent(c, s);
		addGUIComponentWithTag(R.id.menu_lin, one);

	}

	public void addOther(String s, Long target) {

		// addOne(s,c);
		//Log.i("criar comentario", " com id " + c.getId());
		ComponentDefinitions cd = new ComponentDefinitions();
		GUIComponent one = cd.getComponent(target, s);
		addGUIComponentWithTag(R.id.menu_lin, one);

	}

	public void verDependenciaString(String s, Long target) {
		for (Dependency d : dep) {
			if (d.getTarget().equals(s)) {
				Log.i("achou dependencia", "  com-> target " + s + " source "
						+ d.getSource());
				if (d.isToMany()) {
					lista = comentario.getListSimple(target, this);
					for (ComponentSimpleModel model : lista) {
						addOther(d.getSource(), model);
						verDependenciaString(d.getSource(),model.getId());
					}
				} else {
					Log.i("botar na tela", " do tipo " + d.getSource());// cria
					addOther(d.getSource(),target);
					verDependenciaString(d.getSource(),target);
				}

			}

		}
	}

	public void addSomething() {

		startTransaction();
		addGUIComponent(R.id.menu_lin, photo);
		verDependenciaString("Photo",commentTarget);

		// addDependentList(lista, "OneComment", 3,
		// listDependents,R.id.menu_lin, this);
		// addListForOne(lista,"OneComment", 3, R.id.menu_lin, this);

		// addGUIComponent(R.id.menu_lin, sendCom);
		finishTransaction();

	}

	@Override
	public void deletarAlgo(Long target, GenericComponent component) {
		callbackRemove(target, component);
	}

}