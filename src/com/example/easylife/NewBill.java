package com.example.easylife;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.text.format.DateFormat;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.EditText;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.easylife.R;


public class NewBill extends FragmentActivity implements View.OnClickListener{
	
	private MediaPlayer mediaPlayer;
	private MediaRecorder recorder;
	private  String OUTPUT_FILE;
    private Intent i;
    final static int CameraData = 0;
    private Bitmap bmp; 
    private ImageView showpic;
    private ImageButton takepic;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		
		OUTPUT_FILE = "/sdcard/"+generateFileName()+".3gp";
        setContentView(R.layout.new_bill);
        InputStream is = getResources().openRawResource(R.drawable.nophoto);
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
     
//     takepic = (ImageButton) findViewById (R.id.imageButtonTakePic);
     
//     takepic.setOnClickListener(this);
     
     Button back = (Button)findViewById(R.id.ButtonBack);
     back.setOnClickListener(this);
     Button confirm = (Button)findViewById(R.id.ButtonNext);
     confirm.setOnClickListener(this);
     Button datePicker = (Button)findViewById(R.id.DatePicker);
     datePicker.setOnClickListener(this);
     Button map = (Button)findViewById(R.id.ButtonMap);
     map.setOnClickListener(this);
     
     CheckBox checkbox = (CheckBox)findViewById(R.id.CheckBoxStatus);
     
     checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
		
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			// TODO Auto-generated method stub
			Button datePicker = (Button)findViewById(R.id.DatePicker);
			if(isChecked == true){
				datePicker.setVisibility(4);//Invisible
			}
			else{
				datePicker.setVisibility(0);//Visible
			}
		}
	});
     
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
		final MediaPlayer mpButtonClick = MediaPlayer.create(this, R.raw.button);
		switch (v.getId()) {
		case R.id.imageViewReturnedPic:
			i = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
			startActivityForResult(i, CameraData);
			break;
		case R.id.ButtonMap:
			mpButtonClick.start();
			startActivity( new Intent("com.example.easylife.map"));
			break;
		case R.id.ButtonNext:
			mpButtonClick.start();
			String title = ((EditText)findViewById(R.id.editTextBillTitle)).getText().toString();
			String price_string = ((EditText)findViewById(R.id.editTextPrice)).getText().toString();
			String category = ((Spinner)findViewById(R.id.SpinnerCategory)).getSelectedItem().toString();
			boolean status = ((CheckBox)findViewById(R.id.CheckBoxStatus)).isChecked();
			byte[] image = drawableToByteArray(bmp);
			
			System.out.println("title "+title);
			System.out.println("price_string "+price_string);
			
			if(title.isEmpty() || price_string.isEmpty()){
				Dialog d = new Dialog(NewBill.this);
				d.setTitle("Please fill all the blanks!");
				TextView tv = new TextView(NewBill.this);
				d.setContentView(tv);
				d.show();
			}
			else{
				double price = Double.parseDouble(price_string);	
				Database entry = new Database(NewBill.this);
				entry.open();
				entry.createEntry(title, price, category, status, image, OUTPUT_FILE ,getNowDateTime(),getNowDateTime());
				entry.close();
				
//				Intent toConfirm = new Intent("com.example.easylife.confirm");
//				toConfirm.putExtra("bill_title", title);//String
//				toConfirm.putExtra("bill_price", price);//Double
//				toConfirm.putExtra("bill_category", category);//String
//				toConfirm.putExtra("bill_status", status);//Boolean
//				startActivity(toConfirm);
				finish();
			}							
			break;
		case R.id.ButtonBack:
			mpButtonClick.start();
			finish();
			break;
		case R.id.DatePicker:
			showDatePickerDialog(null);
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
	
	
	public static byte[] drawableToByteArray(Bitmap d) {

	    if (d != null) {
	        ByteArrayOutputStream baos = new ByteArrayOutputStream();
	        d.compress(Bitmap.CompressFormat.PNG, 100, baos);
	        byte[] byteData = baos.toByteArray();

	        return byteData;
	    } else
	        return null;

	}


	public static Bitmap byteToDrawable(byte[] data) {

	    if (data == null)
	        return null;
	    else
	        return new BitmapDrawable(BitmapFactory.decodeByteArray(data, 0, data.length)).getBitmap();
	}

	private String generateFileName() {

		String formatDate = getNowDateTime();
		int random = new Random().nextInt(10000);
		return new StringBuffer().append(formatDate).append(
		random).toString();
	}
	
	private String getNowDateTime(){
		SimpleDateFormat format = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
		String formatDate = format.format(new Date());
		return formatDate;
	}
	
	public void showDatePickerDialog(View v) {
	    DialogFragment newFragment = new DatePickerFragment();
	    newFragment.show(getSupportFragmentManager(), "datePicker");
	}
}
