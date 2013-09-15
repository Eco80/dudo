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

public interface IDieSet<T extends IDice> {

	/**
	 * Remove last dice from the set
	 * @return true if one dice is removed
	 */
	public boolean delDice();

	/**
	 * Restore all die in the set
	 * @param sorting true if you want dice ordered in decreasing order
	 * @return true if operation was done with success
	 */
	public boolean restoreAllDie(boolean sorting);
	
	/**
	 * Roll all the dice (not deleted) in the set
	 * @param sorting true if you want dice ordered in decreasing order
	 * @return true if the roll was correct
	 */
	public boolean rollSet(boolean sorting);

	/**
	 * Return true if the dice set is empty
	 * @return number of die
	 */
	public boolean isEmpty();



}