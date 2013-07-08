package it.ecosw.dudo.games;

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

import java.util.Random;

/**
 * Dice simulator Object
 * @author Enrico Strocchi
 *
 */
public class Dice implements Comparable<Dice>{
	
	private final static Random rnd = new Random();
	
	private int lastroll;
	
	private boolean deleted;
	
	/**
	 * Constructor empty for 1d6
	 */
	public Dice(){
		lastroll = rnd.nextInt(6)+1;
		deleted = false;
	}
	
	/**
	 * Constructor not empty to restore old match
	 * @param val value of dice
	 */
	public Dice(char c){
		lastroll = Character.getNumericValue(c);
		deleted = false;
	}
		
	/**
	 * Return last roll value
	 * @return last roll
	 */
	public int getLastRoll(){
		return lastroll;
	}
	
	/**
	 * Return true if dice is deleted;
	 * @return true if deleted
	 */
	public boolean isDeleted(){
		return deleted;
	}
	
	/**
	 * Delete the current dice
	 */
	public void delete(){
		deleted = true;
	}
	
	/**
	 * Restore the current dice
	 */
	public void restore(){
		deleted = false;
	}
	
	/**
	 * Roll Dice Again
	 * @return Value of roll
	 */
	public int newRoll(){
		if (deleted) return 0;
		lastroll = rnd.nextInt(6)+1;
		return getLastRoll();
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		if(deleted) return "D";
		return lastroll+"";
	}

	@Override
	public int compareTo(Dice dice) {
		// TODO Auto-generated method stub
		if (lastroll > dice.getLastRoll() || lastroll == 0) return 1;
		else if (lastroll == dice.getLastRoll()) return 0;
		else return -1;
	}
	
	

}
