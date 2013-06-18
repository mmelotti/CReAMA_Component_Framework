package com.gw.android.first_components.my_components.comment;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

import com.gw.android.R;
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

	private LayoutInflater li;
	private Comment comment;

	public CommentViewGUI() {
		super();
		preDefined();
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
		view = refreshComment();
		return view;
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
	
	public List<ComponentSimpleModel> getListSimple(Long target,Activity a){
		ArrayList<ComponentSimpleModel> list= new ArrayList<ComponentSimpleModel>();

		initCommentDao(a);
		List<Comment> lista = commentDao.queryBuilder()
				.where(Properties.TargetId.eq(target)).build().list();
		
		for(int i=0;i<lista.size();i++){
			list.add(lista.get(i));
		}
		
		commentDao.getDatabase().close();
		
		return list;
	}
	
	public void initCommentDao(Activity a) {
		//Log.i("en initi","aquiii");
		DevOpenHelper helper = new DaoMaster.DevOpenHelper(a,
				"comments-db", null);
		SQLiteDatabase db = helper.getWritableDatabase();
		DaoMaster daoMaster = new DaoMaster(db);
		daoSession = daoMaster.newSession();
		commentDao = daoSession.getCommentDao();
	}

}