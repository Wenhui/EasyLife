package com.example.easylife;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.easylife.R;


public class Add extends Activity{
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
        setContentView(R.layout.new_bill);
    
        Spinner spinner = (Spinner) findViewById(R.id.SpinnerCategory);
        
     // Create an ArrayAdapter using the string array and a default spinner layout
     ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
             R.array.category, android.R.layout.simple_spinner_item);
     // Specify the layout to use when the list of choices appears
     adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
     // Apply the adapter to the spinner
     spinner.setAdapter(adapter);
     
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
