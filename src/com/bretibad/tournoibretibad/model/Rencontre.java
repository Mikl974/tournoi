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
	private String finsh1;
	private String sh2;
	private int setpsh2;
	private int setcsh2;
	private String finsh2;
	private String sh3;
	private int setpsh3;
	private int setcsh3;
	private String finsh3;
	private String sh4;
	private int setpsh4;
	private int setcsh4;
	private String finsh4;
	private String sd1;
	private int setpsd1;
	private int setcsd1;
	private String finsd1;
	private String sd2;
	private int setpsd2;
	private int setcsd2;
	private String finsd2;
	private String dh1;
	private int setpdh1;
	private int setcdh1;
	private String findh1;
	private String dh2;
	private int setpdh2;
	private int setcdh2;
	private String findh2;
	private String dd1;
	private int setpdd1;
	private int setcdd1;
	private String findd1;
	private String dx1;
	private int setpdx1;
	private int setcdx1;
	private String findx1;
	private String dx2;
	private int setpdx2;
	private int setcdx2;
	private String findx2;
	private int live;
	private Integer matchpour;
	private Integer matchcontre;
	private String finmatch;
	private int sh;
	private int sd;
	private int dh;
	private int dd;
	private int dx;

	public Rencontre(Parcel in) {
		numequipe = in.readInt();
		journee = in.readInt();
		division = in.readString();
		adversaire = in.readString();
		sh1 = in.readString();
		setpsh1 = in.readInt();
		setcsh1 = in.readInt();
		finsh1 = in.readString();

		sh2 = in.readString();
		setpsh2 = in.readInt();
		setcsh2 = in.readInt();
		finsh2 = in.readString();

		sh3 = in.readString();
		setpsh3 = in.readInt();
		setcsh3 = in.readInt();
		finsh3 = in.readString();
		
		sh4 = in.readString();
		setpsh4 = in.readInt();
		setcsh4 = in.readInt();
		finsh4 = in.readString();

		sd1 = in.readString();
		setpsd1 = in.readInt();
		setcsd1 = in.readInt();
		finsd1 = in.readString();

		sd2 = in.readString();
		setpsd2 = in.readInt();
		setcsd2 = in.readInt();
		finsd2 = in.readString();

		dh1 = in.readString();
		setpdh1 = in.readInt();
		setcdh1 = in.readInt();
		findh1 = in.readString();
		
		dh2 = in.readString();
		setpdh2 = in.readInt();
		setcdh2 = in.readInt();
		findh2 = in.readString();

		dd1 = in.readString();
		setpdd1 = in.readInt();
		setcdd1 = in.readInt();
		findd1 = in.readString();

		dx1 = in.readString();
		setpdx1 = in.readInt();
		setcdx1 = in.readInt();
		findx1 = in.readString();

		dx2 = in.readString();
		setpdx2 = in.readInt();
		setcdx2 = in.readInt();
		findx2 = in.readString();

		live = in.readInt();
		matchpour = in.readInt();
		matchcontre = in.readInt();
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
		dest.writeString(finsh1);
		dest.writeString(sh2);
		dest.writeInt(setpsh2);
		dest.writeInt(setcsh2);
		dest.writeString(finsh2);
		dest.writeString(sh3);
		dest.writeInt(setpsh3);
		dest.writeInt(setcsh3);
		dest.writeString(finsh3);
		dest.writeString(sh4);
		dest.writeInt(setpsh4);
		dest.writeInt(setcsh4);
		dest.writeString(finsh4);
		dest.writeString(sd1);
		dest.writeInt(setpsd1);
		dest.writeInt(setcsd1);
		dest.writeString(finsd1);
		dest.writeString(sd2);
		dest.writeInt(setpsd2);
		dest.writeInt(setcsd2);
		dest.writeString(finsd2);
		dest.writeString(dh1);
		dest.writeInt(setpdh1);
		dest.writeInt(setcdh1);
		dest.writeString(findh1);
		dest.writeString(dh2);
		dest.writeInt(setpdh2);
		dest.writeInt(setcdh2);
		dest.writeString(findh2);
		dest.writeString(dd1);
		dest.writeInt(setpdd1);
		dest.writeInt(setcdd1);
		dest.writeString(findd1);
		dest.writeString(dx1);
		dest.writeInt(setpdx1);
		dest.writeInt(setcdx1);
		dest.writeString(findx1);
		dest.writeString(dx2);
		dest.writeInt(setpdx2);
		dest.writeInt(setcdx2);
		dest.writeString(findx2);
		dest.writeInt(live);
		dest.writeInt(matchpour != null ? matchpour : 0);
		dest.writeInt(matchcontre != null ? matchcontre : 0);
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

	public Integer getMatchpour() {
		return matchpour;
	}

	public void setMatchpour(Integer matchpour) {
		this.matchpour = matchpour;
	}

	public Integer getMatchcontre() {
		return matchcontre;
	}

	public void setMatchcontre(Integer matchcontre) {
		this.matchcontre = matchcontre;
	}

	public String getFinsh1() {
		return finsh1;
	}

	public void setFinsh1(String finsh1) {
		this.finsh1 = finsh1;
	}

	public String getFinsh2() {
		return finsh2;
	}

	public void setFinsh2(String finsh2) {
		this.finsh2 = finsh2;
	}

	public String getFinsh3() {
		return finsh3;
	}

	public void setFinsh3(String finsh3) {
		this.finsh3 = finsh3;
	}

	public String getFinsd1() {
		return finsd1;
	}

	public void setFinsd1(String finsd1) {
		this.finsd1 = finsd1;
	}

	public String getFinsd2() {
		return finsd2;
	}

	public void setFinsd2(String finsd2) {
		this.finsd2 = finsd2;
	}

	public String getFindh1() {
		return findh1;
	}

	public void setFindh1(String findh1) {
		this.findh1 = findh1;
	}

	public String getFindd1() {
		return findd1;
	}

	public void setFindd1(String findd1) {
		this.findd1 = findd1;
	}

	public String getFindx1() {
		return findx1;
	}

	public void setFindx1(String findx1) {
		this.findx1 = findx1;
	}

	public String getFindx2() {
		return findx2;
	}

	public void setFindx2(String findx2) {
		this.findx2 = findx2;
	}

	public int getSh() {
		return sh;
	}

	public void setSh(int sh) {
		this.sh = sh;
	}

	public int getSd() {
		return sd;
	}

	public void setSd(int sd) {
		this.sd = sd;
	}

	public int getDh() {
		return dh;
	}

	public void setDh(int dh) {
		this.dh = dh;
	}

	public int getDd() {
		return dd;
	}

	public void setDd(int dd) {
		this.dd = dd;
	}

	public int getDx() {
		return dx;
	}

	public void setDx(int dx) {
		this.dx = dx;
	}

	public String getSh4() {
		return sh4;
	}

	public void setSh4(String sh4) {
		this.sh4 = sh4;
	}

	public int getSetpsh4() {
		return setpsh4;
	}

	public void setSetpsh4(int setpsh4) {
		this.setpsh4 = setpsh4;
	}

	public int getSetcsh4() {
		return setcsh4;
	}

	public void setSetcsh4(int setcsh4) {
		this.setcsh4 = setcsh4;
	}

	public String getFinsh4() {
		return finsh4;
	}

	public void setFinsh4(String finsh4) {
		this.finsh4 = finsh4;
	}

	public String getDh2() {
		return dh2;
	}

	public void setDh2(String dh2) {
		this.dh2 = dh2;
	}

	public int getSetpdh2() {
		return setpdh2;
	}

	public void setSetpdh2(int setpdh2) {
		this.setpdh2 = setpdh2;
	}

	public int getSetcdh2() {
		return setcdh2;
	}

	public void setSetcdh2(int setcdh2) {
		this.setcdh2 = setcdh2;
	}

	public String getFindh2() {
		return findh2;
	}

	public void setFindh2(String findh2) {
		this.findh2 = findh2;
	}
}
