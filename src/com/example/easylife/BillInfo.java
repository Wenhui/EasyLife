package com.example.easylife;

import java.io.ByteArrayOutputStream;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.drawable.BitmapDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.FloatMath;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

@SuppressLint("FloatMath")
public class BillInfo extends Activity implements OnTouchListener{
	
	long bill_id;
	Database db = new Database(this);
	private MediaPlayer mediaPlayer;
	private String OUTPUT_FILE;
	
	ImageView myimage;
	
    private static final String TAG = "Touch";
    @SuppressWarnings("unused")
    private static final float MIN_ZOOM = 1f,MAX_ZOOM = 1f;

    // These matrices will be used to scale points of the image
    Matrix matrix = new Matrix();
    Matrix savedMatrix = new Matrix();

    // The 3 states (events) which the user is trying to perform
    static final int NONE = 0;
    static final int DRAG = 1;
    static final int ZOOM = 2;
    int mode = NONE;

    // these PointF objects are used to record the point(s) the user is touching
    PointF start = new PointF();
    PointF mid = new PointF();
    float oldDist = 1f;

	// Limit zoomable/pannable image
//	private float[] matrixValues = new float[9];
//	private float maxZoom;
//	private float minZoom;
//	private float height;
//	private float width;
//	private RectF viewRect;
	
	
	
	private SensorManager mSensorManager;
	private float mAccel; // acceleration apart from gravity
	private float mAccelCurrent; // current acceleration including gravity
	private float mAccelLast; // last acceleration including gravity

	private final SensorEventListener mSensorListener = new SensorEventListener() {

		public void onSensorChanged(SensorEvent se) {
			float x = se.values[0];
			float y = se.values[1];
			float z = se.values[2];
			mAccelLast = mAccelCurrent;
			mAccelCurrent = (float) Math.sqrt((double) (x*x + y*y + z*z));
			float delta = mAccelCurrent - mAccelLast;
			mAccel = mAccel * 0.9f + delta; // perform low-cut filter
	  
			if(mAccel > 2){
				if(((CheckBox)findViewById(R.id.CheckBoxStatus2)).isChecked() == false)
				{
				((CheckBox)findViewById(R.id.CheckBoxStatus2)).setChecked(true);
				savedata();
				finish();
				}
				}
			}

	    public void onAccuracyChanged(Sensor sensor, int accuracy) {
	    	
	    	
	    }
	};

	
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bill_info2);
		
		
	    mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
	    mSensorManager.registerListener(mSensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
	    mAccel = 0.00f;
	    mAccelCurrent = SensorManager.GRAVITY_EARTH;
	    mAccelLast = SensorManager.GRAVITY_EARTH;
		
	    String bill_title = getIntent().getStringExtra("bill_title");
	    Double bill_price = getIntent().getDoubleExtra("bill_price", 0.0);
	    String bill_category = getIntent().getStringExtra("bill_category");
	    Boolean bill_status = getIntent().getBooleanExtra("bill_status", false);
	    byte[] bill_image = getIntent().getByteArrayExtra("bill_image");
	    OUTPUT_FILE = getIntent().getStringExtra("bill_memo");
	    Bitmap image = byteToDrawable(bill_image);
	    
	    bill_id = getIntent().getLongExtra("bill_id", -1);
	    
	    myimage = (ImageView) findViewById (R.id.imageViewReturnedPic2);
	    myimage.setImageBitmap(image);
	    myimage.setOnTouchListener(this);
	    
	    	

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
				savedata();
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
		});       
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
	
	   /** Called when the activity is first created. */
    public boolean onTouch(View v, MotionEvent event) 
    {
        ImageView view = (ImageView) v;
        view.setScaleType(ImageView.ScaleType.MATRIX);
        float scale;

        dumpEvent(event);
        // Handle touch events here...

        switch (event.getAction() & MotionEvent.ACTION_MASK) 
        {
            case MotionEvent.ACTION_DOWN:   // first finger down only
                                                savedMatrix.set(matrix);
                                                start.set(event.getX(), event.getY());
                                                Log.d(TAG, "mode=DRAG"); // write to LogCat
                                                mode = DRAG;
                                                break;

            case MotionEvent.ACTION_UP: // first finger lifted

            case MotionEvent.ACTION_POINTER_UP: // second finger lifted

                                                mode = NONE;
                                                Log.d(TAG, "mode=NONE");
                                                break;

            case MotionEvent.ACTION_POINTER_DOWN: // first and second finger down

                                                oldDist = spacing(event);
                                                Log.d(TAG, "oldDist=" + oldDist);
                                                if (oldDist > 5f) {
                                                    savedMatrix.set(matrix);
                                                    midPoint(mid, event);
                                                    mode = ZOOM;
                                                    Log.d(TAG, "mode=ZOOM");
                                                }
                                                break;

            case MotionEvent.ACTION_MOVE:

                                                if (mode == DRAG) 
                                                { 
                                                    matrix.set(savedMatrix);
                                                    matrix.postTranslate(event.getX() - start.x, event.getY() - start.y); // create the transformation in the matrix  of points
                                                } 
                                                else if (mode == ZOOM) 
                                                { 
                                                    // pinch zooming
                                                    float newDist = spacing(event);
                                                    Log.d(TAG, "newDist=" + newDist);
                                                    if (newDist > 5f) 
                                                    {
                                                        matrix.set(savedMatrix);
                                                        scale = newDist / oldDist; // setting the scaling of the
                                                                                    // matrix...if scale > 1 means
                                                                                    // zoom in...if scale < 1 means
                                                                                    // zoom out
                                                        matrix.postScale(scale, scale, mid.x, mid.y);
                                                    }
                                                }
                                                break;
        }

        view.setImageMatrix(matrix); // display the transformation on screen

        return true; // indicate event was handled
    }

    /*
     * --------------------------------------------------------------------------
     * Method: spacing Parameters: MotionEvent Returns: float Description:
     * checks the spacing between the two fingers on touch
     * ----------------------------------------------------
     */

    private float spacing(MotionEvent event) 
    {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return FloatMath.sqrt(x * x + y * y);
    }

    /*
     * --------------------------------------------------------------------------
     * Method: midPoint Parameters: PointF object, MotionEvent Returns: void
     * Description: calculates the midpoint between the two fingers
     * ------------------------------------------------------------
     */

    private void midPoint(PointF point, MotionEvent event) 
    {
        float x = event.getX(0) + event.getX(1);
        float y = event.getY(0) + event.getY(1);
        point.set(x / 2, y / 2);
    }

    /** Show an event in the LogCat view, for debugging */
    private void dumpEvent(MotionEvent event) 
    {
        String names[] = { "DOWN", "UP", "MOVE", "CANCEL", "OUTSIDE","POINTER_DOWN", "POINTER_UP", "7?", "8?", "9?" };
        StringBuilder sb = new StringBuilder();
        int action = event.getAction();
        int actionCode = action & MotionEvent.ACTION_MASK;
        sb.append("event ACTION_").append(names[actionCode]);

        if (actionCode == MotionEvent.ACTION_POINTER_DOWN || actionCode == MotionEvent.ACTION_POINTER_UP) 
        {
            sb.append("(pid ").append(action >> MotionEvent.ACTION_POINTER_ID_SHIFT);
            sb.append(")");
        }

        sb.append("[");
        for (int i = 0; i < event.getPointerCount(); i++) 
        {
            sb.append("#").append(i);
            sb.append("(pid ").append(event.getPointerId(i));
            sb.append(")=").append((int) event.getX(i));
            sb.append(",").append((int) event.getY(i));
            if (i + 1 < event.getPointerCount())
                sb.append(";");
        }

        sb.append("]");
        Log.d("Touch Events ---------", sb.toString());
    }	
    
    
    @Override
    protected void onResume() {
      super.onResume();
      mSensorManager.registerListener(mSensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onStop() {
    	super.onStop();
      mSensorManager.unregisterListener(mSensorListener);
      
    }
    
    
    private void savedata(){
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
    }
}


//private void stopPlayingRecording() throws Exception 
//{
//	if(mediaPlayer!=null)
//	{
//		mediaPlayer.stop();
//	}
//}
