package com.bretibad.tournoibretibad.fragment;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
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
						joueurTxt.add(j.getNom() + " " + j.getPrenom());
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
		FragmentManager fm = getActivity().getSupportFragmentManager();
		for (MatchCategory cat : MatchCategory.values()) {
		}
	}

	protected void fillEditRencontrePopup(Builder alert, List<String> joueurTxt) {

		LinearLayout container = new LinearLayout(getActivity());
		container.setOrientation(LinearLayout.VERTICAL);
		
		TextView title = new TextView(getActivity());
		title.setText("Editer: J" + rencontre.getJournee() + " - Equipe" + rencontre.getNumequipe());
		title.setPadding(0, 0, 0, 15);
		container.addView(title);

		for (MatchCategory cat : MatchCategory.values()) {
			LinearLayout ll = new LinearLayout(getActivity());
			ll.setOrientation(LinearLayout.HORIZONTAL);
			TextView t = new TextView(getActivity());
			t.setText(cat.name());

			Spinner sp = new Spinner(getActivity());
			sp.setTag("edit" + cat.name());
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, joueurTxt);

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
		mainView.addView(container);
	}

}
