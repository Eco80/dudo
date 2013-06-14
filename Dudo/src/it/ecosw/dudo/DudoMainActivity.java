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

import it.ecosw.dudo.gui.DieSetAdapter;
import it.ecosw.dudo.media.PlayFX;
import it.ecosw.dudo.settings.SettingsActivity;
import it.ecosw.dudo.settings.SettingsHelper;
import it.ecosw.dudo.R;
import android.os.Build;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Color;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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
        
        settings = new SettingsHelper(this);
        
        // Sound Management
        fx = new PlayFX(this,settings);
        
        parentLayout = (View) findViewById(R.id.parentLayout);
        changeBackground();
                
        // initialize image
        ImageView[] images = new ImageView[5];
        images[0] = (ImageView)findViewById(R.id.ImageButton01);
        images[1] = (ImageView)findViewById(R.id.ImageButton02);
        images[2] = (ImageView)findViewById(R.id.ImageButton03);
        images[3] = (ImageView)findViewById(R.id.ImageButton04);
        images[4] = (ImageView)findViewById(R.id.ImageButton05);
        
        // initialize layout
        RelativeLayout[] layouts = new RelativeLayout[5];
        layouts[0] = (RelativeLayout)findViewById(R.id.LayoutDice01);
        layouts[1] = (RelativeLayout)findViewById(R.id.LayoutDice02);
        layouts[2] = (RelativeLayout)findViewById(R.id.LayoutDice03);
        layouts[3] = (RelativeLayout)findViewById(R.id.LayoutDice04);
        layouts[4] = (RelativeLayout)findViewById(R.id.LayoutDice05);
                
        // Create new Dice Set
        if (settings.getSavedPlay().equals("00000")) {
    		d = new DieSetAdapter(this,settings.isSortingActivated(),settings.isAnimationActivated(),images,layouts);
    		Toast.makeText(DudoMainActivity.this,getText(R.string.new_play),Toast.LENGTH_SHORT).show();
        } else {
        	d = new DieSetAdapter(this,settings.isSortingActivated(),settings.isAnimationActivated(),settings.getSavedPlay(),images,layouts);
        	Toast.makeText(DudoMainActivity.this,getText(R.string.last_play_restored),Toast.LENGTH_SHORT).show();
        }
        
        ImageButton reroll = (ImageButton)findViewById(R.id.buttonReroll);
        reroll.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (d.rollSet(settings.isSortingActivated())){;
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
					fx.playSoundLoseDice();
					fx.vibration();
					if (d.numDice()==0) Toast.makeText(DudoMainActivity.this,getText(R.string.you_lose),Toast.LENGTH_SHORT).show();
				}
			}
		});
        
        ImageButton hideDice = (ImageButton)findViewById(R.id.buttonHideDice);
        hideDice.setOnClickListener(new OnClickListener() {
			
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
		changeBackground();
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
    	    // Create the webview
    		WebView wv = new WebView (getBaseContext());
    		wv.loadData(help, "text/html", "utf-8");
    		wv.setBackgroundColor(Color.WHITE);
    		wv.getSettings().setDefaultTextEncodingName("utf-8");
    		// Create the alert dialog
    		new AlertDialog.Builder(this)
    		.setTitle(R.string.alert_help_label)
    		.setView(wv)
    		.setNeutralButton(R.string.close, new DialogInterface.OnClickListener(){
    		    @Override
    		    public void onClick(DialogInterface dialog, int which) {
    		        dialog.cancel();
    		    }

    		  }) 
    		 .show();
    		return true;
    		
    	case R.id.menu_about:
    		new AlertDialog.Builder(this)
    		.setIcon(R.drawable.ic_launcher)
    		.setTitle(R.string.alert_about_label)
    		.setMessage(getString(R.string.alert_about_msg)+"\n"+getString(R.string.version)+" "+version)
    		.setNeutralButton(R.string.close, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					dialog.cancel();
				}
    			
    		})
    		.show();
    		//AlertDialog creditDialog = builder.create();
    		//creditDialog.show();
    		return true;
    		
    	default:
    		return super.onOptionsItemSelected(item);
    	}
	}
        
    /**
     * Update the background color
     */
    private void changeBackground(){
    	 parentLayout.setBackgroundColor(Color.parseColor(settings.getColorBackground()));
    }
    

}
