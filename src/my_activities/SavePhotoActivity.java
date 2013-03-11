package my_activities;

import java.io.File;
import java.util.Date;
import android.view.View.OnClickListener;

import com.example.firstcomponents.R;
import com.example.my_fragment.ComponentSimpleModel;

import database.DaoMaster;
import database.DaoSession;
import database.DaoMaster.DevOpenHelper;
import database.PhotoDao;
import my_components.Photo;
import my_components.PhotoGUI;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;

public class SavePhotoActivity extends Activity {
	final int SELECT_PICTURE = 1;

	private PhotoDao photoDao;
	private DaoSession daoSession;

	// Browse files
	public void chooseFile() {
		Intent myIntent = new Intent(Intent.ACTION_PICK,
				android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
		startActivityForResult(myIntent, SELECT_PICTURE);
	}

	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			if (requestCode == SELECT_PICTURE) {
				Uri selectedimage = data.getData();
				String[] filepathcolumn = { MediaStore.Images.Media.DATA };

				Cursor cursor = getContentResolver().query(selectedimage,
						filepathcolumn, null, null, null);
				cursor.moveToFirst();

				int columnindex = cursor.getColumnIndex(filepathcolumn[0]);
				String filepath = cursor.getString(columnindex);

				cursor.close();
				
				File f = new File(filepath);
				Bitmap bitmap = PhotoGUI.resizeImage(f, 800);

				//Bitmap bitmap = BitmapFactory.decodeFile(filepath);
				byte[] bArray = PhotoGUI.bitmapToByteArray(bitmap);
				Photo photo = new Photo(
						ComponentSimpleModel
								.getUniqueId(SavePhotoActivity.this),
						null, bArray, null, new Date());
				initPhotoDao();
				long id = photoDao.insert(photo);
				photoDao.getDatabase().close();
				Log.v("Inseriu a foto no banco de dados!", "ID: " + id);
				finish();
			}
		}
	}

	void initPhotoDao() {
		DevOpenHelper helper = new DaoMaster.DevOpenHelper(
				SavePhotoActivity.this, "photos-db", null);
		SQLiteDatabase db = helper.getWritableDatabase();
		DaoMaster daoMaster = new DaoMaster(db);
		daoSession = daoMaster.newSession();
		photoDao = daoSession.getPhotoDao();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.save_photo_layout);

		

		findViewById(R.id.btnBrowse).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				chooseFile();
			}
		});
	}
}
