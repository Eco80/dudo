package it.ecosw.dudo.settings;

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

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Helper for interfacing with settings options
 * @author Enrico Strocchi
 *
 */
public class SettingsHelper {
	
	private Activity mContext;
	
	public static final String SOUND_SETTING = "sound_setting";
	
	public static final String ANIMATION_SETTING = "animation_setting";
	
	public static final String SORTING_SETTING = "sorting_setting";
	
	public static final String VIBRATION_SETTING = "vibration_setting";
	
	public static final String BACKGROUND_SETTING = "background_setting";
	
	public static final String SAVEDPLAY_SETTING = "savedplay_setting";
	
	/**
	 * Constructor
	 * @param context
	 */
	public SettingsHelper(Activity context) {
		mContext = context;
	}
	
	/**
	 * Return true if sound are activated
	 * @return true if sound activated
	 */
	public boolean isSoundActivated(){
		return PreferenceManager.getDefaultSharedPreferences(mContext).getBoolean(SOUND_SETTING, true);
	}
	
	/**
	 * return true if animation are activated
	 * @return true if animation activated
	 */
	public boolean isAnimationActivated(){
		return PreferenceManager.getDefaultSharedPreferences(mContext).getBoolean(ANIMATION_SETTING, true);
	}
	
	/**
	 * Return true if sorting is activated
	 * @return true if sorting activated
	 */
	public boolean isSortingActivated(){
		return PreferenceManager.getDefaultSharedPreferences(mContext).getBoolean(SORTING_SETTING, false);
	}
	
	/**
	 * Return true if vibration is activated
	 * @return true if vibration is activated
	 */
	public boolean isVibrationActivated(){
		return PreferenceManager.getDefaultSharedPreferences(mContext).getBoolean(VIBRATION_SETTING, true);
	}
	
	/**
	 * Return the color for the background
	 * @return color for the background
	 */
	public String getColorBackground(){
		return PreferenceManager.getDefaultSharedPreferences(mContext).getString(BACKGROUND_SETTING, "#21FF21");
	}
	
	/**
	 * Return the value of the last match
	 * @return value.
	 */
	public String getSavedPlay(){
		return PreferenceManager.getDefaultSharedPreferences(mContext).getString(SAVEDPLAY_SETTING, "11111");
	}
	
	/**
	 * Save the value of last play
	 * @param values last value
	 * @return 0 if writing was correct
	 */
	public int setSavedPlay(String values){
		SharedPreferences.Editor spe = PreferenceManager.getDefaultSharedPreferences(mContext).edit();
		spe.putString(SAVEDPLAY_SETTING, values);
		spe.commit();
		return 0;
	}

}
