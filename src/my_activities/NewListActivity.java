package my_activities;

import java.util.ArrayList;
import my_components.Constants;
import my_components.photo.PhotoViewGUI;

import android.os.Bundle;

import android.widget.Toast;

import com.example.firstcomponents.R;
import com.example.my_fragment.ComponentNaming;
import com.example.my_fragment.Dependency;
import com.example.my_fragment.GUIComponent;
import com.example.my_fragment.MyActivity;

public class NewListActivity extends MyActivity {

	private PhotoViewGUI photo;
	private boolean gambiarraFlag = false;

	private ComponentNaming commentView, commentSend, photoView, rating;
	
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
		
		commentView = new ComponentNaming(Constants.CommentViewGUIName, Constants.CommentViewGUIName+"1");
		commentSend = new ComponentNaming(Constants.CommentSendGUIName, Constants.CommentSendGUIName+"1");
		photoView = new ComponentNaming(Constants.PhotoViewGUIName, Constants.PhotoViewGUIName+"1");
		rating = new ComponentNaming(Constants.RatingViewGUIName,Constants.RatingViewGUIName+"1");
		
		d = new Dependency(commentView,photoView, true);
		addDependencie(d);
		d = new Dependency(rating,commentView, false);
		addDependencie(d);
		d = new Dependency(commentSend,photoView, false);
		addDependencie(d);
		
		photo.setNick(photoView.getNickName());
		
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
		verDependenciaString(photoView, idPhoto);
		finishTransaction();
	}

	@Override
	public void deletarAlgo(Long target, GUIComponent component) {
		callbackRemove(target, component.getNick());
	}

}