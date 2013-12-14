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

			initAllWidget(convertView, holder);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		Rencontre r = dtos.get(position);
		holder.title.setText("Equipe " + r.getNumequipe() + " - " + r.getAdversaire());

		initSh1(holder, r);
		initSh2(holder, r);
		initSh3(holder, r);
		initSd1(holder, r);
		initSd2(holder, r);
		initDh1(holder, r);
		initDd1(holder, r);
		initDx1(holder, r);
		initDx2(holder, r);
		return convertView;
	}

	private void initSh1(final ViewHolder holder, final Rencontre r) {
		holder.sh1.setText("SH1: " + r.getSh1());

		holder.sh1sp.setText(r.getSetpsh1() + "");
		holder.sh1sp.setOnEditorActionListener(getAction(holder.sh1sp, holder, r, MatchCategory.SH1, Set.SETP));
		holder.sh1sp.setOnFocusChangeListener(getOnFocusHandler(holder.sh1sp, r, MatchCategory.SH1, Set.SETP));

		holder.sh1sc.setText(r.getSetcsh1() + "");
		holder.sh1sc.setOnEditorActionListener(getAction(holder.sh1sc, holder, r, MatchCategory.SH1, Set.SETC));
		holder.sh1sc.setOnFocusChangeListener(getOnFocusHandler(holder.sh1sc, r, MatchCategory.SH1, Set.SETC));
	}

	private void initSh2(final ViewHolder holder, final Rencontre r) {
		holder.sh2.setText("SH2: " + r.getSh2());

		holder.sh2sp.setText(r.getSetpsh2() + "");
		holder.sh2sp.setOnFocusChangeListener(getOnFocusHandler(holder.sh2sp, r, MatchCategory.SH2, Set.SETP));
		holder.sh2sp.setOnEditorActionListener(getAction(holder.sh2sp, holder, r, MatchCategory.SH2, Set.SETP));

		holder.sh2sc.setText(r.getSetcsh2() + "");
		holder.sh2sc.setOnFocusChangeListener(getOnFocusHandler(holder.sh2sc, r, MatchCategory.SH2, Set.SETC));
		holder.sh2sc.setOnEditorActionListener(getAction(holder.sh2sc, holder, r, MatchCategory.SH2, Set.SETC));
	}

	private void initSh3(final ViewHolder holder, final Rencontre r) {
		if (!r.getDivision().equals("Reg3")) {
			holder.sh3.setText("SH3: " + r.getSh3());

			holder.sh3sp.setText(r.getSetpsh3() + "");
			holder.sh3sp.setOnFocusChangeListener(getOnFocusHandler(holder.sh3sp, r, MatchCategory.SH3, Set.SETP));
			holder.sh3sp.setOnEditorActionListener(getAction(holder.sh3sp, holder, r, MatchCategory.SH3, Set.SETP));

			holder.sh3sc.setText(r.getSetcsh3() + "");
			holder.sh3sc.setOnFocusChangeListener(getOnFocusHandler(holder.sh3sc, r, MatchCategory.SH3, Set.SETC));
			holder.sh3sc.setOnEditorActionListener(getAction(holder.sh3sc, holder, r, MatchCategory.SH3, Set.SETC));
		} else {
			holder.sh3Panel.setVisibility(View.GONE);
		}
	}

	private void initSd1(final ViewHolder holder, final Rencontre r) {
		holder.sd1.setText("SD1 " + r.getSd1());

		holder.sd1sp.setText(r.getSetpsd1() + "");
		holder.sd1sp.setOnFocusChangeListener(getOnFocusHandler(holder.sd1sp, r, MatchCategory.SD1, Set.SETP));
		holder.sd1sp.setOnEditorActionListener(getAction(holder.sd1sp, holder, r, MatchCategory.SD1, Set.SETP));

		holder.sd1sc.setText(r.getSetcsd1() + "");
		holder.sd1sc.setOnFocusChangeListener(getOnFocusHandler(holder.sd1sc, r, MatchCategory.SD1, Set.SETC));
		holder.sd1sc.setOnEditorActionListener(getAction(holder.sd1sc, holder, r, MatchCategory.SD1, Set.SETC));
	}

	private void initSd2(final ViewHolder holder, final Rencontre r) {
		if (!r.getDivision().equals("Reg3")) {
			holder.sd2.setVisibility(View.GONE);
		} else {
			holder.sd2.setText("SD2 " + r.getSd2());

			holder.sd2sp.setText(r.getSetpsd2() + "");
			holder.sd2sp.setOnFocusChangeListener(getOnFocusHandler(holder.sd2sp, r, MatchCategory.SD2, Set.SETP));
			holder.sd2sp.setOnEditorActionListener(getAction(holder.sd2sp, holder, r, MatchCategory.SD2, Set.SETP));

			holder.sd2sc.setText(r.getSetcsd2() + "");
			holder.sd2sc.setOnFocusChangeListener(getOnFocusHandler(holder.sd2sc, r, MatchCategory.SD2, Set.SETC));
			holder.sd2sc.setOnEditorActionListener(getAction(holder.sd2sc, holder, r, MatchCategory.SD2, Set.SETC));
		}
	}

	private void initDh1(final ViewHolder holder, final Rencontre r) {
		holder.dh1.setText("DH1 " + r.getDh1());

		holder.dh1sp.setText(r.getSetpdh1() + "");
		holder.dh1sp.setOnFocusChangeListener(getOnFocusHandler(holder.dh1sp, r, MatchCategory.DH1, Set.SETP));
		holder.dh1sp.setOnEditorActionListener(getAction(holder.dh1sp, holder, r, MatchCategory.DH1, Set.SETP));

		holder.dh1sc.setText(r.getSetcdh1() + "");
		holder.dh1sc.setOnFocusChangeListener(getOnFocusHandler(holder.dh1sc, r, MatchCategory.DH1, Set.SETC));
		holder.dh1sc.setOnEditorActionListener(getAction(holder.dh1sc, holder, r, MatchCategory.DH1, Set.SETC));
	}

	private void initDd1(final ViewHolder holder, final Rencontre r) {
		holder.dd1.setText("DD1 " + r.getDd1());

		holder.dd1sp.setText(r.getSetpdd1() + "");
		holder.dd1sp.setOnFocusChangeListener(getOnFocusHandler(holder.dd1sp, r, MatchCategory.DD1, Set.SETP));
		holder.dd1sp.setOnEditorActionListener(getAction(holder.dd1sp, holder, r, MatchCategory.DD1, Set.SETP));

		holder.dd1sc.setText(r.getSetcdd1() + "");
		holder.dd1sc.setOnFocusChangeListener(getOnFocusHandler(holder.dd1sc, r, MatchCategory.DD1, Set.SETC));
		holder.dd1sc.setOnEditorActionListener(getAction(holder.dd1sc, holder, r, MatchCategory.DD1, Set.SETC));
	}

	private void initDx1(final ViewHolder holder, final Rencontre r) {
		holder.dx1.setText("DX1 " + r.getDx1());

		holder.dx1sp.setText(r.getSetpdx1() + "");
		holder.dx1sp.setOnFocusChangeListener(getOnFocusHandler(holder.dx1sp, r, MatchCategory.DX1, Set.SETP));
		holder.dx1sp.setOnEditorActionListener(getAction(holder.dx1sp, holder, r, MatchCategory.DX1, Set.SETP));

		holder.dx1sc.setText(r.getSetcdx1() + "");
		holder.dx1sc.setOnFocusChangeListener(getOnFocusHandler(holder.dx1sc, r, MatchCategory.DX1, Set.SETC));
		holder.dx1sc.setOnEditorActionListener(getAction(holder.dx1sc, holder, r, MatchCategory.DX1, Set.SETC));
	}

	private void initDx2(final ViewHolder holder, final Rencontre r) {
		if (!r.getDivision().equals("Reg3")) {
			holder.dx2.setVisibility(View.GONE);
		} else {
			holder.dx2.setText("DX2 " + r.getDx2());

			holder.dx2sp.setText(r.getSetpdx2() + "");
			holder.dx2sp.setOnFocusChangeListener(getOnFocusHandler(holder.dx2sp, r, MatchCategory.DX2, Set.SETP));
			holder.dx2sp.setOnEditorActionListener(getAction(holder.dx2sp, holder, r, MatchCategory.DX2, Set.SETP));

			holder.dx2sc.setText(r.getSetcdx2() + "");
			holder.dx2sc.setOnFocusChangeListener(getOnFocusHandler(holder.dx2sc, r, MatchCategory.DX2, Set.SETC));
			holder.dx2sc.setOnEditorActionListener(getAction(holder.dx2sc, holder, r, MatchCategory.DX2, Set.SETC));
		}
	}

	private void initAllWidget(View convertView, final ViewHolder holder) {
		holder.title = (TextView) convertView.findViewById(R.id.rencontre_title);
		holder.sh1Panel = (LinearLayout) convertView.findViewById(R.id.sh1Panel);
		holder.sh1 = (TextView) convertView.findViewById(R.id.sh1);
		holder.sh1sp = (EditText) convertView.findViewById(R.id.sh1sp);
		holder.sh1sc = (EditText) convertView.findViewById(R.id.sh1sc);

		holder.sh2Panel = (LinearLayout) convertView.findViewById(R.id.sh2Panel);
		holder.sh2 = (TextView) convertView.findViewById(R.id.sh2);
		holder.sh2sp = (EditText) convertView.findViewById(R.id.sh2sp);
		holder.sh2sc = (EditText) convertView.findViewById(R.id.sh2sc);

		holder.sh3Panel = (LinearLayout) convertView.findViewById(R.id.sh3Panel);
		holder.sh3 = (TextView) convertView.findViewById(R.id.sh3);
		holder.sh3sp = (EditText) convertView.findViewById(R.id.sh3sp);
		holder.sh3sc = (EditText) convertView.findViewById(R.id.sh3sc);

		holder.sd1Panel = (LinearLayout) convertView.findViewById(R.id.sd1Panel);
		holder.sd1 = (TextView) convertView.findViewById(R.id.sd1);
		holder.sd1sp = (EditText) convertView.findViewById(R.id.sd1sp);
		holder.sd1sc = (EditText) convertView.findViewById(R.id.sd1sc);

		holder.sd2Panel = (LinearLayout) convertView.findViewById(R.id.sd2Panel);
		holder.sd2 = (TextView) convertView.findViewById(R.id.sd2);
		holder.sd2sp = (EditText) convertView.findViewById(R.id.sd2sp);
		holder.sd2sc = (EditText) convertView.findViewById(R.id.sd2sc);

		holder.dh1Panel = (LinearLayout) convertView.findViewById(R.id.dh1Panel);
		holder.dh1 = (TextView) convertView.findViewById(R.id.dh1);
		holder.dh1sp = (EditText) convertView.findViewById(R.id.dh1sp);
		holder.dh1sc = (EditText) convertView.findViewById(R.id.dh1sc);

		holder.dd1Panel = (LinearLayout) convertView.findViewById(R.id.dd1Panel);
		holder.dd1 = (TextView) convertView.findViewById(R.id.dd1);
		holder.dd1sp = (EditText) convertView.findViewById(R.id.dd1sp);
		holder.dd1sc = (EditText) convertView.findViewById(R.id.dd1sc);

		holder.dx1Panel = (LinearLayout) convertView.findViewById(R.id.dx1Panel);
		holder.dx1 = (TextView) convertView.findViewById(R.id.dx1);
		holder.dx1sp = (EditText) convertView.findViewById(R.id.dx1sp);
		holder.dx1sc = (EditText) convertView.findViewById(R.id.dx1sc);

		holder.dx2Panel = (LinearLayout) convertView.findViewById(R.id.dx2Panel);
		holder.dx2 = (TextView) convertView.findViewById(R.id.dx2);
		holder.dx2sp = (EditText) convertView.findViewById(R.id.dx2sp);
		holder.dx2sc = (EditText) convertView.findViewById(R.id.dx2sc);
	}

	// private OnFocusChangeListener getOnFocusHandler(final EditText editText,
	// final int value) {
	// return new OnFocusChangeListener() {
	// @Override
	// public void onFocusChange(View v, boolean hasFocus) {
	// if (!hasFocus) {
	// editText.setText(value + "");
	// }
	// }
	// };
	// }

	private OnFocusChangeListener getOnFocusHandler(final EditText editText, final Rencontre r, final MatchCategory category, final Set set) {
		return new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (!hasFocus) {
					int value = 0;
					switch (category) {
					case SH1:
						if (set.equals(Set.SETP)) {
							value = r.getSetpsh1();
						} else {
							value = r.getSetcsh1();
						}
						break;
					case SH2:
						if (set.equals(Set.SETP)) {
							value = r.getSetpsh2();
						} else {
							value = r.getSetcsh2();
						}

						break;
					case SH3:
						if (set.equals(Set.SETP)) {
							value = r.getSetpsh3();
						} else {
							value = r.getSetcsh3();
						}
						break;
					case SD1:
						if (set.equals(Set.SETP)) {
							value = r.getSetpsd1();
						} else {
							value = r.getSetcsd1();
						}
						break;
					case SD2:
						if (set.equals(Set.SETP)) {
							value = r.getSetpsd2();
						} else {
							value = r.getSetcsd2();
						}
						break;
					case DH1:
						if (set.equals(Set.SETP)) {
							value = r.getSetpdh1();
						} else {
							value = r.getSetcdh1();
						}
						break;
					case DD1:
						if (set.equals(Set.SETP)) {
							value = r.getSetpdd1();
						} else {
							value = r.getSetcdd1();
						}
						break;
					case DX1:
						if (set.equals(Set.SETP)) {
							value = r.getSetpdx1();
						} else {
							value = r.getSetcdx1();
						}
						break;
					case DX2:
						if (set.equals(Set.SETP)) {
							value = r.getSetpdx2();
						} else {
							value = r.getSetcdx2();
						}
						break;
					default:
						break;
					}
					editText.setText(value + "");
				}
			}
		};
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
		case SH3:
			oldValue = r.getSetpsh3();
			champs = set.equals(Set.SETP) ? "setpsh3" : "setcsh3";
			break;
		case SD1:
			oldValue = r.getSetpsd1();
			champs = set.equals(Set.SETP) ? "setpsd1" : "setcsd1";
			break;
		case SD2:
			oldValue = r.getSetpsd2();
			champs = set.equals(Set.SETP) ? "setpsd2" : "setcsd2";
			break;
		case DH1:
			oldValue = r.getSetpdh1();
			champs = set.equals(Set.SETP) ? "setpdh1" : "setcdh1";
			break;
		case DD1:
			oldValue = r.getSetpdd1();
			champs = set.equals(Set.SETP) ? "setpdd1" : "setcdd1";
			break;
		case DX1:
			oldValue = r.getSetpdx1();
			champs = set.equals(Set.SETP) ? "setpdx1" : "setcdx1";
			break;
		case DX2:
			oldValue = r.getSetpdx2();
			champs = set.equals(Set.SETP) ? "setpdx2" : "setcdx2";
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
										editText.clearFocus();
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
									case SH3:
										if (set.equals(Set.SETP)) {
											r.setSetpsh3(Integer.parseInt(editText.getText().toString()));
										} else {
											r.setSetcsh3(Integer.parseInt(editText.getText().toString()));
										}

										break;
									case SD1:
										if (set.equals(Set.SETP)) {
											r.setSetpsd1(Integer.parseInt(editText.getText().toString()));
										} else {
											r.setSetcsd1(Integer.parseInt(editText.getText().toString()));
										}
										break;
									case SD2:
										if (set.equals(Set.SETP)) {
											r.setSetpsd2(Integer.parseInt(editText.getText().toString()));
										} else {
											r.setSetcsd2(Integer.parseInt(editText.getText().toString()));
										}
										break;
									case DH1:
										if (set.equals(Set.SETP)) {
											r.setSetpdh1(Integer.parseInt(editText.getText().toString()));
										} else {
											r.setSetcdh1(Integer.parseInt(editText.getText().toString()));
										}
										break;
									case DD1:
										if (set.equals(Set.SETP)) {
											r.setSetpdd1(Integer.parseInt(editText.getText().toString()));
										} else {
											r.setSetcdd1(Integer.parseInt(editText.getText().toString()));
										}
										break;
									case DX1:
										if (set.equals(Set.SETP)) {
											r.setSetpdx1(Integer.parseInt(editText.getText().toString()));
										} else {
											r.setSetcdx1(Integer.parseInt(editText.getText().toString()));
										}
										break;
									case DX2:
										if (set.equals(Set.SETP)) {
											r.setSetpdx2(Integer.parseInt(editText.getText().toString()));
										} else {
											r.setSetcdx2(Integer.parseInt(editText.getText().toString()));
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
		LinearLayout sh3Panel;
		TextView sh3;
		EditText sh3sp;
		EditText sh3sc;
		LinearLayout sd1Panel;
		TextView sd1;
		EditText sd1sp;
		EditText sd1sc;
		LinearLayout sd2Panel;
		TextView sd2;
		EditText sd2sp;
		EditText sd2sc;
		LinearLayout dh1Panel;
		TextView dh1;
		EditText dh1sp;
		EditText dh1sc;
		LinearLayout dd1Panel;
		TextView dd1;
		EditText dd1sp;
		EditText dd1sc;
		LinearLayout dx1Panel;
		TextView dx1;
		EditText dx1sp;
		EditText dx1sc;
		LinearLayout dx2Panel;
		TextView dx2;
		EditText dx2sp;
		EditText dx2sc;
	}
}