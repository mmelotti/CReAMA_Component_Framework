package com.gw.android.first_components.my_components.photo;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Gallery;

public class CustomGallery extends Gallery {

    public CustomGallery(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		if (changed) {
			super.onLayout(changed, l, t, r, b);
		}
	}
}
