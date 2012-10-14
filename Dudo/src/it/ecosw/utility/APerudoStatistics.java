package it.ecosw.utility;

/**
 * This file is part of Dudo for Android software.
 *
 *  Dudo is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  Dudo is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with Dudo.  If not, see <http://www.gnu.org/licenses/>.
 */

public class APerudoStatistics {
	
	private int roll = 0;
	private int del = 0;
	private int match = 0;
	
	/**
	 * Constructor
	 * @param roll Number of dice Roll
	 * @param del Number of dice Loose
	 * @param match Number of match played
	 */
	public APerudoStatistics(int roll, int del, int match){
		this.roll = roll;
		this.del = del;
		this.match = match;
	}
	
	/**
	 * Increase the number of roll by one
	 * @return new number of roll
	 */
	public int addRoll(){
		return roll++;
	}
	
	/**
	 * Increase the number of dice deleted
	 * @return total number of dice deleted
	 */
	public int addDel(){
		return del++;
	}

	/**
	 * Increase the number of match
	 * @return total number of dice match
	 */
	public int addLoose(){
		return match++;
	}
	
	/**
	 * Reset the statistics
	 */
	public void reset(){
		roll = 0;
		del = 0;
		match = 0;
	}

	/**
	 * Return the number of match
	 * @return number of match
	 */
	public int getNumMatch(){
		return match;
	}
	
	/**
	 * Return the number of roll
	 * @return number of roll
	 */
	public int getNumRoll(){
		return match;
	}
	
	/**
	 * Return the number of dice loose
	 * @return number of dice loose
	 */
	public int getNumDel(){
		return del;
	}
	
	/**
	 * Return a string with percentage of dice reroll vs dice loose
	 * @return text string with perc of roll / loose
	 */
	public String getRollDellPerc(){
		float perc = match / del;
		String s = match+"/"+del+" "+perc+"%";
		return s;
	}
	
}
