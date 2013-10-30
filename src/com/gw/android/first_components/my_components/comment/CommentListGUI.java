package com.gw.android.first_components.my_components.comment;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;

import com.gw.android.R;
import com.gw.android.first_components.database.DaoMaster;
import com.gw.android.first_components.database.DaoSession;
import com.gw.android.first_components.database.DaoMaster.DevOpenHelper;
import com.gw.android.first_components.my_components.comment.CommentDao.Properties;
import com.gw.android.first_components.my_fragment.CRComponent;
import com.gw.android.first_components.my_fragment.ComponentSimpleModel;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

@SuppressLint("ValidFragment")
public class CommentListGUI extends CRComponent {

	private CommentDao commentDao;
	private DaoSession daoSession;
	// private Comentario comentario = new Comentario();

	// private int nInstance = 0;

	// TimeBG t = new TimeBG();

	private Button button;
	private LayoutInflater li;
	private ViewGroup cont;
	private EditText edit;
	// private MyComponent target;
	private Long idTarget = Long.valueOf(1);
	Bundle extras;

	public CommentListGUI(Long idTarget) {
		// this.idTarget = idTarget;
	}

	public CommentListGUI() {

	}

	private void refreshComents() {
		ViewGroup layoutComent = (ViewGroup) cont.findViewById(0);
		layoutComent.removeAllViews();

		initCommentDao();
		List<Comment> lista = commentDao.queryBuilder()
				.where(Properties.TargetId.eq(idTarget)).build().list();
		commentDao.getDatabase().close();

		for (int i = 0; i < lista.size(); i++) {
			View view = li.inflate(R.layout.comment_view, null);
			Comment comm = (Comment) lista.get(i);

			view.findViewById(R.id.button_apaga).setTag("" + comm.getId());

			((TextView) view.findViewById(R.id.body)).setText(""
					+ comm.getText());

			DateFormat df = DateFormat.getDateTimeInstance(DateFormat.SHORT,
					DateFormat.SHORT);
			((TextView) view.findViewById(R.id.date)).setText("Enviado em "
					+ df.format(comm.getDate()));

			((ImageButton) view.findViewById(R.id.button_apaga))
					.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							Long id = Long.valueOf(v.getTag().toString());
							Comment c = findCommentById(id);
							if (c != null) {
								initCommentDao();
								commentDao.delete(c); // deleta do banco de
														// dados
								daoSession.delete(c); // deleta do cache
								commentDao.getDatabase().close();
								refreshComents();
							}
						}
					});

			layoutComent.addView(view);
		}
	}

	Comment findCommentById(Long id) {

		initCommentDao();
		Comment comment = (Comment) commentDao.queryBuilder()
				.where(Properties.Id.eq(id)).build().unique();
		commentDao.getDatabase().close();
		return comment;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		Log.i("en activitycreated", "criuou");
		refreshComents();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		li = inflater;
		cont = container;

		View view = inflater.inflate(R.layout.comment_send, container, false);
		button = (Button) view.findViewById(R.id.button_com);
		edit = (EditText) view.findViewById(R.id.edit_com);

		edit.setOnEditorActionListener(new OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_DONE) {
					submitComent();
					return true;
				}
				return false;
			}
		});

		idTarget = getComponentTarget().getCurrentInstanceId();

		// setMyMessenger(t);
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				submitComent();
			}
		});

		return view;
	}

	public void submitComent() {
		// fecha teclado
		InputMethodManager imm = (InputMethodManager) getActivity()
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(edit.getWindowToken(), 0);

		Long newId = ComponentSimpleModel.getUniqueId(getActivity());
		Comment comentario = new Comment();
		// Comment comentario = new Comment(newId, edit.getText().toString(),
		// new Date(), idTarget);
		edit.setText("");

		initCommentDao();
		commentDao.insert(comentario);
		commentDao.getDatabase().close();
		refreshComents();
	}

	public void initCommentDao() {
		DevOpenHelper helper = new DaoMaster.DevOpenHelper(getActivity(),
				"comments-db", null);
		SQLiteDatabase db = helper.getWritableDatabase();
		DaoMaster daoMaster = new DaoMaster(db);
		daoSession = daoMaster.newSession();
		commentDao = daoSession.getCommentDao();
	}

	public void initCommentDao(Activity a) {
		DevOpenHelper helper = new DaoMaster.DevOpenHelper(a, "comments-db",
				null);
		SQLiteDatabase db = helper.getWritableDatabase();
		DaoMaster daoMaster = new DaoMaster(db);
		daoSession = daoMaster.newSession();
		commentDao = daoSession.getCommentDao();
	}

	public void closeDao() {
		commentDao.getDatabase().close();
	}

	public List<Comment> getList(Long target, Activity a) {
		initCommentDao(a);
		List<Comment> lista = commentDao.queryBuilder()
				.where(Properties.TargetId.eq(target)).build().list();
		commentDao.getDatabase().close();

		return lista;
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

}
