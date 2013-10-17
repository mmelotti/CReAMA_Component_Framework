package com.gw.android.first_components.my_components.comment;

import java.util.ArrayList;
import java.util.Date;
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
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
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
	private List<Comment> lista = new ArrayList<Comment>();
	private ListView listview;
	private Long idTarget;
	Comment comment;
	private String urlFinalJSON = "?_format=json";

	public CommentViewGUI() {
		super();
		preDefined();
	}

	public CommentViewGUI(Long target) {
		super();
		preDefined();
		idTarget = target;
	}

	public CommentViewGUI(Long target, boolean request) {
		super();
		preDefined();
		idTarget = target;
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
		Comment comment = (Comment) commentDao.queryBuilder().where(Properties.Id.eq(id)).build().unique();
		closeDao();
		return comment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		setControlActivity((CRActivity) getActivity());
		View view = inflater.inflate(R.layout.comment_list, container, false);
	
		listview = (ListView) view.findViewById(R.id.listView);	
		listview.setOnTouchListener(new ListView.OnTouchListener() {
	        @Override
	        public boolean onTouch(View v, MotionEvent event) {
	            switch (event.getAction()) {
	            case MotionEvent.ACTION_DOWN:
	                // Disallow ScrollView to intercept touch events.
	                v.getParent().requestDisallowInterceptTouchEvent(true);
	                break;

	            case MotionEvent.ACTION_UP:
	                // Allow ScrollView to intercept touch events.
	                v.getParent().requestDisallowInterceptTouchEvent(false);
	                break;
	            }

	            // Handle ListView touch events.
	            v.onTouchEvent(event);
	            return true;
	        }
	    });
		
		AsyncRequestHandler mHandler = new AsyncRequestHandler() {
			@Override
			public void onSuccess(String response, Request request) {
				parseCommentsJSON(response);
				Log.i("Onsucces e Parser coments ", " id=");
			}

			@Override
			public void onFailure(Throwable arg0, String arg1, Request request) {
				initCommentDao();
				lista = commentDao.queryBuilder()
						.where(Properties.TargetId.eq((idTarget))).list();
				closeDao();

				if (!lista.isEmpty()) 
					listview.setAdapter(new CommentAdapter(getActivity(), lista));

				Log.i("FALHA COMMENTS ", " id=");
			}
		};

		setComponentRequestCallback(mHandler);
		return view;
	}

	private String getUrl() {
		SharedPreferences testPrefs = getActivity().getApplication()
				.getSharedPreferences("test_prefs", Context.MODE_PRIVATE);
		return testPrefs.getString("base_url", "");
	}

	@Override
	protected void onBind() {
		getCommentsRequest();
		Log.i("ONBIND ", " after");
	}

	public void getCommentsRequest() {
		Request request = new Request(null, getUrl() + "/comments/"
				+ getCollabletId() + "/photo/" + idTarget + urlFinalJSON,
				"get", null);
		makeRequest(request);
	}

	public void deleteFromTarget() {
		initCommentDao();
		lista = commentDao.queryBuilder()
				.where(Properties.TargetId.eq((idTarget))).list();
		for(Comment c:lista){
			deleteOne(c);
		}
		closeDao();
	}

	public void parseCommentsJSON(String r) {
		// got the right comments, we can remove the older ones
		deleteFromTarget();
		lista.clear();
		
		try {
			JSONObject commentsObject;
			commentsObject = new JSONObject(r);

			JSONArray nameArray = commentsObject.names();
			JSONArray valArray = commentsObject.toJSONArray(nameArray);
			JSONArray arrayResults = valArray.getJSONArray(0);
			Log.i("Parseando comentario antes for", " foto= ");
			for (int j = 0; j < arrayResults.length(); j++) {
				JSONObject oneComment = arrayResults.getJSONObject(j);
				JSONObject userObject = oneComment.getJSONObject("user");
				String userName = userObject.get("name").toString();
				Long idServ = Long.parseLong(oneComment.get("id").toString());
				String text = oneComment.get("text").toString();
				
				// updating comments, adding on DB
				Long newI = ComponentSimpleModel.getUniqueId(getActivity());
				Comment comment = new Comment(newI, idTarget, idServ, text, new Date());
				initCommentDao();
				commentDao.insert(comment);
				closeDao();

				lista.add(comment);
				Log.i("Parseando comentario", "text = " + text + idServ + userName);
			}
			listview.setAdapter(new CommentAdapter(getActivity(), lista));
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

	public void initCommentDao(Activity a) {
		DevOpenHelper helper = new DaoMaster.DevOpenHelper(a, "comments-db", null);
		SQLiteDatabase db = helper.getWritableDatabase();
		DaoMaster daoMaster = new DaoMaster(db);
		daoSession = daoMaster.newSession();
		commentDao = daoSession.getCommentDao();
	}

}