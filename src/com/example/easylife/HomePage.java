package com.example.easylife;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class HomePage extends Activity implements View.OnClickListener{
	
	EditText sqlName;
	EditText sqlPrice;
	EditText sqlCategory;
	EditText sqlStatus;
    EditText sqlRow;
    Button sqlModify;
    Button sqlGetInfo;
    Button sqlDelete;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);
        
        
        TextView tv = (TextView) findViewById(R.id.tvSQLinfo);
        Database info = new Database(this);
        info.open();
        String data = info.getData();
        info.close();
        tv.setText(data);
        
        
        sqlRow = (EditText)findViewById(R.id.etSQLrowInfo);
        sqlModify = (Button)findViewById(R.id.button_SQLEdit);
        sqlGetInfo = (Button)findViewById(R.id.button_SQLgetInfo);
        sqlDelete = (Button)findViewById(R.id.button_SQLDelete);
        sqlModify.setOnClickListener(this);
        sqlDelete.setOnClickListener(this);
        sqlGetInfo.setOnClickListener(this);
        sqlName = (EditText)findViewById(R.id.SQLname);
        
        
        //set up the button sound
        final MediaPlayer mpButtonClick = MediaPlayer.create(this, R.raw.button);

        Button add = (Button) findViewById (R.id.button_add);
        add.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				startActivity( new Intent("com.example.easylife.add"));
				mpButtonClick.start();
			}
		});
        
        
        Button report = (Button) findViewById (R.id.button_report);
        report.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				startActivity( new Intent("com.example.easylife.report"));
				mpButtonClick.start();
				
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

	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.button_SQLgetInfo:
			String s = sqlRow.getText().toString();
			long l = Long.parseLong(s);
			Database db = new Database(this);
			db.open();
			String returnedName = db.getName(l);
			double returnedPrice = db.getPrice(l);
			String returnedCategory = db.getCategory(l);
			boolean returnedStatus = db.getStatus(l);
			db.close();
			
			sqlName.setText(returnedName);
//			sqlPrice.setText(returnedPrice);
//			sqlCategory.setText(returnedCategory);
			break;
			
			
		case R.id.button_SQLEdit:
			String mName = sqlName.getText().toString();
			String sRow = sqlRow.getText().toString();
			long lRow = Long.parseLong(sRow);
			
			Database mdb = new Database(this);
			mdb.open();
			mdb.update(lRow, mName);
	        TextView tv = (TextView) findViewById(R.id.tvSQLinfo);
	        String data = mdb.getData();
	        mdb.close();
	        tv.setText(data);
			break;
			
		case R.id.button_SQLDelete:
			String sRow1 = sqlRow.getText().toString();
			long lRow1 = Long.parseLong(sRow1);
			Database ex1 = new Database(this);
			ex1.open();
			ex1.delete(lRow1);
	        TextView tv2 = (TextView) findViewById(R.id.tvSQLinfo);
	        String data2 = ex1.getData();
	        tv2.setText(data2);
			ex1.close();
			break;
			
		}
	}
	
}
