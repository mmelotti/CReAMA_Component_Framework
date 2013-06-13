package com.gw.android.my_components.request;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.gw.android.database.DaoMaster;
import com.gw.android.database.DaoSession;
import com.gw.android.database.DaoMaster.DevOpenHelper;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;

public class RequestUtils {

	public static boolean sendRequest(Request r, boolean tryLater,
			RequestDoneListener listener) {

		return false;
	}

	public static RequestDao initRequestDao(Context context) {
		DevOpenHelper helper = new DaoMaster.DevOpenHelper(context,
				"requests-db", null);
		SQLiteDatabase db = helper.getWritableDatabase();
		DaoMaster daoMaster = new DaoMaster(db);
		DaoSession daoSession = daoMaster.newSession();
		return daoSession.getRequestDao();
	}
	
	public static RequestParams stringToParams(String str) {	
		RequestParams params = new RequestParams();
		if (str == null) 
			return params;
		str.replace(" ", "((ms))");
		
		for (String aux : str.split("__")) {
			Log.e("aux", ""+aux);
			String[] aux2 = aux.split("--");
			params.put(aux2[0], aux2.length > 1? aux2[1] : "");
		}
		
		return params;
	}

	public static void makeRequest(Request request, Context context, AsyncHttpResponseHandler handler) {
		// Este handler é o que objeto que contém as funções de Callback de sucesso e erro
		AsyncHttpClient client = new AsyncHttpClient();
		PersistentCookieStore myCookieStore = new PersistentCookieStore(context);
		client.setCookieStore(myCookieStore);
		RequestParams params = stringToParams(request.getKeyValuePairs());

		if (request.getType().equals("get"))
			client.get(request.getUrl(), params, handler);
		else
			client.post(request.getUrl(), params, handler);
	}

}