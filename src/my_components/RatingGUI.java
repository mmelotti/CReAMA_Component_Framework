package my_components;

import com.example.firstcomponents.R;
import com.example.my_fragment.GUIComponent;



import database.DatabaseHandler;


import android.graphics.Color;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

public class RatingGUI extends GUIComponent implements RatingBar.OnRatingBarChangeListener{
	
	RatingBar ratingClickable; // declare RatingBar object
	Rating rating = new Rating();
	TextView ratingText;// declare TextView Object
	private Button button;
	private Bundle extras;
	private Long idTarget;
	private DatabaseHandler db;
	
	 @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.ratingone, container, false);
		//setContentView(R.layout.ratingone);// set content from main.xml
		ratingText=(TextView) view.findViewById(R.id.ratingText);// create TextView object
		ratingText.setTextColor(getActivity().getResources().getColor(R.color.mycolor1));
		ratingText.setText("Avalie a imagem!");
		
		ratingClickable=(RatingBar) view.findViewById(R.id.rating);// create RatingBar object
		
		extras = getActivity().getIntent().getExtras();
		if (extras != null) {
			// recebendo target como parametro
			//idTarget = extras.getLong("nImagem");
			idTarget = getComponentTarget().getCurrent();
		}
		
		button = (Button) view.findViewById(R.id.button_rating);
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				rating.setQuantidade((int)ratingClickable.getRating());
				rating.save();
				rating.setTargetId(idTarget);
				db.addRating(rating);
				
				//ratingText.setTextColor(getActivity().getResources().getColor(R.color.mycolor1));
				ratingText.setText("Media de Avaliações: "+ db.getAverageRatingFrom(idTarget));
				
				rating = new Rating();				
			}
		});
		
		if(ratingClickable != null){
			ratingClickable.setOnRatingBarChangeListener(this);// select listener to be HelloAndroid (this) class	
		}

		return view;
	}
	
	// implement abstract method onRatingChanged
	public void onRatingChanged(RatingBar ratingBar,float rating, boolean fromUser){
		ratingText.setText(""+this.ratingClickable.getRating()); // display rating as number in TextView, use "this.rating" to not confuse with "float rating"
		this.rating.setQuantidade((int)ratingClickable.getRating());
	}
	
	public void setDb(DatabaseHandler db) {
		this.db = db;
	}
}