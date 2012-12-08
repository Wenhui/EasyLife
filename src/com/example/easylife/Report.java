package com.example.easylife;
import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.easylife.R;


public class Report extends Activity{
	
    Database db = new Database(this);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		final MediaPlayer mpButtonClick = MediaPlayer.create(this, R.raw.button);
		super.onCreate(savedInstanceState);
		
	    Database info = new Database(this);
	    info.open();
	    String[] values = info.getStatisticInfo();
	    info.close();
		
       setContentView(R.layout.report2);
       
       ListView listView = (ListView) findViewById(R.id.listView_report);
       
       ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
    		   R.layout.itemview, R.id.textitem, values);
    	        // Assign adapter to ListView
    	        listView.setAdapter(adapter); 
    	        
       Button back = (Button) findViewById (R.id.back_button);
       back.setOnClickListener(new View.OnClickListener() {
 			
 			public void onClick(View v) {
 				// TODO Auto-generated method stub
 				
 				mpButtonClick.start();
 				finish();
 			}
 		});   
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}

}
