package com.bretibad.tournoibretibad.adpter;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

import com.bretibad.tournoibretibad.R;
import com.bretibad.tournoibretibad.model.MatchCategory;
import com.bretibad.tournoibretibad.model.Rencontre;
import com.bretibad.tournoibretibad.model.Set;
import com.bretibad.tournoibretibad.service.RencontreService;

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

		initSh1(holder, r);
		initSh2(holder, r);
		return convertView;
	}

	private void initSh2(final ViewHolder holder, final Rencontre r) {
		holder.sh2.setText("SH2: " + r.getSh2());
		
		holder.sh2sp.setText(r.getSetpsh2() + "");
		holder.sh2sp.setOnFocusChangeListener(getOnFocusHandler(holder.sh2sp, r.getSetpsh2()));
		holder.sh2sp.setOnEditorActionListener(getAction(holder.sh2sp, holder, r, MatchCategory.SH2, Set.SETP));
		
		holder.sh2sc.setText(r.getSetcsh2() + "");
		holder.sh2sc.setOnFocusChangeListener(getOnFocusHandler(holder.sh2sc, r.getSetcsh2()));
		holder.sh2sc.setOnEditorActionListener(getAction(holder.sh2sc, holder, r, MatchCategory.SH2, Set.SETC));
	}

	private OnFocusChangeListener getOnFocusHandler(final EditText editText, final int value) {
		return new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (!hasFocus) {
					editText.setText(value + "");
				}
			}
		};
	}

	private void initSh1(final ViewHolder holder, final Rencontre r) {
		holder.sh1.setText("SH1: " + r.getSh1());
		holder.sh1sp.setText(r.getSetpsh1() + "");
		holder.sh1sc.setText(r.getSetcsh1() + "");
		holder.sh1sp.setOnEditorActionListener(getAction(holder.sh1sp, holder, r, MatchCategory.SH1, Set.SETP));
		holder.sh1sc.setOnEditorActionListener(getAction(holder.sh1sc, holder, r, MatchCategory.SH1, Set.SETC));
	}

	private OnEditorActionListener getAction(final EditText editText, final ViewHolder holder, final Rencontre r, final MatchCategory category,
			final Set set) {
		int oldValue = 0;
		String champs = "";
		switch (category) {
		case SH1:
			oldValue = r.getSetpsh1();
			champs = set.equals(Set.SETP) ? "setpsh1" : "setcsh1";
			break;
		case SH2:
			oldValue = r.getSetpsh2();
			champs = set.equals(Set.SETP) ? "setpsh2" : "setcsh2";
			break;

		default:
			break;
		}
		final int oldValueFinal = oldValue;
		final String champsFinal = champs;
		return new OnEditorActionListener() {

			@Override
			public boolean onEditorAction(final TextView v, int actionId, KeyEvent event) {
				if (actionId == KeyEvent.KEYCODE_ENDCALL) {
					Intent updateResultatsIntent = RencontreService.getInstance(v.getContext()).getUpdateResultatsIntent(r.getNumequipe(),
							r.getJournee(), champsFinal, editText.getText().toString(), new ResultReceiver(new Handler()) {

								@Override
								protected void onReceiveResult(int resultCode, Bundle resultData) {
									if (resultCode == 200) {
										updateScore(editText, r, v, category);
									} else {
										editText.setText(oldValueFinal + "");
										Toast.makeText(v.getContext(), "Erreur: Impossible de mettre à jour le score", Toast.LENGTH_SHORT).show();
									}
								}

								private void updateScore(final EditText editText, final Rencontre r, final TextView v, MatchCategory category) {
									switch (category) {
									case SH1:
										if (set.equals(Set.SETP)) {
											r.setSetpsh1(Integer.parseInt(editText.getText().toString()));
										} else {
											r.setSetcsh1(Integer.parseInt(editText.getText().toString()));
										}
										break;
									case SH2:
										if (set.equals(Set.SETP)) {
											r.setSetpsh2(Integer.parseInt(editText.getText().toString()));
										} else {
											r.setSetcsh2(Integer.parseInt(editText.getText().toString()));
										}

										break;

									default:
										break;
									}

									Toast.makeText(v.getContext(), "OK", Toast.LENGTH_SHORT).show();
								}
							});
					v.getContext().startService(updateResultatsIntent);
				}
				return false;
			}
		};
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
