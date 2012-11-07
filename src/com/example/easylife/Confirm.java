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
        
//        String bill_title = ((EditText)findViewById(R.id.editTextBillTitle)).getText().toString();
//        ((TextView)findViewById(R.id.TextBillTitleDisplay)).append(bill_title);
        
        setContentView(R.layout.confirm_bill);
	}

}
