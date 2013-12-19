package com.bretibad.tournoibretibad.fragment;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.bretibad.tournoibretibad.MainActivity;
import com.bretibad.tournoibretibad.R;
import com.bretibad.tournoibretibad.adpter.RencontreAdapter;
import com.bretibad.tournoibretibad.model.Rencontre;
import com.bretibad.tournoibretibad.service.RencontreService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class RencontreEquipeResponderFragment extends RESTResponderFragment {

	List<Rencontre> rencontres;
	int numEquipe;
	int journee;

	ListView listView;
	RencontreAdapter adapter;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setRencontres(numEquipe, journee);
	}

	@Override
	public void onResume() {
		super.onResume();
		getActivity().setTitle(R.string.rencontreEnCours);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActivity().setProgressBarIndeterminateVisibility(true);
		if (savedInstanceState != null) {
			// rencontres =
			// savedInstanceState.getParcelableArrayList("rencontres");
		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putParcelableArrayList("rencontres", rencontres != null ? new ArrayList<Rencontre>(rencontres) : null);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.rencontre_list, container, false);
		adapter = new RencontreAdapter(getActivity(), R.layout.rencontre_list, new ArrayList<Rencontre>());
		// listView = (ListView) v.findViewById(R.id.rencontres_listview);
		listView = (ListView) v.findViewById(android.R.id.list);
		listView.setEmptyView(v.findViewById(android.R.id.empty));
		listView.setAdapter(adapter);
		return v;
	}

	private void setRencontres(int numequipe, int journee) {
		MainActivity activity = (MainActivity) getActivity();

		if (rencontres == null && activity != null) {
			Intent rencontreIntent = RencontreService.getInstance(activity).getRencontreIntent(getResultReceiver(), numequipe, journee);
			activity.startService(rencontreIntent);
		} else if (activity != null) {
			adapter.clear();
			for (Rencontre r : rencontres) {
				adapter.add(r);
			}
			activity.setProgressBarIndeterminateVisibility(false);
		}
	}

	@Override
	public void onRESTResult(int code, String result) {
		if (code == 200 && result != null) {
			Type rencontreType = new TypeToken<List<Rencontre>>() {
			}.getType();
			Gson gson = new Gson();
			List<Rencontre> rencontres = gson.fromJson(result, rencontreType);

			this.rencontres = rencontres;
			setRencontres(numEquipe, journee);
		} else {
			Activity activity = getActivity();
			if (activity != null) {
				Toast.makeText(activity, "Impossible de charger la liste des rencontre en cours. Verifier votre connexion internet.",
						Toast.LENGTH_SHORT).show();
			}
		}
	}

	public List<Rencontre> getRencontres() {
		return rencontres;
	}

	public void setRencontres(List<Rencontre> rencontres, int numequipe, int journee) {
		this.rencontres = rencontres;
		this.numEquipe = numequipe;
		this.journee = journee;
	}
}
