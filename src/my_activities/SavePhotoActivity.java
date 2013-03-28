package my_activities;

import java.io.File;
import java.util.Date;
import android.view.View.OnClickListener;

import com.example.firstcomponents.R;
import com.example.my_fragment.ComponentSimpleModel;

import my_components.photo.PhotoDao;
import my_components.photo.PhotoViewGUI;
import my_components.photo.PhotoUtils;
import my_components.photo.Photo;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;

public class SavePhotoActivity extends Activity {
	final int SELECT_PICTURE = 1;

	// Browse files
	public void chooseFile() {
		Intent myIntent = new Intent(Intent.ACTION_PICK,
				android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
		startActivityForResult(myIntent, SELECT_PICTURE);
	}

	@Override
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
				Bitmap bitmap = PhotoViewGUI.resizeImage(f, 800);

				// Bitmap bitmap = BitmapFactory.decodeFile(filepath);
				byte[] bArray = PhotoUtils.bitmapToByteArray(bitmap);
				Photo photo = new Photo(
						ComponentSimpleModel
								.getUniqueId(SavePhotoActivity.this),
						null, bArray, null, new Date());
				
				PhotoDao photoDao = PhotoUtils.initPhotoDao(this);
				long id = photoDao.insert(photo);
				photoDao.getDatabase().close();
				
				Log.v("Inseriu a foto no banco de dados!", "ID: " + id);
				finish();
			}
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.save_photo_layout);

		findViewById(R.id.btnBrowse).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				chooseFile();
			}
		});
	}
}
