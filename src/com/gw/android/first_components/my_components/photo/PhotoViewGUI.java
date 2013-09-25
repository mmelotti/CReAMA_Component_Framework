package com.gw.android.first_components.my_components.photo;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.gw.android.R;
import com.gw.android.first_components.my_components.photo.PhotoDao.Properties;
import com.gw.android.first_components.my_fragment.CRComponent;

@SuppressLint("ValidFragment")
public class PhotoViewGUI extends CRComponent {

	private ImageView image;
	private Button proxima, anterior;
	private Photo photo;
	private PhotoDao photoDao;
	private boolean showNavigation = false;

	public PhotoViewGUI(Long imageId) {
		setCurrent(imageId);
		preDefined();
	}

	public Long getImageId() {
		preDefined();
		return getCurrentInstanceId();
	}

	public void preDefined() {
		setGeneralGUIId(3);
	}

	public void zoomPhoto() {
		Intent i = new Intent(getActivity(), ImageZoomActivity.class);
		i.putExtra("image", photo.getPhotoBytes());
		startActivity(i);
	}

	public static Long searchFirstPhoto(PhotoDao dao, Context ctx) {
		PhotoDao mDao = (dao == null ? PhotoUtils.initPhotoDao(ctx) : dao);
		List<Photo> l = mDao.queryBuilder().orderAsc(Properties.Id).build()
				.list();

		// aqui ja pode fechar o BD
		mDao.getDatabase().close();

		if (l.isEmpty()) {
			return -1L;
		} else {
			Photo photo = l.get(0);
			return photo.getId();
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		photoDao = PhotoUtils.initPhotoDao(getActivity());
		View view = inflater.inflate(R.layout.imageone, container, false);
		anterior = (Button) view.findViewById(R.id.imagem_anterior);
		proxima = (Button) view.findViewById(R.id.imagem_proxima);
		image = (ImageView) view.findViewById(R.id.imageView1);
		
		/* 	
		http://arquigrafia.org.br/photo/img-crop/2230?_log=no
		http://arquigrafia.org.br/photo/img-show/2230.jpeg
		 */
		
		if (!showNavigation)
			hidePreviousNext();
		
		image.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//zoomPhoto();
			}
		});

		photo = (Photo) photoDao.queryBuilder()
				.where(Properties.Id.eq(getCurrentInstanceId())).build()
				.unique();

		closeDao();
		if (photo != null) {
			byte[] data = photo.getPhotoBytes();
			Bitmap bm = BitmapFactory.decodeByteArray(data, 0, data.length);
			Log.e("width e height", bm.getWidth() + " - " + bm.getHeight());
			final float scale = getActivity().getResources().getDisplayMetrics().density;
			Bitmap scaledBm = Bitmap.createScaledBitmap(bm, (int) (400 * scale + 0.5f), (int) (300 * scale + 0.5f), true);	// diminui a imagem 
			bm.recycle();
			image.setImageBitmap(scaledBm); 
		} else {
			Toast.makeText(getActivity(), "Ainda não há fotos para exibir!", 
					Toast.LENGTH_SHORT).show();
			getActivity().finish();
		}

		proxima.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) { 
				Intent trocatela = new Intent(getActivity(), getActivity()
						.getClass());
				trocatela.putExtra("nImagem", proximaImagem());
				getActivity().startActivity(trocatela);
				getActivity().finish();
			}
		});

		anterior.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent trocatela = new Intent(getActivity(), getActivity()
						.getClass());
				trocatela.putExtra("nImagem", imagemAnterior());
				getActivity().startActivity(trocatela);
				getActivity().finish();
			}
		});

		return view; 
	}
	
	public void showNavigation(boolean b) {
		showNavigation = b;
	}
	
	private void hidePreviousNext() {
		anterior.setVisibility(View.GONE);
		proxima.setVisibility(View.GONE);
	}

	private Long proximaImagem() {
		photoDao = PhotoUtils.initPhotoDao(getActivity());
		List<Photo> l = photoDao.queryBuilder()
				.where(Properties.Id.gt(getCurrentInstanceId()))
				.orderAsc(Properties.Id).list();
		closeDao();
		return (l.isEmpty() ? getCurrentInstanceId() : ((Photo) l.get(0))
				.getId());
	}

	private Long imagemAnterior() {
		photoDao = PhotoUtils.initPhotoDao(getActivity());
		List<Photo> l = photoDao.queryBuilder()
				.where(Properties.Id.lt(getCurrentInstanceId()))
				.orderDesc(Properties.Id).list();
		closeDao();
		return (l.isEmpty() ? getCurrentInstanceId() : ((Photo) l.get(0))
				.getId());
	}

	public void closeDao() {
		photoDao.getDatabase().close();
	}

}
