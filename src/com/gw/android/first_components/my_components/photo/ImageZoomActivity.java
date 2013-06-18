package com.gw.android.first_components.my_components.photo;

import com.gw.android.R;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

public class ImageZoomActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.image_zoom);
		
		ImageView iv = (ImageView) findViewById(R.id.expanded_image);
		
		Bitmap bm = PhotoUtils.byteArrayToBitmap(getIntent()
				.getByteArrayExtra("image"));
		Log.e("zoomed width e height", bm.getWidth() + " - " + bm.getHeight());
		iv.setImageBitmap(bm);

		iv.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				ImageZoomActivity.this.finish();
			}
		});

	}
}