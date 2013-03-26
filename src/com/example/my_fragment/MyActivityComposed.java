package com.example.my_fragment;

import android.app.Activity;
import android.support.v4.app.FragmentTransaction;

public abstract class MyActivityComposed extends MyActivity{

	public abstract void addFromOutside(FragmentTransaction transaction,Activity a);
	
	public abstract void setMyListFromOutside(Activity a,Long commentTarget);

}
