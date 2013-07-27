package it.ecosw.dudo.historical;

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

import java.text.SimpleDateFormat;
import java.util.Locale;

import it.ecosw.dudo.R;
import it.ecosw.dudo.media.GenDiceImage;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Adapter for history row
 * @author Enrico Strocchi
 */
public class RollDataRowAdapter extends ArrayAdapter<RollData> {
	
	private GenDiceImage gdi;
	
	/**
	 * Constructor
	 * @param context current app context
	 * @param textViewResourceId The resource ID for a layout file containing a TextView to use when instantiating views.  
	 * @param objects The objects to represent in the ListView.
	 * @param gdi class to generate dice image
	 */
	public RollDataRowAdapter(Context context, int textViewResourceId, RollData[] objects, GenDiceImage gdi) {
		super(context, textViewResourceId, objects);
		// TODO Auto-generated constructor stub
		this.gdi = gdi;
	}

	/* (non-Javadoc)
	 * @see android.widget.ArrayAdapter#getView(int, android.view.View, android.view.ViewGroup)
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		RollData roll = getItem(position);
		convertView = inflater.inflate(R.layout.historical_row, null);
		ImageView[] die = new ImageView[5];
		die[0] = (ImageView)convertView.findViewById(R.id.imageHistDice1);
		die[1] = (ImageView)convertView.findViewById(R.id.imageHistDice2);
		die[2] = (ImageView)convertView.findViewById(R.id.imageHistDice3);
		die[3] = (ImageView)convertView.findViewById(R.id.imageHistDice4);
		die[4] = (ImageView)convertView.findViewById(R.id.imageHistDice5);
		TextView date = (TextView) convertView.findViewById(R.id.textRollDate);
		TextView time = (TextView) convertView.findViewById(R.id.textRollTime);
		TextView id = (TextView) convertView.findViewById(R.id.textRollID);
		
		char c;
		for(int i=0;i<5;i++){
			c = roll.getRoll().charAt(i);
			if(c != '0') die[i].setImageBitmap(gdi.getImage(Character.getNumericValue(c)));
			else die[i].setVisibility(View.INVISIBLE);
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy",Locale.getDefault());
		date.setText(sdf.format(roll.getDate()));
		sdf = new SimpleDateFormat("HH:mm:ss",Locale.getDefault());
		time.setText(sdf.format(roll.getDate()));
		
		id.setText((position+1)+") ");
		
		return convertView;
	}

	/* (non-Javadoc)
	 * @see android.widget.BaseAdapter#isEnabled(int)
	 */
	@Override
	public boolean isEnabled(int position) {
		// TODO Auto-generated method stub
		return false;
	}

	
	
}
