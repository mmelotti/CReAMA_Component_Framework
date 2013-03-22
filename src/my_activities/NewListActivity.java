package my_activities;

import java.util.ArrayList;
import java.util.List;

import my_components.ComentarioGUI;
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
import com.example.my_fragment.MyActivity;
import com.example.my_fragment.MyComponent;

public class NewListActivity extends MyActivity {

	private PhotoGUI photo;
	private ComentarioGUI comentario;
	private RatingGUI rating;
	private CommentSendGUI sendCom;
	private boolean gambiarraFlag = false;

	private List<Integer> fragmentId = new ArrayList<Integer>();
	private List<Long> deleteId = new ArrayList<Long>();

	private List<MyComponent> componentes = new ArrayList<MyComponent>();

	// comment =1, rating =2
	private int source = 2;
	private int target = 1;

	private int[] dependencies = new int[] { 1, 3 };
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

		FragmentManager fm = getSupportFragmentManager();
		Fragment fragment = fm.findFragmentById(R.id.fragment_content);

		if (fragment == null) {
			FragmentTransaction ft = fm.beginTransaction();
			ft.add(R.id.fragment_content, photo);
			ft.commit();
		}

		// set targets
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
		// TODO Auto-generated method stub
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
		comentario = new ComentarioGUI(commentTarget);
		lista = comentario.getList(commentTarget, this); 
	}

	public void addSomething() {
		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();

		for (int i = 0; i < lista.size(); i++) {
			OneCommentGUI com = new OneCommentGUI(lista.get(i));
			com.setGeneralGUIId(1);
			com.setComponentTargetId(3);
			com.setRelativeFragmentId(idroot);
			com.setMya(this);
			ft.add(R.id.menu_lin, com, "" + idroot);
			componentes.add(com);

			// add para ser deletado depois
			// deleteId.add(lista.get(i).getId());
			fragmentId.add(idroot++);

			// para cada comentario um avaliador!
			RatingGUI rat = new RatingGUI(lista.get(i).getId());
			rat.setGeneralGUIId(2);
			rat.setComponentTargetId(1);
			rat.setRelativeFragmentId(idroot);
			rat.setMya(this);

			ft.add(R.id.menu_lin, rat, "" + idroot);
			componentes.add(rat);

			fragmentId.add(idroot++);
			// multiplos ratings
		}

		ft.add(R.id.menu_lin, sendCom);
		ft.commit();
	}

	@Override
	public void deletarAlgo(Long target, MyComponent component) {
		callbackRemove(target, component);
	}

	public void callbackRemove(Long target, MyComponent component) {
		boolean foundIt = false;

		for (int i = 0; i < dependencies.length; i++) {
			// tem alguem que depende do que vai ser deletado?
			if (dependencies[i] == component.getGeneralGUIId()) {
				Log.i("Remover!", "encontrou " + dependencies[i]);
				foundIt = true;
				break;
			}
		}

		if (foundIt) {
			for (int i = 0; i < componentes.size(); i++) {
				if (componentes.get(i).getComponentTargetId() == component
						.getGeneralGUIId()) {
					componentes.get(i).deleteAllFrom(target);
					Log.i("Remover!", "from " + target);
					break;
				}
			}
		}
	}

}