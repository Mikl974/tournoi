package com.bretibad.tournoibretibad;

import java.util.List;

import uk.co.senab.actionbarpulltorefresh.library.ActionBarPullToRefresh;
import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshLayout;
import uk.co.senab.actionbarpulltorefresh.library.listeners.OnRefreshListener;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.ListView;

import com.bretibad.tournoibretibad.model.Tournoi;

public class MainActivity extends Activity implements OnRefreshListener {

	ListView tournoisListView;
	TournoisAdapter tournoisAdapter;
	PullToRefreshLayout mPullToRefreshLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);

		List<Tournoi> tournois = TournoiService.getInstance(
				getApplicationContext()).getTournois();

		tournoisListView = (ListView) findViewById(R.id.tournois_list);
		tournoisAdapter = new TournoisAdapter(getApplicationContext(),
				R.id.tournois_list, tournois);
		tournoisListView.setAdapter(tournoisAdapter);

		// Now find the PullToRefreshLayout to setup
		mPullToRefreshLayout = (PullToRefreshLayout) findViewById(R.id.ptr_layout);

		// Now setup the PullToRefreshLayout
		ActionBarPullToRefresh.from(this)
		// Mark All Children as pullable
				.allChildrenArePullable()
				// Set the OnRefreshListener
				.listener(this)
				// Finally commit the setup to our PullToRefreshLayout
				.setup(mPullToRefreshLayout);
	}

	@Override
	public void onRefreshStarted(View view) {
		/**
		 * Simulate Refresh with 4 seconds sleep
		 */
		new AsyncTask<Void, Void, Void>() {
			List<Tournoi> tournois;

			@Override
			protected Void doInBackground(Void... params) {
				tournois = TournoiService.getInstance(getApplicationContext())
						.getTournois();
				return null;
			}

			@Override
			protected void onPostExecute(Void result) {
				super.onPostExecute(result);
				tournoisAdapter.clear();
				tournoisAdapter.addAll(tournois);
				tournoisAdapter.notifyDataSetChanged();

				// Notify PullToRefreshLayout that the refresh has finished
				mPullToRefreshLayout.setRefreshComplete();
			}
		}.execute();
	}

}
