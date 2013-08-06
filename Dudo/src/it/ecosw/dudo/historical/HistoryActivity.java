package it.ecosw.dudo.historical;

import it.ecosw.dudo.R;
import it.ecosw.dudo.media.Background;
import it.ecosw.dudo.media.BackgroundStatus;
import it.ecosw.dudo.media.GenDiceImage;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;

public class HistoryActivity extends Activity implements OnClickListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historical);
        
        // Set Screen orientation different for Android 2.2 and 2.3+
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.GINGERBREAD)
        	setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        else
        	setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE); 

        Bundle bundle = (Bundle)this.getIntent().getExtras();
        BackgroundStatus status = (BackgroundStatus)bundle.getSerializable("BACKGROUND");
        Background back = new Background(this, (View)findViewById(R.id.historyParent),null);
        back.setBackground(status);
        
        SqlHelper hysto = new SqlHelper(this);
        GenDiceImage gdi = new GenDiceImage(this, bundle.getString("STYLE"));
        
        ListView lv = (ListView)findViewById(R.id.historicalListView);
        RollData[] rolls = hysto.getRollData().toArray(new RollData[hysto.countRow()]);
        RollDataRowAdapter rdra = new RollDataRowAdapter(this, R.layout.historical_row, rolls, gdi);
        lv.setAdapter(rdra);
        
        //OK
        Button ok = (Button)findViewById(R.id.histOkButton);
        ok.setOnClickListener(this);
    }
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		finish();
	}

}
