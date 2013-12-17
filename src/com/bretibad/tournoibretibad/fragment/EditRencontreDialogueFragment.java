package com.bretibad.tournoibretibad.fragment;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.util.AttributeSet;
import android.util.Pair;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bretibad.tournoibretibad.model.Joueur;
import com.bretibad.tournoibretibad.model.MatchCategory;
import com.bretibad.tournoibretibad.model.Rencontre;
import com.bretibad.tournoibretibad.service.RencontreService;
import com.bretibad.tournoibretibad.utils.RestResultReceiver;
import com.bretibad.tournoibretibad.utils.StringUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class EditRencontreDialogueFragment extends DialogFragment {

	private Rencontre rencontre;
	ScrollView mainView;

	Spinner sh1List, sh2List, sh3List, sd1List, sd2List, dh1J1List, dh1J2List, dd1J1List, dd1J2List, dx1J1List, dx1J2List, dx2J1List, dx2J2List;
	CheckBox fin, live;

	public NoticeDialogListener mListener;

	public interface NoticeDialogListener {
		public void onDialogPositiveClick(DialogFragment dialog);

	}

	public static EditRencontreDialogueFragment newInstance(Rencontre rencontre) {
		EditRencontreDialogueFragment f = new EditRencontreDialogueFragment();

		// Supply index input as an argument.
		Bundle args = new Bundle();
		args.putParcelable("rencontre", rencontre);
		f.setArguments(args);

		return f;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		rencontre = (Rencontre) getArguments().getParcelable("rencontre");
		mainView = new ScrollView(getActivity());
		// Use the Builder class for convenient dialog construction
		final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

		builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				saveRencontre();
			}
		}).setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
			}
		}).setView(mainView);

		Intent listJoueurIntent = RencontreService.getInstance(getActivity()).getListJoueurIntent(new RestResultReceiver() {

			@Override
			public void onRESTResult(int resultCode, String result) {
				if (resultCode == 200) {
					Type joueurType = new TypeToken<List<Joueur>>() {
					}.getType();
					Gson gson = new Gson();
					List<Joueur> joueurs = gson.fromJson(result, joueurType);
					List<String> joueurTxt = new ArrayList<String>();
					for (Joueur j : joueurs) {
						joueurTxt.add(j.getPrenom() + " " + j.getNom());
					}

					fillEditRencontrePopup(builder, joueurTxt);

				} else {
					Toast.makeText(getActivity(), "Erreur: Impossible de récupérer la liste de joueurs", Toast.LENGTH_SHORT).show();
				}
			}
		});
		getActivity().startService(listJoueurIntent);

		return builder.create();
	}

	protected void saveRencontre() {
		Object sh1Selected = sh1List.getSelectedItem();
		Object sh2Selected = sh2List.getSelectedItem();
		System.out.println();

	}

	protected void fillEditRencontrePopup(Builder alert, List<String> joueurTxt) {

		LinearLayout container = new LinearLayout(getActivity());
		container.setOrientation(LinearLayout.VERTICAL);

		TextView title = new TextView(getActivity());
		title.setText("Editer: J" + rencontre.getJournee() + " - Equipe" + rencontre.getNumequipe());
		title.setPadding(0, 0, 0, 15);
		container.addView(title);

		addCheckBox(container);

		for (MatchCategory cat : MatchCategory.values()) {
			LinearLayout ll = new LinearLayout(getActivity());
			ll.setOrientation(LinearLayout.HORIZONTAL);

			switch (cat) {
			case SH1:
				sh1List = initSpinner(joueurTxt, cat.name(), ll, rencontre.getSh1());
				break;
			case SH2:
				sh2List = initSpinner(joueurTxt, cat.name(), ll, rencontre.getSh2());
				break;
			case SH3:
				if (!rencontre.getDivision().equals("Reg3")) {
					sh3List = initSpinner(joueurTxt, cat.name(), ll, rencontre.getSh3());
				}

				break;
			case SD1:
				sd1List = initSpinner(joueurTxt, cat.name(), ll, rencontre.getSd1());
				break;
			case SD2:
				if (rencontre.getDivision().equals("Reg3")) {
					sd2List = initSpinner(joueurTxt, cat.name(), ll, rencontre.getSd2());
				}
				break;
			case DH1:
				Pair<Spinner, Spinner> spinnerPairDh1 = initDoubleSpinner(joueurTxt, cat.name(), ll, rencontre.getDh1());
				dh1J1List = spinnerPairDh1.first;
				dh1J2List = spinnerPairDh1.second;
				break;
			case DD1:
				Pair<Spinner, Spinner> spinnerPairDd1 = initDoubleSpinner(joueurTxt, cat.name(), ll, rencontre.getDd1());
				dd1J1List = spinnerPairDd1.first;
				dd1J2List = spinnerPairDd1.second;
				break;
			case DX1:
				Pair<Spinner, Spinner> spinnerPairDx1 = initDoubleSpinner(joueurTxt, cat.name(), ll, rencontre.getDx1());
				dx1J1List = spinnerPairDx1.first;
				dx1J2List = spinnerPairDx1.second;
				break;
			case DX2:
				if (rencontre.getDivision().equals("Reg3")) {
					Pair<Spinner, Spinner> spinnerPairDx2 = initDoubleSpinner(joueurTxt, cat.name(), ll, rencontre.getDx2());
					dx2J1List = spinnerPairDx2.first;
					dx2J2List = spinnerPairDx2.second;
				}
				break;
			default:
				break;
			}

			container.addView(ll);
		}
		mainView.addView(container);
	}

	private void addCheckBox(LinearLayout container) {
		fin = new CheckBox(getActivity());
		live = new CheckBox(getActivity());
		TextView finLabel = new TextView(getActivity());
		finLabel.setText("Fin: ");
		TextView liveLabel = new TextView(getActivity());
		liveLabel.setText("Live: ");
		fin.setChecked(rencontre.getFinmatch() != null && rencontre.getFinmatch().equalsIgnoreCase("OK"));
		live.setChecked(rencontre.getLive() == 1);
		LinearLayout l = new LinearLayout(getActivity());
		l.setOrientation(LinearLayout.HORIZONTAL);
		l.addView(finLabel);
		l.addView(fin);
		l.addView(liveLabel);
		l.addView(live);
		container.addView(l);
	}

	private Spinner initSpinner(List<String> joueurTxt, String cat, LinearLayout ll, String selectedValue) {
		TextView label = new TextView(getActivity());
		label.setText(cat);
		Spinner spinner = new Spinner(getActivity());
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, joueurTxt);
		spinner.setAdapter(adapter);
		if (selectedValue != null) {
			int position = adapter.getPosition(selectedValue);
			spinner.setSelection(position);
		}

		ll.addView(label);
		ll.addView(spinner);
		return spinner;
	}

	private Pair<Spinner, Spinner> initDoubleSpinner(List<String> joueurTxt, String cat, LinearLayout ll, String selectedValueDouble) {
		TextView label = new TextView(getActivity());
		label.setText(cat);
		LinearLayout spinnerPanel = new LinearLayout(getActivity());
		spinnerPanel.setOrientation(LinearLayout.VERTICAL);

		ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, joueurTxt);
		ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, joueurTxt);

		Spinner spinner1 = new Spinner(getActivity());
		spinner1.setAdapter(adapter1);
		Spinner spinner2 = new Spinner(getActivity());
		spinner2.setAdapter(adapter2);
		spinnerPanel.addView(spinner1);
		spinnerPanel.addView(spinner2);

		if (selectedValueDouble != null) {
			String[] split = selectedValueDouble.split(" - ");
			if (split.length == 2) {
				int positionJ1 = adapter1.getPosition(split[0]);
				int positionJ2 = adapter2.getPosition(split[1]);
				spinner1.setSelection(positionJ1);
				spinner2.setSelection(positionJ2);
			}
		}

		ll.addView(label);
		ll.addView(spinnerPanel);
		return Pair.create(spinner1, spinner2);
	}
}
