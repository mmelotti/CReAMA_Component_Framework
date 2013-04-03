package my_activities;

import java.util.ArrayList;
import my_components.Constants;
import my_components.photo.PhotoViewGUI;
import my_components.rating2comment.RatingToComment;
import my_components.rating2comment.RatingToCommentGUI;

import android.os.Bundle;

import android.widget.Toast;

import com.example.firstcomponents.R;
import com.example.my_fragment.Dependency;
import com.example.my_fragment.MyActivity;
import com.example.my_fragment.GenericComponent;

public class Rating2Comment2Photo extends MyActivity {

	private PhotoViewGUI photo;
	private boolean gambiarraFlag = false;
	private int[] thisDependencies = new int[] { 1, 3 };
	// aqui existe dependencia de comentario, e foto

	private long idPhoto;
	Long photoId;

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
		idPhoto = photo.getCurrentInstanceId();

		Dependency d;
		setDependencies(new ArrayList<Dependency>());
		d = new Dependency(Constants.CommentViewGUIName,
				Constants.PhotoViewGUIName, true);
		addDependencie(d);
		d = new Dependency(Constants.RatingViewGUIName,
				Constants.CommentViewGUIName, false);
		addDependencie(d);
		

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

	}

	public void setMyList() {

	}

	public void addSomething() {
		startTransaction();
		addGUIComponent(R.id.menu_lin, photo);
		verDependenciaString(Constants.PhotoViewGUIName, idPhoto);
		addGUIComponent(R.id.menu_lin, new RatingToCommentGUI(idPhoto));
		finishTransaction();
	}

	@Override
	public void deletarAlgo(Long target, GenericComponent component) {
		callbackRemove(target, component);
	}

}