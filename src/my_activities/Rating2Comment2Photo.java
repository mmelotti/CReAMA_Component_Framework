package my_activities;

import java.util.ArrayList;
import my_components.Constants;
import my_components.photo.PhotoViewGUI;
import my_components.rating2comment.RatingToCommentGUI;

import android.os.Bundle;

import android.widget.Toast;

import com.example.firstcomponents.R;
import com.example.my_fragment.ComponentNaming;
import com.example.my_fragment.Dependency;
import com.example.my_fragment.CRComponent;
import com.example.my_fragment.CRActivity;

public class Rating2Comment2Photo extends CRActivity {

	private PhotoViewGUI photo;
	private boolean gambiarraFlag = false;
	
	private ComponentNaming commentView, commentSend, photoView, rating,r2cView;
	private CRComponent rating2comment;
	
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
		r2cView = new ComponentNaming(Constants.RatingToCommentGUIName,Constants.RatingToCommentGUIName+"1");
		
		d = new Dependency(commentView,photoView, true);
		addDependency(d);
		d = new Dependency(rating,commentView, false);
		addDependency(d);
		d = new Dependency(commentSend,photoView, false);
		addDependency(d);
		
		rating2comment.setNick(r2cView.getNickName());
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
		
		rating2comment =new RatingToCommentGUI(photoId);
		
	}

	public void setMyList() {

	}

	public void addSomething() {
		startTransaction();
		addGUIComponent(R.id.menu_lin, photo);
		verDependenciaString(photoView, idPhoto);
		addGUIComponent(R.id.menu_lin, rating2comment);
		finishTransaction();
	}

	@Override
	public void deletarAlgo(Long target, CRComponent component) {
		callbackRemove(target, component.getNick());
	}

}