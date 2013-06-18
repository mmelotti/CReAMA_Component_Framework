package com.gw.android.first_components.my_components.comment;

import java.util.Date;

import com.gw.android.R;
import com.gw.android.first_components.database.DaoMaster;
import com.gw.android.first_components.database.DaoSession;
import com.gw.android.first_components.database.DaoMaster.DevOpenHelper;
import com.gw.android.first_components.my_fragment.CRActivity;
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
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

@SuppressLint("ValidFragment")
public class CommentSendGUI extends CRComponent {

	private CommentDao commentDao;
	private DaoSession daoSession;
	private Button button;
	private EditText edit;
	private Long idTarget = Long.valueOf(-1);
	Bundle extras;

	public CommentSendGUI(Long idTarget) {
		this.idTarget = idTarget;
		
		preDefined();
	}

	public CommentSendGUI() {
		preDefined();
	}

	public void preDefined(){
		setGeneralGUIId(4);
	
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		Log.i("en activitycreated", "criuou");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.comment_send, container, false);
		button = (Button) view.findViewById(R.id.button_com);
		edit = (EditText) view.findViewById(R.id.edit_com);
		edit.setOnEditorActionListener(new OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_DONE) {
					submitComent();
					reloadActivity();
					return true;
				}
				return false;
			}
		});
		if(idTarget==-1L){
			idTarget = getComponentTarget().getCurrentInstanceId();
		}
		
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				submitComent();
				reloadActivity();
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
		comentario.setDate(new Date());
		comentario.setId(newId);
		comentario.setText(edit.getText().toString());
		comentario.setTargetId(idTarget);
	
		edit.setText("");
		initCommentDao();
		commentDao.insert(comentario);
		closeDao();
		((CRActivity) getActivity()).inserirAlgo(newId, this);
		//getControlActivity().inserirAlgo(newId, this);
	}

	public void initCommentDao() {
		//Log.i("en initi", "aquiii");
		DevOpenHelper helper = new DaoMaster.DevOpenHelper(getActivity(),
				"comments-db", null);
		SQLiteDatabase db = helper.getWritableDatabase();
		DaoMaster daoMaster = new DaoMaster(db);
		daoSession = daoMaster.newSession();
		commentDao = daoSession.getCommentDao();
	}

	public void initCommentDao(Activity a) {
		//Log.i("en initi", "aquiii");
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

}