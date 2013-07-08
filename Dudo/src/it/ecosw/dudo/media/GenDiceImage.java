package it.ecosw.dudo.media;

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
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.BitmapFactory.Options;
import it.ecosw.dudo.R;

/**
 * Generate Image
 * @author Enrico Strocchi
 */
public class GenDiceImage {
	
	private Bitmap[] images;
	
	/**
	 * Constructor
	 * @param context context of the apps
	 */
	public GenDiceImage(Context context){
		images = new Bitmap[7];
		for(int i=0;i<7;i++) {
			images[i] = genImage(context,i);
		}
	}
	
	/**
	 * Return bitmap of dice
	 * @param value 0 dice empty, 1-6 dice with 1 to 6
	 * @return bitmap of the dice
	 */
	public Bitmap getImage(int value){
		if(value<0 || value>6) return images[0];
		return images[value];
	}
	
	/**
	 * Generate a bitmap for the dice
	 * @param context app context
	 * @param value value of dice
	 * @return bitmap object
	 */
	private static Bitmap genImage(Context context, int value) {
		//Create a new image bitmap and attach a brand new canvas to it
		Options options = new BitmapFactory.Options();
		options.inScaled = false;
		options.inDither = false;
		options.inPreferredConfig = Bitmap.Config.ARGB_8888;
		Bitmap bm = BitmapFactory.decodeResource(context.getResources(), R.drawable.dice_empty,options).copy(Bitmap.Config.ARGB_8888, true);
		Bitmap dot;
		dot = BitmapFactory.decodeResource(context.getResources(), R.drawable.redpoint,options).copy(Bitmap.Config.ARGB_8888, true);
		Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		Canvas canvas = new Canvas(bm);
		switch(value){
		case(1):
			Bitmap lama = BitmapFactory.decodeResource(context.getResources(), R.drawable.lama,options).copy(Bitmap.Config.ARGB_8888, true);
			canvas.drawBitmap(lama,bm.getWidth()/2-lama.getWidth()/2, bm.getHeight()/2-lama.getHeight()/2, paint);
			break;
		case(2):
			canvas.drawBitmap(dot,bm.getWidth()/3-dot.getWidth()/2, bm.getHeight()/3-dot.getHeight()/2, paint);
			canvas.drawBitmap(dot,2*bm.getWidth()/3-dot.getWidth()/2, 2*bm.getHeight()/3-dot.getHeight()/2, paint);
			break;
		case(3):
			canvas.drawBitmap(dot,bm.getWidth()/4-dot.getWidth()/2, bm.getHeight()/4-dot.getHeight()/2, paint);
			canvas.drawBitmap(dot,bm.getWidth()/2-dot.getWidth()/2, bm.getHeight()/2-dot.getHeight()/2, paint);
			canvas.drawBitmap(dot,3*bm.getWidth()/4-dot.getWidth()/2, 3*bm.getHeight()/4-dot.getHeight()/2, paint);
			break;
		case(4):
			canvas.drawBitmap(dot,bm.getWidth()/4-dot.getWidth()/2, bm.getHeight()/4-dot.getHeight()/2, paint);
			canvas.drawBitmap(dot,3*bm.getWidth()/4-dot.getWidth()/2, bm.getHeight()/4-dot.getHeight()/2, paint);
			canvas.drawBitmap(dot,3*bm.getWidth()/4-dot.getWidth()/2, 3*bm.getHeight()/4-dot.getHeight()/2, paint);
			canvas.drawBitmap(dot,bm.getWidth()/4-dot.getWidth()/2, 3*bm.getHeight()/4-dot.getHeight()/2, paint);
			break;
		case(5):
			canvas.drawBitmap(dot,bm.getWidth()/4-dot.getWidth()/2, bm.getHeight()/4-dot.getHeight()/2, paint);
			canvas.drawBitmap(dot,3*bm.getWidth()/4-dot.getWidth()/2, bm.getHeight()/4-dot.getHeight()/2, paint);
			canvas.drawBitmap(dot,bm.getWidth()/2-dot.getWidth()/2, bm.getHeight()/2-dot.getHeight()/2, paint);
			canvas.drawBitmap(dot,3*bm.getWidth()/4-dot.getWidth()/2, 3*bm.getHeight()/4-dot.getHeight()/2, paint);
			canvas.drawBitmap(dot,bm.getWidth()/4-dot.getWidth()/2, 3*bm.getHeight()/4-dot.getHeight()/2, paint);
			break;
		case(6):
			canvas.drawBitmap(dot,bm.getWidth()/4-dot.getWidth()/2, bm.getHeight()/4-dot.getHeight()/2, paint);
			canvas.drawBitmap(dot,3*bm.getWidth()/4-dot.getWidth()/2, bm.getHeight()/4-dot.getHeight()/2, paint);
			canvas.drawBitmap(dot,bm.getWidth()/4-dot.getWidth()/2, bm.getHeight()/2-dot.getHeight()/2, paint);
			canvas.drawBitmap(dot,3*bm.getWidth()/4-dot.getWidth()/2, bm.getHeight()/2-dot.getHeight()/2, paint);
			canvas.drawBitmap(dot,3*bm.getWidth()/4-dot.getWidth()/2, 3*bm.getHeight()/4-dot.getHeight()/2, paint);
			canvas.drawBitmap(dot,bm.getWidth()/4-dot.getWidth()/2, 3*bm.getHeight()/4-dot.getHeight()/2, paint);
			break;
		}
		return bm;
	}

}
