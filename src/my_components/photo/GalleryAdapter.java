package my_components.photo;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class GalleryAdapter extends BaseAdapter {
	private Context mContext;
	private List<Photo> mList;

	public GalleryAdapter(Context c, List<Photo> l) {
		mContext = c;
		mList = l;
	}

	public int getCount() {
		return mList.size();
	}

	public Object getItem(int position) {  
		return null;
	}

	public long getItemId(int position) {
		return mList.get(position).getId();
	}

	// create a new ImageView for each item referenced by the Adapter
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView imageView;
		if (convertView == null) { // if it's not recycled, initialize some attributes
			imageView = new ImageView(mContext);
			imageView.setLayoutParams(new GridView.LayoutParams(250, 250));
			imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
			imageView.setPadding(10, 10, 10, 10);
		} else
			imageView = (ImageView) convertView;

		byte[] data = mList.get(position).getPhotoBytes();
		Bitmap bm = BitmapFactory.decodeByteArray(data, 0, data.length);
		imageView.setImageBitmap(bm);
		return imageView;
	}
}
