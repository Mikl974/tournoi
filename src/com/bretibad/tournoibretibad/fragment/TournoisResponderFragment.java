package com.bretibad.tournoibretibad.fragment;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import uk.co.senab.actionbarpulltorefresh.library.ActionBarPullToRefresh;
import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshLayout;
import uk.co.senab.actionbarpulltorefresh.library.listeners.OnRefreshListener;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.bretibad.tournoibretibad.R;
import com.bretibad.tournoibretibad.MainActivity;
import com.bretibad.tournoibretibad.adpter.TournoisAdapter;
import com.bretibad.tournoibretibad.model.Tournoi;
import com.bretibad.tournoibretibad.service.TournoiService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class TournoisResponderFragment extends RESTResponderFragment implements OnRefreshListener {

	private List<Tournoi> mTournois;
	ListView listView;
	TournoisAdapter adapter;
	private int currentPosition = 0;
	PullToRefreshLayout mPullToRefreshLayout;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setTournois();
	}
	
	@Override
	public void onResume() {
		super.onResume();
		getActivity().setTitle(R.string.app_name);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActivity().setProgressBarIndeterminateVisibility(true);
		if (savedInstanceState != null) {
			currentPosition = savedInstanceState.getInt("currentTournoiPosition", 0);
			mTournois = savedInstanceState.getParcelableArrayList("tournois");
		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt("currentTournoiPosition", currentPosition);
		outState.putParcelableArrayList("tournois", mTournois != null ? new ArrayList<Tournoi>(mTournois) : null);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.tournoi_list, container, false);
		adapter = new TournoisAdapter(getActivity(), R.layout.tournoi_list, new ArrayList<Tournoi>());
		listView = (ListView) v.findViewById(R.id.tournois_listview);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Tournoi tournoi = mTournois != null ? mTournois.get(position) : null;
				currentPosition = position;
				MainActivity activity = (MainActivity) getActivity();
				activity.showJoueur(tournoi);
			}
		});
		return v;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		// Now find the PullToRefreshLayout to setup
		mPullToRefreshLayout = (PullToRefreshLayout) getActivity().findViewById(R.id.pull_tournoi_layout);

		// Now setup the PullToRefreshLayout
		ActionBarPullToRefresh.from(getActivity())
		// Mark All Children as pullable
				.allChildrenArePullable()
				// Set the OnRefreshListener
				.listener(this)
				// Finally commit the setup to our PullToRefreshLayout
				.setup(mPullToRefreshLayout);
	}

	public void refresh() {
		mTournois = null;
		setTournois();
	}

	private void setTournois() {
		MainActivity activity = (MainActivity) getActivity();

		if (mTournois == null && activity != null) {
			Intent tournoisIntent = TournoiService.getInstance(activity).getTournoisIntent(getResultReceiver());
			activity.startService(tournoisIntent);
		} else if (activity != null) {
			adapter.clear();
			for (Tournoi t : mTournois) {
				adapter.add(t);
			}

			if ((getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE
					&& getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
				activity.showJoueur(mTournois.get(currentPosition));
			}

			activity.setProgressBarIndeterminateVisibility(false);
			// Notify PullToRefreshLayout that the refresh has finished
			mPullToRefreshLayout.setRefreshComplete();
		}
	}

	@Override
	public void onRESTResult(int code, String result) {
		if (code == 200 && result != null) {
			Type tournoiType = new TypeToken<List<Tournoi>>() {
			}.getType();
			Gson gson = new Gson();
			List<Tournoi> tournois = gson.fromJson(result, tournoiType);

			mTournois = tournois;
			setTournois();
		} else {
			Activity activity = getActivity();
			if (activity != null) {
				Toast.makeText(activity, "Impossible de charger la liste des tournois. Verifier votre connexion internet.", Toast.LENGTH_SHORT)
						.show();
			}
		}
	}

	@Override
	public void onRefreshStarted(View view) {
		refresh();
	}

}
