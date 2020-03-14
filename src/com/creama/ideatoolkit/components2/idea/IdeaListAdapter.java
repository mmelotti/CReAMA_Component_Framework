package com.creama.ideatoolkit.components2.idea;

import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.creama.interfaces.IListAdapter;
import com.gw.android.R;

public abstract class IdeaListAdapter extends ArrayAdapter<Idea> implements
		IListAdapter {

	private final Context context;
	private final List<Idea> ideas;
	boolean itemRemovido = false;

	public IdeaListAdapter(Context context, List<Idea> ideas) {
		super(context, R.layout.conecte_one_ideia_forlist_comp, ideas);
		this.context = context;
		this.ideas = ideas;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView;
		rowView = inflater.inflate(R.layout.conecte_one_ideia_forlist_comp,
				parent, false);
		Idea c = ideas.get(position);
		((TextView) rowView.findViewById(R.id.idea_titulo)).setText(c
				.getTitle());
		((TextView) rowView.findViewById(R.id.idea_titulo))
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {

						onClickListAdapterItem(v, ideas.get(position)
								.getServerId());

						/*
						 * Long id = Long.valueOf(v.getTag().toString());
						 * Comment c = findCommentById(id); if (c != null) {
						 * initCommentDao(); // deleteOne(c); closeDao();
						 * reloadActivity(); }
						 */
					}
				});

		if (position + 1 == ideas.size()) {
			// gambiarra pra alguns dispositivos... add void at the end
			((TextView) rowView.findViewById(R.id.idea_body)).setText(c
					.getText() + smallEmptyString());
		} else {
			((TextView) rowView.findViewById(R.id.idea_body)).setText(c
					.getText());
		}
		// ((TextView)
		// rowView.findViewById(R.id.date)).setText("Enviado em "
		// + df.format(c.getDate()));
		/*
		 * ((ImageButton) rowView.findViewById(R.id.button_apaga))
		 * .setOnClickListener(new OnClickListener() {
		 * 
		 * @Override public void onClick(View v) { /* Long id =
		 * Long.valueOf(v.getTag().toString()); Comment c = findCommentById(id);
		 * if (c != null) { initCommentDao(); // deleteOne(c); closeDao();
		 * reloadActivity(); }
		 * 
		 * 
		 * } });
		 */
		return rowView;
	}

	public void clearEmptyItem() {
		ideas.remove(0);
		notifyDataSetChanged();
		itemRemovido = true;
	}

	public String smallEmptyString() {
		return "\n\n\n\n\n\n\n\n\n\n\n\n\n";
	}

	public String createEmptyString() {
		String mark = ".";
		String space = "\n\u00A0\u00A0";
		int cont = 0;
		while (cont < 2) {
			cont++;
			space += space;
		}
		space = mark + space;
		space = space + mark;
		return space;
	}

	

	public void onClickListAdapterItem(View v, Long id) {
		Log.e("ONCLICK", "dentro LIST ADAPTER");
	}

}
