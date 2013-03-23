package my_components;

import java.util.List;

import com.example.firstcomponents.R;
import com.example.my_fragment.ComponentSimpleModel;
import com.example.my_fragment.GUIComponent;
import com.example.my_fragment.MyActivity;
import com.example.my_fragment.MyComponent;

import database.DaoMaster;
import database.DaoSession;
import database.CommentDao.Properties;
import database.DaoMaster.DevOpenHelper;
import database.RatingDao;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

public class RatingGUI extends GUIComponent implements
		RatingBar.OnRatingBarChangeListener {

	RatingBar ratingClickable; // declare RatingBar object
	Rating rating;
	TextView ratingText;// declare TextView Object
	private Button button;
	private boolean calculouMedia = false;

	private RatingDao ratingDao;
	private DaoSession daoSession;
	private Long newTarget = 1L;
	private MyActivity mya;

	private float average = 0;
	private int tamanho = 0;
	private float soma = 0;
	
	LinearLayout ratingBody;
	
	public RatingGUI() {

	}

	public RatingGUI(Long target) {
		newTarget = target;
	}

	public RatingGUI(MyComponent target) {
		setComponentTarget(target);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.ratingone, container, false);

		ratingText = (TextView) view.findViewById(R.id.ratingText);
		ratingText.setTextColor(getActivity().getResources().getColor(
				R.color.mycolor1));
		ratingText.setText("Avalie!");
		ratingText.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				int visibility = ratingBody.getVisibility() == View.GONE ? View.VISIBLE : View.GONE;
				ratingBody.setVisibility(visibility);
			}
		});
		
		ratingBody = (LinearLayout) view.findViewById(R.id.ratingBody);
		
		ratingClickable = (RatingBar) view.findViewById(R.id.rating);

		button = (Button) view.findViewById(R.id.button_rating);
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Long newId = ComponentSimpleModel.getUniqueId(getActivity());

				
				rating = new Rating(newId, newTarget, ratingClickable.getRating());

				initRatingDao();
				ratingDao.insert(rating);

				if (!calculouMedia) {
					getAverage();
				} else {
					tamanho++;
					soma = soma + ratingClickable.getRating();
					average = soma / tamanho;
				}

				ratingText.setText("Média de avaliações: " + average);
				closeDao();
			}
		});

		if (ratingClickable != null) {
			ratingClickable.setOnRatingBarChangeListener(this);
		}

		return view;
	}

	// implement abstract method onRatingChanged
	public void onRatingChanged(RatingBar ratingBar, float rating,
			boolean fromUser) {
		ratingText.setText("" + this.ratingClickable.getRating());
	}

	public void initRatingDao() {
		DevOpenHelper helper = new DaoMaster.DevOpenHelper(getActivity(),
				"ratings-db", null);
		SQLiteDatabase db = helper.getWritableDatabase();
		DaoMaster daoMaster = new DaoMaster(db);
		daoSession = daoMaster.newSession();
		ratingDao = daoSession.getRatingDao();
	}

	public void closeDao() {
		ratingDao.getDatabase().close();
	}

	public void getAverage() {

		List<Rating> lista = ratingDao.queryBuilder()
				.where(Properties.TargetId.eq(newTarget)).build().list();
		if (!lista.isEmpty()) {
			
			for (int i = 0; i < lista.size(); i++) {
				soma = soma + lista.get(i).getValue();
			}

			// atributos usados para otimizar calculo
			tamanho = lista.size();
			
			average = soma / tamanho;
			calculouMedia = true;
			Log.i("average", "baixo " + newTarget);
		}
		
	}

	public void setNewTargetId(Long t) {
		newTarget = t;
	}

	public void deleteOne(Rating r) {
		ratingDao.delete(r);
		daoSession.delete(r);
		mya.deletarAlgo(r.getId(), this);
	}

	@Override
	public void deleteAllFrom(Long target) {
		initRatingDao();
		List<Rating> lista = ratingDao.queryBuilder()
				.where(Properties.TargetId.eq(target)).build().list();

		for (int i = 0; i < lista.size(); i++) {
			deleteOne(lista.get(i));
		}

		closeDao();
	}

	public MyActivity getMya() {
		return mya;
	}

	public void setMya(MyActivity mya) {
		this.mya = mya;
	}

}