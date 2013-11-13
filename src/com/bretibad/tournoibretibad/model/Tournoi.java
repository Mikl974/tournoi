package com.bretibad.tournoibretibad.model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Tournoi {

	private String nom;

	public Tournoi() {
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public static Tournoi fromJson(JSONObject jsonTournoi) {
		try {
			Tournoi t = new Tournoi();
			t.setNom(jsonTournoi.getString("nom"));
			return t;
		} catch (JSONException e) {
			System.err.println(e.getMessage());
			return null;
		}
	}

	public static List<Tournoi> fromJsonArray(String json) {
		List<Tournoi> results = new ArrayList<Tournoi>();
		try {
			JSONArray array = new JSONArray(json);
			for (int i = 0; i < array.length(); i++) {
				results.add(Tournoi.fromJson(array.getJSONObject(i)));
			}
		} catch (JSONException e) {
			System.err.println(e.getMessage());
		}
		return results;
	}
}
