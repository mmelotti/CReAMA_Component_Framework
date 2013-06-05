package com.gw.android.my_activities;

import java.util.ArrayList;

import android.os.Bundle;

import android.widget.Toast;

import com.gw.android.R;
import com.gw.android.my_components.Constants;
import com.gw.android.my_components.photo.PhotoViewGUI;
import com.gw.android.my_components.rating2comment.RatingToCommentGUI;
import com.gw.android.my_fragment.CRActivity;
import com.gw.android.my_fragment.CRComponent;
import com.gw.android.my_fragment.ComponentNaming;
import com.gw.android.my_fragment.Dependency;

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