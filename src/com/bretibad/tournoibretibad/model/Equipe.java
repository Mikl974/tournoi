package com.bretibad.tournoibretibad.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Equipe implements Parcelable {

	private int num;
	private String division;
	private String capitaine;

	public Equipe(Parcel in) {
		num = in.readInt();
		division = in.readString();
		capitaine = in.readString();
	}

	public Equipe() {
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(num);
		dest.writeString(division);
		dest.writeString(capitaine);
	}

	public static final Parcelable.Creator<Equipe> CREATOR = new Parcelable.Creator<Equipe>() {
		public Equipe createFromParcel(Parcel in) {
			return new Equipe(in);
		}

		public Equipe[] newArray(int size) {
			return new Equipe[size];
		}
	};

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getDivision() {
		return division;
	}

	public void setDivision(String division) {
		this.division = division;
	}

	public String getCapitaine() {
		return capitaine;
	}

	public void setCapitaine(String capitaine) {
		this.capitaine = capitaine;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Equipe other = (Equipe) obj;
		if (division == null) {
			if (other.division != null)
				return false;
		} else if (!division.equals(other.division))
			return false;
		if (num != other.num)
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((division == null) ? 0 : division.hashCode());
		result = prime * result + num;
		return result;
	}
}
