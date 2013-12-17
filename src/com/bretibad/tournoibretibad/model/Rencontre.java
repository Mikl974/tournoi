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
	private String sh3;
	private int setpsh3;
	private int setcsh3;
	private String sd1;
	private int setpsd1;
	private int setcsd1;
	private String sd2;
	private int setpsd2;
	private int setcsd2;
	private String dh1;
	private int setpdh1;
	private int setcdh1;
	private String dd1;
	private int setpdd1;
	private int setcdd1;
	private String dx1;
	private int setpdx1;
	private int setcdx1;
	private String dx2;
	private int setpdx2;
	private int setcdx2;
	private int live;
	private String finmatch;

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
		sh3 = in.readString();
		setpsh3 = in.readInt();
		setcsh3 = in.readInt();
		sd1 = in.readString();
		setpsd1 = in.readInt();
		setcsd1 = in.readInt();
		sd2 = in.readString();
		setpsd2 = in.readInt();
		setcsd2 = in.readInt();
		dh1 = in.readString();
		setpdh1 = in.readInt();
		setcdh1 = in.readInt();
		sh2 = in.readString();
		setpsh2 = in.readInt();
		setcdd1 = in.readInt();
		dx1 = in.readString();
		setpdx1 = in.readInt();
		setcdx1 = in.readInt();
		dx2 = in.readString();
		setpdx2 = in.readInt();
		setcdx2 = in.readInt();
		live = in.readInt();
		finmatch = in.readString();
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
		dest.writeString(sh3);
		dest.writeInt(setpsh3);
		dest.writeInt(setcsh3);
		dest.writeString(sd1);
		dest.writeInt(setpsd1);
		dest.writeInt(setcsd1);
		dest.writeString(sd2);
		dest.writeInt(setpsd2);
		dest.writeInt(setcsd2);
		dest.writeString(dh1);
		dest.writeInt(setpdh1);
		dest.writeInt(setcdh1);
		dest.writeString(dd1);
		dest.writeInt(setpdd1);
		dest.writeInt(setcdd1);
		dest.writeString(dx1);
		dest.writeInt(setpdx1);
		dest.writeInt(setcdx1);
		dest.writeString(dx2);
		dest.writeInt(setpdx2);
		dest.writeInt(setcdx2);
		dest.writeInt(live);
		dest.writeString(finmatch);
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

	public String getSd1() {
		return sd1;
	}

	public void setSd1(String sd1) {
		this.sd1 = sd1;
	}

	public int getSetpsd1() {
		return setpsd1;
	}

	public void setSetpsd1(int setpsd1) {
		this.setpsd1 = setpsd1;
	}

	public int getSetcsd1() {
		return setcsd1;
	}

	public void setSetcsd1(int setcsd1) {
		this.setcsd1 = setcsd1;
	}

	public String getSd2() {
		return sd2;
	}

	public void setSd2(String sd2) {
		this.sd2 = sd2;
	}

	public int getSetpsd2() {
		return setpsd2;
	}

	public void setSetpsd2(int setpsd2) {
		this.setpsd2 = setpsd2;
	}

	public int getSetcsd2() {
		return setcsd2;
	}

	public void setSetcsd2(int setcsd2) {
		this.setcsd2 = setcsd2;
	}

	public String getDh1() {
		return dh1;
	}

	public void setDh1(String dh1) {
		this.dh1 = dh1;
	}

	public int getSetpdh1() {
		return setpdh1;
	}

	public void setSetpdh1(int setpdh1) {
		this.setpdh1 = setpdh1;
	}

	public int getSetcdh1() {
		return setcdh1;
	}

	public void setSetcdh1(int setcdh1) {
		this.setcdh1 = setcdh1;
	}

	public String getDd1() {
		return dd1;
	}

	public void setDd1(String dd1) {
		this.dd1 = dd1;
	}

	public int getSetpdd1() {
		return setpdd1;
	}

	public void setSetpdd1(int setpdd1) {
		this.setpdd1 = setpdd1;
	}

	public int getSetcdd1() {
		return setcdd1;
	}

	public void setSetcdd1(int setcdd1) {
		this.setcdd1 = setcdd1;
	}

	public String getDx1() {
		return dx1;
	}

	public void setDx1(String dx1) {
		this.dx1 = dx1;
	}

	public int getSetpdx1() {
		return setpdx1;
	}

	public void setSetpdx1(int setpdx1) {
		this.setpdx1 = setpdx1;
	}

	public int getSetcdx1() {
		return setcdx1;
	}

	public void setSetcdx1(int setcdx1) {
		this.setcdx1 = setcdx1;
	}

	public String getDx2() {
		return dx2;
	}

	public void setDx2(String dx2) {
		this.dx2 = dx2;
	}

	public int getSetpdx2() {
		return setpdx2;
	}

	public void setSetpdx2(int setpdx2) {
		this.setpdx2 = setpdx2;
	}

	public int getSetcdx2() {
		return setcdx2;
	}

	public void setSetcdx2(int setcdx2) {
		this.setcdx2 = setcdx2;
	}

	public String getSh3() {
		return sh3;
	}

	public void setSh3(String sh3) {
		this.sh3 = sh3;
	}

	public int getSetpsh3() {
		return setpsh3;
	}

	public void setSetpsh3(int setpsh3) {
		this.setpsh3 = setpsh3;
	}

	public int getSetcsh3() {
		return setcsh3;
	}

	public void setSetcsh3(int setcsh3) {
		this.setcsh3 = setcsh3;
	}

	public int getLive() {
		return live;
	}

	public void setLive(int live) {
		this.live = live;
	}

	public String getFinmatch() {
		return finmatch;
	}

	public void setFinmatch(String finmatch) {
		this.finmatch = finmatch;
	}
}
