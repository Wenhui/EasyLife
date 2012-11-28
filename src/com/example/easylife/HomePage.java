package com.example.easylife;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

public class HomePage extends Activity implements View.OnClickListener{
	
	EditText sqlName;
	EditText sqlPrice;
	EditText sqlCategory;
	EditText sqlStatus;
    EditText sqlRow;
    Button add;
    Button report;
    Button sqlModify;
    Button sqlGetInfo;
    Button sqlDelete;
    Database db = new Database(this);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);
        
        ListView listView = (ListView) findViewById(R.id.listView);
	    Database info = new Database(this);
	    info.open();
	    String[] values = info.getData();
	    info.close();
        // First paramenter - Context
        // Second parameter - Layout for the row
        // Third parameter - ID of the TextView to which the data is written
        // Forth - the Array of data
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
          android.R.layout.simple_list_item_1, android.R.id.text1, values);

        // Assign adapter to ListView
        listView.setAdapter(adapter); 
        
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView parent, View view, int position, long id){
                // Start your Activity according to the item just clicked.
                String product = ((TextView) view).getText().toString(); 
                String parameters[]  = product.split(" ");
                long item_id = Long.parseLong(parameters[0]);
	            db.open();           
				Intent bill_info = new Intent("com.example.easylife.bill_info");
				bill_info.putExtra("bill_title", db.getName(item_id));//String
				bill_info.putExtra("bill_price", db.getPrice(item_id));//Double
				bill_info.putExtra("bill_category", db.getCategory(item_id));//String
				bill_info.putExtra("bill_status", db.getStatus(item_id));//Boolean
				bill_info.putExtra("bill_id", item_id);
	            db.close();    
				startActivity(bill_info);
            }
        });
        
//        TextView tv = (TextView) findViewById(R.id.tvSQLinfo);
//        Database info = new Database(this);
//        info.open();
//        String data = info.getData();
//        info.close();
//        tv.setText(data);
//        
        
          add = (Button)findViewById(R.id.button_add);
          add.setOnClickListener(this);
          report = (Button)findViewById(R.id.button_report);
          report.setOnClickListener(this);
//        sqlRow = (EditText)findViewById(R.id.etSQLrowInfo);
//        sqlModify = (Button)findViewById(R.id.button_SQLEdit);
//        sqlGetInfo = (Button)findViewById(R.id.button_SQLgetInfo);
//        sqlDelete = (Button)findViewById(R.id.button_SQLDelete);
//        sqlModify.setOnClickListener(this);
//        sqlDelete.setOnClickListener(this);
//        sqlGetInfo.setOnClickListener(this);
//        sqlName = (EditText)findViewById(R.id.SQLname);
//        
        
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
        ListView listView = (ListView) findViewById(R.id.listView);
	    Database info = new Database(this);
	    info.open();
	    String[] values = info.getData();
	    info.close();
        // First paramenter - Context
        // Second parameter - Layout for the row
        // Third parameter - ID of the TextView to which the data is written
        // Forth - the Array of data
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
          android.R.layout.simple_list_item_1, android.R.id.text1, values);

        // Assign adapter to ListView
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
		final MediaPlayer mpButtonClick = MediaPlayer.create(this, R.raw.button);
		switch (v.getId()) {
		case R.id.button_add:
			startActivity( new Intent("com.example.easylife.add"));
			mpButtonClick.start();
			break;
		case R.id.button_report:
			startActivity( new Intent("com.example.easylife.report"));
			mpButtonClick.start();
			break;			
			
//		case R.id.button_SQLgetInfo:
//			String s = sqlRow.getText().toString();
//			long l = Long.parseLong(s);
//			Database db = new Database(this);
//			db.open();
//			String returnedName = db.getName(l);
//			double returnedPrice = db.getPrice(l);
//			String returnedCategory = db.getCategory(l);
//			boolean returnedStatus = db.getStatus(l);
//			db.close();
//			
//			sqlName.setText(returnedName);
////			sqlPrice.setText(returnedPrice);
////			sqlCategory.setText(returnedCategory);
//			break;
//			
//			
//		case R.id.button_SQLEdit:
//			String mName = sqlName.getText().toString();
//			String sRow = sqlRow.getText().toString();
//			long lRow = Long.parseLong(sRow);
//			
//			Database mdb = new Database(this);
//			mdb.open();
//			mdb.update(lRow, mName);
//	        TextView tv = (TextView) findViewById(R.id.tvSQLinfo);
//	        String data = mdb.getData();
//	        mdb.close();
//	        tv.setText(data);
//			break;
//			
//		case R.id.button_SQLDelete:
//			String sRow1 = sqlRow.getText().toString();
//			long lRow1 = Long.parseLong(sRow1);
//			Database ex1 = new Database(this);
//			ex1.open();
//			ex1.delete(lRow1);
//	        TextView tv2 = (TextView) findViewById(R.id.tvSQLinfo);
//	        String data2 = ex1.getData();
//	        tv2.setText(data2);
//			ex1.close();
//			break;
			
		}
	}
	
}
