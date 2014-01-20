package it.ecosw.dudo.newgame;

import it.ecosw.dudo.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnFocusChangeListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

public class NewGamePlayerRowAdapter extends ArrayAdapter<NewGamePlayerRow> {

	public NewGamePlayerRowAdapter(Context context, int textViewResourceId,NewGamePlayerRow[] objects) {
		super(context, textViewResourceId, objects);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see android.widget.ArrayAdapter#getView(int, android.view.View, android.view.ViewGroup)
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		NewGamePlayerRow c = getItem(position);
		if(c.isHidden()) {
			convertView = inflater.inflate(R.layout.newgame_empty_row, null);
			//convertView.setVisibility(View.GONE);
			return convertView;
		}
		convertView = inflater.inflate(R.layout.newgame_row, null);
		//else convertView.setVisibility(View.VISIBLE);
		TextView number = (TextView)convertView.findViewById(R.id.progressPlayerNum);
		EditText name = (EditText)convertView.findViewById(R.id.formPlayerName);
		name.setTag(position);
		
		name.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
	            if (!hasFocus){
	            	final int position = (Integer)v.getTag();
	            	getItem(position).setName(((EditText)v).getText().toString());
	            }
			}
		});
		
		number.setText(c.getNumber()+")");
		name.setText(c.getName());

		return convertView;
	}
	
	
}
