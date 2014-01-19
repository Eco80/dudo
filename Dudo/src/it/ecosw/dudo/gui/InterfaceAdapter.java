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
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import it.ecosw.dudo.R;
import it.ecosw.dudo.games.PlayerInfo;
import it.ecosw.dudo.games.PlayerSet;
import it.ecosw.dudo.media.PlayFX;

/**
 * Adapter for set of die
 * @author Enrico Strocchi
 */
public class InterfaceAdapter implements OnClickListener,AnimationListener {
	
	private static char dicesymbols[] = {'\u2680','\u2681','\u2682','\u2683','\u2684','\u2685'};
	
	private Context context;
	
	private DiceGraphicObjects[] dgos = null;
	
	private Button[] playerbuttons;
	
	private TextView playername;
	
	private PlayerSet[] sets;
	
	private boolean sorting;
	
	private PlayFX fx;
	
	private boolean diceHide;
	
	private boolean isAnimEnabled;
	
	private int cur;
	
	public InterfaceAdapter(Context context, DiceGraphicObjects[] dgos, Button[] playerbuttons, TextView playername, PlayFX fx, boolean sorting) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.dgos = dgos;
		this.playerbuttons = playerbuttons;
		this.sorting = sorting;
		this.fx = fx;
		// Set button player
		for(int i=0;i<6;i++) {
			playerbuttons[i].setOnClickListener(this);
			playerbuttons[i].setId(1000+i);
		}
		
		this.playername = playername;
		
		cur = 0;
		diceHide = false;
		isAnimEnabled = true;
		
	}

	/**
	 * Activate or deactivate animation during dice roll
	 * @param anim the anim to set
	 */
	public void setAnimEnabled(boolean anim) {
		this.isAnimEnabled = anim;
	}
	
	public boolean rollAllDie(){
		diceHide = true;
		// Roll the dice for all the players
		for(int i=0;i<sets.length;i++){
			sets[i].rollSet(sorting);
		}
		fx.playSoundRoll();
		fx.vibration();
		for(int i=0;i<5;i++) {
			if (sets[cur].isDiceDeleted(i)) dgos[i].deleteAnimation(false,null);
			else {
				dgos[i].rollAnimation(sets[cur].getLastDiceValue(i), isAnimEnabled);
			}
		}
		return true;
	}
	
	/**
	 * Delete a dice from current set
	 * @param sort true if dice shall be sorted
	 * @return true if a dice was deleted
	 */
	public boolean delDice(int player) {
		// TODO Auto-generated method stub
		// delete a dice from sets
		int pos = sets[player].delDice();
		if (pos == -1) {
			fx.playErrorSound();
			return false;
		}
		fx.playSoundLoseDice();
		fx.vibration();
		updatePlayers();
		if (cur == player) dgos[pos].deleteAnimation(isAnimEnabled,this);
		else rollAllDie();
		if (sets[player].isEmpty()) {
			Toast.makeText(
					context,
					sets[player].getPlayerName()+" "+context.getResources().getText(R.string.you_lose),
					Toast.LENGTH_SHORT).show();
			playerbuttons[player].setVisibility(View.GONE);
		}
		//Toast.makeText(context,context.getResources().getText(R.string.text_clickplayername),Toast.LENGTH_SHORT).show();
		
		return true;
	}

	/**
	 * Set Dice Hide
	 * @param true to hide dice
	 */
	public void switchDiceHide(){
		if(sets[cur].isEmpty()) return;
		if (!diceHide) {
			diceHide = true;
			fx.playClickoffSound();
			for(int i=0;i<5;i++) {
				if(!sets[cur].isDiceDeleted(i)) dgos[i].hide(isAnimEnabled);
			}
			return;
		} if (diceHide) {
			diceHide = false;
			fx.playClickonSound();
			for(int i=0;i<5;i++) {
				if(!sets[cur].isDiceDeleted(i)) dgos[i].show(sets[cur].getLastDiceValue(i),isAnimEnabled);
			}
			return;
		}
	}
	
	/**
	 * Restart the match
	 * @param sorting true if the die shall be sort
	 */
	public void restart(){
		cur = 0;
		for(int i=0;i<sets.length;i++) sets[cur].restoreAllDie(sorting);
		updatePlayers();
   		fx.playSoundRoll();
		fx.vibration();
		diceHide = true;
		for(int i=0;i<5;i++) {
			int value = sets[cur].getLastDiceValue(i);
			if (value != 0) dgos[i].rollAnimation(0, isAnimEnabled);
		}
	}

	/**
	 * Set player info
	 * @param sets Array of Player Info
	 * @param numofplayers Number of player
	 */
	public void setPlayerStatus(PlayerSet[] sets){
		this.sets = sets;
        updatePlayers();
        cur = 0;
        playername.setText(sets[0].getPlayerName());
        diceHide = true;
		for(int i=0;i<5;i++) {
			if (sets[cur].isDiceDeleted(i)) dgos[i].deleteAnimation(false,null);
			else dgos[i].rollAnimation(0, isAnimEnabled);
		}
	}
	
	/**
	 * Return player info
	 * @param num player number
	 * @return Player info
	 */
	public PlayerInfo getPlayerInfo(int num){
		return new PlayerInfo(sets[num].getPlayerName(), sets[num].getStatus());
	}

	/**
	 * Return number of current player
	 * @return number of current player
	 */
	public int getNumPlayerCurrent(){
		return cur;
	}
	
	/**
	 * Return current number of player
	 * @return number of player
	 */
	public int getNumPlayer(){
		return sets.length;
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int tmp = v.getId()-1000;
		cur = tmp;
		diceHide = true;
		fx.playSoundRoll();
		fx.vibration();
		for(int i=0;i<5;i++) {
			if (sets[cur].isDiceDeleted(i)) dgos[i].deleteAnimation(false,null);
			else dgos[i].rollAnimation(0, isAnimEnabled);
		}
		playername.setText(sets[cur].getPlayerName());
	}

	/**
	 * Internal method to update players bar
	 */
	private void updatePlayers(){
		// Set text and visibility for player bar
		for(int i=0;i<sets.length;i++) {
			String text = sets[i].getPlayerName()+"\n";
			for(int j=0;j<sets[i].getDieNumber();j++) text += dicesymbols[j];
			playerbuttons[i].setText(text);
			if(sets[i].isEmpty()) playerbuttons[i].setVisibility(View.GONE);
			else playerbuttons[i].setVisibility(View.VISIBLE);
		}
		// Remove player button for player out of the sets
		for(int i=sets.length;i<6;i++)
			playerbuttons[i].setVisibility(View.GONE);
		// Remove bar if there is only one player
		if(sets.length == 1) playerbuttons[0].setVisibility(View.GONE);
	}

	/**
	 * Set sorting for dice
	 * @param sorting true to sort the die set
	 */
	public void setSorting(boolean sorting) {
		this.sorting = sorting;
	}

	@Override
	public void onAnimationEnd(Animation animation) {
		// TODO Auto-generated method stub
		rollAllDie();
	}

	@Override
	public void onAnimationRepeat(Animation animation) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onAnimationStart(Animation animation) {
		// TODO Auto-generated method stub
		
	}
	
}