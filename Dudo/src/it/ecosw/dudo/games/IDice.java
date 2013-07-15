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

public interface IDice extends Comparable<IDice>{

	/**
	 * Return last roll value
	 * @return last roll
	 */
	public int getLastRoll();

	/**
	 * Roll Dice Again
	 * @return Value of roll
	 */
	public int newRoll();
	
	/**
	 * Delete current dice from set
	 * @return true if one dice was deleted
	 */
	public boolean delete();
	
	/**
	 * Restore current dice
	 * @return result of dice launch
	 */
	public int restore();
	
	/**
	 * Return true if dice is deleted
	 * @return true if dice is deleted
	 */
	public boolean isDeleted();

}