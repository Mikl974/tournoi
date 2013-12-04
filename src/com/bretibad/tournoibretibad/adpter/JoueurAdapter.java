package com.bretibad.tournoibretibad.adpter;

import java.util.List;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bretibad.tournoibretibad.R;
import com.bretibad.tournoibretibad.model.Joueur;

public class JoueurAdapter extends ArrayAdapter<Joueur> {

	List<Joueur> dtos;
	LayoutInflater inflater;
	private SparseBooleanArray mSelectedItemsIds;

	// TODO: remove this
	String tournoi;

	public String getTournoi() {
		return tournoi;
	}

	public void setTournoi(String tournoi) {
		this.tournoi = tournoi;
	}

	public JoueurAdapter(Context context, int resId, List<Joueur> dtos, String tournoi) {
		super(context, resId, dtos);
		inflater = LayoutInflater.from(context);
		this.dtos = dtos;
		mSelectedItemsIds = new SparseBooleanArray();
		this.tournoi = tournoi;
	}

	public void toggleSelection(int position) {
		selectView(position, !mSelectedItemsIds.get(position));
	}

	public void removeSelection() {
		mSelectedItemsIds = new SparseBooleanArray();
		notifyDataSetChanged();
	}

	public void selectView(int position, boolean value) {
		if (value)
			mSelectedItemsIds.put(position, value);
		else
			mSelectedItemsIds.delete(position);

		notifyDataSetChanged();
	}

	public int getSelectedCount() {
		return mSelectedItemsIds.size();
	}

	public SparseBooleanArray getSelectedIds() {
		return mSelectedItemsIds;
	}

	public int[] getSelectedIdsArray() {
		int[] ids = new int[mSelectedItemsIds.size()];
		for (int i = 0; i < mSelectedItemsIds.size(); i++) {
			if (mSelectedItemsIds.get(i)) {
				ids[i] = i;
			}
		}
		return ids;
	}

	@Override
	public boolean isEnabled(int position) {
		return true;// dtos.get(position).getPaye() == 0;
	}

	@Override
	public int getCount() {
		return dtos.size();
	}

	@Override
	public Joueur getItem(int position) {
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

			holder.fullName = (TextView) convertView.findViewById(R.id.joueur_fullName);
			holder.licence = (TextView) convertView.findViewById(R.id.joueur_license);
			holder.payeImage = (ImageView) convertView.findViewById(R.id.joueur_paye);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.fullName.setText(dtos.get(position).getNom() + " " + dtos.get(position).getPrenom());
		holder.licence.setText(dtos.get(position).getLicence());
		if (dtos.get(position).getPaye() != 0) {
			holder.payeImage.setVisibility(View.VISIBLE);
			holder.payeImage.setImageResource(R.drawable.euro);
		} else {
			holder.payeImage.setVisibility(View.INVISIBLE);
		}

		return convertView;
	}

	private class ViewHolder {
		TextView fullName;
		TextView licence;
		ImageView payeImage;
	}
}
