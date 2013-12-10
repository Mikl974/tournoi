package com.bretibad.tournoibretibad.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Rencontre implements Parcelable {

	private int numequipe;
	private int journee;
	private String division;
	private String adversaire;
	private String sh1;
	private int setpsh1;
	private int setcsh1;
	private String sh2;
	private int setpsh2;
	private int setcsh2;

	public Rencontre(Parcel in) {
		numequipe = in.readInt();
		journee = in.readInt();
		division = in.readString();
		adversaire = in.readString();
		sh1 = in.readString();
		setpsh1 = in.readInt();
		setcsh1 = in.readInt();
		sh2 = in.readString();
		setpsh2 = in.readInt();
		setcsh2 = in.readInt();
	}

	public Rencontre() {
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(numequipe);
		dest.writeInt(journee);
		dest.writeString(division);
		dest.writeString(adversaire);
		dest.writeString(sh1);
		dest.writeInt(setpsh1);
		dest.writeInt(setcsh1);
		dest.writeString(sh2);
		dest.writeInt(setpsh2);
		dest.writeInt(setcsh2);
	}

	public String getSh2() {
		return sh2;
	}

	public void setSh2(String sh2) {
		this.sh2 = sh2;
	}

	public int getSetpsh2() {
		return setpsh2;
	}

	public void setSetpsh2(int setpsh2) {
		this.setpsh2 = setpsh2;
	}

	public int getSetcsh2() {
		return setcsh2;
	}

	public void setSetcsh2(int setcsh2) {
		this.setcsh2 = setcsh2;
	}

	public static final Parcelable.Creator<Rencontre> CREATOR = new Parcelable.Creator<Rencontre>() {
		public Rencontre createFromParcel(Parcel in) {
			return new Rencontre(in);
		}

		public Rencontre[] newArray(int size) {
			return new Rencontre[size];
		}
	};

	public int getNumequipe() {
		return numequipe;
	}

	public void setNumequipe(int numequipe) {
		this.numequipe = numequipe;
	}

	public int getJournee() {
		return journee;
	}

	public void setJournee(int journee) {
		this.journee = journee;
	}

	public String getDivision() {
		return division;
	}

	public void setDivision(String division) {
		this.division = division;
	}

	public String getAdversaire() {
		return adversaire;
	}

	public void setAdversaire(String adversaire) {
		this.adversaire = adversaire;
	}

	public String getSh1() {
		return sh1;
	}

	public void setSh1(String sh1) {
		this.sh1 = sh1;
	}

	public int getSetpsh1() {
		return setpsh1;
	}

	public void setSetpsh1(int setpsh1) {
		this.setpsh1 = setpsh1;
	}

	public int getSetcsh1() {
		return setcsh1;
	}

	public void setSetcsh1(int setcsh1) {
		this.setcsh1 = setcsh1;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((division == null) ? 0 : division.hashCode());
		result = prime * result + journee;
		result = prime * result + numequipe;
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
		Rencontre other = (Rencontre) obj;
		if (division == null) {
			if (other.division != null)
				return false;
		} else if (!division.equals(other.division))
			return false;
		if (journee != other.journee)
			return false;
		if (numequipe != other.numequipe)
			return false;
		return true;
	}

}
