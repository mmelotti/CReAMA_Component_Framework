package com.gw.android.first_components.my_components.gps;

import java.util.Locale;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gw.android.R;
import com.gw.android.first_components.database.DaoMaster;
import com.gw.android.first_components.database.DaoMaster.DevOpenHelper;
import com.gw.android.first_components.my_components.rating.RatingDao.Properties;
import com.gw.android.first_components.my_fragment.CRComponent;


@SuppressLint("ValidFragment")
public class GPSViewGUI extends CRComponent {
	String latV, lonV;
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

		TextView lat = (TextView) view.findViewById(R.id.latitudeValue);
		TextView lon = (TextView) view.findViewById(R.id.longitudeValue);
		TextView ad1 = (TextView) view.findViewById(R.id.addressLine1);
		TextView ad2 = (TextView) view.findViewById(R.id.addressLine2);
		TextView ad3 = (TextView) view.findViewById(R.id.addressLine3);

		coord = getCoordinates();
		if (coord != null) {
			lat.setText(""+coord.getLatitude());
			lon.setText(""+coord.getLongitude());
			ad1.setText(coord.getAddressLine1());
			ad2.setText(coord.getAddressLine2());
			ad3.setText(coord.getAddressLine3());
		}

		return view;
	}
	
	public static Address getAddress(Context c, double latitude, double longitude) {
		try {
			return new Geocoder(c, Locale.getDefault()).getFromLocation(latitude, longitude, 1).get(0);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
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
