package com.example.easylife.activities;
import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;

import com.example.easylife.R;
import com.example.easylife.R.id;
import com.example.easylife.R.layout;
import com.example.easylife.R.raw;
import com.example.easylife.services.Database;


public class Report extends Activity{
	
    Database db = new Database(this);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		final MediaPlayer mpButtonClick = MediaPlayer.create(this, R.raw.button);
		super.onCreate(savedInstanceState);

		db.open();
		String[] values = db.getStatisticInfo2();
		db.close();

		setContentView(R.layout.report);

		//Create the GridView to show the statistic information the bill
		GridView gridView = (GridView)findViewById(R.id.gridView1);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
		R.layout.itemview, R.id.textitem, values);
		// Assign adapter to ListView
		gridView.setAdapter(adapter); 


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
