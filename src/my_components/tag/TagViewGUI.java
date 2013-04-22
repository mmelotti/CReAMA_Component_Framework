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

import android.annotation.SuppressLint;
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

@SuppressLint("ValidFragment")
public class TagViewGUI extends GUIComponent {

	private Tag tag = new Tag();

	private TagDao tagDao;
	private DaoSession daoSession;

	// TimeBG t = new TimeBG();

	private Button button;
	private EditText edit;
	private TextView tags;
	private Bundle extras;
	private Long idTarget;
	private String stringList;

	// private MyComponent target;

	@SuppressLint("ValidFragment")
	public TagViewGUI(Long target) {
		idTarget = target;
	}
	
	@SuppressLint("ValidFragment")
	public TagViewGUI(ComponentSimpleModel c) {
		tag = (Tag) c;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.tagview, container, false);

		tags = (TextView) view.findViewById(R.id.tags);

		extras = getActivity().getIntent().getExtras();
		if (extras != null) {
			// recebendo target como parametro
			// idTarget = extras.getLong("nImagem");
			idTarget = getComponentTarget().getCurrentInstanceId();
		}

		// busca tags para component pela primeira vez
		stringList = getAllStrings(idTarget);

		// sublinhado
		SpannableString spanString = new SpannableString(stringList);
		spanString.setSpan(new UnderlineSpan(), 0, spanString.length(), 0);
		tags.setText(spanString);
		// tags.setTe

		// set listener para mudar de tela, tag by tag
		tags.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent trocatela = new Intent(getActivity(), TagActivity.class);
				// trocatela.putExtra("nImagem", proximaImagem());
				getActivity().startActivity(trocatela);

				Log.i("clicou texto!", "ok!!");

			}
		});

		// setMyMessenger(t);

		return view;
	}

	public String getAllStrings(Long id) {
		String ret = "";
		List<Tag> lista = getAllFromTarget(id);
		for (Tag t : lista) {
			ret += t.getTag() + " ";
		}

		return ret;
	}

	public List<Tag> getAllFromTarget(Long id) {
		initRatingDao();
		List<Tag> lista = tagDao.queryBuilder()
				.where(Properties.TargetId.eq(id)).build().list();
		closeDao();

		return lista;
	}

	public boolean addOneTag(Tag tag) {
		boolean achou = false;

		List<Tag> lista = getAllFromTarget(tag.getTargetId());
		for (Tag t : lista) {
			if (t.getTargetId() == tag.getTargetId()) {
				achou = true;
				break;
			}
		}

		if (!achou) {
			Long newId = ComponentSimpleModel.getUniqueId(getActivity());
			tag.setId(newId);
			initRatingDao();
			tagDao.insert(tag);
			closeDao();
		}

		return !achou;
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
