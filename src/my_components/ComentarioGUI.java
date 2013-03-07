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
	private LayoutInflater li;
	private ViewGroup cont;
	private EditText edit;
	// private MyComponent target;
	private int idTarget = 1;
	Bundle extras;

	private void refreshComents() {
		ViewGroup layoutComent = (ViewGroup) cont
				.findViewById(R.id.comentariosRoot);
		layoutComent.removeAllViews();
		View view;
		String[] array = db.getCommentsFrom(idTarget);

		for (int i = 0; i < array.length; i++) {
			view = li.inflate(R.layout.single_coment, null);
			view.setTag("coment" + i);
			((TextView) view.findViewById(R.id.body)).setText(array[i]);

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

		extras = getActivity().getIntent().getExtras();

		if (extras != null) {
			// recebendo target como parametro
			idTarget = extras.getInt("nImagem");
		}

		// busca comentarios para component pela primeira vez
		
		// ta dando erro por enquanto, so da pra atualizar pelo botao... acho
		// que é porque dentro desse método ele ainda nao terminou de criar a
		// view, sei lá... depois acerto
		// refreshComents();

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

				refreshComents();

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
