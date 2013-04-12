package my_components.tag;


import java.util.List;

import my_components.tag.TagDao.Properties;




import com.example.my_fragment.ComponentSimpleModel;
import com.example.my_fragment.GUIComponent;

import com.example.firstcomponents.R;

import database.DaoMaster;
import database.DaoSession;
import database.DatabaseHandler;
import database.DaoMaster.DevOpenHelper;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class TagGUI extends GUIComponent {

	
	
	private Long newTarget;
	private Tag tag = new Tag();

	private int nInstance = 0;
	private TagDao tagDao;
	private DaoSession daoSession;

	// TimeBG t = new TimeBG();

	private Button button;
	private EditText edit;
	private TextView tags;
	private Bundle extras;
	private Long idTarget = new Long(1);
	// private MyComponent target;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		

		View view = inflater.inflate(R.layout.tagone, container, false);
		button = (Button) view.findViewById(R.id.button_tag);
		edit = (EditText) view.findViewById(R.id.edit_tag);

		tags = (TextView) view.findViewById(R.id.tags);

		extras = getActivity().getIntent().getExtras();
		if (extras != null) {
			// recebendo target como parametro
			//idTarget = extras.getLong("nImagem");
			idTarget = getComponentTarget().getCurrentInstanceId();
		}

		// busca tags para component pela primeira vez
		String tempString= "";
		
		//sublinhado
		SpannableString spanString = new SpannableString(tempString);
		  spanString.setSpan(new UnderlineSpan(), 0, spanString.length(), 0);
		tags.setText(spanString);
		//tags.setTe
		
		//set listener para mudar de tela, tag by tag
		tags.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Intent trocatela = new Intent(getActivity(),
						TagActivity.class);
				//trocatela.putExtra("nImagem", proximaImagem());
				getActivity().startActivity(trocatela);
				
				Log.i("clicou texto!","ok!!");
				
		}
	});
		
		
		// setMyMessenger(t);
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Activity activity = getActivity();

				// String r = sendMessage("hora");

				
				tag.setTag(edit.getText().toString());

				// teste
				if (activity != null) {
					// Toast.makeText(activity, comentario.getText(),
					// Toast.LENGTH_LONG).show();
					// Toast.makeText(activity, db.checkSomething(),
					// Toast.LENGTH_LONG).show();
				}

				
				tag.setTargetId(idTarget);
				
				addOneTag(tag);

				
				
				
				String tempString = ""; //fazer aqui lista
				
				//sublinhado
				SpannableString spanString = new SpannableString(tempString);
				  spanString.setSpan(new UnderlineSpan(), 0, spanString.length(), 0);
				tags.setText(spanString);
				// comentarios.setText(db.checkSomething(1));

				tag = new Tag();
				// inicia outro

			}

		});

		return view;
	}

	
	
	public void addOneTag(Tag tag){
		boolean achou=false;
		

		
		
		initRatingDao();
		List<Tag> lista = tagDao.queryBuilder()
				.where(Properties.Tag.eq(tag.getTag())).build().list();
		closeDao();
		for(Tag t:lista){
			if(t.getTargetId()==tag.getTargetId()){
				achou=true;
				
				break;
			}
		}
		
		Long newId = ComponentSimpleModel.getUniqueId(getActivity());
		tag.setId(newId);
		initRatingDao();
		tagDao.insert(tag);

		closeDao();
		
		
		
		
		
	}
	
	
	public void initRatingDao() {
		DevOpenHelper helper = new DaoMaster.DevOpenHelper(getActivity(),
				"tags-db", null);
		SQLiteDatabase db = helper.getWritableDatabase();
		DaoMaster daoMaster = new DaoMaster(db);
		daoSession = daoMaster.newSession();
		tagDao = daoSession.getTagDao();
	}

	public void closeDao() {
		tagDao.getDatabase().close();
	}
	
}
