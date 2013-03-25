package my_components;

import java.text.DateFormat;
import java.util.List;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.firstcomponents.R;
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
	private MyActivity controlActivity;
	private LayoutInflater li;
	private Comment comment;

	public OneCommentGUI() {
	}

	public OneCommentGUI(Comment c) {
		comment = c;
	}

	Comment findCommentById(Long id) {
		initCommentDao();
		Comment comment = (Comment) commentDao.queryBuilder()
				.where(Properties.Id.eq(id)).build().unique();
		closeDao();
		return comment;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
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
	
	
	public void deleteOne(Comment c){
		commentDao.delete(c);
		daoSession.delete(c);
		controlActivity.deletarAlgo(c.getId(), this);
	}
	
	@Override
	public void deleteAllFrom(Long target){
		initCommentDao();		
		List<Comment> lista = commentDao.queryBuilder()
				.where(Properties.TargetId.eq(target)).build().list();		
		for(int i=0;i<lista.size();i++){
			deleteOne(lista.get(i));
		}		
		closeDao();
		
	}

	public void closeDao(){
		commentDao.getDatabase().close();
	}
	
	public MyActivity getControlActivity() {
		return controlActivity;
	}

	public void setControlActivity(MyActivity mya) {
		this.controlActivity = mya;
	}

}