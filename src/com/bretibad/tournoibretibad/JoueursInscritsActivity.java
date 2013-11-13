package com.bretibad.tournoibretibad;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.bretibad.tournoibretibad.model.Joueur;

public class JoueursInscritsActivity extends Activity {

	ListView joueursListView;
	JoueurAdapter joueurAdapter;
	private ActionMode mActionMode;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.joueurs_inscripts);

		final ArrayList<Joueur> joueurs = getIntent().getExtras()
				.getParcelableArrayList("joueurs");
		String tournoi = getIntent().getExtras().getString("tournoi");

		joueursListView = (ListView) findViewById(R.id.joueurs_list);
		joueurAdapter = new JoueurAdapter(getApplicationContext(),
				R.id.joueurs_list, joueurs, tournoi);
		joueursListView.setAdapter(joueurAdapter);

		setTitle("Tournoi " + tournoi);

		joueursListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> a, View view, int position,
					long id) {
				joueurAdapter.toggleSelection(position);
				boolean hasCheckedItems = joueurAdapter.getSelectedCount() > 0;
				if (hasCheckedItems && mActionMode == null) {
					mActionMode = startActionMode(new ActionModeCallback());
				} else if (!hasCheckedItems && mActionMode != null) {
					mActionMode.finish();
				}

				if (mActionMode != null) {
					mActionMode.setTitle(String.valueOf(joueurAdapter
							.getSelectedCount()) + " selected");
				}
				// ListView listView = (ListView) a;
				// SparseBooleanArray selectedItems = listView
				// .getCheckedItemPositions();
				// StringBuilder sb = new StringBuilder("Name selected: ");
				// boolean isSelected = false;
				// for (int i = 0; i < selectedItems.size(); i++) {
				// if (selectedItems.get(i)) {
				// isSelected = true;
				// sb.append(joueurs.get(i).getNom() + ", ");
				// }
				// }
				// Toast.makeText(JoueursInscritsActivity.this, sb.toString(),
				// Toast.LENGTH_LONG).show();
				//
				// if (isSelected) {
				// }

			}
		});
	}

	// @Override
	// public boolean onCreateOptionsMenu(Menu menu) {
	// getMenuInflater().inflate(R.menu.joueur_menu, menu);
	// return true;
	// }

	private class ActionModeCallback implements ActionMode.Callback {

		@Override
		public boolean onCreateActionMode(ActionMode mode, Menu menu) {
			// inflate contextual menu
			mode.getMenuInflater().inflate(R.menu.joueur_menu, menu);
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
				TournoiService.getInstance(getApplicationContext())
						.joueurPayer(joueurAdapter.getTournoi(), joueurs);
				mode.finish(); // Action picked, so close the CAB

				refreshListJoueur();

				return true;
			case R.id.joueurImpayer:
				TournoiService.getInstance(getApplicationContext())
						.joueurImpayer(joueurAdapter.getTournoi(), joueurs);
				mode.finish(); // Action picked, so close the CAB

				refreshListJoueur();

				return true;
			default:
				return false;
			}

		}

		private void refreshListJoueur() {
			joueurAdapter.clear();
			joueurAdapter.addAll(TournoiService.getInstance(
					getApplicationContext()).getJoueursInscrits(
					joueurAdapter.getTournoi()));
			joueurAdapter.notifyDataSetChanged();
		}

		private List<Joueur> getSelectedJoueurs() {
			// retrieve selected items and delete them out
			SparseBooleanArray selected = joueurAdapter.getSelectedIds();
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
			joueurAdapter.removeSelection();
			mActionMode = null;
		}
	}

}
