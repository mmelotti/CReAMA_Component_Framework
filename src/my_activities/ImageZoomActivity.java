package my_activities;

import com.example.firstcomponents.R;

import my_components.PhotoGUI;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class ImageZoomActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.image_zoom);

		ImageView iv = (ImageView) findViewById(R.id.expanded_image);
		iv.setImageBitmap(PhotoGUI.byteArrayToBitmap(getIntent()
				.getByteArrayExtra("image")));

		iv.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				ImageZoomActivity.this.finish();
			}
		});

	}
}
