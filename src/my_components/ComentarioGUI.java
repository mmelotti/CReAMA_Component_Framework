package my_components;

import java.util.Date;
import java.util.List;

import com.example.my_fragment.GUIComponent;
import com.example.firstcomponents.R;

import database.CommentDao;
import database.CommentDao.Properties;
import database.DaoMaster;
import database.DaoMaster.DevOpenHelper;
import database.DaoSession;
import database.DatabaseHandler;
import de.greenrobot.dao.Query;
import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

public class ComentarioGUI extends GUIComponent {

	DatabaseHandler db;
	private CommentDao commentDao;
	// private Comentario comentario = new Comentario();

	private int nInstance = 0;

	// TimeBG t = new TimeBG();

	private Button button;
	private LayoutInflater li;
	private ViewGroup cont;
	private EditText edit;
	// private MyComponent target;
	private Long idTarget = new Long(1);
	Bundle extras;

	private void refreshComents() {
		ViewGroup layoutComent = (ViewGroup) cont
				.findViewById(R.id.comentariosRoot);
		layoutComent.removeAllViews();
		View view;

		Query qr = commentDao.queryBuilder()
				.where(Properties.TargetId.eq(idTarget)).build();
		List lista = qr.list();

		for (int i = 0; i < lista.size(); i++) {
			view = li.inflate(R.layout.single_coment, null);
			view.setTag("coment" + i);
			((TextView) view.findViewById(R.id.body))
					.setText("" + ((Comment) lista.get(i)).getText());

			layoutComent.addView(view);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		li = inflater;
		cont = container;

		this.setId(5);

		View view = inflater.inflate(R.layout.coment, container, false);
		// lista = (ListView) view.findViewById(R.id.listView);
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

		extras = getActivity().getIntent().getExtras();

		if (extras != null) {
			// recebendo target como parametro
			idTarget = extras.getLong("nImagem");
		}

		// busca comentarios para component pela primeira vez

		// ta dando erro por enquanto, so da pra atualizar pelo botao... acho
		// que é porque dentro desse método ele ainda nao terminou de criar a
		// view, sei lá... depois acerto
		// refreshComents();
		
		initCommentDao();

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
		// String r = sendMessage("hora");

		Comment comentario = new Comment(null, edit.getText().toString(),
				new Date(), idTarget);
		comentario.setInstanceId(getId() + "-" + nInstance);
		nInstance++;
		edit.setText(""); 

		// comentario.save(); 
		comentario.setTargetId(idTarget);
		// db.addComentario(comentario);
		commentDao.insert(comentario);

		refreshComents();
		// inicia outro
	}

	public void setDb(DatabaseHandler db) {
		this.db = db;
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
		DaoSession daoSession = daoMaster.newSession();
		// Access the Notes DAO
		commentDao = daoSession.getCommentDao();
	}

}
