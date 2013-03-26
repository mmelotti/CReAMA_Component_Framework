package my_activities;


import java.util.ArrayList;
import java.util.List;

import my_components.CommentListGUI;
import my_components.CommentSendGUI;
import my_components.PhotoGUI;

import android.os.Bundle;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.firstcomponents.R;
import com.example.my_fragment.ComponentSimpleModel;
import com.example.my_fragment.MyActivity;
import com.example.my_fragment.MyComponent;

public class NewListComposed extends MyActivity {

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
		commentTarget = photo.getCurrent();
		sendCom.setComponentTarget(photo);
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
		listDependents.add("Rating");
		listDependents.add("Rating");
		listDependents.add("CommentSend");
		
	}

	public void addSomething() {

		startTransaction();
		addGUIComponent(R.id.menu_lin, photo);

		addDependentList(lista, "OneComment", 3, listDependents,R.id.menu_lin, this);
		addListForOne(lista,"OneComment", 3, R.id.menu_lin, this);

		addGUIComponent(R.id.menu_lin, sendCom);
		finishTransaction();
		
		
		//parte de teste
		TestComponentsGroup t = new TestComponentsGroup();
		FragmentManager fragmentManager = getSupportFragmentManager();
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		t.addFromOutside(transaction,this);
		
	}

	@Override
	public void deletarAlgo(Long target, MyComponent component) {
		callbackRemove(target, component);
	}


	

}