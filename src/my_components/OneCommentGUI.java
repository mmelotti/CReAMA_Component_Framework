package my_components;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
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

import com.example.firstcomponents.R;
import com.example.my_fragment.ComponentSimpleModel;
import com.example.my_fragment.GUIComponent;
import com.example.my_fragment.MyActivity;

import database.CommentDao;
import database.DaoMaster;
import database.DaoSession;
import database.CommentDao.Properties;
import database.DaoMaster.DevOpenHelper;

public class OneCommentGUI extends GUIComponent {

	private CommentDao commentDao;
	private DaoSession daoSession;
	private MyActivity mya;

	private LayoutInflater li;
	
	// private MyComponent target;
	private Long idTarget = Long.valueOf(1);
	Bundle extras;
	Comment comment;

	public OneCommentGUI() {

	}

	public OneCommentGUI(Comment c) {
		comment = c;
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

		// refreshComents();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		li = inflater;

		View view = inflater.inflate(R.layout.single_coment, container, false);

		view = refreshComment();

		return view;
	}

	private View refreshComment() {
		View view = li.inflate(R.layout.single_coment, null);

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
					 // deleta do cache
					
					//
					commentDao.getDatabase().close();
					reloadActivity();
				} 
			}
		});
		

		return view;
	}

	void initCommentDao() {
		// As we are in development we will use the DevOpenHelper which drops
		// the database on a schema update
		DevOpenHelper helper = new DaoMaster.DevOpenHelper(getActivity(),
				"comments-db", null);
		// Access the database using the helper
		SQLiteDatabase db = helper.getWritableDatabase();
		// Construct the DaoMaster which brokers DAOs for the Domain Objects
		DaoMaster daoMaster = new DaoMaster(db);
		// Create the session which is a container for the DAO layer and has a
		// cache which will return handles to the same object across multiple
		// queries
		daoSession = daoMaster.newSession();
		// Access the Comments DAO
		commentDao = daoSession.getCommentDao();
	}
	
	
	public void deleteOne(Comment c){
		commentDao.delete(c);
		daoSession.delete(c);
		mya.deletarAlgo(c.getId(), this);
	}
	
	@Override
	public void deleteAllFrom(Long target){
		initCommentDao();
		
		List<Comment> lista = commentDao.queryBuilder()
				.where(Properties.TargetId.eq(target)).build().list();
		
		for(int i=0;i<lista.size();i++){
			deleteOne(lista.get(i));
		}
		
		commentDao.getDatabase().close();
		
	}

	public MyActivity getMya() {
		return mya;
	}

	public void setMya(MyActivity mya) {
		this.mya = mya;
	}

}