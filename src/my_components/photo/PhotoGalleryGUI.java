package my_components.photo;

import java.util.List;

import my_activities.NewListActivity;
import my_components.photo.PhotoDao.Properties;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.example.firstcomponents.R;
import com.example.my_fragment.CRComponent;

import database.DaoMaster;
import database.DaoMaster.DevOpenHelper;

@SuppressLint("ValidFragment")
public class PhotoGalleryGUI extends CRComponent {
	private PhotoDao photoDao; 
	 
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.image_gallery, container, false);
		
		photoDao = initPhotoDao(getActivity());
		List<Photo> l = photoDao.queryBuilder().orderAsc(Properties.Id).list();
		photoDao.getDatabase().close();
		
		GridView gridview = (GridView) view.findViewById(R.id.gridview);
		gridview.setAdapter(new GalleryAdapter(getActivity(), l));

		gridview.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				Intent trocatela = new Intent(getActivity(), NewListActivity.class);
				trocatela.putExtra("nImagem", id);
				getActivity().startActivity(trocatela); 
			}
		});
		
		return view;
	}

	public static PhotoDao initPhotoDao(Context ctx) {
		DevOpenHelper helper = new DaoMaster.DevOpenHelper(ctx, "photos-db",
				null);
		SQLiteDatabase db = helper.getWritableDatabase();
		DaoMaster daoMaster = new DaoMaster(db);
		PhotoDao photoDao = daoMaster.newSession().getPhotoDao();
		return photoDao;
	}
}
