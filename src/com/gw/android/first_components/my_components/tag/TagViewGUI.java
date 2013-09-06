package com.gw.android.first_components.my_components.tag;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.gw.android.R;
import com.gw.android.components.connection_manager.AsyncRequestHandler;
import com.gw.android.components.request.Request;
import com.gw.android.first_components.database.DaoMaster;
import com.gw.android.first_components.database.DaoSession;
import com.gw.android.first_components.database.DaoMaster.DevOpenHelper;
import com.gw.android.first_components.my_components.tag.TagDao.Properties;
import com.gw.android.first_components.my_fragment.CRComponent;
import com.gw.android.first_components.my_fragment.ComponentSimpleModel;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.TextView;

@SuppressLint("ValidFragment")
public class TagViewGUI extends CRComponent {

	private Tag tag = new Tag();

	private TagDao tagDao;
	private DaoSession daoSession;
	private TextView tags;

	private Long idTarget;
	private String stringList;
	private String urlFinalJSON = "?_format=json";

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

		View view = inflater.inflate(R.layout.tag_view, container, false);
		tags = (TextView) view.findViewById(R.id.tags);

		AsyncRequestHandler mHandler = new AsyncRequestHandler() {
			@Override
			public void onSuccess(String response, Request request) {
				parseTagsJSON(response);
				Log.i("Onsucces TAGsParser ", " id=");
			}
		};
		setComponentRequestCallback(mHandler);

		// busca tags para component pela primeira vez
		// stringList = tag.getTag();
		stringList = "nadaAinda";

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

	@Override
	protected void onBind() {
		getTagsRequest();
		Log.i("ONBIND ", " after");
	}

	private String getUrl() {
		SharedPreferences testPrefs = getActivity().getApplication()
				.getSharedPreferences("test_prefs", Context.MODE_PRIVATE);
		return testPrefs.getString("base_url", "");
	}

	public void parseTagsJSON(String r) {

		try {
			JSONObject tagsObject;
			tagsObject = new JSONObject(r);

			JSONArray nameArray = tagsObject.names();
			JSONArray valArray = tagsObject.toJSONArray(nameArray);
			JSONArray arrayResults = valArray.getJSONArray(0);

			for (int j = 0; j < arrayResults.length(); j++) {
				JSONObject oneTag = arrayResults.getJSONObject(j);
				String tagName = oneTag.get("name").toString();
				Long idServ = Long.parseLong(oneTag.get("id").toString());
				Log.i("Parseando tags", " text= " + idServ + tagName);
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void getTagsRequest() {
		Request request = new Request(null, getUrl() + "/tags/"
				+ getCollabletId() + "/photo/" + idTarget + urlFinalJSON,
				"get", null);
		makeRequest(request);
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
		initTagDao();
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
			initTagDao();
			tagDao.insert(tag);
			closeDao();
		}

		return !achou;
	}

	public void initTagDao() {
		DevOpenHelper helper = new DaoMaster.DevOpenHelper(getActivity(),
				"tags-db", null);
		SQLiteDatabase db = helper.getWritableDatabase();
		DaoMaster daoMaster = new DaoMaster(db);
		daoSession = daoMaster.newSession();
		tagDao = daoSession.getTagDao();
	}

	public void initTagDao(Activity a) {
		DevOpenHelper helper = new DaoMaster.DevOpenHelper(a, "tags-db", null);
		SQLiteDatabase db = helper.getWritableDatabase();
		DaoMaster daoMaster = new DaoMaster(db);
		daoSession = daoMaster.newSession();
		tagDao = daoSession.getTagDao();
	}

	public void closeDao() {
		tagDao.getDatabase().close();
	}

	public List<ComponentSimpleModel> getListSimple(Long target, Activity a) {
		ArrayList<ComponentSimpleModel> list = new ArrayList<ComponentSimpleModel>();

		initTagDao(a);
		List<Tag> lista = tagDao.queryBuilder()
				.where(Properties.TargetId.eq(target)).build().list();

		for (int i = 0; i < lista.size(); i++) {
			list.add(lista.get(i));
		}

		tagDao.getDatabase().close();

		return list;
	}

}
