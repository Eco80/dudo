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
public class DieSet <T extends IDice> implements IDieSet<T>{

	protected T[] set = null;
	
	/**
	 * Constructor, include 6 dice
	 */
	public DieSet(T[] set) {
		// TODO Auto-generated constructor stub
		this.set = set;
	}
	
	/* (non-Javadoc)
	 * @see it.ecosw.dudo.games.IDieSet#delDice()
	 */
	public int delDice(){
		for(int i=4;i>=0;i--) {
			if(!set[i].isDeleted()) {
				set[i].delete();
				return i;
			}
		}
		return -1;
	}
	
	@Override
	public boolean restoreAllDie(boolean sorting) {
		// TODO Auto-generated method stub
		for(int i=0;i<5;i++) set[i].restore();
		if (sorting) java.util.Arrays.sort(set);
		return true;
	}

	/* (non-Javadoc)
	 * @see it.ecosw.dudo.games.IDieSet#rollSet(boolean)
	 */
	public boolean rollSet(boolean sorting){
			for(int i=0;i<5;i++) if(!set[i].isDeleted()) set[i].newRoll();
			if (sorting) java.util.Arrays.sort(set);
			return true;
	}
		
	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		for(int i=0;i<5;i++) if(!set[i].isDeleted()) return false;
		return true;
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
