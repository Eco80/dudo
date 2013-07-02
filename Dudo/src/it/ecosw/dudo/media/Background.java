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

import it.ecosw.dudo.settings.SettingsHelper;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;

/**
 * Class to change background image or color
 * @author Enrico Strocchi
 *
 */
public class Background {
	
	private Context context;
	
	private View parentLayout;
	
	/**
	 * Constructor
	 * @param context App context
	 */
	public Background(Context context,View parentLayout){
		this.context = context;
		this.parentLayout = parentLayout;
	}
	
	/**
	 * Set image or solid color
	 * solor color if string start with '#' otherwise is considered as image
	 * image shall be indicated with the same name in ImageBackground class
	 * @param isimage true if is a image
	 * @param color Solid color
	 * @param image Image name
	 */
	public void setImagebyString(SettingsHelper setting){
		if(!setting.isImageBackgroundType()) setSolidColor(setting.getColorBackground());
		else {
			for (ImageBackground st : ImageBackground.values()) {
		        if (setting.getImageBackground().equals(st.name())) {
		        	setImageBackground(st);
		        	break;
		        }
		    }
		}
	}
	
	/**
	 * Set one image as background
	 * @param ib image to set
	 */
	private void setImageBackground(ImageBackground ib){
		Bitmap bmp = BitmapFactory.decodeResource(context.getResources(),ib.getImageId());
	    BitmapDrawable bitmapDrawable = new BitmapDrawable(context.getResources(),bmp);
	    bitmapDrawable.setTileModeXY(TileMode.REPEAT, TileMode.REPEAT);
		parentLayout.setBackgroundDrawable(bitmapDrawable);
	}
	
	/**
	 * Set the background to the solid color passed by parametes
	 * @param color new color of background
	 */
	private void setSolidColor(int color){
		//parentLayout.setBackgroundColor(Color.parseColor(color));
		parentLayout.setBackgroundColor(color);
	}
	
}
