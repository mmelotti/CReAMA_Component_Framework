package com.gw.android.first_components.my_components.photo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.loopj.android.image.SmartImage;

public class GWImage implements SmartImage {
	Long id;

	GWImage(Long id) {
		this.id = id;
	}

	@Override
	public Bitmap getBitmap(Context ctx) {
		byte[] data = PhotoUtils.getPhotoById(id, ctx).getPhotoBytes();
		return BitmapFactory.decodeByteArray(data, 0, data.length);
	}

}
