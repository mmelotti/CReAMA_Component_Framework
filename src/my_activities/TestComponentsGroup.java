package my_activities;

import java.util.ArrayList;
import java.util.List;

import my_components.comment.CommentListGUI;
import my_components.comment.CommentSendGUI;
import my_components.photo.PhotoViewGUI;

import android.support.v4.app.FragmentTransaction;
import android.app.Activity;
import android.os.Bundle;

import android.util.Log;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.firstcomponents.R;
import com.example.my_fragment.ComponentDefinitions;
import com.example.my_fragment.ComponentSimpleModel;
import com.example.my_fragment.GUIComponent;
import com.example.my_fragment.MyActivity;
import com.example.my_fragment.MyActivityComposed;
import com.example.my_fragment.GenericComponent;
import com.example.my_fragment.MyGrouping;

public class TestComponentsGroup extends MyActivityComposed {

	private PhotoViewGUI photo;
	private CommentListGUI comentario;
	private CommentSendGUI sendCom;
	private boolean gambiarraFlag = false;
	private FragmentTransaction transacti;

	private int[] thisDependencies = new int[] { 1, 3 };
	// aqui existe dependencia de comentario, e foto

	private long commentTarget;
	Long photoId;
	int relativeGUIIdCont = 55;
	private List<ComponentSimpleModel> lista;
	private List<String> listDependents;
	private List<MyGrouping> agrupamento;

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
		setDependenciesInt(thisDependencies);
		setMyList();
		addSomething();
	}

	public void configurarTargets() {
		if (gambiarraFlag)
			return;
		// rating.setComponentTarget(photo);
		commentTarget = photo.getCurrentInstanceId();
		sendCom.setComponentTarget(photo);
	}
	
	public void instanciarComponents() {
		photoId = getIntent().getLongExtra("nImagem", -1L);
		if (photoId != -1L)
			photo = new PhotoViewGUI(photoId);
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
		listDependents.add("Rating");
		//listDependents.add("Rating");
		//listDependents.add("CommentSend");
		
	}
	
	public void setMyListFromOutside(Activity a,Long commentTarget) {
		comentario = new CommentListGUI(commentTarget);
		// lista = comentario.getList(commentTarget, this);
		// o tipo diferente de lista
		lista = comentario.getListSimple(commentTarget, a);
		listDependents = new ArrayList<String>();
		listDependents.add("Rating");
		//listDependents.add("Rating");
		//listDependents.add("CommentSend");
	}

	public void addSomething() {
		startTransaction();
		addGUIComponent(R.id.menu_lin, photo);

		addDependentList(lista, "OneComment", 3, listDependents,R.id.menu_lin, this);
		//addListForOne(lista,"OneComment", 3, R.id.menu_lin, this);

		addGUIComponent(R.id.menu_lin, sendCom);
		finishTransaction();

		Log.i("OUTRO," ,"ADDSOMETHING");
	}

	public void addFromOutside(FragmentTransaction transaction,Activity a){
		setDependenciesInt(thisDependencies);
		
		photoId = 1L;
		if (photoId != -1L)
			photo = new PhotoViewGUI(photoId);
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
		sendCom.setComponentTarget(photo);
		
		transacti=transaction;
		addGUIComponentWithTagOutside(R.id.menu_lin, photo);
		
		setMyListFromOutside(a,photoId);
		addDependentListOutside(lista, "OneComment", 3, listDependents,R.id.menu_lin, this);
		
		addGUIComponentWithTagOutside(R.id.menu_lin, sendCom);
		
		transacti.commit();
		
		Log.i("OUTRO," ,"ADDoutside");
	}
	
	
	@Override
	public void deletarAlgo(Long target, GenericComponent component) {
		callbackRemove(target, component);
	}

	private void setMyGrouping(){
		agrupamento = new ArrayList<MyGrouping>();
		MyGrouping mg = new MyGrouping(1L, 2L, 1L);//rating to coment
		agrupamento.add(mg);
		mg = new MyGrouping(2L, 1L, 3L);//comment to photo
		agrupamento.add(mg);	
	}
	
	public void addDependentListOutside(List<ComponentSimpleModel> lista, String s1,
			int target, List<String> list2, int fragmentId, MyActivity control) {

		ComponentDefinitions def = new ComponentDefinitions();

		for (int i = 0; i < lista.size(); i++) {

			GUIComponent com = def.getComponent(lista.get(i), s1);
			com.setComponentTargetId(target);
			com.setControlActivity(control);
			addGUIComponentWithTagOutside(fragmentId, com);

			// para cada component, outros mais!
			for (int j = 0; j < list2.size(); j++) {

				GUIComponent rat = def.getComponent(lista.get(i).getId(),
						list2.get(j));
				rat.setComponentTargetId(1);
				rat.setControlActivity(control);
				addGUIComponentWithTagOutside(fragmentId, rat);
			}

		}
	}
	
	public void addGUIComponentWithTagOutside(int id, GUIComponent c) {
		c.setRelativeFragmentId(relativeGUIIdCont);
		addComponent(c);
		transacti.add(id, c, "" + relativeGUIIdCont);
		upRelativeId();
	}
	

}