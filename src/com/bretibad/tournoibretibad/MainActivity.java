package com.bretibad.tournoibretibad;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.widget.ListView;

import com.bretibad.tournoibretibad.R;
import com.bretibad.tournoibretibad.model.Tournoi;

public class MainActivity extends Activity {

	ListView tournoisListView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);

		List<Tournoi> tournois = TournoiService.getInstance().getTournois();

		tournoisListView = (ListView) findViewById(R.id.tournois_list);
		tournoisListView.setAdapter(new TournoisAdapter(
				getApplicationContext(), tournois));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
