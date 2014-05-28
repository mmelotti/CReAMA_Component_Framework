package com.creama.conecte.components.comment;

import java.text.DateFormat;
import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.creama.conecte.components.comment.Comment;
import com.gw.android.R;

public abstract class CommentListAdapter extends BaseAdapter {

	
	private final List<Comment> list;
	private LinearLayout toHide;
	boolean itemRemovido = false;

	public CommentListAdapter(List<Comment> l) {	
		list = l;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return list.get(position).getId();
	}
	
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) parent.getContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View rowView;

		rowView = inflater.inflate(R.layout.conecte_one_comment_forlist_comp,
				parent, false);
		Comment c = list.get(position);
		Log.i(" NAOOOO ULTIMA POSICAO", "s "+list.size()+" position-"+ position);
		((TextView) rowView.findViewById(R.id.textComment)).setText(c
				.getTexto());
		((TextView) rowView.findViewById(R.id.nameUserComment)).setText(c
				.getNome());

		((TextView) rowView.findViewById(R.id.nameUserComment))
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						onClickOneItensTitleComponent(v, list.get(position)
								.getId());

						/*
						 * Long id = Long.valueOf(v.getTag().toString());
						 * Comment c = findCommentById(id); if (c != null) {
						 * initCommentDao(); // deleteOne(c); closeDao();
						 * reloadActivity(); }
						 */

					}
				});

		if (position + 1 == list.size()) {
			// gambiarra pra alguns dispositivos... add void at the end
			((TextView) rowView.findViewById(R.id.textComment)).setText(c
					.getTexto() + smallEmptyString());
		} else {

			((TextView) rowView.findViewById(R.id.textComment)).setText(c
					.getTexto());
		}

		DateFormat df = DateFormat.getDateTimeInstance(DateFormat.SHORT,
				DateFormat.SHORT);

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
		list.remove(0);
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

	public abstract void onClickOneItensTitleComponent(View v, Long id);
	

}