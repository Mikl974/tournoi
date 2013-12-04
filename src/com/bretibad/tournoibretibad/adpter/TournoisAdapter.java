package com.bretibad.tournoibretibad.adpter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.bretibad.tournoibretibad.R;
import com.bretibad.tournoibretibad.model.Tournoi;

public class TournoisAdapter extends ArrayAdapter<Tournoi> {

	List<Tournoi> dtos;
	LayoutInflater inflater;

	public TournoisAdapter(Context context, int resId, List<Tournoi> dtos) {
		super(context, resId, dtos);
		inflater = LayoutInflater.from(context);
		this.dtos = dtos;
	}

	@Override
	public int getCount() {
		return dtos.size();
	}

	@Override
	public Tournoi getItem(int position) {
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
			convertView = inflater.inflate(R.layout.tournoi_item, null);

			holder.nom = (TextView) convertView.findViewById(R.id.tournoi_nom);
			holder.tarif = (TextView) convertView.findViewById(R.id.tournoi_tarif);
			holder.nbPaye = (TextView) convertView.findViewById(R.id.tournoi_nb_paye);
			holder.nbInscrits = (TextView) convertView.findViewById(R.id.tournoi_nb_inscrits);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		Tournoi tournoi = dtos.get(position);
		final String nomTournoi = tournoi.getNom();
		StringBuilder sb = new StringBuilder();
		sb.append(tournoi.getTarif1()).append("/").append(tournoi.getTarif2()).append("/").append(tournoi.getTarif3());
		final String tarif = sb.toString();
		holder.nom.setText(nomTournoi);
		holder.tarif.setText(tarif);
		holder.nbInscrits.setText(String.valueOf(tournoi.getNbInscrits()));
		holder.nbPaye.setText(String.valueOf(tournoi.getNbPaye()));

		return convertView;
	}

	private class ViewHolder {
		TextView nom;
		TextView tarif;
		TextView nbPaye;
		TextView nbInscrits;
	}
}
