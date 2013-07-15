package it.ecosw.dudo;

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

import it.ecosw.dudo.gui.DiceAdapter;
import it.ecosw.dudo.gui.DiceGraphicObjects;
import it.ecosw.dudo.gui.DieSetAdapter;
import it.ecosw.dudo.gui.HtmlViewerWindow;
import it.ecosw.dudo.media.Background;
import it.ecosw.dudo.media.GenDiceImage;
import it.ecosw.dudo.media.PlayFX;
import it.ecosw.dudo.settings.SettingsActivity;
import it.ecosw.dudo.settings.SettingsHelper;
import it.ecosw.dudo.R;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Main Activity of Dudo Software
 * @author Enrico Strocchi
 *
 */
public class DudoMainActivity extends Activity {
		
	private DieSetAdapter d = null;
	
	private SettingsHelper settings;
	
	private PlayFX fx;
	
	private String version;
	
	private View parentLayout;
	
	private Background background;
	
	private TextView playername;
	
	private Chronometer chrono;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dudo);

        // Set Screen orientation different for Android 2.2 and 2.3+
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.GINGERBREAD)
        	setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        else
        	setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE); 
        
        // Avoid Standby Mode
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        
        try {
        	version = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			Toast.makeText(DudoMainActivity.this,getText(R.string.package_not_found),Toast.LENGTH_SHORT).show();
		}
        
        // Load setting object
        settings = new SettingsHelper(this);
        
        // Check if last version runned was different
        String last = settings.getLastVersionRun();
        if(!version.equals(last)) {
        	   String help = new java.util.Scanner(getResources().openRawResource(R.raw.changelog)).useDelimiter("\\A").next();
       			HtmlViewerWindow.showWindow(this, help,getString(R.string.alert_changelog_label),R.drawable.ic_launcher);
        }
        settings.setLastVersionRun(version);
        
        // Sound Management
        fx = new PlayFX(this,settings);
        
        parentLayout = (View) findViewById(R.id.parentLayout);
        background = new Background(this, parentLayout);
        background.setImagebyString(settings);
                
        // Set playerName
        playername = (TextView)findViewById(R.id.playernameTextView);
        playername.setText(settings.getPlayerName());
        
        // Set chrono
        chrono = (Chronometer)findViewById(R.id.chronometer);
        chrono.setFormat(getText(R.string.text_time)+" %s");
        chrono.start();
        
        DiceGraphicObjects[] dgos = new DiceGraphicObjects[5];
        dgos[0] = new DiceGraphicObjects(
        		1, 
        		(ImageView)findViewById(R.id.ImageButton01), 
        		(RelativeLayout)findViewById(R.id.LayoutDice01)
        );
        dgos[1] = new DiceGraphicObjects(
        		2, 
        		(ImageView)findViewById(R.id.ImageButton02), 
        		(RelativeLayout)findViewById(R.id.LayoutDice02)
        );
        dgos[2] = new DiceGraphicObjects(
        		3, 
        		(ImageView)findViewById(R.id.ImageButton03), 
        		(RelativeLayout)findViewById(R.id.LayoutDice03)
        );
        dgos[3] = new DiceGraphicObjects(
        		4, 
        		(ImageView)findViewById(R.id.ImageButton04), 
        		(RelativeLayout)findViewById(R.id.LayoutDice04)
        );
        dgos[4] = new DiceGraphicObjects(
        		5, 
        		(ImageView)findViewById(R.id.ImageButton05), 
        		(RelativeLayout)findViewById(R.id.LayoutDice05)
        );
        
        // Create new Dice Set
        GenDiceImage gdi = new GenDiceImage(this);
        DiceAdapter[] set = new DiceAdapter[5];
        if (settings.getSavedPlay().equals("00000")) {
        	for(int i=0;i<5;i++) set[i] = new DiceAdapter(gdi, dgos[i],settings.isAnimationActivated());
    		d = new DieSetAdapter(set,dgos,settings.isSortingActivated());
    		Toast.makeText(DudoMainActivity.this,getText(R.string.new_play),Toast.LENGTH_SHORT).show();
        } else {
        	for(int i=0;i<5;i++) set[i] = new DiceAdapter(settings.getSavedPlay().charAt(i),gdi, dgos[i],settings.isAnimationActivated());
        	d = new DieSetAdapter(set,dgos,settings.isSortingActivated());
        	Toast.makeText(DudoMainActivity.this,getText(R.string.last_play_restored),Toast.LENGTH_SHORT).show();
        }
        
        ImageButton reroll = (ImageButton)findViewById(R.id.buttonReroll);
        reroll.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (d.rollSet(settings.isSortingActivated())){;
					chrono.setBase(SystemClock.elapsedRealtime());
					chrono.start();
					fx.playSoundRoll();
					fx.vibration();
				}
			}
		});
        
        ImageButton deldice = (ImageButton)findViewById(R.id.buttonDeldice);
        deldice.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (d.delDice()!=0) {
					chrono.setBase(SystemClock.elapsedRealtime());
					chrono.stop();
					fx.playSoundLoseDice();
					fx.vibration();
					if (d.isEmpty()) {
						Toast.makeText(DudoMainActivity.this,settings.getPlayerName()+" "+getText(R.string.you_lose),Toast.LENGTH_SHORT).show();
					}
				}
			}
		});
        
        View die = (View)findViewById(R.id.dieLayout);
        die.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				d.switchDiceHide();
			}
		});
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_perudo_main, menu);
        return true;
    }
    
    /* (non-Javadoc)
	 * @see android.app.Activity#onStop()
	 */
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		settings.setSavedPlay(d.toString());
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onStart()
	 */
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		background.setImagebyString(settings);
		playername.setText(settings.getPlayerName());
		d.setAnimEnabled(settings.isAnimationActivated());
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
    	switch (item.getItemId()){
    	case R.id.menu_restart:
    		d.restart(settings.isSortingActivated());
    		fx.playSoundRoll();
    		fx.vibration();
    		return true;
    	
    	case R.id.menu_settings:
    		Intent intent = new Intent(getBaseContext(),SettingsActivity.class);
    		startActivity(intent);
    		return true;
    		
    	case R.id.menu_help:
    		// Convert HTML file in raw resource to string
    	    String help = new java.util.Scanner(getResources().openRawResource(R.raw.help)).useDelimiter("\\A").next();
    		HtmlViewerWindow.showWindow(this, help,getString(R.string.alert_help_label),R.drawable.ic_help);
    		return true;
    		
    	case R.id.menu_about:
    		String about = new java.util.Scanner(getResources().openRawResource(R.raw.about)).useDelimiter("\\A").next();
    		HtmlViewerWindow.showWindow(this, about, getString(R.string.alert_about_label)+" - "+getString(R.string.version)+" "+version, R.drawable.ic_launcher);
    		return true;
    		
    	default:
    		return super.onOptionsItemSelected(item);
    	}
	}

}
