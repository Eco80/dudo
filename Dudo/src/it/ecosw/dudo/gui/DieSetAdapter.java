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

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import it.ecosw.dudo.games.DieSet;
import it.ecosw.dudo.media.GenDiceAnimation;
import it.ecosw.dudo.media.GenDiceImage;

public class DieSetAdapter extends DieSet {
	
	private ImageView[] images = null;
	
	private RelativeLayout[] layouts = null;
	
	private boolean anim = true;
	
	private boolean diceHide = false;
	
	private GenDiceImage gdi;
	
	public DieSetAdapter(Context context, boolean sorting, boolean anim, ImageView[] images, RelativeLayout[] layouts) {
		super();
		// TODO Auto-generated constructor stub
		this.images = images;
		this.anim = anim;
		this.layouts = layouts;
		gdi = new GenDiceImage(context);
		update(anim);
	}

	public DieSetAdapter(Context context, boolean sorting, boolean anim, String init, ImageView[] images, RelativeLayout[] layouts) {
		super(init);
		// TODO Auto-generated constructor stub
		this.images = images;
		this.anim = anim;
		this.layouts = layouts;
		gdi = new GenDiceImage(context);
		update(anim);
	}
	

	/**
	 * Activate or deactivate animation during dice roll
	 * @param anim the anim to set
	 */
	public void setAnimEnabled(boolean anim) {
		this.anim = anim;
		if (anim == false) {
			for(int i=0;i<5;i++) images[i].setAnimation(null);
		}
	}
	
		
	/* (non-Javadoc)
	 * @see it.ecosw.dudo.games.DieSet#delDice()
	 */
	@Override
	public int delDice() {
		// TODO Auto-generated method stub
		if (diceHide) return 0;
		int num = super.delDice();
		if (num == 0) return 0;
		update(false);
		return num;
	}


	/* (non-Javadoc)
	 * @see it.ecosw.dudo.games.DieSet#rollSet(boolean)
	 */
	@Override
	public boolean rollSet(boolean sort) {
		// TODO Auto-generated method stub
		if (numDice()==0) restoreAllDie(sort);
		if (diceHide) return false;
		super.rollSet(sort);
		update(anim);
		return true;
	}

	/**
	 * Set Dice Hide
	 * @param true to hide dice
	 */
	public void switchDiceHide(){
		if (!diceHide && numDice()!=0) diceHide = true;
		else diceHide = false;
		update(false);
	}
	
	/**
	 * Restart the match
	 * @param sorting true if the die shall be sort
	 */
	public void restart(boolean sorting){
		restoreAllDie(sorting);
		update(anim);
	}
	
	/**
	 * Update graphics
	 */
	private void update(boolean anim){
		if(diceHide){
			for(int i=0;i<5;i++) 
				images[i].setImageBitmap(gdi.getImage(0));
			return;
		}
		for (int i=0; i<5; i++ ){
			if (isDiceDeleted(i)) {
				images[i].clearAnimation();
				layouts[i].setVisibility(View.GONE);
			} else {
				images[i].setImageBitmap(gdi.getImage(getDiceValue(i)));
				layouts[i].setVisibility(View.VISIBLE);
				if(anim)images[i].startAnimation(GenDiceAnimation.animationFactory(900));
			}
		}
	}
	
}
