package com.bretibad.tournoibretibad.adpter;

import java.lang.reflect.Method;
import java.util.List;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.text.InputFilter;
import android.text.InputType;
import android.text.method.DigitsKeyListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bretibad.tournoibretibad.R;
import com.bretibad.tournoibretibad.model.MatchCategory;
import com.bretibad.tournoibretibad.model.Rencontre;
import com.bretibad.tournoibretibad.service.RencontreService;
import com.bretibad.tournoibretibad.utils.StringUtils;

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
		holder.scoreSh1.setText(r.getSetpsh1() + " / " + r.getSetcsh1());

		holder.sh1Panel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Builder alertDialog = getAlertDialog(v, r, holder.scoreSh1, MatchCategory.SH1);
				alertDialog.show();
			}
		});
	}

	private void initSh2(final ViewHolder holder, final Rencontre r) {
		holder.sh2.setText("SH2: " + r.getSh2());

		holder.scoreSh2.setText(r.getSetpsh2() + " / " + r.getSetcsh2());
		holder.sh2Panel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Builder alertDialog = getAlertDialog(v, r, holder.scoreSh2, MatchCategory.SH2);
				alertDialog.show();
			}
		});
	}

	private void initSh3(final ViewHolder holder, final Rencontre r) {
		if (!r.getDivision().equals("Reg3")) {
			holder.sh3.setText("SH3: " + r.getSh3());

			holder.scoreSh3.setText(r.getSetpsh3() + " / " + r.getSetcsh3());
			holder.sh3Panel.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Builder alertDialog = getAlertDialog(v, r, holder.scoreSh3, MatchCategory.SH3);
					alertDialog.show();
				}
			});
		} else {
			holder.sh3Panel.setVisibility(View.GONE);
		}
	}

	private void initSd1(final ViewHolder holder, final Rencontre r) {
		holder.sd1.setText("SD1 " + r.getSd1());

		holder.scoreSd1.setText(r.getSetpsd1() + " / " + r.getSetcsd1());
		holder.sd1Panel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Builder alertDialog = getAlertDialog(v, r, holder.scoreSd1, MatchCategory.SD1);
				alertDialog.show();
			}
		});
	}

	private void initSd2(final ViewHolder holder, final Rencontre r) {
		if (!r.getDivision().equals("Reg3")) {
			holder.sd2.setVisibility(View.GONE);
		} else {
			holder.sd2.setText("SD2 " + r.getSd2());

			holder.scoreSd2.setText(r.getSetpsd2() + " / " + r.getSetcsd2());
			holder.sd2Panel.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Builder alertDialog = getAlertDialog(v, r, holder.scoreSd2, MatchCategory.SD2);
					alertDialog.show();
				}
			});
		}
	}

	private void initDh1(final ViewHolder holder, final Rencontre r) {
		holder.dh1.setText("DH1 " + r.getDh1());

		holder.scoreDh1.setText(r.getSetpdh1() + " / " + r.getSetcdh1());
		holder.dh1Panel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Builder alertDialog = getAlertDialog(v, r, holder.scoreDh1, MatchCategory.DH1);
				alertDialog.show();
			}
		});
	}

	private void initDd1(final ViewHolder holder, final Rencontre r) {
		holder.dd1.setText("DD1 " + r.getDd1());

		holder.scoreDd1.setText(r.getSetpdd1() + " / " + r.getSetcdd1());
		holder.dd1Panel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Builder alertDialog = getAlertDialog(v, r, holder.scoreDd1, MatchCategory.DD1);
				alertDialog.show();
			}
		});
	}

	private void initDx1(final ViewHolder holder, final Rencontre r) {
		holder.dx1.setText("DX1 " + r.getDx1());

		holder.scoreDx1.setText(r.getSetpdx1() + " / " + r.getSetcdx1());
		holder.dx1Panel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Builder alertDialog = getAlertDialog(v, r, holder.scoreDx1, MatchCategory.DX1);
				alertDialog.show();
			}
		});
	}

	private void initDx2(final ViewHolder holder, final Rencontre r) {
		if (!r.getDivision().equals("Reg3")) {
			holder.dx2.setVisibility(View.GONE);
		} else {
			holder.dx2.setText("DX2 " + r.getDx2());

			holder.scoreDx2.setText(r.getSetpdx2() + " / " + r.getSetcdx2());
			holder.dx2Panel.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Builder alertDialog = getAlertDialog(v, r, holder.scoreDx2, MatchCategory.DX2);
					alertDialog.show();
				}
			});
		}
	}

	private Builder getAlertDialog(final View v, final Rencontre r, final TextView scoreTextVIew, final MatchCategory cat) {
		AlertDialog.Builder alert = new AlertDialog.Builder(v.getContext());

		final EditText setp = getEditText(v);
		final EditText setc = getEditText(v);
		
		try {
			Class<?> c = Class.forName(Rencontre.class.getName());
			Method getJoueurMethod = c.getDeclaredMethod("get" + StringUtils.toCamelCase(cat.name().toLowerCase()));
			String joueur = (String) getJoueurMethod.invoke(r);
			alert.setTitle(cat.name() + " : " + joueur);

			Method getSetpMethod = c.getDeclaredMethod("getSetp" + cat.name().toLowerCase());
			int setpValue = (Integer) getSetpMethod.invoke(r);
			Method getSetcMethod = c.getDeclaredMethod("getSetc" + cat.name().toLowerCase());
			int setcValue = (Integer) getSetcMethod.invoke(r);

			setp.setText(setpValue + "");
			setc.setText(setcValue + "");

		} catch (Exception e) {

		}

		LinearLayout l = new LinearLayout(v.getContext());
		l.setOrientation(LinearLayout.HORIZONTAL);
		l.setPadding(15, 0, 0, 0);
	
		LinearLayout setpLayout = new LinearLayout(v.getContext());
		setpLayout.setPadding(0, 0, 50, 0);
		TextView setpText = new TextView(v.getContext());
		setpText.setText("Set pour: ");
		setpLayout.addView(setpText);
		setpLayout.addView(setp);
		
		LinearLayout setcLayout = new LinearLayout(v.getContext());
		TextView setcText = new TextView(v.getContext());
		setcText.setText("Set contre: ");
		setcLayout.addView(setcText);
		setcLayout.addView(setc);
		
		l.addView(setpLayout);
		l.addView(setcLayout);

		alert.setView(l);

		alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				final String setPvalue = setp.getText().toString();
				final String setCvalue = setc.getText().toString();

				String setpChamps = "setp" + cat.name().toLowerCase();
				String setcChamps = "setc" + cat.name().toLowerCase();

				Intent updateResultatsIntent = RencontreService.getInstance(v.getContext()).getUpdateResultatsIntentNew(r.getNumequipe(),
						r.getJournee(), setpChamps, setPvalue, setcChamps, setCvalue, new ResultReceiver(new Handler()) {

							@Override
							protected void onReceiveResult(int resultCode, Bundle resultData) {
								if (resultCode == 200) {

									try {
										Class<?> c = Class.forName(Rencontre.class.getName());
										Method setSetpMethod = c.getDeclaredMethod("setSetp" + cat.name().toLowerCase(), int.class);
										setSetpMethod.invoke(r, Integer.parseInt(setPvalue));
										Method setSetcMethod = c.getDeclaredMethod("setSetc" + cat.name().toLowerCase(), int.class);
										setSetcMethod.invoke(r, Integer.parseInt(setCvalue));

										scoreTextVIew.setText(setPvalue + " / " + setCvalue);
										Toast.makeText(v.getContext(), "Valeur sauvée", Toast.LENGTH_SHORT).show();
									} catch (Exception e) {
										e.printStackTrace();
									}
								} else {
									Toast.makeText(v.getContext(), "Erreur: Impossible de mettre à jour le score", Toast.LENGTH_SHORT).show();
								}
							};

						});
				v.getContext().startService(updateResultatsIntent);
			}
		});

		alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				// Canceled.
			}
		});
		return alert;
	}
	
	public EditText getEditText(View v){
		final EditText editText = new EditText(v.getContext());
		editText.setInputType(InputType.TYPE_CLASS_NUMBER);
		editText.setKeyListener(DigitsKeyListener.getInstance("012"));
		editText.setFilters(new InputFilter[] {new InputFilter.LengthFilter(1)});
		return editText;
	}

	private void initAllWidget(View convertView, final ViewHolder holder) {
		holder.title = (TextView) convertView.findViewById(R.id.rencontre_title);

		holder.sh1Panel = (LinearLayout) convertView.findViewById(R.id.sh1Panel);
		holder.sh1 = (TextView) convertView.findViewById(R.id.sh1);
		holder.scoreSh1 = (TextView) convertView.findViewById(R.id.scoreSh1);

		holder.sh2Panel = (LinearLayout) convertView.findViewById(R.id.sh2Panel);
		holder.sh2 = (TextView) convertView.findViewById(R.id.sh2);
		holder.scoreSh2 = (TextView) convertView.findViewById(R.id.scoreSh2);

		holder.sh3Panel = (LinearLayout) convertView.findViewById(R.id.sh3Panel);
		holder.sh3 = (TextView) convertView.findViewById(R.id.sh3);
		holder.scoreSh3 = (TextView) convertView.findViewById(R.id.scoreSh3);

		holder.sd1Panel = (LinearLayout) convertView.findViewById(R.id.sd1Panel);
		holder.sd1 = (TextView) convertView.findViewById(R.id.sd1);
		holder.scoreSd1 = (TextView) convertView.findViewById(R.id.scoreSd1);

		holder.sd2Panel = (LinearLayout) convertView.findViewById(R.id.sd2Panel);
		holder.sd2 = (TextView) convertView.findViewById(R.id.sd2);
		holder.scoreSd2 = (TextView) convertView.findViewById(R.id.scoreSd2);

		holder.dh1Panel = (LinearLayout) convertView.findViewById(R.id.dh1Panel);
		holder.dh1 = (TextView) convertView.findViewById(R.id.dh1);
		holder.scoreDh1 = (TextView) convertView.findViewById(R.id.scoreDh1);

		holder.dd1Panel = (LinearLayout) convertView.findViewById(R.id.dd1Panel);
		holder.dd1 = (TextView) convertView.findViewById(R.id.dd1);
		holder.scoreDd1 = (TextView) convertView.findViewById(R.id.scoreDd1);

		holder.dx1Panel = (LinearLayout) convertView.findViewById(R.id.dx1Panel);
		holder.dx1 = (TextView) convertView.findViewById(R.id.dx1);
		holder.scoreDx1 = (TextView) convertView.findViewById(R.id.scoreDx1);

		holder.dx2Panel = (LinearLayout) convertView.findViewById(R.id.dx2Panel);
		holder.dx2 = (TextView) convertView.findViewById(R.id.dx2);
		holder.scoreDx2 = (TextView) convertView.findViewById(R.id.scoreDx2);

	}

	private class ViewHolder {
		TextView title;
		LinearLayout sh1Panel;
		TextView sh1;
		TextView scoreSh1;

		LinearLayout sh2Panel;
		TextView sh2;
		TextView scoreSh2;

		LinearLayout sh3Panel;
		TextView sh3;
		TextView scoreSh3;

		LinearLayout sd1Panel;
		TextView sd1;
		TextView scoreSd1;

		LinearLayout sd2Panel;
		TextView sd2;
		TextView scoreSd2;

		LinearLayout dh1Panel;
		TextView dh1;
		TextView scoreDh1;

		LinearLayout dd1Panel;
		TextView dd1;
		TextView scoreDd1;

		LinearLayout dx1Panel;
		TextView dx1;
		TextView scoreDx1;

		LinearLayout dx2Panel;
		TextView dx2;
		TextView scoreDx2;
	}
}