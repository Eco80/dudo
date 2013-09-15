package it.ecosw.dudo.gui;

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

import it.ecosw.dudo.games.DieSet;

public class DieSetAdapter extends DieSet<DiceAdapter> {
	
	private DiceGraphicObjects[] dgos = null;
	
	private boolean diceHide = false;
	
	public DieSetAdapter(DiceAdapter[] set, DiceGraphicObjects[] dgos, boolean sorting) {
		super(set);
		// TODO Auto-generated constructor stub
		this.dgos = dgos;
		for(int i=0;i<5;i++) {
			set[i].setDgo(dgos[i]);
			set[i].update();
		}
	}

	/**
	 * Activate or deactivate animation during dice roll
	 * @param anim the anim to set
	 */
	public void setAnimEnabled(boolean anim) {
		for(int i=0;i<5;i++) set[i].setAnimated(anim);
	}
	
	/* (non-Javadoc)
	 * @see it.ecosw.dudo.games.DieSet#delDice()
	 */
	@Override
	public boolean delDice() {
		// TODO Auto-generated method stub
		if (diceHide) return false;
		return super.delDice();
	}


	/* (non-Javadoc)
	 * @see it.ecosw.dudo.games.DieSet#rollSet(boolean)
	 */
	@Override
	public boolean rollSet(boolean sort) {
		// TODO Auto-generated method stub
		if (diceHide) return false;
		if (isEmpty()) restoreAllDie(sort);
		super.rollSet(sort);
		for(int i=0;i<5;i++) {
			set[i].setDgo(dgos[i]);
			set[i].update();
		}
		return true;
	}

	/**
	 * Set Dice Hide
	 * @param true to hide dice
	 */
	public void switchDiceHide(){
		if (!diceHide && !isEmpty()) {
			diceHide = true;
			for(int i=0;i<5;i++) set[i].hide();
			return;
		} 
		if (diceHide && !isEmpty()) {
			diceHide = false;
			for(int i=0;i<5;i++) set[i].show();
			return;
		}
	}
	
	/**
	 * Restart the match
	 * @param sorting true if the die shall be sort
	 */
	public void restart(boolean sorting){
		restoreAllDie(sorting);
	}
	
}
