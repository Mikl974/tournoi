package com.bretibad.tournoibretibad.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.util.Log;

import com.bretibad.tournoibretibad.model.Joueur;
import com.bretibad.tournoibretibad.utils.Config;

public class TournoiService {

	public static TournoiService instance = null;
	private String baseUrl = "";

	DefaultHttpClient client = new DefaultHttpClient();
	private Context context;
	private Config config;

	public TournoiService(Context context) {
		config = Config.getInstance(context);
		baseUrl = config.getProperty("baseUrl");
		this.context = context;
	}

	public Intent getTournoisIntent(ResultReceiver receiver) {
		Intent intent = new Intent(context, RESTService.class);
		intent.setData(Uri.parse(baseUrl + config.getProperty("listTournoi")));

		Bundle params = new Bundle();
		params.putString("q", "android");

		intent.putExtra(RESTService.EXTRA_PARAMS, params);
		intent.putExtra(RESTService.EXTRA_RESULT_RECEIVER, receiver);
		return intent;
	}

	public Intent getJoueursIntent(String tournoi, ResultReceiver receiver) {
		Intent intent = new Intent(context, RESTService.class);
		intent.setData(Uri.parse(baseUrl + config.getProperty("listInscrits") + tournoi));

		Bundle params = new Bundle();
		params.putString("q", "android");

		intent.putExtra(RESTService.EXTRA_PARAMS, params);
		intent.putExtra(RESTService.EXTRA_RESULT_RECEIVER, receiver);
		return intent;
	}

	public Intent getJoueursPayerIntent(String tournoi, List<Joueur> joueurs, boolean paid, ResultReceiver receiver) {
		Intent intent = new Intent(context, RESTService.class);
		intent.setData(Uri.parse(baseUrl + (paid ? config.getProperty("joueurPayer") : config.getProperty("joueurImpayer"))));

		Bundle params = new Bundle();
		params.putString("tournoi", tournoi);
		params.putString("licence", getStringLicenseList(joueurs));

		intent.putExtra(RESTService.EXTRA_HTTP_VERB, RESTService.POST);
		intent.putExtra(RESTService.EXTRA_PARAMS, params);
		intent.putExtra(RESTService.EXTRA_RESULT_RECEIVER, receiver);
		return intent;
	}

	public void joueurPayer(String tournoi, List<Joueur> joueurs) {
		try {
			HttpPost post = new HttpPost(baseUrl + config.getProperty("joueurPayer"));
			post.setEntity(new UrlEncodedFormEntity(getPostParam(tournoi, joueurs)));

			HttpResponse response = client.execute(post);
			HttpEntity entity = response.getEntity();
			String string = EntityUtils.toString(entity);
			string.length();
		} catch (Exception e) {
			Log.e("Error", "Error" + e.getMessage());
		}
	}

	public void joueurImpayer(String tournoi, List<Joueur> joueurs) {
		try {
			HttpPost post = new HttpPost(baseUrl + config.getProperty("joueurImpayer"));
			post.setEntity(new UrlEncodedFormEntity(getPostParam(tournoi, joueurs)));

			HttpResponse response = client.execute(post);
			HttpEntity entity = response.getEntity();
			String string = EntityUtils.toString(entity);
			string.length();
		} catch (Exception e) {
			Log.e("Error", "Error" + e.getMessage());
		}
	}
	
	public Intent getEquipeIntent(ResultReceiver receiver) {
		Intent intent = new Intent(context, RESTService.class);
		intent.setData(Uri.parse(baseUrl + config.getProperty("listEquipe")));

		Bundle params = new Bundle();
		params.putString("q", "android");

		intent.putExtra(RESTService.EXTRA_PARAMS, params);
		intent.putExtra(RESTService.EXTRA_RESULT_RECEIVER, receiver);
		return intent;
	}

	private List<NameValuePair> getPostParam(String tournoi, List<Joueur> joueurs) {

		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
		nameValuePairs.add(new BasicNameValuePair("licence", getStringLicenseList(joueurs)));
		nameValuePairs.add(new BasicNameValuePair("tournoi", tournoi));
		return nameValuePairs;
	}

	private String getStringLicenseList(List<Joueur> joueurs) {
		StringBuilder licenceList = new StringBuilder();
		int i = 0;
		for (Joueur j : joueurs) {
			licenceList.append("\"" + j.getLicence() + "\"");
			if (i < joueurs.size() - 1) {
				licenceList.append(",");
			}
			i++;
		}
		return licenceList.toString();
	}

	public static TournoiService getInstance(Context context) {
		if (instance == null) {
			instance = new TournoiService(context);
		}
		return instance;
	}
}
