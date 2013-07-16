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
public class Dice implements IDice{
	
	private final static Random rnd = new Random();
	
	private int lastroll;
	
	private boolean deleted;
	
	/**
	 * Constructor empty for 1d6
	 */
	public Dice(){
		deleted = false;
		lastroll = rnd.nextInt(6)+1;
	}
	
	/**
	 * Constructor not empty to restore old match
	 * @param val value of dice
	 */
	public Dice(char c){
		deleted = false;
		if(c=='0') deleted=true;
		lastroll = Character.getNumericValue(c);
	}
		
	/* (non-Javadoc)
	 * @see it.ecosw.dudo.games.IDice#getLastRoll()
	 */
	public int getLastRoll(){
		if(isDeleted()) return 0;
		return lastroll;
	}
	
	/* (non-Javadoc)
	 * @see it.ecosw.dudo.games.IDice#newRoll()
	 */
	public int newRoll(){
		if(isDeleted()) return 0;
		lastroll = rnd.nextInt(6)+1;
		return getLastRoll();
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		if(isDeleted()) return "0";
		return lastroll+"";
	}

	/* (non-Javadoc)
	 * @see it.ecosw.dudo.games.IDice#compareTo(it.ecosw.dudo.games.Dice)
	 */
	@Override
	public int compareTo(IDice dice) {
		// TODO Auto-generated method stub
		if(!isDeleted() && dice.isDeleted()) return -1;
		else if (isDeleted() && dice.isDeleted()) return 0;
		else if (isDeleted() && !dice.isDeleted()) return 1;
		
		if (lastroll > dice.getLastRoll()) return 1;
		else if (lastroll == dice.getLastRoll()) return 0;
		else return -1;
	}

	@Override
	public boolean isDeleted() {
		// TODO Auto-generated method stub
		return deleted;
	}

	@Override
	public boolean delete() {
		// TODO Auto-generated method stub
		if(deleted) return false;
		deleted = true;
		return true;
	}

	@Override
	public int restore() {
		// TODO Auto-generated method stub
		if(!deleted) return 0;
		deleted = false;
		return newRoll();
	}
	
	

}
