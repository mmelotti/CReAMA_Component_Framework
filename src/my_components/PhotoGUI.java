package my_components;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.my_fragment.GUIComponent;
import com.example.firstcomponents.R;

import database.DaoMaster;
import database.DaoSession;
import database.PhotoDao;
import database.DaoMaster.DevOpenHelper;
import database.PhotoDao.Properties;

public class PhotoGUI extends GUIComponent {

	private ImageView image;
	private Long imagemAtual;
	private Button proxima, anterior;
	Bundle extras;

	private PhotoDao photoDao;
	private DaoSession daoSession;

	public static Bitmap byteArrayToBitmap(byte[] imageBytes) {
		return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
	}

	public static byte[] bitmapToByteArray(Bitmap image) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		image.compress(Bitmap.CompressFormat.JPEG, 80, baos);
		return baos.toByteArray();
		// byte[] encodedImage = Base64.encode(b, Base64.DEFAULT);
	}

	// reduz a imagem para ocupar menos memória
	public static Bitmap resizeImage(File f, int size) {
		try {
			// Decode image size
			BitmapFactory.Options o = new BitmapFactory.Options();
			o.inJustDecodeBounds = true;
			BitmapFactory.decodeStream(new FileInputStream(f), null, o);

			// Find the correct scale value. It should be the power of 2.
			int scale = 1;
			if (o.outHeight > o.outWidth)
				while (o.outWidth / scale / 2 >= size)
					scale *= 2;
			else
				while (o.outHeight / scale / 2 >= size)
					scale *= 2;

			// Decode with inSampleSize
			BitmapFactory.Options o2 = new BitmapFactory.Options();
			o2.inSampleSize = scale;
			return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
		} catch (FileNotFoundException e) {
		}
		return null;
	}

	void initPhotoDao() {
		DevOpenHelper helper = new DaoMaster.DevOpenHelper(getActivity(),
				"photos-db", null);
		SQLiteDatabase db = helper.getWritableDatabase();
		DaoMaster daoMaster = new DaoMaster(db);
		daoSession = daoMaster.newSession();
		photoDao = daoSession.getPhotoDao();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		initPhotoDao();
		View view = inflater.inflate(R.layout.imageone, container, false);

		anterior = (Button) view.findViewById(R.id.imagem_anterior);
		proxima = (Button) view.findViewById(R.id.imagem_proxima);
		image = (ImageView) view.findViewById(R.id.imageView1);
		extras = getActivity().getIntent().getExtras();

		if (extras != null) {
			// mudou imagem, recebeu parametro
			imagemAtual = extras.getLong("nImagem");
			Photo photo = (Photo) photoDao.queryBuilder()
					.where(Properties.Id.eq(imagemAtual)).build().unique();
			if (photo != null)
				image.setImageBitmap(byteArrayToBitmap(photo.getPhotoBytes()));
		} else {
			List<Photo> l = photoDao.queryBuilder().orderAsc(Properties.Id)
					.build().list();
			if (l.isEmpty()) {
				Toast.makeText(getActivity(),
						"Ainda não existem fotos para visualizar.",
						Toast.LENGTH_SHORT).show();
				getActivity().finish();
			} else {
				Photo photo = l.get(0);
				image.setImageBitmap(byteArrayToBitmap(photo.getPhotoBytes()));
				imagemAtual = photo.getId();
			}
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

	private Long proximaImagem() {
		List<Photo> l = photoDao.queryBuilder()
				.where(Properties.Id.gt(imagemAtual)).orderAsc(Properties.Id)
				.list();

		return (l.isEmpty() ? imagemAtual : ((Photo) l.get(0)).getId());
	}

	private Long imagemAnterior() {
		List<Photo> l = photoDao.queryBuilder()
				.where(Properties.Id.lt(imagemAtual)).orderDesc(Properties.Id)
				.list();
		return (l.isEmpty() ? imagemAtual : ((Photo) l.get(0)).getId());
	}

}
