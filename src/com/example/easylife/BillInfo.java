package com.example.easylife;

import java.io.ByteArrayOutputStream;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

public class BillInfo  extends Activity{
	
	long bill_id;
	Database db = new Database(this);
	private MediaPlayer mediaPlayer;
	private MediaRecorder recorder;
	private String OUTPUT_FILE;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bill_info);
	    String bill_title = getIntent().getStringExtra("bill_title");
	    Double bill_price = getIntent().getDoubleExtra("bill_price", 0.0);
	    String bill_category = getIntent().getStringExtra("bill_category");
	    Boolean bill_status = getIntent().getBooleanExtra("bill_status", false);
	    byte[] bill_image = getIntent().getByteArrayExtra("bill_image");
	    OUTPUT_FILE = getIntent().getStringExtra("bill_memo");
	    Bitmap image = byteToDrawable(bill_image);
	    
	    bill_id = getIntent().getLongExtra("bill_id", -1);
	    
	    ImageView showpic = (ImageView) findViewById (R.id.imageViewReturnedPic2);
	    showpic.setImageBitmap(image);
	    	

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
					Dialog d = new Dialog(BillInfo.this);
					d.setTitle("Please fill all the blanks!");
					TextView tv = new TextView(BillInfo.this);
					d.setContentView(tv);
					d.show();
				}
				else{
					double price = Double.parseDouble(price_string);	
					db.open();				
					db.updateAllInfo(bill_id, title, price, category, status);
					db.close();	
				}
				finish();
			}
		});         
        
      
      Button back = (Button) findViewById (R.id.ButtonBack2);
      back.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});    
      
      Button play = (Button) findViewById (R.id.ButtonRecord2);
      play.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try {
					playRecording();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
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
	
}
