package com.gw.android.first_components.my_components.photo;

import java.io.ByteArrayInputStream;

import com.gw.android.R;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class ImageZoomActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.image_zoom);
		
		ImageView iv = (ImageView) findViewById(R.id.expanded_image);
		
		ByteArrayInputStream is = new ByteArrayInputStream(getIntent().getByteArrayExtra("image"));
		iv.setImageDrawable(Drawable.createFromStream(is, "image"));

		iv.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				ImageZoomActivity.this.finish();
			}
		});

	}
}
