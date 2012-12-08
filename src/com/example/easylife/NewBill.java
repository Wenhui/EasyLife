package com.example.easylife;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.EditText;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.easylife.R;


public class NewBill extends Activity implements View.OnClickListener{
	
	private MediaPlayer mediaPlayer;
	private MediaRecorder recorder;
	private  String OUTPUT_FILE;
    private Intent i;
    final static int CameraData = 0;
    final static int MapData = 1;
    private Bitmap bmp; 
    private ImageView showpic;
    private Spinner spinner;
    private boolean flag;
    
	private int year;
	private int month;
	private int day;
	String title;
	
    private ScheduleClient scheduleClient;
    
    static final int DATE_DIALOG_ID = 999;
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		
	    outState.putByteArray("image", drawableToByteArray(bmp));
	    outState.putBoolean("flag", flag);
	    
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		if(savedInstanceState != null){
		flag = savedInstanceState.getBoolean("flag");  
		
		if(savedInstanceState.getByteArray("image") != null)
			bmp = byteToDrawable(savedInstanceState.getByteArray("image"));
		}
        // Create a new service client and bind our activity to this service
        scheduleClient = new ScheduleClient(this);
        scheduleClient.doBindService();
		
		
		final Calendar c = Calendar.getInstance();
		year = c.get(Calendar.YEAR);
		month = c.get(Calendar.MONTH);
		day = c.get(Calendar.DAY_OF_MONTH);
 
		
		OUTPUT_FILE = "/sdcard/"+generateFileName()+".3gp";
        setContentView(R.layout.new_bill2);


	    showpic = (ImageView) findViewById (R.id.imageViewReturnedPic);
	    showpic.setOnClickListener(this);
	    System.out.println("flag =" + flag);
	    if(flag == true)
	    	showpic.setImageBitmap(bmp);
        final MediaPlayer mpButtonClick = MediaPlayer.create(this, R.raw.button);
             
 
        spinner = (Spinner) findViewById(R.id.SpinnerCategory);
	     // Create an ArrayAdapter using the string array and a default spinner layout
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
				R.array.category, android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		spinner.setAdapter(adapter);

	 

	    
	     
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
    	// When our activity is stopped ensure we also stop the connection to the service
    	// this stops us leaking our activity into the system *bad*
    	if(scheduleClient != null)
    		scheduleClient.doUnbindService();
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
			startActivityForResult( new Intent("com.example.easylife.map"), MapData);
			break;
		case R.id.ButtonNext:
			mpButtonClick.start();
			title = ((EditText)findViewById(R.id.editTextBillTitle)).getText().toString();
			String price_string = ((EditText)findViewById(R.id.editTextPrice)).getText().toString();
			String category = ((Spinner)findViewById(R.id.SpinnerCategory)).getSelectedItem().toString();
			boolean status = ((CheckBox)findViewById(R.id.CheckBoxStatus)).isChecked();
	        InputStream is = getResources().openRawResource(R.drawable.nophoto);
//	        if(flag == false)
//	        	bmp = BitmapFactory.decodeStream(is);
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
				entry.createEntry(title, price, category, status, image, OUTPUT_FILE , year + "_" + month + "_" + day, getNowDateTime());
				entry.close();
		      	Calendar c = Calendar.getInstance();
		      	c.set(year, month, day);
		      	c.set(Calendar.HOUR_OF_DAY, 0);
		      	c.set(Calendar.MINUTE, 0);
		      	c.set(Calendar.SECOND, 0);
		      	if(status == false){
		      	// Ask our service to set an alarm for that date, this activity talks to the client that talks to the service
		      	scheduleClient.setAlarmForNotification(c);
		      	// Notify the user what they just did
		    	Toast.makeText(NewBill.this, "Notification set for: "+ (month+1)+"/"+ day  +"/"+ year, Toast.LENGTH_SHORT).show();
		      	}
				finish();
			}	
			break;
		case R.id.ButtonBack:
			mpButtonClick.start();
			finish();
			break;
		case R.id.DatePicker:
			showDialog(DATE_DIALOG_ID);
			break;
			
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		switch(requestCode) {
		
		case (CameraData): {
		if(resultCode == RESULT_OK) {
			flag = true;
			Bundle extras = data.getExtras();
			bmp = (Bitmap) extras.get("data");
			showpic.setImageBitmap(bmp);
		}
		break;
		}
		case(MapData): {
			 if (resultCode == Activity.RESULT_OK) {
			        // TODO Extract the data returned from the child Activity.
				 
				 String location = data.getStringExtra("location");
				 String titleValue = data.getStringExtra("BillTitle");
				 
//				 TextView l = (TextView)findViewById(R.id.location);
//				 l.setText(titleValue);
				 if (location.contains("Clothing")) {
					 spinner.setSelection(4);
				 	 EditText edittitle = (EditText)findViewById(R.id.editTextBillTitle);
				 	 edittitle.setText(titleValue);
				 }
				 else if (location.contains("Post Office"))
				 {
					 spinner.setSelection(1);
				 	 EditText edittitle = (EditText)findViewById(R.id.editTextBillTitle);
				 	 edittitle.setText(titleValue);
				 }
				 else if (location.contains("Gas Station"))
				 {
					 spinner.setSelection(2);
				 	 EditText edittitle = (EditText)findViewById(R.id.editTextBillTitle);
				 	 edittitle.setText(titleValue);
				 }
				 else if (location.contains("Rent"))
				 {
					 spinner.setSelection(3);
				 	 EditText edittitle = (EditText)findViewById(R.id.editTextBillTitle);
				 	 edittitle.setText(titleValue);
				 }
				 else spinner.setSelection(0);
			      }
			 break;
		}
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
	
   
	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DATE_DIALOG_ID:
		   // set date picker as current date
		   return new DatePickerDialog(this, datePickerListener, 
                         year, month,day);
		}
		return null;
	}

	protected DatePickerDialog.OnDateSetListener datePickerListener 
                = new DatePickerDialog.OnDateSetListener() {
		public void onDateSet(DatePicker view, int selectedYear,
				int selectedMonth, int selectedDay) {
			
			year = selectedYear;
			month = selectedMonth;
			day = selectedDay; 
		    Button datePicker = (Button)findViewById(R.id.DatePicker);
		    datePicker.setText("DueDate:"+month+"/"+day+"/"+year);		
		}
	};
}

  
//private void stopPlayingRecording() throws Exception 
//{
//	if(mediaPlayer!=null)
//	{
//		mediaPlayer.stop();
//	}
//}
