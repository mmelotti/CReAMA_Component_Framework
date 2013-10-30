package com.gw.android.first_components.my_components.comment;

import java.text.DateFormat;
import java.util.List;

import com.gw.android.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

public class CommentAdapter extends ArrayAdapter<Comment> {

	private final Context context;
	private final List<Comment> comments;

	public CommentAdapter(Context context, List<Comment> comments) {
		super(context, R.layout.comment_view, comments);
		this.context = context;
		this.comments = comments;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.comment_view, parent, false);

		Comment c = comments.get(position);

		((TextView) rowView.findViewById(R.id.body)).setText(c.getText());
		DateFormat df = DateFormat.getDateTimeInstance(DateFormat.SHORT,
				DateFormat.SHORT);
		((TextView) rowView.findViewById(R.id.date)).setText("Enviado em "
				+ df.format(c.getDate()));

		((ImageButton) rowView.findViewById(R.id.button_apaga))
				.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						/*
						 * Long id = Long.valueOf(v.getTag().toString());
						 * Comment c = findCommentById(id); if (c != null) {
						 * initCommentDao(); // deleteOne(c); closeDao();
						 * reloadActivity(); }
						 */

					}
				});

		return rowView;
	}

}
