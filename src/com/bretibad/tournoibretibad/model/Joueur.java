package com.bretibad.tournoibretibad.model;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;

public class Joueur implements Parcelable {

	private String nom;
	private String prenom;
	private String licence;
	private String genre;
	private int paye;

	public Joueur() {
	}

	public Joueur(Parcel in) {
		readFromParcel(in);
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getLicence() {
		return licence;
	}

	public void setLicence(String licence) {
		this.licence = licence;
	}

	public int getPaye() {
		return paye;
	}

	public void setPaye(int paye) {
		this.paye = paye;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public String getFullName() {
		return prenom + " " + nom;
	}

	public static Joueur fromJson(JSONObject jsonJoueur) {
		try {
			Joueur j = new Joueur();
			j.setNom(jsonJoueur.getString("nom"));
			j.setPrenom(jsonJoueur.getString("prenom"));
			j.setGenre(jsonJoueur.getString("genre"));
			j.setLicence(jsonJoueur.getString("licence"));
			j.setPaye(jsonJoueur.getInt("paye"));
			return j;
		} catch (JSONException e) {
			System.err.println(e.getMessage());
			return null;
		}
	}

	public static ArrayList<Joueur> fromJsonArray(String json) {
		ArrayList<Joueur> results = new ArrayList<Joueur>();
		try {
			JSONArray array = new JSONArray(json);
			for (int i = 0; i < array.length(); i++) {
				results.add(Joueur.fromJson(array.getJSONObject(i)));
			}
		} catch (JSONException e) {
			System.err.println(e.getMessage());
		}
		return results;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(nom);
		dest.writeString(prenom);
		dest.writeString(genre);
		dest.writeString(licence);
		dest.writeInt(paye);
	}

	private void readFromParcel(Parcel in) {
		nom = in.readString();
		prenom = in.readString();
		genre = in.readString();
		licence = in.readString();
		paye = in.readInt();

	}

	public static final Parcelable.Creator<Joueur> CREATOR = new Parcelable.Creator<Joueur>() {
		public Joueur createFromParcel(Parcel in) {
			return new Joueur(in);
		}

		public Joueur[] newArray(int size) {
			return new Joueur[size];
		}
	};

}
