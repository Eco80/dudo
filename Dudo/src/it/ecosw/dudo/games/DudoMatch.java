package it.ecosw.dudo.games;

import java.util.ArrayList;

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
 * Manage a multiplayer match
 * @author Enrico Strocchi
 *
 */
public class DudoMatch {
	
	public ArrayList<Player> players;
	
	public int current;
	/**
	 * Constructor
	 * @param players players name
	 */
	public DudoMatch(String[] names){
		players = new ArrayList<DudoMatch.Player>();
		for(int i=0;i<names.length;i++){
			players.add(new Player(names[i]));
		}
		current = -1;
	}
	
	/**
	 * Return the next player
	 * @return next player
	 */
	public Player next(){
		if(players.size()==1) return null;
		current++;
		if(current>players.size()-1) current=0;
		return players.get(current);
	}
	
	/**
	 * Player don't believe, true is right, false not right
	 * @param numDie number of Die in play
	 * @param valDie value of die in play
	 * @return true in right
	 */
	public boolean dudo(int numDie, int valDie){
		int[] val = new int[6];
		for(int i=0;i<players.size();i++){
			DieSet ds = players.get(i).getDieSet();
			for(int j=0;j<ds.numDice();j++){
				val[ds.getDiceValue(j)-1]++;
			}
		}
		if(val[valDie-1]>=numDie) return true;
		else {
			players.get(current).looseDice();
			if(players.get(current).getDieSet().numDice()==0) 
				players.remove(current);
			return false;
		}
	}
	
	/**
	 * Player Inner class
	 * @author Enrico Strocchi
	 *
	 */
	public class Player {
		private String name;
		private DieSet die;
		
		/**
		 * Constructor
		 * @param name Name of the player
		 */
		public Player(String name){
			this.name=name;
			die = new DieSet();
		}
		
		/**
		 * Return the name of the player
		 * @return Name of the player
		 */
		public String getName(){
			return name;
		}
		
		/**
		 * Remove a dice of the player
		 */
		public void looseDice(){
			die.delDice();
		}
		
		/**
		 * Return the number of die in play
		 * @return number of die
		 */
		public int getNumDie(){
			return die.numDice();
		}
		
		/**
		 * Return the current dieset of the player
		 * @return dieset
		 */
		public DieSet getDieSet(){
			return die;
		}
		
		/* (non-Javadoc)
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			// TODO Auto-generated method stub
			return name+" ("+getNumDie()+")";
		}
		
		
	}
	
}
