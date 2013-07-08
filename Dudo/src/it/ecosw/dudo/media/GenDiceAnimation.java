package it.ecosw.dudo.media;

import java.util.Random;

import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.BounceInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;

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
 * This is a static class to generate animation for dice 
 * @author Enrico Strocchi
 */
public class GenDiceAnimation {

	private static Random rnd = new Random();
	
	/**
	 * Generate animation for dice
	 * @return Animation for dice
	 */
	public static Animation animationFactory(){
		//Rotate Animation
		RotateAnimation ra = new RotateAnimation(0,rnd.nextInt(70)-35, 
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		ra.setInterpolator(new LinearInterpolator());
		ra.setDuration(600);
		ra.setFillAfter(true);
			
		//Scale Animation
		ScaleAnimation sa = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, 
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		sa.setInterpolator(new LinearInterpolator());
		sa.setDuration(600);
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
