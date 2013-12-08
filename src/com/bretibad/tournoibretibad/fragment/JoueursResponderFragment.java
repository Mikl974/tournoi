package com.bretibad.tournoibretibad.fragment;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.bretibad.tournoibretibad.R;
import com.bretibad.tournoibretibad.adpter.JoueurAdapter;
import com.bretibad.tournoibretibad.model.Joueur;
import com.bretibad.tournoibretibad.service.TournoiService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class JoueursResponderFragment extends RESTResponderFragment {

	private List<Joueur> mJoueurs;
	private String tournoi;
	JoueurAdapter joueurAdapter;
	ListView listView;
	private ActionMode mActionMode;
	private boolean isInActionMode = false;

	public static JoueursResponderFragment newInstance(String tournoi) {
		JoueursResponderFragment f = new JoueursResponderFragment();

		// Supply index input as an argument.
		Bundle args = new Bundle();
		args.putString("tournoi", tournoi);
		f.setArguments(args);

		return f;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActivity().setProgressBarIndeterminateVisibility(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.joueurs_inscripts, container, false);
		listView = (ListView) v.findViewById(android.R.id.list);
		listView.setEmptyView(v.findViewById(android.R.id.empty));
		return v;
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		outState.putBoolean("ActionMode", isInActionMode);
		outState.putIntArray("selecedJoueur", joueurAdapter.getSelectedIdsArray());
		super.onSaveInstanceState(outState);
	}
	
	@Override
	public void onResume() {
		super.onResume();
		getActivity().setTitle("Tournoi " + tournoi);
	}

	@Override
	public void onViewCreated(View view, final Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		tournoi = getArguments().getString("tournoi");

		joueurAdapter = new JoueurAdapter(getActivity(), android.R.id.list, new ArrayList<Joueur>(), tournoi);
		listView.setAdapter(joueurAdapter);

		if (savedInstanceState != null && savedInstanceState.getBoolean("ActionMode", false)) {
			int[] intArray = savedInstanceState.getIntArray("selecedJoueur");
			for (int i : intArray) {
				joueurAdapter.toggleSelection(i);
				listView.setItemChecked(i, false);
			}
			ActionModeCallback actionModeCallback = new ActionModeCallback();
			mActionMode = getActivity().startActionMode(actionModeCallback);
			if (mActionMode != null) {
				mActionMode.setTitle(String.valueOf(joueurAdapter.getSelectedCount()) + " selected");
			}
		}

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> a, View view, int position, long id) {
				joueurAdapter.toggleSelection(position);
				boolean hasCheckedItems = joueurAdapter.getSelectedCount() > 0;

				if (hasCheckedItems && mActionMode == null) {
					ActionModeCallback actionModeCallback = new ActionModeCallback();
					mActionMode = getActivity().startActionMode(actionModeCallback);
				} else if (!hasCheckedItems && mActionMode != null) {
					mActionMode.finish();
				}

				if (mActionMode != null) {
					mActionMode.setTitle(String.valueOf(joueurAdapter.getSelectedCount()) + " selected");
				}
			}
		});
		setJoueurs();
	}

	private void setJoueurs() {
		Activity activity = getActivity();
		listView.getEmptyView().setVisibility(ListView.GONE);
		if (tournoi != null && mJoueurs == null && activity != null) {
			Intent joueursIntent = TournoiService.getInstance(activity).getJoueursIntent(tournoi, getResultReceiver());
			activity.startService(joueursIntent);
		} else if (activity != null) {
			joueurAdapter.clear();
			for (Joueur joueur : mJoueurs) {
				joueurAdapter.add(joueur);
			}
			getActivity().setProgressBarIndeterminateVisibility(false);
		}
	}

	@Override
	public void onRESTResult(int code, String result) {
		if (code == 200 && result != null) {

			Type joueurType = new TypeToken<List<Joueur>>() {
			}.getType();
			Gson gson = new Gson();
			List<Joueur> joueurs = gson.fromJson(result, joueurType);
			mJoueurs = joueurs != null ? joueurs : new ArrayList<Joueur>();
			setJoueurs();
		} else {
			Activity activity = getActivity();
			if (activity != null) {
				Toast.makeText(activity, "Failed to load Joueurs inscrit. Check your internet settings.", Toast.LENGTH_SHORT).show();
			}
		}
	}

	private class ActionModeCallback implements ActionMode.Callback {

		@Override
		public boolean onCreateActionMode(ActionMode mode, Menu menu) {
			// inflate contextual menu
			mode.getMenuInflater().inflate(R.menu.joueur_menu, menu);
			isInActionMode = true;
			return true;
		}

		@Override
		public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
			return false;
		}

		@Override
		public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
			List<Joueur> joueurs = getSelectedJoueurs();
			switch (item.getItemId()) {
			case R.id.inscrire_joueur:
				getActivity().setProgressBarIndeterminateVisibility(true);
				Intent joueursPayerIntent = TournoiService.getInstance(getActivity().getApplicationContext()).getJoueursPayerIntent(tournoi, joueurs,
						true, new ResultReceiver(new Handler()) {

							@Override
							protected void onReceiveResult(int resultCode, Bundle resultData) {
								refreshListJoueur();
							}

						});
				getActivity().startService(joueursPayerIntent);
				mode.finish();

				return true;
			case R.id.joueurImpayer:
				getActivity().setProgressBarIndeterminateVisibility(true);
				Intent joueursImpayerIntent = TournoiService.getInstance(getActivity().getApplicationContext()).getJoueursPayerIntent(tournoi,
						joueurs, false, new ResultReceiver(new Handler()) {

							@Override
							protected void onReceiveResult(int resultCode, Bundle resultData) {
								refreshListJoueur();
							}

						});
				getActivity().startService(joueursImpayerIntent);
				mode.finish();

				return true;
			default:
				return false;
			}

		}

		private void refreshListJoueur() {
			joueurAdapter.clear();
			mJoueurs = null;
			setJoueurs();
		}

		private List<Joueur> getSelectedJoueurs() {
			// retrieve selected items and delete them out
			SparseBooleanArray selected = listView.getCheckedItemPositions();
			List<Joueur> joueurs = new ArrayList<Joueur>();
			for (int i = (selected.size() - 1); i >= 0; i--) {
				if (selected.valueAt(i)) {
					joueurs.add(joueurAdapter.getItem(selected.keyAt(i)));
				}
			}
			return joueurs;
		}

		@Override
		public void onDestroyActionMode(ActionMode mode) {
			isInActionMode = false;
			SparseBooleanArray checkedItemPositions = listView.getCheckedItemPositions();
			for (int i = 0; i < checkedItemPositions.size(); i++) {
				if (checkedItemPositions.valueAt(i)) {
					listView.setItemChecked(i, false);
				}
			}
			joueurAdapter.removeSelection();
			mActionMode = null;
		}
	}
}
