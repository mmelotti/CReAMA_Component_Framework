package com.gw.android.first_components.my_activities;

import java.util.ArrayList;

import android.os.Bundle;

import android.util.Log;
import android.widget.Toast;

import com.gw.android.R;
import com.gw.android.first_components.my_components.Constants;
import com.gw.android.first_components.my_components.photo.PhotoViewGUI;
import com.gw.android.first_components.my_fragment.CRActivity;
import com.gw.android.first_components.my_fragment.CRComponent;
import com.gw.android.first_components.my_fragment.ComponentNaming;
import com.gw.android.first_components.my_fragment.Dependency;

public class CoordCommentActivity extends CRActivity {

	private PhotoViewGUI photo;
	private boolean gambiarraFlag = false;
	private ComponentNaming commentView, commentSend, photoView, coordView,
			coordSend;
	private long idPhoto;
	private Long photoId;

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

		setDependencies(new ArrayList<Dependency>());

		commentView = new ComponentNaming(Constants.CommentViewGUIName,
				Constants.CommentViewGUIName + "1");
		commentSend = new ComponentNaming(Constants.CommentSendGUIName,
				Constants.CommentSendGUIName + "1");
		photoView = new ComponentNaming(Constants.PhotoViewGUIName,
				Constants.PhotoViewGUIName + "1");

		coordView = new ComponentNaming(Constants.GPSViewGUIName, "GPS");
		coordSend = new ComponentNaming(Constants.GPSListenerName, "GPSsend");

		Log.i("adding", "binomio gui");

		addDependency(new Dependency(commentView, photoView, true));
		addDependency(new Dependency(coordView, commentView, false));
		addDependency(new Dependency(commentSend, photoView, false));
		addDependency(new Dependency(coordSend, commentSend, false));

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
	public void deletarAlgo(Long target, CRComponent component) {
		callbackRemove(target, component.getNick());
	}

	@Override
	public void inserirAlgo(Long target, CRComponent component) {
		callbackAdd(target, component.getNick());
	}

}