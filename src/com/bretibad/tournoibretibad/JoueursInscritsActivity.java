package com.bretibad.tournoibretibad;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ListView;

import com.bretibad.tournoibretibad.model.Joueur;

public class JoueursInscritsActivity extends Activity {

	ListView joueursListView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.joueurs_inscripts);

		ArrayList<Joueur> joueurs = getIntent().getExtras()
				.getParcelableArrayList("joueurs");

		joueursListView = (ListView) findViewById(R.id.joueurs_list);
		joueursListView.setAdapter(new JoueurAdapter(getApplicationContext(),
				joueurs));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
