package com.example.easylife;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class Bill_info  extends Activity{
	
	long bill_id;
	Database db = new Database(this);
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bill_info);
	    String bill_title = getIntent().getStringExtra("bill_title");
	    Double bill_price = getIntent().getDoubleExtra("bill_price", 0.0);
	    String bill_category = getIntent().getStringExtra("bill_category");
	    Boolean bill_status = getIntent().getBooleanExtra("bill_status", false);
	    bill_id = getIntent().getLongExtra("bill_id", -1);
	    	

       Spinner spinner = (Spinner) findViewById(R.id.SpinnerCategory2);
       // Create an ArrayAdapter using the string array and a default spinner layout
       ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
               R.array.category, android.R.layout.simple_spinner_item);
       // Specify the layout to use when the list of choices appears
       adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
       // Apply the adapter to the spinner
       spinner.setAdapter(adapter);	

        ((EditText)findViewById(R.id.editTextBillTitle2)).setText(bill_title);
        ((EditText)findViewById(R.id.editTextPrice2)).setText(bill_price.toString());
        int category_id = adapter.getPosition(bill_category);
        spinner.setSelection(category_id);
        ((CheckBox)findViewById(R.id.CheckBoxStatus2)).setChecked(bill_status);
        
      Button edit = (Button) findViewById (R.id.ButtonEdit2);
      edit.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub


				String title = ((EditText)findViewById(R.id.editTextBillTitle2)).getText().toString();
				String price_string = ((EditText)findViewById(R.id.editTextPrice2)).getText().toString();
				String category = ((Spinner)findViewById(R.id.SpinnerCategory2)).getSelectedItem().toString();
				boolean status = ((CheckBox)findViewById(R.id.CheckBoxStatus2)).isChecked();
				
				System.out.println("title "+title);
				System.out.println("price_string "+price_string);
				
				if(title.isEmpty() || price_string.isEmpty()){
					Dialog d = new Dialog(Bill_info.this);
					d.setTitle("Please fill all the blanks!");
					TextView tv = new TextView(Bill_info.this);
					d.setContentView(tv);
					d.show();
				}
				else{
					double price = Double.parseDouble(price_string);	
					db.open();				
					db.updateAllInfo(bill_id, title, price, category, status);
					db.close();	
				}	
			}
		});         
        
        
//
//        ((TextView)findViewById(R.id.TextBillTitleDisplay)).append(bill_title);
//        ((TextView)findViewById(R.id.TextPriceDisplay)).append(bill_price.toString());
//        ((TextView)findViewById(R.id.TextCategoryDisplay)).append(bill_category);
//        if (bill_status == true){
//        	 ((TextView)findViewById(R.id.TextStatusDisplay)).append("Paid");        		 
//        }
//        else{
//        	((TextView)findViewById(R.id.TextStatusDisplay)).append("Unpaid");     
//        }
//        
//        final MediaPlayer mpButtonClick = MediaPlayer.create(this, R.raw.button);
//        
//        Button confirm = (Button) findViewById (R.id.button_confirm);
//        confirm.setOnClickListener(new View.OnClickListener() {
//			
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				
//				startActivity( new Intent("com.example.easylife.homepage"));
//				mpButtonClick.start();
//			}
//		});        
	}
}
