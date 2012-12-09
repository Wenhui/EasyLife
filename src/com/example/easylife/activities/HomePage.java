package com.example.easylife.activities;

import java.util.Calendar;

import com.example.easylife.R;
import com.example.easylife.R.drawable;
import com.example.easylife.R.id;
import com.example.easylife.R.layout;
import com.example.easylife.R.raw;
import com.example.easylife.services.Database;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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
    String product2;
    String[] values;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);

        //set the listView
        ListView listView = (ListView) findViewById(R.id.listView);
	    Database info = new Database(this);
	    info.open();
	    values = info.getData();
	    info.close();
	    
	    for(int i = 0; i < values.length; i++){
	    	String temp[] = values[i].split("	", 2);
	    	values[i] = temp[1];
	    }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
          R.layout.itemview, R.id.textitem, values);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView parent, View view, int position, long id){
            	String product = values[position];
                long item_id = -1;
        	    db.open();
        	    String[] values_temp = db.getData();
        	    db.close();
        	    String temp[] = new String [2];
        	    for(int i = 0; i < values_temp.length; i++){
        	    	temp = values_temp[i].split("	", 2);
        	    	if(temp[1].equalsIgnoreCase(product)){
                        String parameters[]  = values_temp[i].split("	");
                        item_id = Long.parseLong(parameters[0]);
        	    	}
        	    }
	            db.open();           
				Intent bill_info = new Intent("com.example.easylife.billinfo");
				bill_info.putExtra("bill_title", db.getName(item_id));//String
				bill_info.putExtra("bill_price", db.getPrice(item_id));//Double
				bill_info.putExtra("bill_category", db.getCategory(item_id));//String
				bill_info.putExtra("bill_status", db.getStatus(item_id));//Boolean
				bill_info.putExtra("bill_image", db.getImage(item_id));
				bill_info.putExtra("bill_memo", db.getMemo(item_id));
				bill_info.putExtra("bill_id", item_id);
	            db.close();    
				startActivity(bill_info);
            }
        });
        
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {  
        	  
            
            public boolean onItemLongClick(AdapterView parent, View view, int position, long id){  
                // TODO Auto-generated method stub  
            	product2 = values[position];
            	AlertDialog.Builder alertDialog = new AlertDialog.Builder(HomePage.this);
            	 
                // Setting Dialog Title
                alertDialog.setTitle("Delete");
         
                // Setting Dialog Message
                alertDialog.setMessage("Do you want to delete this?");
         
                // Setting Icon to Dialog
                alertDialog.setIcon(R.drawable.delete);
         
                // Setting Positive "Yes" Button
                alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int which) {
         
                    // Write your code here to invoke YES event
                        long item_id = -1;
                	    db.open();
                	    String[] values_temp = db.getData();
                	    db.close();
                	    String temp[] = new String [2];
                	    for(int i = 0; i < values_temp.length; i++){
                	    	temp = values_temp[i].split("	", 2);
                	    	if(temp[1].equalsIgnoreCase(product2)){
                                String parameters[]  = values_temp[i].split("	");
                                item_id = Long.parseLong(parameters[0]);
                	    	}
                	    }
                        //need a dialog here
        	            db.open();           
        	            db.delete(item_id);
        	            db.close();    
        	            onResume();
                    }
                });
         
                // Setting Negative "NO" Button
                alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                    }
                });
         
                // Showing Alert Message
                alertDialog.show();                
                return true;  
            }  
          });  

          add = (Button)findViewById(R.id.button_add);
          add.setOnClickListener(this);
          report = (Button)findViewById(R.id.button_report);
          report.setOnClickListener(this);      
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
        //update the listview when resume
        ListView listView = (ListView) findViewById(R.id.listView);
	    Database info = new Database(this);
	    info.open();
	    values = info.getData();
	    info.close();
	    
	    for(int i = 0; i < values.length; i++){
	    	String temp[] = values[i].split("	", 2);
	    	values[i] = temp[1];
	    }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
        		R.layout.itemview, R.id.textitem, values);

        // Assign adapter to ListView
        listView.setAdapter(adapter); 
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
		}
	}	
}
