package it.ecosw.dudo.newgame;

import it.ecosw.dudo.R;
import it.ecosw.dudo.games.PlayerInfo;
import it.ecosw.dudo.media.Background;
import it.ecosw.dudo.media.BackgroundStatus;
import it.ecosw.dudo.settings.SettingsHelper;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class NewGameActivity extends Activity implements OnSeekBarChangeListener,OnClickListener{
	
	public static int MAX_NUM_PLAYERS = 6;
	
	private TextView pn;
	private SeekBar bar;
	private ListView lv;
	
	private int num_player;
	private NewGamePlayerRow[] players;
	
	private SettingsHelper config;
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_newgame);
		
        // Set Screen orientation different for Android 2.2 and 2.3+
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.GINGERBREAD)
        	setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        else
        	setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE); 
        
        // Setting Help
        config = new SettingsHelper(this);
        
        BackgroundStatus status = config.getBackgroundStatus();
        Background back = new Background(this, (View)findViewById(R.id.newgameParent),null);
        back.setBackground(status);
        
        // Player Number Seekbar
        bar = (SeekBar)findViewById(R.id.numplayer_bar);
        bar.setMax(MAX_NUM_PLAYERS-1);
        bar.incrementProgressBy(1);
        bar.setProgress(config.getNumPlayers()-1);
        bar.setOnSeekBarChangeListener(this);
        
        num_player = config.getNumPlayers();
        
        // Text with player number
        pn = (TextView)findViewById(R.id.numplayerSelected);
        pn.setText(getResources().getString(R.string.newgame_numberofplayer)+": "+(bar.getProgress()+1)+"");
        
        // Define the listview of players
        lv = (ListView)findViewById(R.id.numplayer_ListView);
        players = new NewGamePlayerRow[MAX_NUM_PLAYERS];
        for(int i=0;i<MAX_NUM_PLAYERS;i++){
        	players[i] = new NewGamePlayerRow(i+1,config.getPlayerStatus(i).getName());
			if(i<=bar.getProgress()) players[i].setHidden(false);
			else players[i].setHidden(true);
        }
    	NewGamePlayerRowAdapter adapter = new NewGamePlayerRowAdapter(this, R.layout.newgame_row, players);
    	lv.setAdapter(adapter);
        
        // OK Button
        Button ok = (Button)findViewById(R.id.OkButton);
        ok.setOnClickListener(this);
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,boolean fromUser) {
		// TODO Auto-generated method stub
		pn.setText(getResources().getString(R.string.newgame_numberofplayer)+": "+(bar.getProgress()+1)+"");
		num_player = progress+1;
		NewGamePlayerRowAdapter npra = (NewGamePlayerRowAdapter)lv.getAdapter();
		for(int i=0;i<MAX_NUM_PLAYERS;i++){
			if(i<=progress) npra.getItem(i).setHidden(false);
			else npra.getItem(i).setHidden(true);
		}
		lv.invalidateViews();
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		config.setNumPlayers(num_player);
		for(int i=0;i<num_player;i++){
			PlayerInfo pi = new PlayerInfo(players[i].getName(),"00000");
			config.setPlayerStatus(i,pi);
		}
		setResult(RESULT_OK);
		finish();
	}

}
