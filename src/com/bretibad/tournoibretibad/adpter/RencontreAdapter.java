package com.bretibad.tournoibretibad.adpter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.bretibad.tournoibretibad.R;
import com.bretibad.tournoibretibad.model.Rencontre;

public class RencontreAdapter extends ArrayAdapter<Rencontre> {

	List<Rencontre> dtos;
	LayoutInflater inflater;

	public RencontreAdapter(Context context, int resId, List<Rencontre> dtos) {
		super(context, resId, dtos);
		inflater = LayoutInflater.from(context);
		this.dtos = dtos;
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
	public Rencontre getItem(int position) {
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
			convertView = inflater.inflate(R.layout.rencontre_item, null);

			holder.title = (TextView) convertView.findViewById(R.id.rencontre_title);
			holder.sh1Panel = (LinearLayout) convertView.findViewById(R.id.sh1Panel);
			holder.sh1 = (TextView) convertView.findViewById(R.id.sh1);
			holder.sh1sp = (EditText) convertView.findViewById(R.id.sh1sp);
			holder.sh1sc = (EditText) convertView.findViewById(R.id.sh1sc);
			
			holder.sh2Panel = (LinearLayout) convertView.findViewById(R.id.sh2Panel);
			holder.sh2 = (TextView) convertView.findViewById(R.id.sh2);
			holder.sh2sp = (EditText) convertView.findViewById(R.id.sh2sp);
			holder.sh2sc = (EditText) convertView.findViewById(R.id.sh2sc);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		Rencontre r = dtos.get(position);
		holder.title.setText("Equipe " + r.getNumequipe() + " - " + r.getAdversaire());
		
		holder.sh1.setText("SH1: " + r.getSh1());
		holder.sh1sp.setText(r.getSetpsh1() + "");
		holder.sh1sc.setText(r.getSetcsh1() + "");
		
		holder.sh2.setText("SH2: " + r.getSh2());
		holder.sh2sp.setText(r.getSetpsh2() + "");
		holder.sh2sc.setText(r.getSetcsh2() + "");
		return convertView;
	}

	private class ViewHolder {
		TextView title;
		LinearLayout sh1Panel;
		TextView sh1;
		EditText sh1sp;
		EditText sh1sc;
		LinearLayout sh2Panel;
		TextView sh2;
		EditText sh2sp;
		EditText sh2sc;
	}

	private void initNumberPicker(NumberPicker picker) {
		picker.setMaxValue(3);
		picker.setMinValue(0);
		picker.setWrapSelectorWheel(true);
	}
}
