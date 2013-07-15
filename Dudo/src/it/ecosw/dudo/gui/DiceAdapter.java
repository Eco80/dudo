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

import android.view.View;
import it.ecosw.dudo.games.Dice;
import it.ecosw.dudo.media.GenDiceAnimation;
import it.ecosw.dudo.media.GenDiceImage;

/**
 * Dice adapter to manage graphics
 * @author Enrico Strocchi
 */
public class DiceAdapter extends Dice {
	
	private final static int ANIMATIONTIME = 900; 
	
	private GenDiceImage gdi;
	
	private boolean anim;
	
	private boolean hide;
	
	private DiceGraphicObjects dgo;
	
	public DiceAdapter(GenDiceImage gdi, DiceGraphicObjects dgo, boolean animation) {
		super();
		// TODO Auto-generated constructor stub
		this.gdi = gdi;
		this.dgo = dgo;
		setAnimated(animation);
		hide = false;
		dgo.getImage().setImageBitmap(gdi.getImage(getLastRoll()));
		dgo.getLayout().setVisibility(View.VISIBLE);
		if(isAnimated()) dgo.getImage().startAnimation(GenDiceAnimation.animationRollFactory(ANIMATIONTIME));
		else dgo.getImage().setAnimation(null);
	}

	public DiceAdapter(char c, GenDiceImage gdi, DiceGraphicObjects dgo, boolean animation) {
		super(c);
		// TODO Auto-generated constructor stub
		this.gdi = gdi;
		this.dgo = dgo;
		setAnimated(animation);
		hide = false;
		dgo.getImage().setImageBitmap(gdi.getImage(getLastRoll()));
		dgo.getLayout().setVisibility(View.VISIBLE);
		if(isAnimated()) dgo.getImage().startAnimation(GenDiceAnimation.animationRollFactory(900));
		else dgo.getImage().setAnimation(null);
	}

	/**
	 * Hide current dice
	 */
	public void hide(){
		hide = true;
		dgo.getImage().setImageBitmap(gdi.getImage(0));
	}
	
	/**
	 * Show current dice
	 */
	public void show(){
		hide = false;
		dgo.getImage().setImageBitmap(gdi.getImage(getLastRoll()));
	}
	
	/* (non-Javadoc)
	 * @see it.ecosw.dudo.games.Dice#newRoll()
	 */
	@Override
	public int newRoll() {
		// TODO Auto-generated method stub
		if(isDeleted()) return 0;
		return super.newRoll();
	}
	
	/* (non-Javadoc)
	 * @see it.ecosw.dudo.games.Dice#delete()
	 */
	@Override
	public boolean delete() {
		// TODO Auto-generated method stub
		if(isDeleted()) return false;
		boolean b = super.delete();
		if (b) {
			if(isAnimated()) dgo.getImage().startAnimation(GenDiceAnimation.animationDelFactory(ANIMATIONTIME));
			else dgo.getLayout().setVisibility(View.GONE);
		}
		return b;
	}

	/**
	 * Return true if animations are enabled
	 * @return true if animation are enabled
	 */
	public boolean isAnimated() {
		return anim;
	}

	/**
	 * Set the animation enabled
	 * @param anim the anim to set
	 */
	public void setAnimated(boolean anim) {
		this.anim = anim;
	}

	/**
	 * Set current Graphic Objects
	 * @param dgo the dgo to set
	 */
	public void setDgo(DiceGraphicObjects dgo) {
		this.dgo = dgo;
	}
	
	/**
	 * Update the graphic of current dice
	 */
	public void update(){
		if(isDeleted()){
			dgo.getLayout().setVisibility(View.GONE);
		} else {
			if(hide) dgo.getImage().setImageBitmap(gdi.getImage(0));
			else {
				dgo.getImage().setImageBitmap(gdi.getImage(getLastRoll()));
				dgo.getLayout().setVisibility(View.VISIBLE);
				if(isAnimated()) dgo.getImage().startAnimation(GenDiceAnimation.animationRollFactory(ANIMATIONTIME));
				else dgo.getImage().setAnimation(null);
			}
		}
	}
	
}
