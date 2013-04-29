package my_components.gps;

import my_components.tag.TagActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.example.firstcomponents.R;
import com.example.my_fragment.GUIComponent;

public class GPSViewGUI extends GUIComponent {

	String latV, lonV;
	TextView lat, lon;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater
				.inflate(R.layout.coordinate_view, container, false);

		lat = (TextView) view.findViewById(R.id.latitudeValue);
		lon = (TextView) view.findViewById(R.id.longitudeValue);

		return view;
	}

}
