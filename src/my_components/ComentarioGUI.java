package my_components;

import com.example.my_fragment.GUIComponent;
import com.example.firstcomponents.R;

import database.DatabaseHandler;

import android.app.Activity;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;



public class ComentarioGUI extends GUIComponent {

	DatabaseHandler db;
	private Comentario comentario = new Comentario();

	private int nInstance = 0;

	// TimeBG t = new TimeBG();

	private Button button;
	private EditText edit;
	private TextView comentarios;
	// private MyComponent target;
	private int idTarget = 1;
	Bundle extras;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		this.setId(5);

		View view = inflater.inflate(R.layout.coment, container, false);
		button = (Button) view.findViewById(R.id.button_com);
		edit = (EditText) view.findViewById(R.id.edit_com);

		comentarios = (TextView) view.findViewById(R.id.comentarios);

		extras = getActivity().getIntent().getExtras();

		
		if (extras != null) {
			// recebendo target como parametro
			idTarget = extras.getInt("nImagem");
		}

		// busca comentarios para component pela primeira vez
		comentarios.setText(db.getCommentsFrom(idTarget));

		// setMyMessenger(t);
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Activity activity = getActivity();

				// String r = sendMessage("hora");

				comentario.setInstanceId(getId() + "-" + nInstance);
				nInstance++;
				comentario.setText(edit.getText().toString());
				edit.setText("");

				// teste
				if (activity != null) {
					// Toast.makeText(activity, comentario.getText(),
					// Toast.LENGTH_LONG).show();
					// Toast.makeText(activity, db.checkSomething(),
					// Toast.LENGTH_LONG).show();
				}

				comentario.save();
				comentario.setTargetId(idTarget);
				db.addComentario(comentario);

				comentarios.setText(db.getCommentsFrom(idTarget));

				comentario = new Comentario();
				// inicia outro
			}

		});

		return view;
	}

	public void setDb(DatabaseHandler db) {
		this.db = db;
	}

}
