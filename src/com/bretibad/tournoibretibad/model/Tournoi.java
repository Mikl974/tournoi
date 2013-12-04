package com.bretibad.tournoibretibad.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Tournoi implements Parcelable {

	private String nom;
	private String date;
	private String dateLimite;
	private String tarif1;
	private String tarif2;
	private String tarif3;
	private int nbInscrits;
	private int nbPaye;

	public int getNbInscrits() {
		return nbInscrits;
	}

	public void setNbInscrits(int nbInscrits) {
		this.nbInscrits = nbInscrits;
	}

	public int getNbPaye() {
		return nbPaye;
	}

	public void setNbPaye(int nbPaye) {
		this.nbPaye = nbPaye;
	}

	public Tournoi(Parcel in) {
		nom = in.readString();
		date = in.readString();
		dateLimite = in.readString();
		tarif1 = in.readString();
		tarif2 = in.readString();
		tarif3 = in.readString();
	}

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

	public String getTarif1() {
		return tarif1;
	}

	public void setTarif1(String tarif) {
		this.tarif1 = tarif;
	}

	public Tournoi() {
	}

	public Tournoi(String nom) {
		this.nom = nom;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nom == null) ? 0 : nom.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Tournoi other = (Tournoi) obj;
		if (nom == null) {
			if (other.nom != null)
				return false;
		} else if (!nom.equals(other.nom))
			return false;
		return true;
	}

	public String getTarif2() {
		return tarif2;
	}

	public void setTarif2(String tarif2) {
		this.tarif2 = tarif2;
	}

	public String getTarif3() {
		return tarif3;
	}

	public void setTarif3(String tarif3) {
		this.tarif3 = tarif3;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(nom);
		dest.writeString(date);
		dest.writeString(dateLimite);
		dest.writeString(tarif1);
		dest.writeString(tarif2);
		dest.writeString(tarif3);
	}

	public static final Parcelable.Creator<Tournoi> CREATOR = new Parcelable.Creator<Tournoi>() {
		public Tournoi createFromParcel(Parcel in) {
			return new Tournoi(in);
		}

		public Tournoi[] newArray(int size) {
			return new Tournoi[size];
		}
	};

}
