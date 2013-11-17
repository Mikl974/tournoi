package com.bretibad.tournoibretibad;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import android.content.Context;
import android.util.Log;

import com.bretibad.tournoibretibad.model.Joueur;
import com.bretibad.tournoibretibad.model.Tournoi;
import com.bretibad.tournoibretibad.utils.AssetsPropertyReader;

public class TournoiService {

	public static TournoiService instance = null;
	private String baseUrl = "";
	Properties config;

	DefaultHttpClient client = new DefaultHttpClient();
	AssetsPropertyReader assetsPropertyReader;

	public TournoiService(Context context) {
		assetsPropertyReader = new AssetsPropertyReader(context);
		config = assetsPropertyReader.getProperties("config.properties");
		baseUrl = config.getProperty("baseUrl");
	}

	public List<Tournoi> getTournois() {
		List<Tournoi> results = new ArrayList<Tournoi>();
		try {
			HttpGet get = new HttpGet(baseUrl
					+ config.getProperty("listTournoi"));
			HttpResponse resp = client.execute(get);
			String content = EntityUtils.toString(resp.getEntity());

			results = Tournoi.fromJsonArray(content);
		} catch (Exception e) {
			Log.e("Error", "Error" + e.getMessage());
		}
		return results;
	}

	public ArrayList<Joueur> getJoueursInscrits(String tournoi) {
		ArrayList<Joueur> results = new ArrayList<Joueur>();
		try {
			HttpGet get = new HttpGet(baseUrl
					+ config.getProperty("listInscrits") + tournoi);
			HttpResponse resp = client.execute(get);
			String content = EntityUtils.toString(resp.getEntity());

			results = Joueur.fromJsonArray(content);
		} catch (Exception e) {
			Log.e("Error", "Error" + e.getMessage());
		}
		return results;
	}

	public void joueurPayer(String tournoi, List<Joueur> joueurs) {
		try {
			HttpPost post = new HttpPost(baseUrl
					+ config.getProperty("joueurPayer"));
			post.setEntity(new UrlEncodedFormEntity(getPostParam(tournoi,
					joueurs)));

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
			HttpPost post = new HttpPost(baseUrl
					+ config.getProperty("joueurImpayer"));
			post.setEntity(new UrlEncodedFormEntity(getPostParam(tournoi,
					joueurs)));

			HttpResponse response = client.execute(post);
			HttpEntity entity = response.getEntity();
			String string = EntityUtils.toString(entity);
			string.length();
		} catch (Exception e) {
			Log.e("Error", "Error" + e.getMessage());
		}

	}

	private List<NameValuePair> getPostParam(String tournoi,
			List<Joueur> joueurs) {
		StringBuilder licenceList = new StringBuilder();
		int i = 0;
		for (Joueur j : joueurs) {
			licenceList.append("\"" + j.getLicence() + "\"");
			if (i < joueurs.size() - 1) {
				licenceList.append(",");
			}
			i++;
		}

		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
		nameValuePairs.add(new BasicNameValuePair("licence", licenceList
				.toString()));
		nameValuePairs.add(new BasicNameValuePair("tournoi", tournoi));
		return nameValuePairs;
	}

	public static TournoiService getInstance(Context context) {
		if (instance == null) {
			instance = new TournoiService(context);
		}
		return instance;
	}
}
