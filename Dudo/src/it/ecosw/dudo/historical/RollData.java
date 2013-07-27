package it.ecosw.dudo.historical;

import java.util.Date;

import android.text.format.DateFormat;

public class RollData implements Comparable<RollData> {

	private String player;
	private String roll;
	private Date date;
	
	/**
	 * Constructor
	 * @param ID progressive number
	 * @param roll roll coded into a string
	 * @param date date of roll
	 */
	public RollData(String player, String roll, Date date){
		this.player = player;
		this.roll = roll;
		this.date = date;
	}

	/**
	 * Return the playername
	 * @return playername
	 */
	public String getPlayer(){
		return player;
	}
	
	/**
	 * Return the roll coded into a string
	 * @return the roll
	 */
	public String getRoll() {
		return roll;
	}
	
	

	/**
	 * Retur date of the roll
	 * @return the date object date
	 */
	public Date getDate() {
		return date;
	}

	@Override
	public int compareTo(RollData other) {
		// TODO Auto-generated method stub
		return other.getDate().compareTo(getDate());
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Roll: "+roll+" Date: "+DateFormat.format("yyyy-MM-dd hh:mm:ss", date);
	}
	
}
