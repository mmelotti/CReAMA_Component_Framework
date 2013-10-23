package com.gw.android.first_components.my_components.photo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.loopj.android.image.SmartImage;

public class GWImage implements SmartImage {
	Long id;
	boolean downloadBig = false;

	GWImage(Long id) {
		this.id = id;		
	}

	GWImage(Long id, boolean downloadBigPhoto) {
		this.id = id;
		this.downloadBig = downloadBigPhoto;
	}
	
	@Override
	public Bitmap getBitmap(Context ctx) {
		Photo photo = PhotoUtils.getPhotoById(id, ctx);
		byte[] data = photo.getPhotoBytes();
		
		if(data != null) {
			return BitmapFactory.decodeByteArray(data, 0, data.length);
		} else{
			return null;
		}
		
	}

}
