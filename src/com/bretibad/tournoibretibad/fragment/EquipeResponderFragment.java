package com.bretibad.tournoibretibad.fragment;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import android.R.integer;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
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

import com.bretibad.tournoibretibad.MainActivity;
import com.bretibad.tournoibretibad.R;
import com.bretibad.tournoibretibad.adpter.RencontreAdapter;
import com.bretibad.tournoibretibad.model.Equipe;
import com.bretibad.tournoibretibad.model.Rencontre;
import com.bretibad.tournoibretibad.model.Tournoi;
import com.bretibad.tournoibretibad.service.RencontreService;
import com.bretibad.tournoibretibad.service.TournoiService;
import com.bretibad.tournoibretibad.utils.RestResultReceiver;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class EquipeResponderFragment extends RESTResponderFragment {

	Spinner equipeSpinner;
	Spinner journeeSpinner;
	ListView rencontreJourneeListView;
	RencontreAdapter rencontreAdapter;
	int currentEquipe = 0;
	int currentJournee = 0;
	List<Equipe> mEquipes;
	ArrayAdapter<String> equipeAdapter;
	RencontreEquipeResponderFragment rencontreEquipeFragment;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setEquipes();
	}

	private void setEquipes() {

		MainActivity activity = (MainActivity) getActivity();

		if (mEquipes == null && activity != null) {
			Intent equipeIntent = TournoiService.getInstance(activity).getEquipeIntent(getResultReceiver());
			activity.startService(equipeIntent);
		} else if (activity != null) {
			equipeAdapter.clear();
			for (Equipe t : mEquipes) {
				equipeAdapter.add("Equipe " + t.getNum() + " - " + t.getDivision());
			}

			activity.setProgressBarIndeterminateVisibility(false);
			// Notify PullToRefreshLayout that the refresh has finished
		}

	}

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
		equipeAdapter = new ArrayAdapter<String>(v.getContext(), android.R.layout.simple_spinner_item, new ArrayList<String>());
		equipeSpinner.setAdapter(equipeAdapter);

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
					if (rencontres != null) {
						for (Rencontre rencontre : rencontres) {
							journees.add("J" + rencontre.getJournee());
						}
					} else {
						rencontreAdapter.clear();
					}
					ArrayAdapter<String> journeeAdapter = new ArrayAdapter<String>(currentActivity, android.R.layout.simple_spinner_item, journees);
					journeeSpinner.setVisibility(View.VISIBLE);
					journeeSpinner.setAdapter(journeeAdapter);
					journeeSpinner.setSelection(currentJournee);

				} else {
					Toast.makeText(currentActivity, "Impossible de charger la liste des journées. Verifier votre connexion internet.",
							Toast.LENGTH_SHORT).show();
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
					Toast.makeText(currentActivity, "Impossible de charger la liste des rencontres. Verifier votre connexion internet.",
							Toast.LENGTH_SHORT).show();
				}
				currentActivity.setProgressBarIndeterminateVisibility(false);
			}
		}, equipeSpinner.getSelectedItemPosition() + 1, selectedJournee + 1);

		currentActivity.startService(rencontreIntent);
	}

	public void refreshRencontre(int equipe, int journee) {
		refreshRencontre(journee - 1);
	}

	@Override
	public void onRESTResult(int code, String result) {
		if (code == 200 && result != null) {
			Type equipeType = new TypeToken<List<Equipe>>() {
			}.getType();
			Gson gson = new Gson();
			List<Equipe> equipes = gson.fromJson(result, equipeType);

			mEquipes = equipes;
			setEquipes();
		} else {
			Activity activity = getActivity();
			if (activity != null) {
				Toast.makeText(activity, "Impossible de charger la liste des equipes. Verifier votre connexion internet.", Toast.LENGTH_SHORT).show();
			}
		}
	}
}
