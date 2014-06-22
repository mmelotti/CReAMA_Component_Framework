package com.creama.conecte.components.action;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.creama.interfaces.IListAdapter;
import com.gw.android.R;

public abstract class ActionListAdapter extends BaseAdapter implements IListAdapter{

	ArrayList<Action> list;

	public ActionListAdapter(List<Action> l) {
		list = (ArrayList<Action>) l;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		FeedViewHolder viewHolder;
		View itemView = convertView;

		Log.e("tipo feed", " " + list.get(position).getTipo());

		if (itemView == null) {
			LayoutInflater inflater = (LayoutInflater) parent.getContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			itemView = inflater.inflate(
					R.layout.conecte_one_action_forlist_comp, null);
			viewHolder = new FeedViewHolder();
			viewHolder.texto = (TextView) itemView.findViewById(R.id.texto);
			viewHolder.data = (TextView) itemView.findViewById(R.id.data);
			itemView.setTag(viewHolder);
		} else
			viewHolder = (FeedViewHolder) itemView.getTag();

		final Action f = list.get(position);
		if (f != null) {
			viewHolder.texto.setText(f.getTexto());
			//viewHolder.data.setText(DateFormat.parseDate(f.dataHora));
		}
		return itemView;
	}

	static class FeedViewHolder {
		TextView texto, data;
	}

	public abstract void onClickOneItensTitleComponent(View v, Long id);

}