package com.gw.android.testsapp;

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

public class TagActivity extends CRActivity {

	private PhotoViewGUI photo;
	private boolean gambiarraFlag = false;

	private ComponentNaming tagView, tagSend, photoView, binAverage;

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

		tagView = new ComponentNaming(Constants.TagViewGUIName,
				Constants.TagViewGUIName + "1");
		tagSend = new ComponentNaming(Constants.TagSendGUIName,
				Constants.TagSendGUIName + "1");
		photoView = new ComponentNaming(Constants.PhotoViewGUIName,
				Constants.PhotoViewGUIName + "1");
		binAverage = new ComponentNaming(Constants.BinomioAverageGUIName,
				"Binomio2");

		Log.i("adding", "binomio gui");

		d = new Dependency(tagView, photoView, true);
		addDependency(d);
		d = new Dependency(tagSend, photoView, false);
		addDependency(d);
		d = new Dependency(binAverage, photoView, false);
		addDependency(d);

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

}