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

import it.ecosw.dudo.games.Match;
import it.ecosw.dudo.gui.DiceGraphicObjects;
import it.ecosw.dudo.gui.InterfaceAdapter;
import it.ecosw.dudo.gui.HtmlViewerWindow;
import it.ecosw.dudo.media.Background;
import it.ecosw.dudo.media.GenDiceImage;
import it.ecosw.dudo.media.PlayFX;
import it.ecosw.dudo.newgame.NewGameActivity;
import it.ecosw.dudo.settings.SettingsActivity;
import it.ecosw.dudo.settings.SettingsHelper;
import it.ecosw.dudo.R;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Main Activity of Dudo Software
 * @author Enrico Strocchi
 *
 */
public class DudoMainActivity extends Activity {
	
	protected static final int SUB_ACTIVITY_NEW_MATCH = 100;
	
	private InterfaceAdapter d = null;
	
	private SettingsHelper settings;
	
	private String version;
	
	private View parentLayout;
	
	private Background background;
	
	private Chronometer chrono;
	
	private GenDiceImage gdi;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

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
        
        // Check if last version run was different
        String last = settings.getLastVersionRun();
        if(!version.equals(last)) {
        	java.util.Scanner scanner = new java.util.Scanner(getResources().openRawResource(R.raw.changelog));
			String help = scanner.useDelimiter("\\A").next();
			scanner.close();
       		HtmlViewerWindow.showWindow(this, help,getString(R.string.alert_changelog_label),R.drawable.ic_launcher);
        }
        settings.setLastVersionRun(version);
        
        // Sound Management
        PlayFX fx = new PlayFX(this,settings);
        
        // Set playerName
        TextView playername = (TextView)findViewById(R.id.playernameTextView);
        
        // Set chrono
        chrono = (Chronometer)findViewById(R.id.chronometer);
        chrono.setFormat("%s");
		chrono.setBase(SystemClock.elapsedRealtime()+settings.getChronoTime());
        chrono.start();
        
        // Set initial background
        TextView[] text = new TextView[]{playername,chrono};
        parentLayout = (View) findViewById(R.id.parentLayout);
        background = new Background(this, parentLayout,text);
        background.setBackground(settings.getBackgroundStatus());  
        
        // Create new Dice Set
        gdi = new GenDiceImage(this, settings.getStyle());
        
        // Generate static graphic object
        DiceGraphicObjects[] dgos = new DiceGraphicObjects[6];
        dgos[0] = new DiceGraphicObjects(1, 
        		(ImageView)findViewById(R.id.ImageButton01), 
        		(ViewGroup)findViewById(R.id.LayoutDice01),
        		gdi,settings.isAnimationActivated());
        dgos[1] = new DiceGraphicObjects(2, 
        		(ImageView)findViewById(R.id.ImageButton02), 
        		(ViewGroup)findViewById(R.id.LayoutDice02),
        		gdi,settings.isAnimationActivated());
        dgos[2] = new DiceGraphicObjects(3, 
        		(ImageView)findViewById(R.id.ImageButton03), 
        		(ViewGroup)findViewById(R.id.LayoutDice03),
        		gdi,settings.isAnimationActivated());
        dgos[3] = new DiceGraphicObjects(4, 
        		(ImageView)findViewById(R.id.ImageButton04), 
        		(ViewGroup)findViewById(R.id.LayoutDice04),
        		gdi,settings.isAnimationActivated());
        dgos[4] = new DiceGraphicObjects(5, 
        		(ImageView)findViewById(R.id.ImageButton05), 
        		(ViewGroup)findViewById(R.id.LayoutDice05),
        		gdi,settings.isAnimationActivated());
        dgos[5] = new DiceGraphicObjects(5, 
        		(ImageView)findViewById(R.id.ImageButton06), 
        		(ViewGroup)findViewById(R.id.LayoutDice06),
        		gdi,settings.isAnimationActivated());
        
        Button[] players = new Button[6];
        players[0] = (Button)findViewById(R.id.PlayerButton01);
        players[1] = (Button)findViewById(R.id.PlayerButton02);
        players[2] = (Button)findViewById(R.id.PlayerButton03);
        players[3] = (Button)findViewById(R.id.PlayerButton04);
        players[4] = (Button)findViewById(R.id.PlayerButton05);
        players[5] = (Button)findViewById(R.id.PlayerButton06);
        
		d = new InterfaceAdapter(this,dgos,players,playername,fx,settings.isSortingActivated());
        d.setAnimEnabled(settings.isAnimationActivated());
		d.setPlayerStatus(new Match(settings));
        
        View die = (View)findViewById(R.id.dieLayout);
        die.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				d.switchDiceHide();
			}
		});
        die.setOnLongClickListener(new View.OnLongClickListener() {
			
        	@Override
        	public boolean onLongClick(View v) {
				// TODO Auto-generated method stub
				DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
					
					public void onClick(DialogInterface dialog, int which) {
						switch (which) {
								case DialogInterface.BUTTON_POSITIVE:
									d.delDice(d.getNumPlayerCurrent());
									break;
								case DialogInterface.BUTTON_NEGATIVE:
									break;
						}
					}
				};
				AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
				String name = d.getPlayerInfo(d.getNumPlayerCurrent()).getName();
				Object[] MessageArgument = {name};
				String msg = String.format(getString(R.string.are_you_sure),MessageArgument);
				builder.setMessage(msg)
		                  .setPositiveButton(getString(R.string.text_yes), dialogClickListener)
		                  .setNegativeButton(getString(R.string.text_no), dialogClickListener).show();

				return false;

			}
		});
    }
    
    /* (non-Javadoc)
	 * @see android.app.Activity#onActivityResult(int, int, android.content.Intent)
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode == SUB_ACTIVITY_NEW_MATCH) {
			if(resultCode == RESULT_OK) {
				d.setPlayerStatus(new Match(settings));
	    		chrono.setBase(SystemClock.elapsedRealtime());
				chrono.start();
			}
		}
	}

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.dudo_menu, menu);
        return true;
    }
    
    /* (non-Javadoc)
	 * @see android.app.Activity#onStop()
	 */
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		chrono.stop();
		settings.setChronoTime(calculateElapsedTime(chrono));
		for(int i=0;i<d.getNumPlayer();i++) settings.setPlayerStatus(i, d.getPlayerInfo(i));
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onStart()
	 */
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		chrono.setBase(SystemClock.elapsedRealtime()-settings.getChronoTime());
		chrono.start();
		background.setBackground(settings.getBackgroundStatus());
		d.setAnimEnabled(settings.isAnimationActivated());
		d.setSorting(settings.isSortingActivated());
		d.setSixDice(settings.isSixthDieActivated());
		if(!gdi.getCurrent().equals(settings.getStyle())) {
			gdi.setStyle(settings.getStyle());
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
    	switch (item.getItemId()){
    	
    	case R.id.menu_roll:
    		d.rollAllDie();
    		return true;
    	
    	case R.id.menu_new:
    		chrono.setBase(SystemClock.elapsedRealtime());
			chrono.stop();
    		Intent i = new Intent(this,NewGameActivity.class);
    		startActivityForResult(i,SUB_ACTIVITY_NEW_MATCH);
    		return true;
    	
    	case R.id.menu_restart:
    		d.restart();
    		chrono.setBase(SystemClock.elapsedRealtime());
			chrono.start();
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
	
	private long calculateElapsedTime(Chronometer mChronometer) {

	    long stoppedMilliseconds = 0;

	    String chronoText = mChronometer.getText().toString();
	    String array[] = chronoText.split(":");
	    if (array.length == 2) {
	        stoppedMilliseconds = Integer.parseInt(array[0]) * 60 * 1000
	                + Integer.parseInt(array[1]) * 1000;
	    } else if (array.length == 3) {
	        stoppedMilliseconds = Integer.parseInt(array[0]) * 60 * 60 * 1000
	                + Integer.parseInt(array[1]) * 60 * 1000
	                + Integer.parseInt(array[2]) * 1000;
	    }

	    return stoppedMilliseconds;

	}
	
}
