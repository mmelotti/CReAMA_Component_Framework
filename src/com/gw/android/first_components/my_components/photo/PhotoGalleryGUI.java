package com.gw.android.first_components.my_components.photo;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TextView;

import com.gw.android.R;
import com.gw.android.components.connection_manager.AsyncRequestHandler;
import com.gw.android.components.request.Request;
import com.gw.android.first_components.database.DaoMaster;
import com.gw.android.first_components.database.DaoMaster.DevOpenHelper;
import com.gw.android.first_components.my_components.faq.Faq;
import com.gw.android.first_components.my_components.photo.PhotoDao.Properties;
import com.gw.android.first_components.my_fragment.CRComponent;

@SuppressLint("ValidFragment")
public class PhotoGalleryGUI extends CRComponent {
	private PhotoDao photoDao;
	private Gallery picGallery;
	private PicAdapter imgAdapt;
	private TextView description;
	List<Photo> list;

	private boolean conectado = true;
	private String photoIdUrl;

	String ip = "200.137.66.94";
	String url = "http://" + ip
			+ ":8080/GW-Application-Arquigrafia/groupware-workbench";
	private String jsonTestUrl = "" + "http://" + ip
			+ ":8080/GW-Application-Arquigrafia/tresfotos.json";
	private String urlPhotoArquigrafia = "http://www.arquigrafia.org.br/photo/";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.image_gallery, container, false);

		picGallery = (Gallery) view.findViewById(R.id.gallery);
		description = (TextView) view.findViewById(R.id.description);

		photoDao = initPhotoDao(getActivity());
		list = photoDao.queryBuilder().orderAsc(Properties.Id).list();
		photoDao.getDatabase().close();

		Photo photo = null;
		if (!list.isEmpty()) {
			photo = list.get(0);
		}

		if (photo != null) {
			byte[] data = photo.getPhotoBytes();
			Bitmap bm = BitmapFactory.decodeByteArray(data, 0, data.length);
		}

		imgAdapt = new PicAdapter(getActivity());
		picGallery.setAdapter(imgAdapt);
		picGallery.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View v,
					int position, long id) {
				description.setText(list.get(position).getText());
				Log.e("view", " " + position);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// Do nothing
			}
		});

		/*
		 * GridView gridview = (GridView) view.findViewById(R.id.gridview);
		 * gridview.setAdapter(new GalleryAdapter(getActivity(), l));
		 * 
		 * gridview.setOnItemClickListener(new OnItemClickListener() { public
		 * void onItemClick(AdapterView<?> parent, View v, int position, long
		 * id) { Intent trocatela = new Intent(getActivity(),
		 * NewListActivity.class); trocatela.putExtra("nImagem", id);
		 * getActivity().startActivity(trocatela); } });
		 */

		AsyncRequestHandler mHandler = new AsyncRequestHandler() {
			@Override
			public void onSuccess(String response) {
				parseAndDownload(response);
				Log.i("Onsucces e Parser ", " id=");

			}
		};
		setComponentRequestCallback(mHandler);

		return view;
	}

	void parseAndDownload(String response) {
		try {
			JSONObject json = new JSONObject(response);

			JSONArray nameArray = json.names();
			JSONArray valArray = json.toJSONArray(nameArray);
			JSONArray arrayResults = valArray.getJSONArray(0);

			// apaga tudo no cache para recriar... por enquanto fazemos isso
			// apagaNoCache();
			// só pode executar isso se tiver conectado

			Log.i("Parseando ", " antes do for");

			for (int j = 0; j < arrayResults.length(); j++) {
				JSONObject object = arrayResults.getJSONObject(j);
				Long idServ = Long.parseLong(object.get("id").toString());
				String nome = object.get("nomeArquivo").toString();

				Log.i("Parseando ", " id=" + idServ);
				Log.i("Parseando ", " arq=" + nome);
				
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

	public static PhotoDao initPhotoDao(Context ctx) {
		DevOpenHelper helper = new DaoMaster.DevOpenHelper(ctx, "photos-db",
				null);
		SQLiteDatabase db = helper.getWritableDatabase();
		DaoMaster daoMaster = new DaoMaster(db);
		PhotoDao photoDao = daoMaster.newSession().getPhotoDao();
		return photoDao;
	}

	@Override
	protected void onBind() {
		getPhotosIdRequest();
		Log.i("ONBIND ", " after");
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

			// create bitmap array
			imageBitmaps = new Bitmap[list.size()];
			// decode the placeholder image
			// placeholder = BitmapFactory.decodeResource(getResources(),
			// R.drawable.ic_launcher);

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
			// get the styling attributes - use default Android system resources
			TypedArray styleAttrs = galleryContext
					.obtainStyledAttributes(R.styleable.PicGallery);
			// get the background resource
			defaultItemBackground = styleAttrs.getResourceId(
					R.styleable.PicGallery_android_galleryItemBackground, 0);
			// recycle attributes
			styleAttrs.recycle();
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

		// custom methods for this app

		// helper method to add a bitmap to the gallery when the user chooses
		// one
		public void addPic(Bitmap newPic) {
			// set at currently selected index
			imageBitmaps[10] = newPic;
		}

		// return bitmap at specified position for larger display
		public Bitmap getPic(int posn) {
			// return bitmap at posn index
			return imageBitmaps[posn];
		}
	}
}
