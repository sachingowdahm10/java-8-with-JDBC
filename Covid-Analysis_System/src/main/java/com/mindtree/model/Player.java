package com.mindtree.model;

import java.sql.Date;

public class Player {
	private Date date;
	private String state;
	private String district;
	private int tested;
	private int confirmed;
	private int recovered;
	private int id;

	public Player(Date date, String state, String district, int tested, int confirmed, int recovered, int id) {
		super();
		this.date = date;
		this.state = state;
		this.district = district;
		this.tested = tested;
		this.confirmed = confirmed;
		this.recovered = recovered;
		this.id = id;
	}

	public Player() {
		super();
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public int getTested() {
		return tested;
	}

	public void setTested(int tested) {
		this.tested = tested;
	}

	public int getConfirmed() {
		return confirmed;
	}

	public void setConfirmed(int confirmed) {
		this.confirmed = confirmed;
	}

	public int getRecovered() {
		return recovered;
	}

	public void setRecovered(int recovered) {
		this.recovered = recovered;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || obj.getClass() != this.getClass())

		{
			return false;
		}

		Player player = (Player) obj;

		boolean isStateEqual = this.state != null ? this.state.equals(player.getState())
				: player.getState() != null ? player.getState().equals(this.state) : true;
		boolean isDistrictEqual = this.district != null ? this.district.equals(player.getDistrict())
				: player.getDistrict() != null ? player.getDistrict().equals(this.district) : true;

		boolean isDateEqual = this.date != null ? this.date.toString().equals(player.getDate().toString())
				: player.getDate()!= null ? player.getDate().toString().equals(this.date.toString()) : true;

		return (player.getConfirmed() == this.confirmed && player.getId() == this.id && isDateEqual && isDistrictEqual
				&& player.getRecovered() == this.recovered && isStateEqual && player.getTested() == this.tested);
	}

	@Override
	public int hashCode() {
		return (int) (this.state.charAt(0) + this.state.charAt(1) + this.date.getTime() + this.id + this.confirmed
				+ this.recovered + this.tested);
	}

}
