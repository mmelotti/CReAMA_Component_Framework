package com.gw.android.first_components.my_components.photo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.gw.android.first_components.my_components.photo.PhotoDao.Properties;
import com.loopj.android.image.SmartImage;

public class GWImage implements SmartImage {
	private Photo photo;
	Long id;

	GWImage(Long id) {
		this.id = id;
	}

	@Override
	public Bitmap getBitmap(Context ctx) {
		PhotoDao photoDao = PhotoUtils.initPhotoDao(ctx);
		photo = (Photo) photoDao.queryBuilder().where(Properties.Id.eq(id))
				.build().unique();
		photoDao.getDatabase().close();
		byte[] data = photo.getPhotoBytes();
		return BitmapFactory.decodeByteArray(data, 0, data.length);
	}

}
