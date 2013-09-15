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

import android.widget.ImageView;
import android.widget.RelativeLayout;

/**
 * This class allow to anchor che graphics die with controller die
 * @author Enrico Strocchi
 */
public class DiceGraphicObjects {

	private int pos;
	
	private ImageView image;
	
	private RelativeLayout layout;
	
	/**
	 * Constructor
	 * @param pos Position in the graphics
	 * @param image Dice image
	 * @param layout Layout
	 */
	public DiceGraphicObjects(int pos, ImageView image, RelativeLayout layout){
		this.pos = pos;
		this.image = image;
		this.layout = layout;
	}
	
	/**
	 * Return the position of dice in the layout (no from 1 to 5)
	 * @return the pos position of dice in the layout
	 */
	public int getPos() {
		return pos;
	}

	/**
	 * Return ImageView of dice
	 * @return ImageView of dice
	 */
	public ImageView getImage() {
		return image;
	}

	/**
	 * Return layout of dice
	 * @return layout of dice
	 */
	public RelativeLayout getLayout() {
		return layout;
	}
	
}
