package com.bretibad.tournoibretibad;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bretibad.tournoibretibad.model.Joueur;

public class JoueurAdapter extends BaseAdapter {

	List<Joueur> dtos;
	LayoutInflater inflater;

	public JoueurAdapter(Context context, ArrayList<Joueur> dtos) {
		inflater = LayoutInflater.from(context);
		this.dtos = dtos;
	}

	@Override
	public int getCount() {
		return dtos.size();
	}

	@Override
	public Object getItem(int position) {
		return dtos.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;

		if (convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.joueur_item, null);

			holder.fullName = (TextView) convertView
					.findViewById(R.id.joueur_fullName);
			holder.licence = (TextView) convertView
					.findViewById(R.id.joueur_license);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.fullName.setText(dtos.get(position).getNom() + " "
				+ dtos.get(position).getPrenom());
		holder.licence.setText(dtos.get(position).getLicence());

		return convertView;
	}

	private class ViewHolder {
		TextView fullName;
		TextView licence;
	}
}
