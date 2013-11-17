package com.bretibad.tournoibretibad.model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Tournoi {

	private String nom;
	private String date;
	private String dateLimite;
	private String tarif;

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getDateLimite() {
		return dateLimite;
	}

	public void setDateLimite(String dateLimite) {
		this.dateLimite = dateLimite;
	}

	public String getTarif() {
		return tarif;
	}

	public void setTarif(String tarif) {
		this.tarif = tarif;
	}

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
			t.setTarif(jsonTournoi.getString("tarif1") + "€ / "
					+ jsonTournoi.getString("tarif2") + "€ / "
					+ jsonTournoi.getString("tarif3") + "€");
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
