package my_components.gps;

import my_components.rating.RatingDao.Properties;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.example.firstcomponents.R;
import com.example.my_fragment.ComponentSimpleModel;
import com.example.my_fragment.CRComponent;
import com.gw.android.components.sensor_service.SensorManagerService;

import database.DaoMaster;
import database.DaoSession;
import database.DaoMaster.DevOpenHelper;

@SuppressLint("ValidFragment")
public class GPSViewGUI extends CRComponent {

	@SuppressLint("ValidFragment")
	String latV, lonV;
	TextView lat, lon;
	Long idTarget;
	private Coordinates coord;
	private CoordinatesDao coordDao;
	private DaoSession daoSession;

	public GPSViewGUI(Long t) {
		// TODO Auto-generated constructor stub

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
		if(coord!=null){
			lat.setText(Long.toString(coord.getLatitude()));
			lon.setText(Long.toString(coord.getLongitude()));
			
		}

		return view;
	}

	public Coordinates getCoordinates() {
		initCoordDao();
		Coordinates c = (Coordinates) coordDao.queryBuilder()
				.where(Properties.TargetId.eq(idTarget)).build().list();
		closeDao();
		return c;
	}

	public void initCoordDao() {
		DevOpenHelper helper = new DaoMaster.DevOpenHelper(getActivity(),
				"coordinates-db", null);
		SQLiteDatabase db = helper.getWritableDatabase();
		DaoMaster daoMaster = new DaoMaster(db);
		daoSession = daoMaster.newSession();
		coordDao = daoSession.getCoordinatesDao();
	}

	public void initCoordDao(Activity a) {
		DevOpenHelper helper = new DaoMaster.DevOpenHelper(a, "coordinates-db",
				null);
		SQLiteDatabase db = helper.getWritableDatabase();
		DaoMaster daoMaster = new DaoMaster(db);
		daoSession = daoMaster.newSession();
		coordDao = daoSession.getCoordinatesDao();
	}

	
	
	
	public void closeDao() {
		coordDao.getDatabase().close();
	}

}
