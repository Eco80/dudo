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

import java.util.Random;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.BounceInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import it.ecosw.dudo.games.DieSet;
import it.ecosw.dudo.media.GenDiceImage;

public class DieSetAdapter extends DieSet {
	
	private ImageView[] images = null;
	
	private RelativeLayout[] layouts = null;
	
	private boolean anim = true;
	
	private boolean diceHide = false;
	
	private Random rnd = null;
	
	private Context context;
	

	public DieSetAdapter(Context context, boolean sorting, boolean anim, ImageView[] images, RelativeLayout[] layouts) {
		super();
		// TODO Auto-generated constructor stub
		this.context = context;
		this.images = images;
		this.anim = anim;
		this.layouts = layouts;
		rnd = new Random();
		for(int i=0;i<5;i++)images[i].setImageBitmap(GenDiceImage.getImage(context, 1));
		update(anim);
	}

	public DieSetAdapter(Context context, boolean sorting, boolean anim, String init, ImageView[] images, RelativeLayout[] layouts) {
		super(init);
		// TODO Auto-generated constructor stub
		this.context = context;
		this.images = images;
		this.anim = anim;
		this.layouts = layouts;
		rnd = new Random();
		for(int i=0;i<5;i++)images[i].setImageBitmap(GenDiceImage.getImage(context, 1));
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
		if (!diceHide && this.numDice()!=0){
			diceHide = true;
			for (int i=0; i<5; i++) 
				images[i].setImageBitmap(GenDiceImage.getImage(context, 0));
		} else {
			diceHide = false;
			update(false);
		}
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
		for (int i=0; i < 5; i++ ){
			updateDice(i);
			if (getDiceValue(i)!=0 && anim) { 
				images[i].startAnimation(animationFactory());
			} 
		}
	}
	
	/**
	 * Update the imageview for the single dice
	 * @param pos position of the dice
	 */
	private void updateDice(int pos){
		int value = getDiceValue(pos);
		if (value != 0) layouts[pos].setVisibility(View.VISIBLE);
		switch (value) {
			case 1: images[pos].setImageBitmap(GenDiceImage.getImage(context, 1)); break;
			case 2: images[pos].setImageBitmap(GenDiceImage.getImage(context, 2)); break;
			case 3: images[pos].setImageBitmap(GenDiceImage.getImage(context, 3)); break;
			case 4: images[pos].setImageBitmap(GenDiceImage.getImage(context, 4)); break;
			case 5: images[pos].setImageBitmap(GenDiceImage.getImage(context, 5)); break;
			case 6: images[pos].setImageBitmap(GenDiceImage.getImage(context, 6)); break;
			default: 
				images[pos].clearAnimation();
				layouts[pos].setVisibility(View.GONE);
		}
	}
	
	/**
	 * Generate animation for dice
	 * @return Animation for dice
	 */
	private Animation animationFactory(){
		//Rotate Animation
		RotateAnimation ra = new RotateAnimation(0,rnd.nextInt(70)-35, 
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		ra.setInterpolator(new LinearInterpolator());
		ra.setDuration(500);
		ra.setFillAfter(true);
		
		//Scale Animation
		ScaleAnimation sa = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, 
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		sa.setInterpolator(new LinearInterpolator());
		sa.setDuration(500);
		sa.setFillAfter(false);
		
		// Animation Set with combination of Rotate and Scale
		AnimationSet as = new AnimationSet(true);
	    as.setFillEnabled(true);
	    as.setFillAfter(true);
	    as.setInterpolator(new BounceInterpolator());
	    as.addAnimation(ra);
	    as.addAnimation(sa);
		
	    return as;
		
	}

}
