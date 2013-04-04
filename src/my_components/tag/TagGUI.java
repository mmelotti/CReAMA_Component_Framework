
/*
package my_components.tag;


import com.example.my_fragment.GUIComponent;

import com.example.firstcomponents.R;

import database.DatabaseHandler;

import android.app.Activity;
import android.content.Intent;
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

	DatabaseHandler db;
	private Tag tag = new Tag();

	private int nInstance = 0;

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
		String tempString=db.getTagsFrom(idTarget);
		
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

				tag.setInstanceId(getId() + "-" + nInstance);
				nInstance++;
				tag.setTag(edit.getText().toString());

				// teste
				if (activity != null) {
					// Toast.makeText(activity, comentario.getText(),
					// Toast.LENGTH_LONG).show();
					// Toast.makeText(activity, db.checkSomething(),
					// Toast.LENGTH_LONG).show();
				}

				tag.save();
				tag.setTargetId(idTarget);
				
				db.addTag(tag);

				
				
				
				String tempString=db.getTagsFrom(idTarget);
				
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

	public void setDb(DatabaseHandler db) {
		this.db = db;

	}

}*/
