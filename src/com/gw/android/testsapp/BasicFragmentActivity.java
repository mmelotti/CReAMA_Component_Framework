package com.gw.android.testsapp;

import com.gw.android.R;
import com.gw.android.first_components.my_components.comment.CommentListGUI;
import com.gw.android.first_components.my_components.photo.PhotoViewGUI;
import com.gw.android.first_components.my_components.rating.RatingViewGUI;
import com.gw.android.first_components.my_fragment.CRActivity;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.Toast;

public class BasicFragmentActivity extends CRActivity {

	private PhotoViewGUI photo;
	private CommentListGUI comentario;
	private RatingViewGUI rating;
	Long photoId;

	protected void photoNotFound() {
		Toast.makeText(this,
				"Foto não encontrada. Verifique se já existe alguma foto.",
				Toast.LENGTH_SHORT).show();
		finish();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fragment_runtime);

		FragmentManager fm = getSupportFragmentManager();
		Fragment fragment = fm.findFragmentById(R.id.fragment_content);

		if (fragment == null) {
			FragmentTransaction ft = fm.beginTransaction();
			ft.add(R.id.fragment_content, photo);
			ft.add(R.id.fragment_content3, comentario);
			ft.add(R.id.fragment_content4, rating);
			ft.commit();
		}

	}

	public void configurarTargets() {
		rating.setComponentTarget(photo);
		comentario.setComponentTarget(photo);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();

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

		comentario = new CommentListGUI();
		rating = new RatingViewGUI(photoId);
	}

}
