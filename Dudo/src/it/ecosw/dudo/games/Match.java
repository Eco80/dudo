package it.ecosw.dudo.games;

import it.ecosw.dudo.settings.SettingsHelper;

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

public class Match {
	
	private PlayerSet[] sets;
	
	private boolean areSixDice;
	
	public int round;
	
	
	/**
	 * Constructor
	 * @param settings Game Settings
	 */
	public Match(SettingsHelper settings){
		areSixDice = settings.isSixthDieActivated();
		sets = new PlayerSet[settings.getNumPlayers()];
		for(int i=0;i<sets.length;i++){
			sets[i]=settings.getPlayerStatus(i).getPlayerSet();
		}
		round = 1;
	}
	
	/**
	 * Restore the match
	 * @param sorting true is dice shall be sorted
	 * @return true if success
	 */
	public boolean restart(boolean sorting){
		for(int i=0;i<sets.length;i++) sets[i].restoreAllDice(sorting);
		round = 1;
		return true;
	}
	
	/**
	 * Return number of players
	 * @return Number of players
	 */
	public int getNumPlayer(){
		return sets.length;
	}
	
	/**
	 * Return true if the match is with six dice
	 * @return true for six dice play
	 */
	public boolean areSixDice(){
		return areSixDice;
	}
	
	/**
	 * Return current player name
	 * @param cur current player
	 * @return Current Player Name
	 */
	public String getPlayerName(int cur){
		return sets[cur].getPlayerName();
	}
	
	/**
	 * Return player status
	 * @param cur Current player
	 * @return player status
	 */
	public String getPlayerStatus(int cur){
		return sets[cur].toString();
	}
	
	/**
	 * Return current round
	 * @return current round
	 */
	public int getRound(){
		return round;
	}
	
	/**
	 * Roll all the dice
	 * @param sorting true if is requested sorting
	 * @return true if success
	 */
	public boolean rollAllDie(boolean sorting){
		for(int i=0;i<sets.length;i++){
			sets[i].rollSet(sorting);
		}
		return true;
	}
	
	/**
	 * Return a dice value
	 * @param cur Current player
	 * @param dice dice number
	 * @return dice value
	 */
	public int getDiceValue(int cur, int dice){
		return sets[cur].getDieValue(dice);
	}
	
	/**
	 * Return current number of dice
	 * @param cur Current Player
	 * @return Number of dice
	 */
	public int getNumDice(int cur){
		return sets[cur].getDiceNumber();
	}
	
	/**
	 * Delete a dice from a player
	 * @param cur current player
	 * @return position of dice deleted
	 */
	public int deleteDice(int cur){
		round++;
		return sets[cur].delDie();
	}
	
	/**
	 * Check if dice indicated is deleted
	 * @param cur Current player
	 * @param dice Dice Number
	 * @return true if it is deleted
	 */
	public boolean isDieDeleted(int cur, int dice){
		return sets[cur].isDieDeleted(dice);
	}
	
	/**
	 * Return true if the player indicate don't have dice
	 * @param cur Current player
	 * @return true if he is empty
	 */
	public boolean isEmpty(int cur){
		return sets[cur].isEmpty();
	}
	
	/**
	 * Set the match with six dice
	 * @param newSixDice true if there are six dice
	 * @return true if something is changed
	 */
	public boolean setSixDice(boolean newSixDice){
		PlayerSet[] tmp = new PlayerSet[sets.length];
		if(!areSixDice && newSixDice){
			areSixDice = newSixDice;
			for(int i=0;i<sets.length;i++) tmp[i] = new PlayerSet(sets[i].getPlayerName(), true);
			sets = tmp;
			return true;
		} else if(areSixDice && !newSixDice){
			areSixDice = newSixDice;
			for(int i=0;i<sets.length;i++) tmp[i] = new PlayerSet(sets[i].getPlayerName(), false);
			sets = tmp;
			return true;
		}
		return false;
	}
	

}
