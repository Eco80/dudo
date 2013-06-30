package it.ecosw.utility;

import it.ecosw.dudo.games.DieSet;

public class DudoStatistics {
	
	private int numDices;
	
	/**
	 * Constructor
	 * @param numPlayer Number of players
	 */
	public DudoStatistics(int numPlayer){
		numDices = numPlayer * 5;
	}
	
	/**
	 * Return statistics about this hand
	 * @param set Current Set
	 * @return Statistics
	 */
	public String getStatistics(DieSet set){
		String s = numDices+" DADI,";
		int other = numDices - set.numDice();
		int[] stat = new int[6];
		for(int i=0;i<set.numDice();i++) stat[set.getDiceValue(i)-1]++;
		for(int i=0;i<6;i++){
			s += " "+(i+1)+"="+((other/3)+stat[i]);
		}
		return s;
	}

}
