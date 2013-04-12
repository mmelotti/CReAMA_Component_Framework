package my_components.photo;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import database.DaoMaster;
import database.DaoSession;
import database.DaoMaster.DevOpenHelper;

public class PhotoUtils {
	static int SELECT_PICTURE = 1;

	public static PhotoDao initPhotoDao(Context context) {
		DevOpenHelper helper = new DaoMaster.DevOpenHelper(context,
				"photos-db", null);
		SQLiteDatabase db = helper.getWritableDatabase();
		DaoMaster daoMaster = new DaoMaster(db);
		DaoSession daoSession = daoMaster.newSession();
		return daoSession.getPhotoDao();
	}
	
	public static Bitmap byteArrayToBitmap(byte[] imageBytes) {
		return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
	}

	public static byte[] bitmapToByteArray(Bitmap image) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		image.compress(Bitmap.CompressFormat.JPEG, 80, baos);
		return baos.toByteArray();
	}


	// reduz a imagem para ocupar menos memória
	public static Bitmap resizeImage(File f, int size) {
		try {
			// Decode image size
			BitmapFactory.Options o = new BitmapFactory.Options();
			o.inJustDecodeBounds = true;
			BitmapFactory.decodeStream(new FileInputStream(f), null, o);
			Log.e("BEFORE RESIZE width e height", o.outWidth + " - " + o.outHeight);
			
			// Find the correct scale value. It should be the power of 2.
			int scale = 1;
			if (o.outHeight < o.outWidth)
				while (o.outWidth / scale >= size)
					scale *= 2;
			else
				while (o.outHeight / scale >= size)
					scale *= 2;

			BitmapFactory.Options o2 = new BitmapFactory.Options();
			o2.inSampleSize = scale;
			return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
		} catch (FileNotFoundException e) {
		}
		return null;
	}

	public static Bitmap resizeImage(File f, int width, int height) {
		try {
			// Decode image size
			BitmapFactory.Options o = new BitmapFactory.Options();
			o.inJustDecodeBounds = true;
			BitmapFactory.decodeStream(new FileInputStream(f), null, o);
			Log.e("BEFORE RESIZE width e height", o.outWidth + " - " + o.outHeight);
			
			// Find the correct scale value. It should be the power of 2.
			int scale = 1;
			if (o.outHeight < o.outWidth)
				while (o.outWidth / scale / 2 >= width)
					scale *= 2;
			else
				while (o.outHeight / scale / 2 >= height)
					scale *= 2;

			scale *= 2; 
			BitmapFactory.Options o2 = new BitmapFactory.Options();
			o2.inSampleSize = scale;
			return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
		} catch (FileNotFoundException e) {
		}
		return null;
	}
	
}
