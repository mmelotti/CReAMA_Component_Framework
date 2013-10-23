package com.gw.android.first_components.my_components.photo;

import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import com.github.johnpersano.supertoasts.SuperToast;
import com.gw.android.R;
import com.gw.android.Utils.SuperToastUtils;
import com.gw.android.components.connection_manager.AsyncRequestHandler;
import com.gw.android.components.request.Request;
import com.gw.android.first_components.my_components.photo.PhotoDao.Properties;
import com.gw.android.first_components.my_fragment.CRComponent;
import com.gw.android.first_components.my_fragment.ComponentSimpleModel;
import com.loopj.android.image.SmartImageView;

@SuppressLint("ValidFragment")
public class PhotoGalleryGUI extends CRComponent {
	public static int MAX_GALLERY_PHOTOS = 10;
	private PhotoDao photoDao;
	private Gallery picGallery;
	private PicAdapter imgAdapt;
	private int currentGallerySelected = -1;
	private Long[] photoIds;
	
	private boolean conectado = true;

	String ip = "200.137.66.94";
	String url = "http://" + ip
			+ ":8080/GW-Application-Arquigrafia/groupware-workbench";
	private String jsonTestUrl = "/photos/7/amount/" + MAX_GALLERY_PHOTOS;
	private String urlEndArquigrafia = "?_format=json";
	private boolean getOnlyLocal = true;
	
	private int size = PhotoUtils.CROP;

	public PhotoGalleryGUI(boolean getLocal, int current) {
		getOnlyLocal = getLocal;
		currentGallerySelected = current;
	}

	public PhotoGalleryGUI(boolean getLocal) {
		getOnlyLocal = getLocal;
	}

	public PhotoGalleryGUI() {

	}

	/*
	 * IMAGENS ARQUIGRAFIA
	 * http://arquigrafia.org.br/photo/img-thumb/2230?_log=no
	 * http://arquigrafia.org.br/photo/img-crop/2230?_log=no
	 * http://arquigrafia.org.br/photo/img-show/2230.jpeg
	 */

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.image_gallery, container, false);

		picGallery = (Gallery) view.findViewById(R.id.gallery);

		imgAdapt = new PicAdapter(getActivity());
		picGallery.setAdapter(imgAdapt);

		picGallery.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View v,
					int position, long id) {
				currentGallerySelected = position;
				onItemSelectedApplication(parent, v, position, id, photoIds[position]);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// Do nothing
			}
		});

		picGallery
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long arg3) {
						onItemClickApplication(parent, view, position, arg3, photoIds[position]);
					}
				});

		AsyncRequestHandler mFileHandler = new AsyncRequestHandler() {
			@Override
			public void onSuccess(byte[] b, Request request) {
				Log.i("MFILEHANDLER - Fez download da imagem", " sim!");
				String url = request.getUrl();
				String auxArray[] = url.split("/");
				Log.i("MFILEHANDLER - Fez download da imagem", auxArray[auxArray.length - 1]);
				//String auxArray2[] = auxArray[auxArray.length - 1].split(".j");
				//String auxArray2[] = auxArray[auxArray.length - 1].split(urlEndImage[CROP]);
				
				String photoServerId = auxArray[auxArray.length - 1].replace(PhotoUtils.urlEndImage[size], "");
				Log.i("ID SERVER", photoServerId);
				saveImageAfterDownload(photoServerId, b);
				imgAdapt.updateAdapter();
				imgAdapt.notifyDataSetChanged();
			}
		};

		AsyncRequestHandler mHandler = new AsyncRequestHandler() {
			@Override
			public void onSuccess(String response, Request request) {
				if (response.startsWith("[")) { // fotos aleatorias
					Log.i("antes parse array", " id");
					parseArrayJSON(response);
				} else if (response.startsWith("{\"photo\":")) { // dados de uma foto
					Log.i("pegando dados de uma foto", "uma foto");
					parseOnePhotoJSON(response);
				} 
			}
		};
		setComponentRequestCallback(mHandler);
		setComponentFileRequestCallback(mFileHandler);
		if(currentGallerySelected!=-1){
			picGallery.setSelection(currentGallerySelected);
		}
		
		return view;
	}

	public void onItemSelectedApplication(AdapterView<?> parent, View v,
			int position, long id, Long photoId) { 
		// Photo photo = PhotoUtils.getPhotoById(photoId, getActivity());
	}

	public void onItemClickApplication(AdapterView<?> parent, View view,
			int position, long arg3, Long photoId) {
		// Photo photo = PhotoUtils.getPhotoById(photoId, getActivity());
	}

	void saveImageAfterDownload(String serverId, byte[] b) {
		photoDao = PhotoUtils.initPhotoDao(getActivity());
		List<Photo> found = photoDao.queryBuilder()
				.where(Properties.ServerId.eq(Long.parseLong(serverId))).list();
		if (found.isEmpty()) {
			Log.d("SALVANDO", "SERVER ID NÃO EXISTE");
			Long newI = ComponentSimpleModel.getUniqueId(getActivity());
			Photo photo = new Photo(newI, null, Long.parseLong(serverId), size == PhotoUtils.CROP, b,
					null, new Date());
			Log.d("SALVANDO", "id= " + newI);
			photoDao.insert(photo);
		} else
			Log.d("SALVANDO", "SERVER ID EXISTENTE");

		photoDao.getDatabase().close();
	}

	void parseOnePhotoJSON(String r) {
		Log.i("Parseando uma foto", " inicio");
		JSONObject object;
		try {
			object = new JSONObject(r);

			JSONObject photoObject = object.getJSONObject("photo");

			String nome = photoObject.get("name").toString();
			Long idServ = Long.parseLong(photoObject.get("id").toString());

			Log.i("Parseando uma foto", " name=" + nome);
			getTheImage(Long.toString(idServ));

		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	void parseArrayJSON(String response) {
		try {
			JSONArray nameArray = new JSONArray(response);

			for (int j = 0; j < nameArray.length(); j++) {
				JSONObject object = nameArray.getJSONObject(j);
				Long idServ = Long.parseLong(object.get("id").toString());
				getOnePhotoRequest(Long.toString(idServ));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void onBind() {
		if (getOnlyLocal == false) {
			getPhotosIdRequest();
			SuperToastUtils.showSuperToast(getActivity(), SuperToast.BACKGROUND_GREENTRANSLUCENT, "Iniciando download das imagens...");
			Log.i("ON Bbind", " FEZ REQUEST"); 
		}

	}

	public void createSimpleFileRequest(String url, String type) {
		Request request = new Request(null, url, type, null);
		if (conectado) {
			makeFileRequest(request);
		} else { // pega no cache
		}
	} 

	public void createSimpleRequest(String url, String type) {
		Request request = new Request(null, url, type, null);
		if (conectado)
			makeRequest(request);
		else { // pega no cache
		}
	}

	private void getPhotosIdRequest() {
		createSimpleRequest(getBaseUrl()+jsonTestUrl, "get");
	}

	private void getOnePhotoRequest(String id) {
		createSimpleRequest(getBaseUrl() + "/photo/" + id + urlEndArquigrafia,
				"get");
	}

	private void getTheImage(String id) {
		createSimpleFileRequest(getBaseUrl() + "/photo" + PhotoUtils.imageType[size] + id
				+ PhotoUtils.urlEndImage[size], "get");
	}

	// Classe auxiliar
	public class PicAdapter extends BaseAdapter {
		int defaultItemBackground;
		private Context galleryContext;
		

		public PicAdapter(Context c) {
			galleryContext = c;
			updateAdapter();

			// get the styling attributes - use default Android system resources
			TypedArray styleAttrs = galleryContext
					.obtainStyledAttributes(R.styleable.PicGallery);
			// get the background resource
			defaultItemBackground = styleAttrs.getResourceId(
					R.styleable.PicGallery_android_galleryItemBackground, 0);
			// recycle attributes
			styleAttrs.recycle();
		}

		void updateAdapter() {
			photoDao = PhotoUtils.initPhotoDao(getActivity());
			List<Photo> list = photoDao.queryBuilder().orderDesc(Properties.Id).limit(MAX_GALLERY_PHOTOS).list();
			photoDao.getDatabase().close();

			photoIds = new Long[list.size()];
			
			for (int i = 0; i < photoIds.length; i++) 
				photoIds[i] = list.get(i).getId();
			
		}

		// BaseAdapter methods

		// return number of data items i.e. bitmap images
		public int getCount() {
			return photoIds.length;
		}

		public Object getItem(int position) {
			return position;
		}

		public long getItemId(int position) {
			return position;
		}

		// get view specifies layout and display options for each thumbnail in
		// the gallery
		public View getView(int position, View convertView, ViewGroup parent) {
			SmartImageView imageView = new SmartImageView(galleryContext);
			final float scale = getActivity().getResources().getDisplayMetrics().density;
			imageView.setImage(new GWImage(photoIds[position]));	
			imageView.setLayoutParams(new Gallery.LayoutParams((int) (170 * scale + 0.5f), (int) (140 * scale + 0.5f)));
			imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
			imageView.setBackgroundResource(defaultItemBackground);
			return imageView;
		}
	}
}
