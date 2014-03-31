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

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import it.ecosw.dudo.R;
import it.ecosw.dudo.games.Match;
import it.ecosw.dudo.games.PlayerInfo;
import it.ecosw.dudo.media.PlayFX;

/**
 * Adapter for set of die
 * @author Enrico Strocchi
 */
public class InterfaceAdapter implements OnClickListener,OnLongClickListener,AnimationListener {
	
	private static char dicesymbols[] = {'\u2680','\u2681','\u2682','\u2683','\u2684','\u2685'};
	
	private Context context;
	
	private DiceGraphicObjects[] dgos = null;
	
	private Button[] playerbuttons;
	
	private TextView playername;
	
	private Match match;
	
	private boolean sorting;
	
	private PlayFX fx;
	
	private boolean diceHide;
	
	private boolean isAnimEnabled;
	
	private int cur;
	
	public InterfaceAdapter(Context context, GraphicsElement ge, PlayFX fx, boolean sorting) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.dgos = ge.getDgos();
		this.playerbuttons = ge.getPlayers();
		this.sorting = sorting;
		this.fx = fx;
		// Set button player listener
		for(int i=0;i<6;i++) {
			playerbuttons[i].setOnClickListener(this);
			playerbuttons[i].setOnLongClickListener(this);
			playerbuttons[i].setId(1000+i);
		}
		
		// Set listener for dice
		ge.getDieLayout().setOnClickListener(this);
		ge.getDieLayout().setId(1);
		
		this.playername = ge.getPlayername();
		
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
		match.rollAllDie(sorting);
		fx.playSoundRoll();
		fx.vibration();
		int max = 0;
		if (match.areSixDice()) max = 6;
		else max = 5;
		for(int i=0;i<max;i++) {
			if (match.isDieDeleted(cur,i)) dgos[i].deleteAnimation(false,null);
			else dgos[i].rollAnimation(0,isAnimEnabled);
		}
		if(!match.areSixDice()) dgos[5].deleteAnimation(false,null); 
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
		int pos = match.deleteDice(player);
		if (pos == -1) {
			fx.playErrorSound();
			return false;
		}
		fx.playSoundLoseDice();
		fx.vibration();
		updatePlayers();
		if (cur == player) dgos[pos].deleteAnimation(isAnimEnabled,this);
		else rollAllDie();
		if (match.isEmpty(player)) {
			Toast.makeText(
					context,
					match.getPlayerName(player)+" "+context.getResources().getText(R.string.you_lose),
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
		if(match.isEmpty(cur)) return;
		if (!diceHide) {
			diceHide = true;
			fx.playClickoffSound();
			for(int i=0;i<6;i++) {
				if(i==5 && !match.areSixDice()) break;
				if(!match.isDieDeleted(cur,i)) dgos[i].hide(isAnimEnabled);
			}
			return;
		} if (diceHide) {
			diceHide = false;
			fx.playClickonSound();
			for(int i=0;i<6;i++) {
				if(i==5 && !match.areSixDice()) break;
				if(!match.isDieDeleted(cur,i)) dgos[i].show(match.getDiceValue(cur,i),isAnimEnabled);
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
		match.restart(sorting);
		updatePlayers();
   		fx.playSoundRoll();
		fx.vibration();
		diceHide = true;
		for(int i=0;i<5;i++) {
			dgos[i].rollAnimation(0, isAnimEnabled);
		}
	}

	/**
	 * Set player info
	 * @param sets Array of Player Info
	 * @param numofplayers Number of player
	 */
	public void setPlayerStatus(Match match){
		this.match = match;
        updatePlayers();
        cur = 0;
        playername.setText(match.getPlayerName(0));
        diceHide = true;
		int max = 0;
		if (match.areSixDice()) max = 6;
		else max = 5;
		for(int i=0;i<max;i++) {
			if (match.isDieDeleted(0,i)) dgos[i].deleteAnimation(false,null);
			else dgos[i].rollAnimation(0, isAnimEnabled);
		}
		if(!match.areSixDice()) dgos[5].deleteAnimation(false,null);
	}
	
	/**
	 * Return player info
	 * @param num player number
	 * @return Player info
	 */
	public PlayerInfo getPlayerInfo(int num){
		return new PlayerInfo(match.getPlayerName(num),match.getPlayerStatus(num));
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
		return match.getNumPlayer();
	}
	
	/**
	 * Internal method to update players bar
	 */
	private void updatePlayers(){
		// Set text and visibility for player bar
		for(int i=0;i<match.getNumPlayer();i++) {
			String text = match.getPlayerName(i)+"\n";
			for(int j=0;j<match.getNumDice(i);j++) text += dicesymbols[j];
			playerbuttons[i].setText(text);
			if(match.isEmpty(i)) playerbuttons[i].setVisibility(View.GONE);
			else playerbuttons[i].setVisibility(View.VISIBLE);
		}
		// Remove player button for player out of the sets
		for(int i=match.getNumPlayer();i<6;i++)
			playerbuttons[i].setVisibility(View.GONE);
		// Remove bar if there is only one player
		if(match.getNumPlayer() == 1) playerbuttons[0].setVisibility(View.GONE);
	}

	/**
	 * Set sorting for dice
	 * @param sorting true to sort the die set
	 */
	public void setSorting(boolean sorting) {
		this.sorting = sorting;
	}
	
	/**
	 * Set six dice on the match
	 * @param sixdice true to set sixdice
	 */
	public void setSixDice(boolean sixdice){
		if(match.setSixDice(sixdice)) rollAllDie();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v.getId() == 1){
			switchDiceHide();
			return;
		}
		if (v.getId() == 2){
			DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
				
				public void onClick(DialogInterface dialog, int which) {
					switch (which) {
							case DialogInterface.BUTTON_POSITIVE:
								delDice(getNumPlayerCurrent());
								break;
							case DialogInterface.BUTTON_NEGATIVE:
								break;
					}
				}
			};
			AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
			String name = getPlayerInfo(getNumPlayerCurrent()).getName();
			Object[] MessageArgument = {name};
			String msg = String.format(context.getString(R.string.delete_are_you_sure),MessageArgument);
			builder.setMessage(msg)
	                  .setPositiveButton(context.getString(R.string.text_yes), dialogClickListener)
	                  .setNegativeButton(context.getString(R.string.text_no), dialogClickListener).show();
	
			return;
		}
		
		int tmp = v.getId()-1000;
		cur = tmp;
		diceHide = true;
		fx.playSoundRoll();
		fx.vibration();
		int max = 0;
		if(match.areSixDice()) max = 6;
		else max = 5;
		for(int i=0;i<max;i++) {
			if (match.isDieDeleted(cur,i)) dgos[i].deleteAnimation(false,null);
			else dgos[i].rollAnimation(0, isAnimEnabled);
		}
		playername.setText(match.getPlayerName(cur));
	}

	@Override
	public boolean onLongClick(View v) {
		// TODO Auto-generated method stub
		return false;
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
