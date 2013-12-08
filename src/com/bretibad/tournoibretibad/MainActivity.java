package com.bretibad.tournoibretibad;

import java.util.Arrays;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.bretibad.tournoibretibad.fragment.JoueursResponderFragment;
import com.bretibad.tournoibretibad.fragment.RencontreResponderFragment;
import com.bretibad.tournoibretibad.fragment.TournoisResponderFragment;
import com.bretibad.tournoibretibad.model.Tournoi;
import com.bretibad.tournoibretibad.utils.Config;
import com.parse.Parse;
import com.parse.ParseInstallation;
import com.parse.PushService;

public class MainActivity extends FragmentActivity {

	TournoisResponderFragment tournoiListFragment;
	RencontreResponderFragment rencontreFragment;

	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;

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

		initDrawer();
	}

	@Override
	public void onResume() {
		super.onResume();
		setTitle(R.string.app_name);
	}

	private void initParse() {
		Config config = Config.getInstance(this);
		Parse.initialize(this, config.getProperty("pushApplicationId"), config.getProperty("pushApplicationClientKey"));
		PushService.subscribe(this, "News", MainActivity.class);
		ParseInstallation.getCurrentInstallation().saveInBackground();
	}

	// public static class JoueursActivity extends FragmentActivity {
	//
	// @Override
	// protected void onCreate(Bundle savedInstanceState) {
	// super.onCreate(savedInstanceState);
	// requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
	// setProgressBarIndeterminateVisibility(true);
	// setContentView(R.layout.activity_joueurs_list);
	//
	// if ((getResources().getConfiguration().screenLayout &
	// Configuration.SCREENLAYOUT_SIZE_MASK) >=
	// Configuration.SCREENLAYOUT_SIZE_LARGE
	// && getResources().getConfiguration().orientation ==
	// Configuration.ORIENTATION_LANDSCAPE) {
	// // If the screen is now in landscape mode, we can show the
	// // dialog in-line with the list so we don't need this activity.
	// finish();
	// return;
	// }
	//
	// if (savedInstanceState == null) {
	// JoueursResponderFragment details = (JoueursResponderFragment)
	// getSupportFragmentManager().findFragmentByTag("JoueurRESTResponder");
	//
	// if (details == null) {
	// details = new JoueursResponderFragment();
	// }
	// details.setArguments(getIntent().getExtras());
	// getSupportFragmentManager().beginTransaction().replace(R.id.fragment_joueurs,
	// details).commit();
	// }
	// }
	// }

	public void showJoueur(Tournoi tournoi) {

		if ((getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE
				&& getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
			JoueursResponderFragment details = (JoueursResponderFragment) getSupportFragmentManager().findFragmentByTag("JoueurRESTResponder");

			if (details == null) {
				details = JoueursResponderFragment.newInstance(tournoi.getNom());
			}
			FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
			ft.replace(R.id.fragment_joueurs, details);
			ft.addToBackStack(null);
			ft.commit();

		} else {
			JoueursResponderFragment details = (JoueursResponderFragment) getSupportFragmentManager().findFragmentByTag("JoueurRESTResponder");

			if (details == null) {
				details = JoueursResponderFragment.newInstance(tournoi.getNom());
			}
			FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
			ft.replace(R.id.fragment_content, details);
			ft.addToBackStack(null);
			ft.commit();

			// Intent intent = new Intent();
			// intent.setClass(this, JoueursActivity.class);
			// intent.putExtra("tournoi", tournoi.getNom());
			// startActivity(intent);
		}
	}

	private void initDrawer() {
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);

		// Set the adapter for the list view
		mDrawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_list_item, Arrays.asList("Tournoi", "Rencontres IC")));
		// Set the list's click listener
		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

		mDrawerToggle = new ActionBarDrawerToggle(this, /* host Activity */
		mDrawerLayout, /* DrawerLayout object */
		R.drawable.ic_drawer, /* nav drawer icon to replace 'Up' caret */
		R.string.drawer_open, /* "open drawer" description */
		R.string.drawer_close /* "close drawer" description */
		) {
		};

		// Set the drawer toggle as the DrawerListener
		mDrawerLayout.setDrawerListener(mDrawerToggle);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Pass the event to ActionBarDrawerToggle, if it returns
		// true, then it has handled the app icon touch event
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		// Handle your other action bar items...

		return super.onOptionsItemSelected(item);
	}

	/* The click listner for ListView in the navigation drawer */
	private class DrawerItemClickListener implements ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			selectItem(position);
		}
	}

	private void selectItem(int position) {
		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();

		switch (position) {
		case 0:
			tournoiListFragment = (TournoisResponderFragment) fm.findFragmentByTag("RESTResponder");
			if (tournoiListFragment == null) {
				tournoiListFragment = new TournoisResponderFragment();
			}
			ft.replace(R.id.fragment_content, tournoiListFragment, "RESTResponder");
			ft.addToBackStack(null);
			ft.commit();

			break;

		case 1:
			rencontreFragment = (RencontreResponderFragment) fm.findFragmentByTag("RencontreResponderFragment");
			if (rencontreFragment == null) {
				rencontreFragment = new RencontreResponderFragment();
			}
			ft.replace(R.id.fragment_content, rencontreFragment, "RencontreResponderFragment");
			ft.addToBackStack(null);
			ft.commit();

			break;
		default:
			break;
		}

		// update selected item and title, then close the drawer
		mDrawerList.setItemChecked(position, true);
		mDrawerList.setSelection(position);
		mDrawerLayout.closeDrawer(mDrawerList);
		// Intent in = new Intent(MainActivity.this, NewsActivity.class);
		// startActivity(in);
	}
}
