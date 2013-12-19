package com.bretibad.tournoibretibad.service;

import org.apache.http.impl.client.DefaultHttpClient;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.ResultReceiver;

import com.bretibad.tournoibretibad.model.Rencontre;
import com.bretibad.tournoibretibad.utils.Config;

public class RencontreService {

	public static RencontreService instance = null;
	private String baseUrl = "";

	DefaultHttpClient client = new DefaultHttpClient();
	private Context context;
	private Config config;

	public RencontreService(Context context) {
		config = Config.getInstance(context);
		baseUrl = config.getProperty("baseUrl");
		this.context = context;
	}

	public Intent getRencontreIntent(ResultReceiver receiver) {
		Intent intent = new Intent(context, RESTService.class);
		intent.setData(Uri.parse(baseUrl + config.getProperty("rencontre")));

		intent.putExtra(RESTService.EXTRA_RESULT_RECEIVER, receiver);
		return intent;
	}

	public Intent getRencontreIntent(ResultReceiver receiver, int numEquipe, int journee) {
		Intent intent = new Intent(context, RESTService.class);
		intent.setData(Uri.parse(baseUrl + config.getProperty("rencontre") + "/" + numEquipe + "/" + journee));

		intent.putExtra(RESTService.EXTRA_RESULT_RECEIVER, receiver);
		return intent;
	}

	public Intent getUpdateResultatsIntent(int numEquipe, int journee, String champsSetp, String valueSetp, String champsSetc, String valueSetc,
			String finMatchChamps, String valueFinMatch, ResultReceiver receiver) {
		Intent intent = new Intent(context, RESTService.class);
		intent.setData(Uri.parse(baseUrl + config.getProperty("updateResultats")));

		Bundle params = new Bundle();
		params.putInt("numEquipe", numEquipe);
		params.putInt("journee", journee);
		params.putString("champsSetp", champsSetp);
		params.putString("valueSetp", valueSetp);
		params.putString("champsSetc", champsSetc);
		params.putString("valueSetc", valueSetc);
		params.putString("finMatchChamps", finMatchChamps);
		params.putString("valueFinMatch", valueFinMatch);

		intent.putExtra(RESTService.EXTRA_HTTP_VERB, RESTService.POST);
		intent.putExtra(RESTService.EXTRA_PARAMS, params);
		intent.putExtra(RESTService.EXTRA_RESULT_RECEIVER, receiver);
		return intent;
	}

	public Intent getJourneeIntent(ResultReceiver receiver, int numequipe) {
		Intent intent = new Intent(context, RESTService.class);
		intent.setData(Uri.parse(baseUrl + config.getProperty("getJournee") + numequipe));

		intent.putExtra(RESTService.EXTRA_RESULT_RECEIVER, receiver);
		return intent;
	}

	public Intent getListJoueurIntent(ResultReceiver receiver) {
		Intent intent = new Intent(context, RESTService.class);
		intent.setData(Uri.parse(baseUrl + config.getProperty("listJoueur")));

		intent.putExtra(RESTService.EXTRA_RESULT_RECEIVER, receiver);
		return intent;
	}

	public Intent getUpdateRencontreIntent(Rencontre r, ResultReceiver receiver) {
		Intent intent = new Intent(context, RESTService.class);
		intent.setData(Uri.parse(baseUrl + config.getProperty("updateRencontre")));

		Bundle params = new Bundle();
		params.putInt("numequipe", r.getNumequipe());
		params.putInt("journee", r.getJournee());

		params.putString("matchpour", r.getMatchpour() != null ? (r.getMatchpour() + "") : "");
		params.putString("matchcontre", r.getMatchcontre() != null ? (r.getMatchcontre() + "") : "");

		params.putString("finmatch", r.getFinmatch());
		params.putInt("live", r.getLive());

		params.putString("sh1", r.getSh1());
		params.putString("sh2", r.getSh2());
		if (!r.getDivision().equals("Reg3")) {
			params.putString("sh3", r.getSh3());
		}
		params.putString("sd1", r.getSd1());
		if (r.getDivision().equals("Reg3")) {
			params.putString("sd2", r.getSd2());
		}
		params.putString("dh1", r.getDh1());
		params.putString("dd1", r.getDd1());
		params.putString("dx1", r.getDx1());
		if (r.getDivision().equals("Reg3")) {
			params.putString("dx2", r.getDx2());
		}

		intent.putExtra(RESTService.EXTRA_HTTP_VERB, RESTService.POST);
		intent.putExtra(RESTService.EXTRA_PARAMS, params);
		intent.putExtra(RESTService.EXTRA_RESULT_RECEIVER, receiver);
		return intent;
	}

	public static RencontreService getInstance(Context context) {
		if (instance == null) {
			instance = new RencontreService(context);
		}
		return instance;
	}
}
