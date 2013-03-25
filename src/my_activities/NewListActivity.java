package my_activities;

import java.util.ArrayList;
import java.util.List;

import my_components.CommentListGUI;
import my_components.Comment;
import my_components.CommentSendGUI;
import my_components.OneCommentGUI;
import my_components.PhotoGUI;
import my_components.RatingGUI;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.firstcomponents.R;
import com.example.my_fragment.GUIComponent;
import com.example.my_fragment.MyActivity;
import com.example.my_fragment.MyComponent;

public class NewListActivity extends MyActivity {

	private PhotoGUI photo;
	private CommentListGUI comentario;
	private CommentSendGUI sendCom;
	private boolean gambiarraFlag = false;
	
	private List<Integer> fragmentId = new ArrayList<Integer>();
	private List<Long> deleteId = new ArrayList<Long>();

	// comment =1, rating =2, foto=3
	private int source = 2;
	private int target = 1;

	private int[] thisDependencies = new int[] { 1, 3 };
	// aqui existe dependencia de comentario, e foto

	private long commentTarget;
	Long photoId;
	int idroot = 55;
	List<Comment> lista;

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

		myview = (LinearLayout) findViewById(R.id.menu_lin);

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

	@Override
	public void onDestroy() {
		super.onDestroy();

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
		lista = comentario.getList(commentTarget, this);
	}

	
	public void addSomething() {
		
		startTransaction();
		addGUIComponent(R.id.menu_lin, photo);
		

		for (int i = 0; i < lista.size(); i++) {
			OneCommentGUI com = new OneCommentGUI(lista.get(i));
			com.setGeneralGUIId(1);
			com.setComponentTargetId(3);
			com.setRelativeFragmentId(idroot);
			com.setControlActivity(this);
			addGUIComponent(R.id.menu_lin, com, "" + idroot);
			

			// add para ser deletado depois
			// deleteId.add(lista.get(i).getId());
			fragmentId.add(idroot++);

			// para cada comentario um avaliador!
			RatingGUI rat = new RatingGUI(lista.get(i).getId());
			rat.setGeneralGUIId(2);
			rat.setComponentTargetId(1);
			rat.setRelativeFragmentId(idroot);
			rat.setControlActivity(this);
			addGUIComponent(R.id.menu_lin, rat, "" + idroot);
			

			fragmentId.add(idroot++);
			// multiplos ratings
		}

		addGUIComponent(R.id.menu_lin, sendCom);
		finishTransaction();
	}

	@Override
	public void deletarAlgo(Long target, MyComponent component) {
		callbackRemove(target, component);
	}

	

}