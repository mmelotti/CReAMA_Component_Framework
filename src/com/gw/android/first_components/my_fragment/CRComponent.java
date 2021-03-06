package com.gw.android.first_components.my_fragment;

import java.util.ArrayList;
import java.util.List;

import com.gw.android.components.connection_manager.AsyncRequestHandler;
import com.gw.android.components.connection_manager.ConnectionManager;
import com.gw.android.components.request.Request;
import com.gw.android.first_components.my_components.gps.Coordinates;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.IBinder;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.Toast;

public abstract class CRComponent extends GenericComponent {

	private CRActivity controlActivity;

	private AsyncRequestHandler componentHandler = new AsyncRequestHandler();
	private AsyncRequestHandler applicationHandler = new AsyncRequestHandler();

	private AsyncRequestHandler componentFileHandler = new AsyncRequestHandler();
	private AsyncRequestHandler applicationFileHandler = new AsyncRequestHandler();

	private RequestListener component = null;
	private String nick, componentType = "";
	private int iconResource = 0;
	private static String MSG_ENVIOU = "enviou";

	private int relativeFragmentId = -1;

	boolean trackable = false;

	public void reloadActivity() {
		Activity a = getActivity();
		a.finish();
		startActivity(a.getIntent());
	}

	public interface RequestListener {
		public String onRequest(String msg);
	}

	public String sendMessage(String msg) {
		String re = component.onRequest(msg);

		return re;
	}

	public RequestListener getComponent() {
		return component;
	}

	public void setComponent(RequestListener component) {
		this.component = component;
	}

	public int getRelativeFragmentId() {
		return relativeFragmentId;
	}

	public void setRelativeFragmentId(int relativeFragmentId) {
		this.relativeFragmentId = relativeFragmentId;
	}

	public void addComponentToView(CRComponent comp, int viewId) {
		FragmentTransaction ft = getChildFragmentManager().beginTransaction();
		ft.add(viewId, comp);
		ft.commit();
	}

	public void setControlActivity(CRActivity mya) {
		controlActivity = mya;
	}

	public CRActivity getControlActivity() {
		return controlActivity;
	}

	public String getNick() {
		return nick;
	}

	public String getComponentType() {
		return componentType;
	}

	public void setComponentType(String componentType) {
		this.componentType = componentType;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public int getIconResource() {
		return iconResource;
	}

	public void setIconResource(int iconResource) {
		this.iconResource = iconResource;
	}

	public int getCollabletId() {
		Activity a = getActivity();
		int resId = a.getResources().getIdentifier(getNick(), "integer",
				a.getPackageName());
		return a.getResources().getInteger(resId);
	}

	public void enviou() {
		if (component != null)
			sendMessage(MSG_ENVIOU);
	}

	public List<ComponentSimpleModel> getListSimple(Long target, Activity a) {
		ArrayList<ComponentSimpleModel> list = new ArrayList<ComponentSimpleModel>();

		return list;
	}

	public void submittedFrom(Long target) {

	}

	protected void setComponentRequestCallback(AsyncRequestHandler h) {
		componentHandler = h;
	}

	public void setApplicationRequestCallback(AsyncRequestHandler h) {
		applicationHandler = h;
	}

	protected void setComponentFileRequestCallback(AsyncRequestHandler h) {
		componentFileHandler = h;
	}

	public void setApplicationFileRequestCallback(AsyncRequestHandler h) {
		applicationFileHandler = h;
	}

	protected void makeRequest(Request request) {
		if (getActivity() == null) {

		} else {
			getConnectionManager().makeRequest(request, getActivity(),
					componentHandler, applicationHandler);
		}
	}

	protected void makeFileRequest(Request request) {
		if (getActivity() == null) {

		} else {
			getConnectionManager().makeFileRequest(request, getActivity(),
					componentFileHandler, applicationFileHandler);
		}
	}

	// Código de comunicação com o serviço ConnectionManager
	private ConnectionManager mBoundService;
	private boolean mIsBound = false;

	protected ConnectionManager getConnectionManager() {
		return mBoundService;
	}

	// Método chamado quando o componente se conecta ao ConnectionManager
	protected void onBind() {

	}

	// Método chamado quando o componente se desconecta do ConnectionManager
	protected void onUnbind() {

	}

	private ServiceConnection mConnection = new ServiceConnection() {
		public void onServiceConnected(ComponentName className, IBinder service) {
			// This is called when the connection with the service has been
			// established, giving us the service object we can use to
			// interact with the service. Because we have bound to a explicit
			// service that we know is running in our own process, we can
			// cast its IBinder to a concrete class and directly access it.

			mBoundService = ((ConnectionManager.LocalBinder) service)
					.getService();
			onBind();
		}

		public void onServiceDisconnected(ComponentName className) {
			// This is called when the connection with the service has been
			// unexpectedly disconnected -- that is, its process crashed.
			// Because it is running in our same process, we should never
			// see this happen.
			mBoundService = null;
			onUnbind();
			Toast.makeText(getActivity(), "Desconectado do serviço",
					Toast.LENGTH_SHORT).show();
			// StandOutWindow.close(context, cls, id);
		}
	};

	void doBindService() {
		// Establish a connection with the service. We use an explicit
		// class name because we want a specific service implementation that
		// we know will be running in our own process (and thus won't be
		// supporting component replacement by other applications).
		getActivity().bindService(
				new Intent(getActivity(), ConnectionManager.class),
				mConnection, Context.BIND_AUTO_CREATE);
		mIsBound = true;
	}

	public String getBaseUrl() {
		SharedPreferences testPrefs = getActivity().getApplication()
				.getSharedPreferences("test_prefs", Context.MODE_PRIVATE);
		return testPrefs.getString("base_url", "");
	}

	public String getSharedData(String key) {
		SharedPreferences testPrefs = getActivity().getApplication()
				.getSharedPreferences("test_prefs", Context.MODE_PRIVATE);
		return testPrefs.getString(key, "");
	}

	public void putSharedData(String key, String value) {
		SharedPreferences testPrefs = getActivity().getApplication()
				.getSharedPreferences("test_prefs", Context.MODE_PRIVATE);
		Editor edit = testPrefs.edit();
		edit.putString(key, value).commit();
	}

	void doUnbindService() {
		if (mIsBound) {
			// Detach our existing connection.
			getActivity().unbindService(mConnection);
			mIsBound = false;
		}
	}

	// ---------------------------------------------------------------

	@Override
	public void onDestroy() {
		super.onDestroy();
		doUnbindService();
	}

	@Override
	public void onStart() {
		super.onStart();
		Log.e("retain instance", "ok");
		setRetainInstance(!trackable);
		doBindService();
	}

	public boolean isTrackable() {
		return trackable;
	}

	public void setTrackable(boolean t) {
		Log.e("set trackable", "ok");
		trackable = t;
	}

	public Coordinates getCoordinates() {
		return new Coordinates(null, null, null, -20.4, -40.30, null, null,
				null);
	}

}
