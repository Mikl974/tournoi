package com.bretibad.tournoibretibad.adpter;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.text.InputFilter;
import android.text.InputType;
import android.text.method.DigitsKeyListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bretibad.tournoibretibad.R;
import com.bretibad.tournoibretibad.fragment.EditRencontreDialogueFragment;
import com.bretibad.tournoibretibad.fragment.EditRencontreDialogueFragment.NoticeDialogListener;
import com.bretibad.tournoibretibad.model.Joueur;
import com.bretibad.tournoibretibad.model.MatchCategory;
import com.bretibad.tournoibretibad.model.Rencontre;
import com.bretibad.tournoibretibad.service.RencontreService;
import com.bretibad.tournoibretibad.utils.RestResultReceiver;
import com.bretibad.tournoibretibad.utils.StringUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

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
		return true;
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
		initEditRencontreIcon(holder.editRencontreIcon, r);

		initMatchLine(MatchCategory.SH1, r, holder.sh1Panel, holder.sh1, holder.scoreSh1, true);
		initMatchLine(MatchCategory.SH2, r, holder.sh2Panel, holder.sh2, holder.scoreSh2, true);
		initMatchLine(MatchCategory.SH3, r, holder.sh3Panel, holder.sh3, holder.scoreSh3, !r.getDivision().equals("Reg3"));
		initMatchLine(MatchCategory.SD1, r, holder.sd1Panel, holder.sd1, holder.scoreSd1, true);
		initMatchLine(MatchCategory.SD2, r, holder.sd2Panel, holder.sd2, holder.scoreSd2, r.getDivision().equals("Reg3"));
		initMatchLine(MatchCategory.DH1, r, holder.dh1Panel, holder.dh1, holder.scoreDh1, true);
		initMatchLine(MatchCategory.DD1, r, holder.dd1Panel, holder.dd1, holder.scoreDd1, true);
		initMatchLine(MatchCategory.DX1, r, holder.dx1Panel, holder.dx1, holder.scoreDx1, true);
		initMatchLine(MatchCategory.DX2, r, holder.dx2Panel, holder.dx2, holder.scoreDx2, r.getDivision().equals("Reg3"));

		return convertView;
	}

	private void initEditRencontreIcon(ImageView editRencontreIcon, final Rencontre r) {
		editRencontreIcon.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				EditRencontreDialogueFragment editRencontreDialogueFragment = EditRencontreDialogueFragment.newInstance(r);
				editRencontreDialogueFragment.mListener = new NoticeDialogListener() {
					
					@Override
					public void onDialogPositiveClick(DialogFragment dialog) {
						
					}
					
				};
				editRencontreDialogueFragment.show(((FragmentActivity) getContext()).getSupportFragmentManager(), "EditRencontre");
			}
		});
	}

	protected Builder getEditRencontrePopup(final View v, final Rencontre r) {
		final AlertDialog.Builder alert = new AlertDialog.Builder(v.getContext());

		Intent listJoueurIntent = RencontreService.getInstance(v.getContext()).getListJoueurIntent(new RestResultReceiver() {

			@Override
			public void onRESTResult(int resultCode, String result) {
				if (resultCode == 200) {
					Type joueurType = new TypeToken<List<Joueur>>() {
					}.getType();
					Gson gson = new Gson();
					List<Joueur> joueurs = gson.fromJson(result, joueurType);
					List<String> joueurTxt = new ArrayList<String>();
					for (Joueur j : joueurs) {
						joueurTxt.add(j.getNom() + " " + j.getPrenom());
					}

					fillEditRencontrePopup(alert, v, r, joueurTxt);
					alert.show();

				} else {
					Toast.makeText(v.getContext(), "Erreur: Impossible de récupérer la liste de joueurs", Toast.LENGTH_SHORT).show();
				}
			}
		});
		getContext().startService(listJoueurIntent);

		return alert;
	}

	protected void fillEditRencontrePopup(Builder alert, View v, Rencontre r, List<String> joueurTxt) {
		ScrollView sv = new ScrollView(getContext());
		LinearLayout container = new LinearLayout(v.getContext());
		container.setOrientation(LinearLayout.VERTICAL);

		for (MatchCategory cat : MatchCategory.values()) {
			LinearLayout ll = new LinearLayout(getContext());
			ll.setOrientation(LinearLayout.HORIZONTAL);
			TextView t = new TextView(v.getContext());
			t.setText(cat.name());

			Spinner sp = new Spinner(getContext());
			sp.setTag("edit" + cat.name());
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, joueurTxt);

			try {
				Class<?> c = Class.forName(Rencontre.class.getName());
				Method getJoueurMethod = c.getDeclaredMethod("get" + StringUtils.toCamelCase(cat.name().toLowerCase(Locale.getDefault())));

			} catch (Exception e) {
			}

			sp.setAdapter(adapter);

			ll.addView(t);
			ll.addView(sp);

			container.addView(ll);
		}
		sv.addView(container);
		alert.setView(sv);
	}

	public View getMatchCategoryEditLine(MatchCategory cat, Rencontre r, List<String> joueurTxt) {
		LinearLayout ll = new LinearLayout(getContext());
		ll.setOrientation(LinearLayout.HORIZONTAL);
		TextView t = new TextView(getContext());
		t.setText(cat.name());

		Spinner sp = new Spinner(getContext());
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, joueurTxt);
		sp.setAdapter(adapter);

		ll.addView(t);
		ll.addView(sp);
		return ll;
	}

	private void initMatchLine(final MatchCategory cat, final Rencontre r, LinearLayout mainPanel, TextView joueurPanel, final TextView scorePanel,
			boolean displayLine) {

		try {
			Class<?> c = Class.forName(Rencontre.class.getName());
			Method getJoueurMethod = c.getDeclaredMethod("get" + StringUtils.toCamelCase(cat.name().toLowerCase(Locale.getDefault())));
			Method getSetpMethod = c.getDeclaredMethod("getSetp" + cat.name().toLowerCase(Locale.getDefault()));
			Method getSetcMethod = c.getDeclaredMethod("getSetc" + cat.name().toLowerCase(Locale.getDefault()));

			String joueur = (String) getJoueurMethod.invoke(r);
			int setpValue = (Integer) getSetpMethod.invoke(r);
			int setcValue = (Integer) getSetcMethod.invoke(r);
			joueurPanel.setText(cat.name() + ": " + joueur);
			scorePanel.setText(setpValue + " / " + setcValue);

			if (displayLine) {
				mainPanel.setVisibility(View.VISIBLE);
				if ((r.getFinmatch() == null || !r.getFinmatch().equalsIgnoreCase("OK")) && r.getLive() == 1) {
					mainPanel.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							Builder alertDialog = getMatchScorePopup(v, r, scorePanel, cat);
							alertDialog.show();
						}
					});
				}
			} else {
				mainPanel.setVisibility(View.GONE);
			}

		} catch (Exception e) {
			Toast.makeText(mainPanel.getContext(), "Erreur: " + e.getMessage(), Toast.LENGTH_SHORT).show();
		}
	}

	private Builder getMatchScorePopup(final View v, final Rencontre r, final TextView scoreTextVIew, final MatchCategory cat) {
		AlertDialog.Builder alert = new AlertDialog.Builder(v.getContext());

		final EditText setp = getEditText(v);
		final EditText setc = getEditText(v);

		try {
			Class<?> c = Class.forName(Rencontre.class.getName());
			Method getJoueurMethod = c.getDeclaredMethod("get" + StringUtils.toCamelCase(cat.name().toLowerCase(Locale.getDefault())));
			String joueur = (String) getJoueurMethod.invoke(r);
			alert.setTitle(cat.name() + " : " + joueur);

			Method getSetpMethod = c.getDeclaredMethod("getSetp" + cat.name().toLowerCase(Locale.getDefault()));
			int setpValue = (Integer) getSetpMethod.invoke(r);
			Method getSetcMethod = c.getDeclaredMethod("getSetc" + cat.name().toLowerCase(Locale.getDefault()));
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

		alert.setPositiveButton("Ok", getSaveMatchListener(v, r, scoreTextVIew, cat, setp, setc));

		alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
			}
		});
		return alert;
	}

	private android.content.DialogInterface.OnClickListener getSaveMatchListener(final View v, final Rencontre r, final TextView scoreTextVIew,
			final MatchCategory cat, final EditText setp, final EditText setc) {
		return new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				final String setPvalue = setp.getText().toString();
				final String setCvalue = setc.getText().toString();

				String setpChamps = "setp" + cat.name().toLowerCase(Locale.getDefault());
				String setcChamps = "setc" + cat.name().toLowerCase(Locale.getDefault());

				Intent updateResultatsIntent = RencontreService.getInstance(v.getContext()).getUpdateResultatsIntentNew(r.getNumequipe(),
						r.getJournee(), setpChamps, setPvalue, setcChamps, setCvalue, new ResultReceiver(new Handler()) {

							@Override
							protected void onReceiveResult(int resultCode, Bundle resultData) {
								if (resultCode == 200) {

									try {
										Class<?> c = Class.forName(Rencontre.class.getName());
										Method setSetpMethod = c.getDeclaredMethod("setSetp" + cat.name().toLowerCase(Locale.getDefault()), int.class);
										setSetpMethod.invoke(r, Integer.parseInt(setPvalue));
										Method setSetcMethod = c.getDeclaredMethod("setSetc" + cat.name().toLowerCase(Locale.getDefault()), int.class);
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
				getContext().startService(updateResultatsIntent);
			}
		};
	}

	public EditText getEditText(View v) {
		final EditText editText = new EditText(v.getContext());
		editText.setInputType(InputType.TYPE_CLASS_NUMBER);
		editText.setKeyListener(DigitsKeyListener.getInstance("012"));
		editText.setFilters(new InputFilter[] { new InputFilter.LengthFilter(1) });
		return editText;
	}

	private void initAllWidget(View convertView, final ViewHolder holder) {
		holder.title = (TextView) convertView.findViewById(R.id.rencontre_title);
		holder.editRencontreIcon = (ImageView) convertView.findViewById(R.id.rencontre_edit_icon);

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
		ImageView editRencontreIcon;

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