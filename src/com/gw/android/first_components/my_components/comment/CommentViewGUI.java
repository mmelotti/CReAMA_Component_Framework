package com.gw.android.first_components.my_components.comment;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

import com.gw.android.R;
import com.gw.android.components.connection_manager.AsyncRequestHandler;
import com.gw.android.components.request.Request;
import com.gw.android.first_components.database.DaoMaster;
import com.gw.android.first_components.database.DaoSession;
import com.gw.android.first_components.database.DaoMaster.DevOpenHelper;
import com.gw.android.first_components.my_components.comment.CommentDao.Properties;
import com.gw.android.first_components.my_fragment.CRActivity;
import com.gw.android.first_components.my_fragment.CRComponent;
import com.gw.android.first_components.my_fragment.ComponentSimpleModel;

@SuppressLint("ValidFragment")
public class CommentViewGUI extends CRComponent {

	private CommentDao commentDao;
	private DaoSession daoSession;
	private boolean conectado=true, teste=true;

	//private String urlTestComment = "http://200.137.66.94:8080/GW-Application-Arquigrafia/comment.json";
	private String urlTestComment="http://valinhos.ime.usp.br:51080/comments/1/photo/1151?_format=json";
	private LayoutInflater li;
	private Comment comment;
	private Long idTarget;

	private String urlFinalJSON="?_format=json";
	
	public CommentViewGUI() {
		super();
		preDefined();
	}
	
	public CommentViewGUI(Long target) {
		super();
		preDefined();
		idTarget=target;
	}

	public CommentViewGUI(ComponentSimpleModel c) {
		comment = (Comment) c;
		preDefined();
	}

	public CommentViewGUI(Comment c) {
		comment = c;
		preDefined();
	}

	public void preDefined() {
		setGeneralGUIId(1);
	}

	private Comment findCommentById(Long id) {
		initCommentDao();
		Comment comment = (Comment) commentDao.queryBuilder()
				.where(Properties.Id.eq(id)).build().unique();
		closeDao();
		return comment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		setControlActivity((CRActivity) getActivity());

		li = inflater;
		View view = inflater.inflate(R.layout.comment_view, container, false);
		
		AsyncRequestHandler mHandler = new AsyncRequestHandler() {
			@Override
			public void onSuccess(String response, Request request) {
				parseCommentsJSON(response);

				Log.i("Onsucces e Parser coments ", " id=");

			}
		};
		setComponentRequestCallback(mHandler);
		
		if(!teste){
			view = refreshComment();
		}
		
		
		
		return view;
	}

	private String getUrl() {
		SharedPreferences testPrefs = getActivity()
				.getApplication()
				.getSharedPreferences("test_prefs", Context.MODE_PRIVATE);
		return testPrefs.getString("base_url", "");
	}
	
	private View refreshComment() {
		View view = li.inflate(R.layout.comment_view, null);
		view.findViewById(R.id.button_apaga).setTag("" + comment.getId());
		((TextView) view.findViewById(R.id.body)).setText(""
				+ comment.getText());
		DateFormat df = DateFormat.getDateTimeInstance(DateFormat.SHORT,
				DateFormat.SHORT);
		((TextView) view.findViewById(R.id.date)).setText("Enviado em "
				+ df.format(comment.getDate()));
		((ImageButton) view.findViewById(R.id.button_apaga))
				.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						Long id = Long.valueOf(v.getTag().toString());
						Comment c = findCommentById(id);
						if (c != null) {
							initCommentDao();
							deleteOne(c);
							closeDao();
							reloadActivity();
						}
					}
				});

		return view;
	}

	@Override
	protected void onBind() {
		getCommentsRequest();
		Log.i("ONBIND ", " after");
	}

	public void getCommentsRequest() {
		Request request = new Request(null, getUrl()+"/comments/"+getCollabletId()+"/photo/"+idTarget+urlFinalJSON, "get", null);
		makeRequest(request);
	}

	public void parseCommentsJSON(String r) {
		Log.i("Parseando commentarios", " inicio");
		JSONObject object;
		try {
			JSONObject commentsObject ;
			commentsObject = new JSONObject(r);
	
			JSONArray nameArray = commentsObject.names();
			JSONArray valArray = commentsObject.toJSONArray(nameArray);
			JSONArray arrayResults = valArray.getJSONArray(0);
			Log.i("Parseando comentario antes for", " foto= " );
			for (int j = 0; j < arrayResults.length(); j++) {
				JSONObject oneComment = arrayResults.getJSONObject(j);
				JSONObject userObject = oneComment.getJSONObject("user");
				String userName = userObject.get("name").toString();
				Long idServ = Long.parseLong(oneComment.get("id").toString());

				String text = oneComment.get("text").toString();
				Log.i("Parseando comentario", " text= " + text + idServ+userName);
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	void initCommentDao() {
		DevOpenHelper helper = new DaoMaster.DevOpenHelper(getActivity(),
				"comments-db", null);
		SQLiteDatabase db = helper.getWritableDatabase();
		DaoMaster daoMaster = new DaoMaster(db);
		daoSession = daoMaster.newSession();
		commentDao = daoSession.getCommentDao();
	}

	public void deleteOne(Comment c) {
		commentDao.delete(c);
		daoSession.delete(c);
		getControlActivity().deletarAlgo(c.getId(), this);
	}

	@Override
	public void deleteAllFrom(Long target) {
		initCommentDao();
		List<Comment> lista = commentDao.queryBuilder()
				.where(Properties.TargetId.eq(target)).build().list();
		for (int i = 0; i < lista.size(); i++) {
			deleteOne(lista.get(i));
		}
		closeDao();

	}

	public void closeDao() {
		commentDao.getDatabase().close();
	}

	public List<ComponentSimpleModel> getListSimple(Long target, Activity a) {
		ArrayList<ComponentSimpleModel> list = new ArrayList<ComponentSimpleModel>();

		initCommentDao(a);
		List<Comment> lista = commentDao.queryBuilder()
				.where(Properties.TargetId.eq(target)).build().list();

		for (int i = 0; i < lista.size(); i++) {
			list.add(lista.get(i));
		}

		commentDao.getDatabase().close();

		return list;
	}

	public void initCommentDao(Activity a) {
		// Log.i("en initi","aquiii");
		DevOpenHelper helper = new DaoMaster.DevOpenHelper(a, "comments-db",
				null);
		SQLiteDatabase db = helper.getWritableDatabase();
		DaoMaster daoMaster = new DaoMaster(db);
		daoSession = daoMaster.newSession();
		commentDao = daoSession.getCommentDao();
	}

}