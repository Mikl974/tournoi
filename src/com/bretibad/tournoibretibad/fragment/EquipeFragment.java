package com.bretibad.tournoibretibad.fragment;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
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
import com.bretibad.tournoibretibad.service.RencontreService;
import com.bretibad.tournoibretibad.utils.RestResultReceiver;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class EquipeFragment extends Fragment {

	Spinner equipeSpinner;
	Spinner journeeSpinner;
	ListView rencontreJourneeListView;
	RencontreAdapter rencontreAdapter;
	int currentEquipe = 0;
	int currentJournee = 0;

	RencontreEquipeResponderFragment rencontreEquipeFragment;

	@Override
	public void onSaveInstanceState(Bundle outState) {
		outState.putInt("currentEquipe", currentEquipe);
		outState.putInt("currentJournee", currentJournee);
		super.onSaveInstanceState(outState);
	}
	
	@Override
	public void onResume() {
		super.onResume();
		getActivity().setTitle(R.string.resultatsEquipes);
	}

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
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(v.getContext(), R.array.equipes, android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
		// Apply the adapter to the spinner
		equipeSpinner.setAdapter(adapter);

		currentEquipe = savedInstanceState != null ? savedInstanceState.getInt("currentEquipe", 0) : 0;
		currentJournee = savedInstanceState != null ? savedInstanceState.getInt("currentJournee", 0) : 0;
		equipeSpinner.setSelection(currentEquipe);

		equipeSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(final AdapterView<?> parentView, View selectedItemView, int position, long id) {
				currentEquipe = position;
				refreshJourneeSpinner(position);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parentView) {
			}

		});

		journeeSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(final AdapterView<?> parentView, View selectedItemView, int position, long id) {
				currentJournee = position;
				refreshRencontre(position);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parentView) {
			}

		});

		return v;
	}

	private void refreshJourneeSpinner(int selectedEquipe) {
		final FragmentActivity currentActivity = getActivity();
		currentActivity.setProgressBarIndeterminateVisibility(true);

		Intent journeeIntent = RencontreService.getInstance(currentActivity).getJourneeIntent(new RestResultReceiver() {

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
					ArrayAdapter<String> journeeAdapter = new ArrayAdapter<String>(currentActivity, android.R.layout.simple_spinner_item, journees);
					journeeSpinner.setVisibility(View.VISIBLE);
					journeeSpinner.setAdapter(journeeAdapter);
					journeeSpinner.setSelection(currentJournee);

				} else {
					Toast.makeText(currentActivity, "Impossible de charger la liste des journées. Verifier votre connexion internet.", Toast.LENGTH_SHORT)
							.show();
				}
				currentActivity.setProgressBarIndeterminateVisibility(false);

			}
		}, selectedEquipe + 1);

		currentActivity.startService(journeeIntent);
	}

	private void refreshRencontre(int selectedJournee) {
		final FragmentActivity currentActivity = getActivity();
		currentActivity.setProgressBarIndeterminateVisibility(true);
		Intent rencontreIntent = RencontreService.getInstance(currentActivity).getRencontreIntent(new RestResultReceiver() {
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
					Toast.makeText(currentActivity, "Impossible de charger la liste des rencontres. Verifier votre connexion internet.", Toast.LENGTH_SHORT)
							.show();
				}
				currentActivity.setProgressBarIndeterminateVisibility(false);
			}
		}, equipeSpinner.getSelectedItemPosition() + 1, selectedJournee + 1);

		currentActivity.startService(rencontreIntent);
	}

	public void refreshRencontre(int equipe, int journee) {
		refreshRencontre(journee - 1);
	}
}
