package com.example.easylife;
import java.io.File;
import java.io.InputStream;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.EditText;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.easylife.R;


public class Add extends Activity implements View.OnClickListener{
	
	private MediaPlayer mediaPlayer;
	private MediaRecorder recorder;
	private static final String OUTPUT_FILE= "/sdcard/recordoutput.3gp";
    private Intent i;
    final static int CameraData = 0;
    private Bitmap bmp; 
    private ImageView showpic;
    private ImageButton takepic;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
        setContentView(R.layout.new_bill);
        InputStream is = getResources().openRawResource(R.drawable.easylifelog);
        bmp = BitmapFactory.decodeStream(is);
             
        final MediaPlayer mpButtonClick = MediaPlayer.create(this, R.raw.button);

        Spinner spinner = (Spinner) findViewById(R.id.SpinnerCategory);

        
     // Create an ArrayAdapter using the string array and a default spinner layout
     ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
             R.array.category, android.R.layout.simple_spinner_item);
     // Specify the layout to use when the list of choices appears
     adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
     // Apply the adapter to the spinner
     spinner.setAdapter(adapter);

     
     showpic = (ImageView) findViewById (R.id.imageViewReturnedPic);
     showpic.setOnClickListener(this);
     
     takepic = (ImageButton) findViewById (R.id.imageButtonTakePic);
     takepic.setOnClickListener(this);
     
     Button recordStart = (Button) findViewById (R.id.ButtonRecord);
     
     
     recordStart.setOnTouchListener(new View.OnTouchListener() {

			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				switch(event.getAction() & MotionEvent.ACTION_MASK) {
					case MotionEvent.ACTION_DOWN:
					try {
						mpButtonClick.start();
						beginRecording();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
						return true;
					case MotionEvent.ACTION_UP:
						try {
							stopRecording();
							mpButtonClick.start();
							playRecording();
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						return true;
					default:
						return false;
		      }
			}
    	  });
     

     
     
     Button back = (Button) findViewById (R.id.ButtonBack);
     
     back.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				startActivity( new Intent("com.example.easylife.homepage"));
				mpButtonClick.start();
				
			}
		});
     
     Button next = (Button) findViewById (R.id.ButtonNext);
     
     
     next.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mpButtonClick.start();
				String title = ((EditText)findViewById(R.id.editTextBillTitle)).getText().toString();
				String price_string = ((EditText)findViewById(R.id.editTextPrice)).getText().toString();
				String category = ((Spinner)findViewById(R.id.SpinnerCategory)).getSelectedItem().toString();
				boolean status = ((CheckBox)findViewById(R.id.CheckBoxStatus)).isChecked();
				
				System.out.println("title "+title);
				System.out.println("price_string "+price_string);
				
				if(title.isEmpty() || price_string.isEmpty()){
					Dialog d = new Dialog(Add.this);
					d.setTitle("Please fill all the blanks!");
					TextView tv = new TextView(Add.this);
					d.setContentView(tv);
					d.show();
				}
				else{
					double price = Double.parseDouble(price_string);	
					Database entry = new Database(Add.this);
					entry.open();
					entry.createEntry(title, price, category, status);
					entry.close();
					
					Intent toConfirm = new Intent("com.example.easylife.confirm");
					toConfirm.putExtra("bill_title", title);//String
					toConfirm.putExtra("bill_price", price);//Double
					toConfirm.putExtra("bill_category", category);//String
					toConfirm.putExtra("bill_status", status);//Boolean
					startActivity(toConfirm);
				}				
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

	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.imageButtonTakePic:
			i = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
			startActivityForResult(i, CameraData);
		case R.id.imageViewReturnedPic:
			break;
			
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode == RESULT_OK) {
			Bundle extras = data.getExtras();
			bmp = (Bitmap) extras.get("data");
			showpic.setImageBitmap(bmp);
		}
	}

	
}
