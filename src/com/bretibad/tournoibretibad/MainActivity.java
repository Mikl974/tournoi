package com.bretibad.tournoibretibad;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Window;

import com.bretibad.tournoibretibad.fragment.JoueursResponderFragment;
import com.bretibad.tournoibretibad.fragment.TournoisResponderFragment;
import com.bretibad.tournoibretibad.model.Tournoi;
import com.bretibad.tournoibretibad.utils.Config;
import com.parse.Parse;
import com.parse.PushService;

public class MainActivity extends FragmentActivity {

	TournoisResponderFragment tournoiListFragment;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		initParse();

		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setProgressBarIndeterminateVisibility(false);

		setContentView(R.layout.activity_rest_service);

		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();

		tournoiListFragment = (TournoisResponderFragment) fm.findFragmentByTag("RESTResponder");
		if (tournoiListFragment == null) {
			tournoiListFragment = new TournoisResponderFragment();

			ft.add(R.id.fragment_content, tournoiListFragment, "RESTResponder");
		}

		ft.commit();

	}

	private void initParse() {
		Config config = Config.getInstance(this);
		Parse.initialize(this, config.getProperty("pushApplicationId"), config.getProperty("pushApplicationClientKey"));

		PushService.subscribe(this, "News", MainActivity.class);
	}

	/**
	 * This is a secondary activity, to show what the user has selected when the
	 * screen is not large enough to show it all in one activity.
	 */

	public static class JoueursActivity extends FragmentActivity {

		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
			setProgressBarIndeterminateVisibility(true);
			setContentView(R.layout.activity_joueurs_list);

			if ((getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE
					&& getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
				// If the screen is now in landscape mode, we can show the
				// dialog in-line with the list so we don't need this activity.
				finish();
				return;
			}

			if (savedInstanceState == null) {
				JoueursResponderFragment details = (JoueursResponderFragment) getSupportFragmentManager().findFragmentByTag("JoueurRESTResponder");

				if (details == null) {
					details = new JoueursResponderFragment();
				}
				details.setArguments(getIntent().getExtras());
				getSupportFragmentManager().beginTransaction().replace(R.id.fragment_joueurs, details).commit();
			}
		}
	}

	public void showJoueur(Tournoi tournoi) {

		if ((getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE
				&& getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
			JoueursResponderFragment details = (JoueursResponderFragment) getSupportFragmentManager().findFragmentByTag("JoueurRESTResponder");

			if (details == null) {
				details = JoueursResponderFragment.newInstance(tournoi.getNom());
			}
			FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
			ft.replace(R.id.fragment_joueurs, details);
			// ft.addToBackStack(null);
			ft.commit();

		} else {
			Intent intent = new Intent();
			intent.setClass(this, JoueursActivity.class);
			intent.putExtra("tournoi", tournoi.getNom());
			startActivity(intent);
		}

	}
}
