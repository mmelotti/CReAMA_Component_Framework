package com.gw.android.first_components.my_components.photo;

import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.widget.TextView;

import com.gw.android.R;
import com.gw.android.components.connection_manager.AsyncRequestHandler;
import com.gw.android.components.request.Request;
import com.gw.android.first_components.my_components.photo.PhotoDao.Properties;
import com.gw.android.first_components.my_fragment.CRComponent;
import com.gw.android.first_components.my_fragment.ComponentSimpleModel;

@SuppressLint("ValidFragment")
public class PhotoGalleryGUI extends CRComponent {
	private PhotoDao photoDao;
	private Gallery picGallery;
	private PicAdapter imgAdapt;
	private TextView description;
	private int currentSelected=0;
	List<Photo> list;

	private boolean conectado = true;
	private String photoIdUrl;

	String ip = "200.137.66.94";
	String url = "http://" + ip
			+ ":8080/GW-Application-Arquigrafia/groupware-workbench";
	private String jsonTestUrl = "" + "http://" + ip
			+ ":8080/GW-Application-FAQN/tresfotos.json";
	private String urlPhotoArquigrafia = "http://www.arquigrafia.org.br/photo/";
	private String urlEndArquigrafia = "?_format=json";

	private String urlOneImage = "http://arquigrafia.org.br/photo/img-crop/";
	private String urlEndImage = "?_log=no";
	
	private boolean getOnlyLocal = true;
	
	public PhotoGalleryGUI(boolean getLocal, int current){
		getOnlyLocal = getLocal;
		currentSelected = current;
	}
	
	public PhotoGalleryGUI(){
		
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
		description = (TextView) view.findViewById(R.id.description); 

		imgAdapt = new PicAdapter(getActivity());
		picGallery.setAdapter(imgAdapt);
		
		picGallery.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View v,
					int position, long id) {
				description.setText(list.get(position).getText());
				Log.e("view", " " + position);
				currentSelected=position;
				onItemSelectedApplication(parent, v, position, id, list.get(position));
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// Do nothing
			}
		});		
		
		picGallery.setOnItemClickListener(new AdapterView.OnItemClickListener()
		{
		    @Override
		    public void onItemClick(AdapterView<?> parent, View view, int position, long arg3)
		    {
		        // TODO Auto-generated method stub
		    	onItemClickApplication(parent, view, position, arg3, list.get(position));
		    }
		});
		

		
		AsyncRequestHandler mFileHandler = new AsyncRequestHandler() {
			@Override
			public void onSuccess(byte[] b, Request request) {
				Log.i("MFILEHANDLER - Fez download da imagem", " sim!");
				String url = request.getUrl();
				String auxArray[] = url.split("/");
				String auxArray2[] = auxArray[auxArray.length-1].split("\\?");
				String photoServerId = auxArray2[0];
				Log.i("ID SERVER", photoServerId);
				saveImageAfterDownload(photoServerId, b);
				imgAdapt.updateAdapter();
				imgAdapt.notifyDataSetChanged();
			}
		};
		
		AsyncRequestHandler mHandler = new AsyncRequestHandler() {
			@Override
			public void onSuccess(String response, Request request) {
				Log.i("ON SUCCES APP", " id");
				if (response.startsWith("{\"photos\":")) {// fotos aleatorias
					Log.i("antes parse array", " id");
					parseArrayJSON(response);
				} else if (response.startsWith("{\"photo\":")) {// dados de uma
																// foto
					parseOnePhotoJSON(response);
				} else {// apenas a imagem
					Log.i("Fez download da imagem callback antigo", " sim!");
					/*Log.i("one photo request test", response);
					String url = request.getUrl();
					String auxArray[] = url.split("/");
					String auxArray2[] = auxArray[auxArray.length-1].split("\\?");
					String photoServerId = auxArray2[0];
					Log.i("ID SERVER", photoServerId);
					saveImageAfterDownload(photoServerId, b);
					imgAdapt.updateAdapter();
					imgAdapt.notifyDataSetChanged();*/
				}
				Log.i("Onsucces e Parser ", " id=");
			}
		};
		setComponentRequestCallback(mHandler);
		setComponentFileRequestCallback(mFileHandler);
		picGallery.setSelection(currentSelected);
		return view;
	}
	
	public void onItemSelectedApplication(AdapterView<?> parent, View v,
			int position, long id, Photo photo){
		
	}
	
	 public void onItemClickApplication(AdapterView<?> parent, View view, int position, long arg3, Photo photo)
	    {
	        // TODO Auto-generated method stub

	    }
	
	
	void saveImageAfterDownload(String serverId, byte[] b) {
		// tenho que saber qual imagem eh pra salvar com as info
				
		photoDao = PhotoUtils.initPhotoDao(getActivity());
		List<Photo> found = photoDao.queryBuilder()
				.where(Properties.ServerId.eq(Long.parseLong(serverId))).list();
		if (found.isEmpty()) {
			Log.d("SALVANDO", "SERVER ID NÃO EXISTE");
			Long newI = ComponentSimpleModel.getUniqueId(getActivity());
			Photo photo = new Photo(
					newI, null,
					Long.parseLong(serverId), b, null, new Date());
			Log.d("SALVANDO", "id= "+newI);
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

			// Log.i("Parseando uma foto", " objeto=" + object.toString());

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
		Log.i("Desnto parse array", " id=");
		try {
			JSONObject json = new JSONObject(response);
			
			JSONArray nameArray = json.names();
			JSONArray valArray = json.toJSONArray(nameArray);
			JSONArray arrayResults = valArray.getJSONArray(0);

			// só pode executar isso se tiver conectado

			// dados de varias fotos, manda request para cada uma
			for (int j = 0; j < arrayResults.length(); j++) {
				JSONObject object = arrayResults.getJSONObject(j);
				Long idServ = Long.parseLong(object.get("id").toString());
				String nome = object.get("nomeArquivo").toString();

				Log.i("Parseando varias fotos", " id=" + idServ);

				getOnePhotoRequest(Long.toString(idServ));

				/*
				 * // cria novo faq para mandar pro cache Faq novo = new Faq(0L,
				 * 0L, idServ, pergunta, resposta); novo =
				 * newOnePersistence(novo); // gera Id unico, entao retorna //
				 * atualizado list.add(novo);
				 */
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void onBind() {
		if(getOnlyLocal=false){
			getPhotosIdRequest();
		}
		
		Log.i("ONBIND ", " after");
	}

	public void createSimpleFileRequest(String url, String type) {
		Request request = new Request(null, url, type, null);

		// se nao estiver conectado, nem vale ir para a fila de request
		// se estiver conectado, vai tentar buscar no servidor as

		// depois salva no cache para acesso offline
		if (conectado) {
			makeFileRequest(request);
		} else {// pega no cache
				// preencheCampos();
		}
	}
	
	public void createSimpleRequest(String url, String type) {
		Request request = new Request(null, url, type, null);

		// se nao estiver conectado, nem vale ir para a fila de request
		// se estiver conectado, vai tentar buscar no servidor as

		// depois salva no cache para acesso offline
		if (conectado)
			makeRequest(request);
		else {// pega no cache
				// preencheCampos();
		}
	}

	private void getPhotosIdRequest() {
		createSimpleRequest(jsonTestUrl, "get");
	}

	private void getOnePhotoRequest(String id) {
		createSimpleRequest(getBaseUrl() +"/photo/"+ id + urlEndArquigrafia, "get");
	}

	private void getTheImage(String id) {
		createSimpleFileRequest(getBaseUrl() +"/photo/img-crop/" + id + urlEndImage, "get");
	}

	// Classe auxiliar
	public class PicAdapter extends BaseAdapter {

		// use the default gallery background image
		int defaultItemBackground;

		// gallery context
		private Context galleryContext;

		// array to store bitmaps to display
		private Bitmap[] imageBitmaps;

		// constructor
		public PicAdapter(Context c) {
			// instantiate context
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
			list = photoDao.queryBuilder().orderAsc(Properties.Id).list();
			photoDao.getDatabase().close();
			imageBitmaps = new Bitmap[list.size()];

			// set placeholder as all thumbnail images in the gallery initially
			for (int i = 0; i < imageBitmaps.length; i++) {
				Photo photo = list.get(i);

				if (photo != null) {
					byte[] data = photo.getPhotoBytes();
					Bitmap bm = BitmapFactory.decodeByteArray(data, 0,
							data.length);
					imageBitmaps[i] = bm;
				}
			}
		}

		// BaseAdapter methods

		// return number of data items i.e. bitmap images
		public int getCount() {
			return imageBitmaps.length;
		}

		// return item at specified position
		public Object getItem(int position) {
			return position;
		}

		// return item ID at specified position
		public long getItemId(int position) {
			return position;
		}

		// get view specifies layout and display options for each thumbnail in
		// the gallery
		public View getView(int position, View convertView, ViewGroup parent) {

			// create the view
			ImageView imageView = new ImageView(galleryContext);
			// specify the bitmap at this position in the array
			imageView.setImageBitmap(imageBitmaps[position]);
			// set layout options
			imageView.setLayoutParams(new Gallery.LayoutParams(200, 180));
			// 400, 300 fica gigante no meu!
			// 300, 200 fica legal ateh mas vou diminuir mais
			// 200. 180 ficou perfeito

			// scale type within view area
			imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
			// set default gallery item background
			imageView.setBackgroundResource(defaultItemBackground);
			// return the view
			return imageView;
		}
	}
}
