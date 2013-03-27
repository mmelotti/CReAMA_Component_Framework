package my_activities;


import java.util.ArrayList;
import java.util.List;

import my_components.CommentListGUI;
import my_components.CommentSendGUI;
import my_components.PhotoGUI;

import android.os.Bundle;

import android.util.Log;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.firstcomponents.R;
import com.example.my_fragment.ComponentDefinitions;
import com.example.my_fragment.ComponentSimpleModel;
import com.example.my_fragment.Dependencie;
import com.example.my_fragment.GUIComponent;
import com.example.my_fragment.MyActivity;
import com.example.my_fragment.MyComponent;

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

	private List<Dependencie> dep;
	
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
		
		Dependencie d;
		dep=new ArrayList<Dependencie>();
		d = new Dependencie("OneComment","Photo",true);
		dep.add(d);
		d = new Dependencie("Rating","OneComment",false);
		dep.add(d);
		d = new Dependencie("CommentSend","Photo",false);
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
		//listDependents.add("Rating");
		//listDependents.add("Rating");
		//listDependents.add("CommentSend");
		
	}
	
	public void addOne(String s,ComponentSimpleModel c){
		//poe vieew
		ComponentDefinitions cd=new ComponentDefinitions();
		GUIComponent g= cd.getOne(c);
	}
	
	public void addOther(String s,ComponentSimpleModel c){
		
				//addOne(s,c);
		Log.i("criar comentario"," com id "+c.getId());
	}

	
	
	
	public void verDependenciaString(String s){
		for(Dependencie d:dep){
			if(d.getTarget().equals(s)){
				Log.i("achou dependencia","  com-> target "+s+" source "+d.getSource());
				if(d.isToMany()){
					lista = comentario.getListSimple(commentTarget, this);
					for(ComponentSimpleModel model:lista){
						addOther(d.getSource(),model);
						verDependenciaString(d.getSource());
					}
				}
				else{
					Log.i("botar na tela"," do tipo "+d.getSource());//cria
					verDependenciaString(d.getSource());
				}
				
				
			}
			
		}
	}
	
	
	
	
	public void addSomething() {
		
		
		
		verDependenciaString("Photo");
		
		startTransaction();
		addGUIComponent(R.id.menu_lin, photo);

		//addDependentList(lista, "OneComment", 3, listDependents,R.id.menu_lin, this);
		addListForOne(lista,"OneComment", 3, R.id.menu_lin, this);

		addGUIComponent(R.id.menu_lin, sendCom);
		finishTransaction();
		
		
	}

	@Override
	public void deletarAlgo(Long target, MyComponent component) {
		callbackRemove(target, component);
	}


	

}