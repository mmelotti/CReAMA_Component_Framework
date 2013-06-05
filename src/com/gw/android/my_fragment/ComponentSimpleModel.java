package com.gw.android.my_fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public abstract class ComponentSimpleModel {

	private String instanceId = "";
	private Long id;
	private Long targetId = Long.valueOf(1);

	public static String SHARED_PREFS_NAME = "gw_shared_prefs";

	public static Long getUniqueId(Context ctx) {
		// pega primeiro id disponivel
		SharedPreferences settings = ctx.getSharedPreferences(
				SHARED_PREFS_NAME, 0);
		Long id = settings.getLong("unique_id", 0);

		// salva o novo id disponivel
		SharedPreferences.Editor editor = settings.edit();
		editor.putLong("unique_id", id + 1);
		editor.commit();

		Log.e("id unico :", ""+id);
		return id;
	}

	public Long getTargetId() {
		return targetId;
	}

	public void setTargetId(Long targetId) {
		this.targetId = targetId;
	}

	public String getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}

	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	

}
