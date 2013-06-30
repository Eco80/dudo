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

import it.ecosw.dudo.R;

/**
 * Enum with the list of background images
 * @author Enrico Strocchi
 *
 */
public enum ImageBackground {
	GREENCARPET(R.drawable.green_carpet),
	PARTYLIGHT(R.drawable.partylight),
	WOOD(R.drawable.wood),
	GALAXY(R.drawable.galaxy),
	LAVA(R.drawable.lava);
	
	private int imageid;
	
	/**
	 * Constructors
	 * @param id Image id
	 */
	private ImageBackground(int id){
		imageid = id;
	}
	
	/**
	 * Get ID of image
	 * @return id of image
	 */
	public int getImageId(){
		return imageid;
	}
}
