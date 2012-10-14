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

/**
 * Create the set of dice to be roll
 * @author Enrico Strocchi
 *
 */
public class DiceSet {

	private Dice[] set = null;
	
	/**
	 * Constructor, include 6 dice
	 * @param sorting true if you want dice ordered in decreasing order
	 */
	public DiceSet(boolean sorting) {
		// TODO Auto-generated constructor stub
		set = new Dice[5];
		for(int i=0; i<5; i++) set[i] = new Dice();
		if (sorting) sort();
	}
	
	/**
	 * Constructor include 6 defined dice
	 * @param sorting ture if you want dice ordered in decreasing order
	 * @param init starting values
	 */
	public DiceSet(boolean sorting, String init){
		set = new Dice[5];
		for(int i=0; i<5; i++) set[i] = new Dice(init.charAt(i));
		if (sorting) sort();
	}
	
	
	/**
	 * Remove a Dice from set
	 * @return Dice Removed
	 */
	public int delDice(){
		for (int i=4; i>=0; i--) {
			if (!set[i].isDeleted()) {
				set[i].delete();
				return i+1;
			}
		}
		return 0;
	}
	
	/**
	 * Restore all dice in set
	 */
	protected void restoreAllDice(){
		for (int i=0;i<5;i++){
			set[i].restore();
		}
	}
	
	/**
	 * Return the number of dice in the set
	 * @return number of dices
	 */
	public int numDice(){
		int j = 0;
		for (int i=0;i<5;i++) if (!set[i].isDeleted()) j++;
		return j;
	}
	
	/**
	 * Roll all the dice in the set
	 * @param sorting true if you want dice ordered in decreasing order
	 * @return true if the roll was correct
	 */
	public boolean rollSet(boolean sorting){
			for(int i=0;i<5;i++) set[i].newRoll();
			if (sorting) sort();
			return true;
	}

	/**
	 * Return the value of dices, 0 if it's deleted
	 * @param i dice number
	 * @return value of dice
	 */
	public int getDiceValue(int i){
		return set[i].getLastRoll();
	}
	
	/**
	 * Return number of dice with the value reported
	 * @param value value to be checked
	 * @return number of dices
	 */
	public int getNumValue(int value){
		int count = 0;
		for(int i=0;i<5;i++)
			if(set[i].getLastRoll()==value) count++;
		return count;
	}
	
	/**
	 * Order the dice in decreasing order
	 */
	public void sort(){
		java.util.Arrays.sort(set);
	}
	
	/**
	 * Return current status of values
	 * @return values
	 */
	public int[] getValue(){
		int[] val = new int[5];
		for(int i=0; i<5;i++) val[i] = set[i].getLastRoll();
		return val;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		String s = "";
		if (set == null) return "empty";
		for(int i=0; i<5; i++) s += set[i].toString();
		return s;
	}
	
	

}
