package com.gw.android.first_components.my_components.tracker;

import com.gw.android.first_components.my_components.gps.Coordinates;

public interface Trackable {

	public String getName();

	public String getComponentType();

	public int getIconResource();

	public Coordinates getCoordinates();

	public void doTrackableRequest();
}
