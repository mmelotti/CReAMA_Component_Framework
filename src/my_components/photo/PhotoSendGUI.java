package my_components.photo;

import java.io.File;
import java.util.Date;
import my_components.Constants;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.my_fragment.ComponentSimpleModel;
import com.example.my_fragment.CRComponent;
import com.example.firstcomponents.R;

public class PhotoSendGUI extends CRComponent {

	// Browse files
	public void chooseFile() {
		Intent myIntent = new Intent(Intent.ACTION_PICK,
				android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
		startActivityForResult(myIntent, PhotoUtils.SELECT_PICTURE);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == Activity.RESULT_OK) {
			if (requestCode == PhotoUtils.SELECT_PICTURE) {
				Uri selectedimage = data.getData();
				String[] filepathcolumn = { MediaStore.Images.Media.DATA };

				Context ctx = getActivity();
				Cursor cursor = ctx.getContentResolver().query(selectedimage,
						filepathcolumn, null, null, null);
				cursor.moveToFirst();

				int columnindex = cursor.getColumnIndex(filepathcolumn[0]);
				String filepath = cursor.getString(columnindex);

				cursor.close();

				File f = new File(filepath);
				Bitmap bitmap = PhotoUtils.resizeImage(f, 768);
				Log.e("AFTER RESIZE width e height", bitmap.getWidth() + " - " + bitmap.getHeight());
				
				byte[] bArray = PhotoUtils.bitmapToByteArray(bitmap);
				Photo photo = new Photo(ComponentSimpleModel.getUniqueId(ctx),
						null, bArray, null, new Date());

				PhotoDao photoDao = PhotoUtils.initPhotoDao(ctx);
				long id = photoDao.insert(photo);
				photoDao.getDatabase().close();

				Toast.makeText(ctx, "Foto salva!", Toast.LENGTH_SHORT).show();
				Log.v("Inseriu a foto no banco de dados!", "ID: " + id);
			}
		}
	}

	public Long getImageId() {
		preDefined();
		return getCurrentInstanceId();
	}

	public void preDefined() {
		setGeneralGUIId(Constants.PhotoViewGeneralGUIId);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.photo_send, container, false);

		Button save = (Button) view.findViewById(R.id.btnBrowse);

		save.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				chooseFile();
			}
		});

		return view;
	}
}
