package com.example.easylife;
import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.EditText;
import android.widget.TextView;

import com.example.easylife.R;


public class Confirm extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.confirm_bill);
        String bill_title = getIntent().getStringExtra("bill_title");
        Double bill_price = getIntent().getDoubleExtra("bill_price", 0.0);
        String bill_category = getIntent().getStringExtra("bill_category");
        Boolean bill_status = getIntent().getBooleanExtra("bill_status", false);

        ((TextView)findViewById(R.id.TextBillTitleDisplay)).append(bill_title);
        ((TextView)findViewById(R.id.TextPriceDisplay)).append(bill_price.toString());
        ((TextView)findViewById(R.id.TextCategoryDisplay)).append(bill_category);
        if (bill_status == true){
        	 ((TextView)findViewById(R.id.TextStatusDisplay)).append("Paid");        		 
        }
        else{
        	((TextView)findViewById(R.id.TextStatusDisplay)).append("Unpaid");     
        }
        
        final MediaPlayer mpButtonClick = MediaPlayer.create(this, R.raw.button);
        
        Button confirm = (Button) findViewById (R.id.button_confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				startActivity( new Intent("com.example.easylife.homepage"));
				mpButtonClick.start();
			}
		});        
	}
}
