package my_components;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

import com.example.my_fragment.ComponentSimpleModel;
import com.example.my_fragment.GUIComponent;
import com.example.firstcomponents.R;

import database.CommentDao;
import database.CommentDao.Properties;
import database.DaoMaster;
import database.DaoMaster.DevOpenHelper;
import database.DaoSession;

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

public class CommentListGUI extends GUIComponent {

	private CommentDao commentDao;
	private DaoSession daoSession;
	// private Comentario comentario = new Comentario();

	private int nInstance = 0;

	// TimeBG t = new TimeBG();

	private Button button;
	private LayoutInflater li;
	private ViewGroup cont;
	private EditText edit;
	// private MyComponent target;
	private Long idTarget = Long.valueOf(1);
	Bundle extras; 
	
	public CommentListGUI(Long idTarget) {
		//this.idTarget = idTarget;
	}
	
	public CommentListGUI() {
		
	}

	private void refreshComents() {
		ViewGroup layoutComent = (ViewGroup) cont
				.findViewById(0);
		layoutComent.removeAllViews();

		//abri DAO, fecha depois o bd
		initCommentDao();
		
		List<Comment> lista = commentDao.queryBuilder()
				.where(Properties.TargetId.eq(idTarget)).build().list();
		
		//fecha bd
		commentDao.getDatabase().close();
		
		for (int i = 0; i < lista.size(); i++) {
			View view = li.inflate(R.layout.single_coment, null);
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
								
								//
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
		Comment comment = (Comment) commentDao.queryBuilder().where(Properties.Id.eq(id))
				.build().unique();
		commentDao.getDatabase().close();
		return comment;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		Log.i("en activitycreated","criuou");
		refreshComents();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		li = inflater;
		cont = container;

		

		View view = inflater.inflate(R.layout.coment, container, false);
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

		idTarget = getComponentTarget().getCurrent();

		
		
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
		Comment comentario = new Comment(newId, edit.getText().toString(),
				new Date(), idTarget);
		//comentario.setInstanceId(getId() + "-" + nInstance);
		//nInstance++;
		edit.setText("");
		
		initCommentDao();
		//comentario.setTargetId(idTarget);
		commentDao.insert(comentario);
		commentDao.getDatabase().close();
		refreshComents();
	}

	public void initCommentDao() {
		
		Log.i("en initi","aquiii");
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
	
public void initCommentDao(Activity a) {
		
		Log.i("en initi","aquiii");
		// As we are in development we will use the DevOpenHelper which drops
		// the database on a schema update
		DevOpenHelper helper = new DaoMaster.DevOpenHelper(a,
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
	
	public void closeDao(){
		commentDao.getDatabase().close();
	}

	public List<Comment> getList(Long target,Activity a){
		
		initCommentDao(a);
		
		List<Comment> lista = commentDao.queryBuilder()
				.where(Properties.TargetId.eq(target)).build().list();
		
		//fecha bd
		commentDao.getDatabase().close();
		
		return lista;
	}
	
	
	
	
}
