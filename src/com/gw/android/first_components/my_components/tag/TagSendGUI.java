package com.gw.android.first_components.my_components.tag;

import java.util.List;



import com.gw.android.R;
import com.gw.android.first_components.database.DaoMaster;
import com.gw.android.first_components.database.DaoSession;
import com.gw.android.first_components.database.DaoMaster.DevOpenHelper;
import com.gw.android.first_components.my_components.tag.TagDao.Properties;
import com.gw.android.first_components.my_fragment.CRComponent;
import com.gw.android.first_components.my_fragment.ComponentSimpleModel;


import android.annotation.SuppressLint;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

@SuppressLint("ValidFragment")
public class TagSendGUI extends CRComponent {

	private Tag tag = new Tag();

	private TagDao tagDao;
	private DaoSession daoSession;

	private Button button;
	private EditText edit;

	private Long idTarget;
	private String stringList;

	@SuppressLint("ValidFragment")
	public TagSendGUI(Long target) {
		idTarget = target;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.tag_send, container, false);
		button = (Button) view.findViewById(R.id.button_tag);
		edit = (EditText) view.findViewById(R.id.edit_tag);

		

		// busca tags para component pela primeira vez
		stringList = getAllStrings(idTarget);

		// sublinhado
		SpannableString spanString = new SpannableString(stringList);
		spanString.setSpan(new UnderlineSpan(), 0, spanString.length(), 0);

		// setMyMessenger(t);
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				tag.setTag(edit.getText().toString());
				tag.setTargetId(idTarget);

				if (addOneTag(tag)) {
					reloadActivity();
				}
			}

		});

		return view;
	}

	public String getAllStrings(Long id) {
		String ret = "";
		List<Tag> lista = getAllFromTarget(id);
		for (Tag t : lista)
			ret += t.getTag() + " ";

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
			if (t.getTag().equals(tag.getTag())) {
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
