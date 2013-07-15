package it.ecosw.dudo.gui;

import android.widget.ImageView;
import android.widget.RelativeLayout;

public class DiceGraphicObjects {

	private int pos;
	
	private ImageView image;
	
	private RelativeLayout layout;
	
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
