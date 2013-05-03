package my_components.gps;

import my_components.rating.RatingDao.Properties;
import android.annotation.SuppressLint;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.firstcomponents.R;
import com.example.my_fragment.CRComponent;
import database.DaoMaster;
import database.DaoMaster.DevOpenHelper;

@SuppressLint("ValidFragment")
public class GPSViewGUI extends CRComponent {
	String latV, lonV;
	TextView lat, lon;
	Long idTarget;
	private Coordinates coord;

	public GPSViewGUI(Long t) {
		idTarget = t;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater
				.inflate(R.layout.coordinate_view, container, false);

		lat = (TextView) view.findViewById(R.id.latitudeValue);
		lon = (TextView) view.findViewById(R.id.longitudeValue);
		coord = getCoordinates();
		if (coord != null) {
			lat.setText(""+coord.getLatitude());
			lon.setText(""+coord.getLongitude());

		}

		return view;
	}

	public Coordinates getCoordinates() {
		CoordinatesDao coordDao = initCoordDao(getActivity());
		Coordinates c = (Coordinates) coordDao.queryBuilder()
				.where(Properties.TargetId.eq(idTarget)).build().unique();
		coordDao.getDatabase().close();
		return c;
	}

	public static CoordinatesDao initCoordDao(Context c) {
		DevOpenHelper helper = new DaoMaster.DevOpenHelper(c, "coordinates-db",
				null);
		SQLiteDatabase db = helper.getWritableDatabase();
		DaoMaster daoMaster = new DaoMaster(db);
		return daoMaster.newSession().getCoordinatesDao();
	}

}
