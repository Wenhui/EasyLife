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
import android.widget.CheckBox;

import com.example.easylife.R;


public class Add extends Activity{
	
	private MediaPlayer mediaPlayer;
	private MediaRecorder recorder;
	private static final String OUTPUT_FILE= "/sdcard/recordoutput.3gp";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
        setContentView(R.layout.new_bill);
        
        final MediaPlayer mpButtonClick = MediaPlayer.create(this, R.raw.button);
    
        Spinner spinner = (Spinner) findViewById(R.id.SpinnerCategory);

        
     // Create an ArrayAdapter using the string array and a default spinner layout
     ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
             R.array.category, android.R.layout.simple_spinner_item);
     // Specify the layout to use when the list of choices appears
     adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
     // Apply the adapter to the spinner
     spinner.setAdapter(adapter);

     
     Button recordStart = (Button) findViewById (R.id.ButtonRecordStart);
     
     recordStart.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
	//			startActivity( new Intent("com.example.easylife.report"));
				mpButtonClick.start();
				try {
					beginRecording();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
     
     
     Button recordStop = (Button) findViewById (R.id.ButtonRecordStop);
     
     recordStop.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
//				startActivity( new Intent("com.example.easylife.report"));
				mpButtonClick.start();
				
				try {
					stopRecording();
					playRecording();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
     
     Button back = (Button) findViewById (R.id.ButtonBack);
     
     back.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
//				startActivity( new Intent("com.example.easylife.report"));
				mpButtonClick.start();
				
			}
		});
     
     Button next = (Button) findViewById (R.id.ButtonNext);
     
     
     next.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mpButtonClick.start();
				Intent toConfirm = new Intent("com.example.easylife.confirm");
				toConfirm.putExtra("bill_title", ((EditText)findViewById(R.id.editTextBillTitle)).getText().toString());//String
				toConfirm.putExtra("bill_price", Double.parseDouble(((EditText)findViewById(R.id.editTextPrice)).getText().toString()));//Double
				toConfirm.putExtra("bill_category", ((Spinner)findViewById(R.id.SpinnerCategory)).getSelectedItem().toString());//String
				toConfirm.putExtra("bill_status", ((CheckBox)findViewById(R.id.CheckBoxStatus)).isChecked());//Boolean
				startActivity(toConfirm);				
			}
		});
     
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		killMediaRecorder();
		killMediaPlayer();
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
	
	
	
	
	private void beginRecording() throws Exception 
	{
		killMediaRecorder();
		File outFile = new File(OUTPUT_FILE);
		if(outFile.exists())
		{
			outFile.delete();
		}
		recorder = new MediaRecorder();
		recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
		recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
		recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
		recorder.setOutputFile(OUTPUT_FILE);
		recorder.prepare();
		recorder.start();
	}
	
	private void stopRecording() throws Exception 
	{
		if (recorder != null) 
		{
			recorder.stop();
		}
	}

	private void killMediaRecorder() 
	{
		if (recorder != null) 
		{
			recorder.release();
		}
	}
	
	private void killMediaPlayer() 
	{
		if (mediaPlayer != null) 
		{
			try {
				mediaPlayer.release();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void playRecording() throws Exception {
		killMediaPlayer();
		mediaPlayer = new MediaPlayer();
		mediaPlayer.setDataSource(OUTPUT_FILE);
		mediaPlayer.prepare();
		mediaPlayer.start();
	}

	private void stopPlayingRecording() throws Exception 
	{
		if(mediaPlayer!=null)
		{
			mediaPlayer.stop();
		}
	}

}
