package com.bretibad.tournoibretibad.fragment;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.bretibad.tournoibretibad.R;
import com.bretibad.tournoibretibad.adpter.RencontreAdapter;
import com.bretibad.tournoibretibad.model.Rencontre;
import com.bretibad.tournoibretibad.service.RESTService;
import com.bretibad.tournoibretibad.service.RencontreService;
import com.bretibad.tournoibretibad.utils.RestResultReceiver;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class EquipeFragment extends Fragment {

	Spinner equipeSpinner;
	Spinner journeeSpinner;
	ListView rencontreJourneeListView;
	RencontreAdapter rencontreAdapter;

	RencontreEquipeResponderFragment rencontreEquipeFragment;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.equipe_list, container, false);
		equipeSpinner = (Spinner) v.findViewById(R.id.equipe_spinner);
		journeeSpinner = (Spinner) v.findViewById(R.id.journee_spinner);
		journeeSpinner.setVisibility(View.GONE);

		rencontreJourneeListView = (ListView) v.findViewById(R.id.rencontres_journee_listview);
		rencontreAdapter = new RencontreAdapter(getActivity(), R.layout.equipe_list, new ArrayList<Rencontre>());
		rencontreJourneeListView.setAdapter(rencontreAdapter);

		// Create an ArrayAdapter using the string array and a default spinner
		// layout
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(v.getContext(), R.array.equipes, android.R.layout.simple_spinner_dropdown_item);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		equipeSpinner.setAdapter(adapter);
		
		//TODO: USE ARGUMENT TO SELECT Equipe and journne when call from popup

		equipeSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(final AdapterView<?> parentView, View selectedItemView, int position, long id) {
				getActivity().setProgressBarIndeterminateVisibility(true);

				Intent journeeIntent = RencontreService.getInstance(parentView.getContext()).getJourneeIntent(new RestResultReceiver() {

					@Override
					public void onRESTResult(int resultCode, String result) {
						if (resultCode == 200) {
							Type journeeType = new TypeToken<List<Rencontre>>() {
							}.getType();
							Gson gson = new Gson();
							List<Rencontre> rencontres = gson.fromJson(result, journeeType);
							List<String> journees = new ArrayList<String>();
							for (Rencontre rencontre : rencontres) {
								journees.add("J" + rencontre.getJournee());
							}
							ArrayAdapter<String> journeeAdapter = new ArrayAdapter<String>(parentView.getContext(),
									android.R.layout.simple_spinner_dropdown_item, journees);
							journeeSpinner.setVisibility(View.VISIBLE);
							journeeSpinner.setAdapter(journeeAdapter);
						} else {
							Toast.makeText(parentView.getContext(), "Impossible de charger la liste des journées. Verifier votre connexion internet.",
									Toast.LENGTH_SHORT).show();
						}
						getActivity().setProgressBarIndeterminateVisibility(false);

					}
				}, position + 1);

				parentView.getContext().startService(journeeIntent);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parentView) {
			}

		});

		journeeSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(final AdapterView<?> parentView, View selectedItemView, int position, long id) {
				getActivity().setProgressBarIndeterminateVisibility(true);

				Intent rencontreIntent = RencontreService.getInstance(parentView.getContext()).getRencontreIntent(new RestResultReceiver() {
					@Override
					public void onRESTResult(int resultCode, String result) {
						if (resultCode == 200) {
							Type rencontreType = new TypeToken<List<Rencontre>>() {
							}.getType();
							Gson gson = new Gson();
							List<Rencontre> rencontres = gson.fromJson(result, rencontreType);
							rencontreAdapter.clear();
							for (Rencontre r : rencontres) {
								rencontreAdapter.add(r);
							}

						} else {
							Toast.makeText(parentView.getContext(), "Impossible de charger la liste des rencontres. Verifier votre connexion internet.",
									Toast.LENGTH_SHORT).show();
						}
						getActivity().setProgressBarIndeterminateVisibility(false);
					}
				}, equipeSpinner.getSelectedItemPosition() + 1, position + 1);

				parentView.getContext().startService(rencontreIntent);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parentView) {
			}

		});

		return v;
	}
}
