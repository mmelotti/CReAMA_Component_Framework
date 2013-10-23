package com.gw.android.first_components.my_components.photo;

import java.util.List;

import uk.co.senab.photoview.PhotoViewAttacher;
import uk.co.senab.photoview.PhotoViewAttacher.OnMatrixChangedListener;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.RectF;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ScaleGestureDetector.OnScaleGestureListener;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.github.johnpersano.supertoasts.SuperToast;
import com.gw.android.R;
import com.gw.android.Utils.SuperToastUtils;
import com.gw.android.components.connection_manager.AsyncRequestHandler;
import com.gw.android.components.request.Request;
import com.gw.android.first_components.my_components.photo.PhotoDao.Properties;
import com.gw.android.first_components.my_fragment.CRComponent;
import com.loopj.android.image.SmartImageView;
import com.readystatesoftware.viewbadger.BadgeView;

@SuppressLint("ValidFragment")
public class PhotoViewGUI extends CRComponent {

	private TextView photoName;
	View rootLayout;
	SmartImageView imageFront, imageBack;
	
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
		imageFront = (SmartImageView) view.findViewById(R.id.imageViewFront);
		imageBack = (SmartImageView) view.findViewById(R.id.imageViewBack);
		
	    rootLayout = view.findViewById(R.id.relLayout);
		
		anterior = (Button) view.findViewById(R.id.imagem_anterior);
		proxima = (Button) view.findViewById(R.id.imagem_proxima);
		photoName = (TextView) view.findViewById(R.id.imageText);
		
		BadgeView badge = new BadgeView(getActivity(), imageFront);
		badge.setText("info");
		badge.setBadgePosition(BadgeView.POSITION_BOTTOM_RIGHT);
		badge.setBadgeBackgroundColor(Color.GRAY);
		badge.show();
		
		if (!showNavigation)
			hidePreviousNext(); 
		
		photoName.setText(PhotoUtils.getPhotoById(getCurrentInstanceId(), getActivity()).getText());
		imageFront.setImage(new GWImage(getCurrentInstanceId()));
		imageBack.setImage(new GWImage(getCurrentInstanceId()));
		
		badge.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				onCardClick();
			}
		});
		
		final PhotoViewAttacher mAttacher = new PhotoViewAttacher(imageFront);
		
		mAttacher.setAllowParentInterceptOnEdge(false);  
		mAttacher.setOnMatrixChangeListener(new OnMatrixChangedListener() {
			@Override
			public void onMatrixChanged(RectF arg0) {
				if (mAttacher.getScale() == 1.0f)
					mAttacher.setAllowParentInterceptOnEdge(true); // Devolve controle ao Scrollview 
				else
					mAttacher.setAllowParentInterceptOnEdge(false); // ImageView intercepta os toques para realizar zoom e pan
			}
		});
		
		proxima.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				onCardClick();
				/*Intent trocatela = new Intent(getActivity(), getActivity()
						.getClass());
				trocatela.putExtra("nImagem", proximaImagem());
				getActivity().startActivity(trocatela);
				getActivity().finish();*/
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
	
	@Override
	protected void onBind() {

		if (isThumb()) { // Se a imagem salva no DAO ainda é a de tamanho
							// pequeno (thumbnail) tenta baixar a foto em
							// tamanho original
			AsyncRequestHandler mFileHandler = new AsyncRequestHandler() {
				@Override
				public void onSuccess(byte[] b, Request request) {
					if (b == null)
						return;
					PhotoDao photoDao = PhotoUtils.initPhotoDao(getActivity()
							.getApplicationContext());
					Photo photo = PhotoUtils.getPhotoById(
							getCurrentInstanceId(), photoDao);
					photo.setPhotoBytes(b);
					photo.setIsThumb(false);
					photoDao.update(photo);
					photoDao.getDatabase().close();

					// atualiza a imagem
					imageFront.setImage(new GWImage(getCurrentInstanceId()));
					imageBack.setImage(new GWImage(getCurrentInstanceId()));
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
			SuperToastUtils.showSuperToast(getActivity() 
					.getApplicationContext(),
					SuperToast.BACKGROUND_GREENTRANSLUCENT,
					"Baixando imagem no tamanho original.");
		}

		super.onBind();
	}
	
	public void onCardClick() { 
	      Log.e("TOQUE", "CARD FLIP");

	      FlipAnimation flipAnimation = new FlipAnimation(imageFront, imageBack);

	      if (imageFront.getVisibility() == View.GONE) {
	          flipAnimation.reverse();
	      }
	      rootLayout.startAnimation(flipAnimation);
	}

}
