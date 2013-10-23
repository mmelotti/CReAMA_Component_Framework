package com.gw.android.first_components.my_components.photo;

import java.util.List;

import uk.co.senab.photoview.PhotoViewAttacher;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.gw.android.R;
import com.gw.android.components.connection_manager.AsyncRequestHandler;
import com.gw.android.components.request.Request;
import com.gw.android.first_components.my_components.photo.PhotoDao.Properties;
import com.gw.android.first_components.my_fragment.CRComponent;
import com.loopj.android.image.SmartImageView;

@SuppressLint("ValidFragment")
public class PhotoViewGUI extends CRComponent {

	private TextView photoName;
	private Button proxima, anterior;
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

	public static Long searchFirstPhoto(PhotoDao dao, Context ctx) {
		PhotoDao mDao = (dao == null ? PhotoUtils.initPhotoDao(ctx) : dao);
		List<Photo> l = mDao.queryBuilder().orderAsc(Properties.Id).build()
				.list();
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
		View view = inflater.inflate(R.layout.imageone, container, false);
		final SmartImageView image = (SmartImageView) view.findViewById(R.id.imageView1);

		anterior = (Button) view.findViewById(R.id.imagem_anterior);
		proxima = (Button) view.findViewById(R.id.imagem_proxima);
		photoName = (TextView) view.findViewById(R.id.imageText);
		
		if (!showNavigation)
			hidePreviousNext();
		
		photoName.setText(PhotoUtils.getPhotoById(getCurrentInstanceId(), getActivity()).getText());
		image.setImage(new GWImage(getCurrentInstanceId()));		
		PhotoViewAttacher mAttacher = new PhotoViewAttacher(image);
		mAttacher.setAllowParentInterceptOnEdge(false); 

		if(isThumb()) { // Se a imagem salva no DAO ainda é a de tamanho pequeno (thumbnail) tenta baixar a foto em tamanho original
			AsyncRequestHandler mFileHandler = new AsyncRequestHandler() {
				@Override
				public void onSuccess(byte[] b, Request request) {
					if (b == null)
						return;
					PhotoDao photoDao = PhotoUtils.initPhotoDao(getActivity().getApplicationContext());
					Photo photo = PhotoUtils.getPhotoById(getCurrentInstanceId(), photoDao);
					photo.setPhotoBytes(b);
					photo.setIsThumb(false);
					photoDao.update(photo);
					photoDao.getDatabase().close();
					
					// atualiza a imagem
					image.setImage(new GWImage(getCurrentInstanceId()));		
				}
			};	
			
			setComponentFileRequestCallback(mFileHandler);
			String url = getBaseUrl()
					+ "/photo"
					+ PhotoUtils.imageType[PhotoUtils.BIG]
					+ PhotoUtils.getServerIdById(getCurrentInstanceId(),
							getActivity().getApplicationContext())
					+ PhotoUtils.urlEndImage[PhotoUtils.BIG];
			Request request = new Request(null, url, "get", null);
			makeFileRequest(request);
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

	private boolean isThumb() {
		return PhotoUtils.getPhotoById(getCurrentInstanceId(), getActivity().getApplicationContext()).getIsThumb();
	}

	public void showNavigation(boolean b) {
		showNavigation = b;
	}

	private void hidePreviousNext() {
		anterior.setVisibility(View.GONE);
		proxima.setVisibility(View.GONE);
	}

	private Long proximaImagem() {
		PhotoDao photoDao = PhotoUtils.initPhotoDao(getActivity());
		List<Photo> l = photoDao.queryBuilder()
				.where(Properties.Id.gt(getCurrentInstanceId()))
				.orderAsc(Properties.Id).list();
		photoDao.getDatabase().close();
		return (l.isEmpty() ? getCurrentInstanceId() : ((Photo) l.get(0))
				.getId());
	}

	private Long imagemAnterior() {
		PhotoDao photoDao = PhotoUtils.initPhotoDao(getActivity());
		List<Photo> l = photoDao.queryBuilder()
				.where(Properties.Id.lt(getCurrentInstanceId()))
				.orderDesc(Properties.Id).list();
		photoDao.getDatabase().close();
		return (l.isEmpty() ? getCurrentInstanceId() : ((Photo) l.get(0))
				.getId());
	}

}
