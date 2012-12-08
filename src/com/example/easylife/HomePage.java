package com.example.easylife;

import java.util.Calendar;

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
    
    
//    private ScheduleClient scheduleClient;
    
//    NotificationManager notificationManager = (NotificationManager) 
//    		  getSystemService(NOTIFICATION_SERVICE); 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage2);
        

        ListView listView = (ListView) findViewById(R.id.listView);
	    Database info = new Database(this);
	    info.open();
	    values = info.getData();
	    info.close();
	    
	    for(int i = 0; i < values.length; i++){
	    	String temp[] = values[i].split("	", 2);
	    	values[i] = temp[1];
	    }
        // First paramenter - Context
        // Second parameter - Layout for the row
        // Third parameter - ID of the TextView to which the data is written
        // Forth - the Array of data
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
          R.layout.itemview, R.id.textitem, values);
        
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView parent, View view, int position, long id){
                // Start your Activity according to the item just clicked.
             //   String product = ((TextView) view).getText().toString(); 
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
 //                   Toast.makeText(getApplicationContext(), "You clicked on YES", Toast.LENGTH_SHORT).show();
                    }
                });
         
                // Setting Negative "NO" Button
                alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    // Write your code here to invoke NO event
 //                   Toast.makeText(getApplicationContext(), "You clicked on NO", Toast.LENGTH_SHORT).show();
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

	
	
    
    
//    
//    public void createNotification(View view) {
//    	
//        //
//	//  Look up the notification manager server 
//        NotificationManager nm = 
//          (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
// 
//        //
//        //  Create your notification 
//        int icon = R.drawable.easylifelog;
//        CharSequence tickerText = "Hello";
//        long when = System.currentTimeMillis();
// 
//        Notification notification = 
//            new Notification( icon, tickerText, when);
// 
//        Context context = getApplicationContext();
//        CharSequence contentTitle = "My notification";
//        CharSequence contentText = "Hello World!";
//        Intent notificationIntent = 
//            new Intent(this, HomePage.class);
//        PendingIntent contentIntent = 
//            PendingIntent.getActivity(this, 0, notificationIntent, 0);
// 
//        notification.setLatestEventInfo(
//            context, 
//            contentTitle, 
//            contentText, 
//            contentIntent);
// 
//        // 
//        //  Send the notification
//  //      nm.notify( 1, notification );
//        
//        notification.flags |= Notification.FLAG_AUTO_CANCEL;
//
//        nm.notify(1, notification);
//
//
//  	  }
    
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
	    values = info.getData();
	    info.close();
	    
	    for(int i = 0; i < values.length; i++){
	    	String temp[] = values[i].split("	", 2);
	    	values[i] = temp[1];
	    }
        // First paramenter - Context
        // Second parameter - Layout for the row
        // Third parameter - ID of the TextView to which the data is written
        // Forth - the Array of data
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
