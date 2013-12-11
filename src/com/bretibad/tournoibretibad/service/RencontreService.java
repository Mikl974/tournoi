package com.bretibad.tournoibretibad.service;

import org.apache.http.impl.client.DefaultHttpClient;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.ResultReceiver;

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
	
	public Intent getUpdateResultatsIntent(int numEquipe, int journee, String champs, String set, ResultReceiver receiver) {
		Intent intent = new Intent(context, RESTService.class);
		intent.setData(Uri.parse(baseUrl + config.getProperty("updateResultats")));

		Bundle params = new Bundle();
		params.putInt("numEquipe", numEquipe);
		params.putInt("journee", journee);
		params.putString("champs", champs);
		params.putString("set", set);

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
