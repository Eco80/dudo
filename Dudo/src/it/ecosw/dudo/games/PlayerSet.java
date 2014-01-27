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
 */
public class PlayerSet {

	private Dice[] set;
	
	private String name;
	
	/**
	 * Constructor
	 * @param name Player Name
	 * @param sixdice true if there are six dice
	 */
	public PlayerSet(String name, boolean sixdice) {
		// TODO Auto-generated constructor stub
		this.name = name;
		if(sixdice) set = new Dice[6];
		else set = new Dice[5];
		for(int i=0;i<set.length;i++) set[i] = new Dice();
	}
	
	/**
	 * Constructor
	 * @param name Playername
	 * @param sixdice true if there are six dice
	 * @param startseq Start sequence
	 */
	public PlayerSet(String name, boolean sixdice, String startseq){
		this.name = name;
		if(sixdice) set = new Dice[6];
		else set = new Dice[5];
		for(int i=0;i<set.length;i++) {
			set[i] = new Dice(startseq.charAt(i));
		}
	}
	
	/**
	 * Return the player name
	 * @return player name
	 */
	public String getPlayerName() {
		// TODO Auto-generated method stub
		return name;
	}

	/**
	 * Remove last die from the set
	 * @return number of dice deleted, (-1) if no dice are removed
	 */
	public int delDie(){
		for(int i=set.length-1;i>=0;i--) {
			if(!set[i].isDeleted()) {
				set[i].delete();
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * Restore all dice in the set
	 * @param sorting true if you want dice ordered in decreasing order
	 * @return true if operation was done with success
	 */
	public boolean restoreAllDice(boolean sorting) {
		// TODO Auto-generated method stub
		for(int i=0;i<set.length;i++) set[i].restore();
		if (sorting) java.util.Arrays.sort(set);
		return true;
	}

	/**
	 * Roll all the dice (not deleted) in the set
	 * @param sorting true if you want dice ordered in decreasing order
	 * @return true if the roll was correct
	 */
	public boolean rollSet(boolean sorting){
			for(int i=0;i<set.length;i++) if(!set[i].isDeleted()) set[i].newRoll();
			if (sorting) java.util.Arrays.sort(set);
			return true;
	}
		
	/**
	 * Return true if the dice set is empty
	 * @return number of die
	 */
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		for(int i=0;i<set.length;i++) if(!set[i].isDeleted()) return false;
		return true;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		String s="";
		for(int i=0;i<set.length;i++) s+=set[i].toString();
		return s;
	}
	
	/**
	 * Return the current number of die
	 * @return number of die
	 */
	public int getDiceNumber() {
		// TODO Auto-generated method stub
		int count = 0;
		for(int i=0;i<set.length;i++) if(!set[i].isDeleted()) count++;
		return count;
	}

	/**
	 * Return value of a die
	 * @param pos dice position
	 * @return die value (0 if die was deleted)
	 */
	public int getDieValue(int pos) {	
		return set[pos].getLastRoll();
	}
	
	/**
	 * Return true if dice selected are deleted
	 * @param pos dice position
	 * @return true if dice was deleted
	 */
	public boolean isDieDeleted(int pos){
		return set[pos].isDeleted();
	}
	
}
