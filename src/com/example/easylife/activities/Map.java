package com.example.easylife.activities;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.example.easylife.R;
import com.example.easylife.R.drawable;
import com.example.easylife.R.id;
import com.example.easylife.R.layout;
import com.example.easylife.R.string;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

public class Map extends MapActivity 
{
	public MapController mapController;
    public MyLocationOverlay myPosition;
//    public GeoPoint NobHill;
    public MapView myMapView;
    private static final int ZOOM_IN=Menu.FIRST; 
    private static final int ZOOM_OUT=Menu.FIRST+1;
    private HelloItemizedOverlay itemizedoverlay;

	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);
        //get LocationManager instance
        LocationManager locationManager;
        String context=Context.LOCATION_SERVICE;
        locationManager=(LocationManager)getSystemService(context);
        myMapView=(MapView)findViewById(R.id.MapView01);
        //use LocationManager to controll map
        mapController=myMapView.getController();
        //set mode to satellite
        myMapView.setSatellite(true);
        myMapView.displayZoomControls(true);  //set zooming mode and use MyLicationOverlay to draw map
        myMapView.setBuiltInZoomControls(true);   // Set android:clickable=true in main.xml
        mapController.setZoom(17);
        myPosition=new MyLocationOverlay();
        List<Overlay> overlays=myMapView.getOverlays();
        overlays.add(myPosition);
//        NobHill = new GeoPoint(0, 0);
//        OverlayItem overlayitemNobHill = new OverlayItem(NobHill, "", "");
        //set criteria information
        Criteria criteria =new Criteria();
        //latitude requirement
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setCostAllowed(false);
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        //get best criteria
        String provider=locationManager.getBestProvider(criteria, true);
        //get loaction information
        Location location=locationManager.getLastKnownLocation(provider);
//        location.setLatitude(37.378547);
//        location.setLongitude(-122.076909);
        
        //update location
        updateWithNewLocation(location);
        //set to update every 3000ms
        //locationListener
        locationManager.requestLocationUpdates(provider, 3000, 0,locationListener);
        
        List<Overlay> mapOverlays = myMapView.getOverlays();
        Drawable drawable = this.getResources().getDrawable(R.drawable.map);
        itemizedoverlay = new HelloItemizedOverlay(drawable, this);
        
        GeoPoint point = new GeoPoint(37378364,-122076469);
        OverlayItem overlayitem = new OverlayItem(point, "Nob Hill", "Food Market");
        
        GeoPoint point2 = new GeoPoint(37378257,-122076093);
        OverlayItem overlayitem2 = new OverlayItem(point2, "US Post Office", "Post Office");
        
        GeoPoint point3 = new GeoPoint(37378683,-122075021);
        OverlayItem overlayitem3 = new OverlayItem(point3, "Grant Road Shel", "Gas Station");
        
        GeoPoint point4 = new GeoPoint(37380763,-122076351);
        OverlayItem overlayitem4 = new OverlayItem(point4, "Pamela Drive Apartments", "Rent");
        
        GeoPoint point5 = new GeoPoint(37378074,-122076841);
        OverlayItem overlayitem5 = new OverlayItem(point5, "Grant Park Plaza Shopping Center", "Clothing");
        
        itemizedoverlay.addOverlay(overlayitem);
        itemizedoverlay.addOverlay(overlayitem2);
        itemizedoverlay.addOverlay(overlayitem3);
        itemizedoverlay.addOverlay(overlayitem4);
        itemizedoverlay.addOverlay(overlayitem5);
        mapOverlays.add(itemizedoverlay);
        
//        super.onPause();
        
//        if (itemizedoverlay.getFlag() == true) {
//        	startActivity( new Intent("com.example.easylife.add"));
//        }
        
        
//        while (itemizedoverlay.getFlag() == false) {
//        	continue;
//        }
//        
//        startActivity( new Intent("com.example.easylife.add"));
        
        Button confirm = (Button) findViewById (R.id.ConfirmLocation);
        confirm.setOnClickListener(new View.OnClickListener() {
                          
                          public void onClick(View v) {
                                  // TODO Auto-generated method stub
                        	  Intent resultIntent = new Intent();
                        	// TODO Add extras or a data URI to this intent as appropriate.
                        	  try {
                        		  resultIntent.putExtra("location", itemizedoverlay.item.getSnippet());
                        		  resultIntent.putExtra("BillTitle", itemizedoverlay.item.getTitle());
                        	  }
                        	  catch (NullPointerException e) {
                        		  resultIntent.putExtra("location", "no location selected");
                        	  }
                        	  finally {
                        	  setResult(Activity.RESULT_OK, resultIntent);
                                  finish();
                        	  }
                          }
                  });
        
    }
	
	
	
	
    @Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
      if (itemizedoverlay.getFlag() == true) {
    	startActivity( new Intent("com.example.easylife.add"));
    	
    	System.out.println(itemizedoverlay.item.getSnippet());
    	System.out.println(itemizedoverlay.item.getTitle());
    }
		
	}




	private void updateWithNewLocation(Location location) 
    {
        String latLongString;
        TextView myLocationText = (TextView)findViewById(R.id.MapLocation);
        
        String addressString="no location found\n";
        
        if(location!=null)
        {
        	//set location for drawing map
            myPosition.setLocation(location);
            //get latitude and longtitude
            Double geoLat=location.getLatitude()*1E6;
            Double geoLng=location.getLongitude()*1E6;
            //convert into int
            GeoPoint point=new GeoPoint(geoLat.intValue(),geoLng.intValue());
            //animate to location point
            mapController.animateTo(point);
            double lat=location.getLatitude();
            double lng=location.getLongitude();
            latLongString="latitude"+lat+"longtitude"+lng;
            
            double latitude=location.getLatitude();
            double longitude=location.getLongitude();
            //geographic code
            Geocoder gc=new Geocoder(this,Locale.getDefault());
            try
            {
            	//get location information
                List<Address> addresses=gc.getFromLocation(latitude, longitude,1);
                StringBuilder sb=new StringBuilder();
                if(addresses.size()>0)
                {
                    Address address=addresses.get(0);
                    for(int i=0;i<address.getMaxAddressLineIndex();i++)
                        sb.append(address.getAddressLine(i)).append("\n");
                        
                        sb.append(address.getLocality()).append("\n");
                        sb.append(address.getPostalCode()).append("\n");
                        sb.append(address.getCountryName());
                        addressString=sb.toString();
                }
            }catch(IOException e){}
        }
        else
        {
            latLongString="no location found\n";
        }
        //
        myLocationText.setText("you current location: \n"+latLongString+"\n");
    }
    private final LocationListener locationListener=new LocationListener()
    {
    	//when  location changes
        public void onLocationChanged(Location location)
        {
        	updateWithNewLocation(location);
        }
        //when GPS is disabled
        public void onProviderDisabled(String provider)
        {
        	updateWithNewLocation(null);
        }
        //when GPS is enabled 
        public void onProviderEnabled(String provider){}
        //when GPS status is changed
        public void onStatusChanged(String provider,int status,Bundle extras){}
    };
    protected boolean isRouteDisplayed()
	{
		return false;
	}
    
    public boolean onCreateOptionsMenu(Menu menu)
	{
		super.onCreateOptionsMenu(menu);
		menu.add(0, ZOOM_IN, Menu.NONE, "zoom in");
		menu.add(0, ZOOM_OUT, Menu.NONE, "zoom out");
		return true;
	}
    public boolean onOptionsItemSelected(MenuItem item)
	{
		super.onOptionsItemSelected(item);
		switch (item.getItemId())
		{
			case (ZOOM_IN):
				//zoom in 
				mapController.zoomIn();
				return true;
			case (ZOOM_OUT):
				//zoom out
				mapController.zoomOut();
				return true;
		}
		return true;
	}

    
	class MyLocationOverlay extends Overlay
	{
		Location mLocation;
		//when location changed, set this mLocation to draw map
		public void setLocation(Location location)
		{
			mLocation = location;
		}
		@Override
		public boolean draw(Canvas canvas, MapView mapView, boolean shadow, long when)
		{
			super.draw(canvas, mapView, shadow);
			Paint paint = new Paint();
			Point myScreenCoords = new Point();
			//convert latitude and longtitude to screen location
			GeoPoint tmpGeoPoint = new GeoPoint((int)(mLocation.getLatitude()*1E6),(int)(mLocation.getLongitude()*1E6));
			mapView.getProjection().toPixels(tmpGeoPoint, myScreenCoords);
			paint.setStrokeWidth(1);
			paint.setARGB(255, 255, 0, 0);
			paint.setStyle(Paint.Style.STROKE);
			Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.map);
			canvas.drawBitmap(bmp, myScreenCoords.x, myScreenCoords.y, paint);
			canvas.drawText("Here am I", myScreenCoords.x, myScreenCoords.y, paint);
			return true;
		}
	}
	
	class HelloItemizedOverlay extends ItemizedOverlay{
		
		private ArrayList<OverlayItem> mOverlays = new ArrayList<OverlayItem>();
		private Context mContext;
		public boolean flag = false;
		public OverlayItem item;
		
		
		
//	    public interface NoticeDialogListener {
//	        public void onDialogPositiveClick(AlertDialog.Builder dialog);
//	        public void onDialogNegativeClick(AlertDialog.Builder dialog);
//	    }
	//    
//	    // Use this instance of the interface to deliver action events
//	    NoticeDialogListener mListener;

		public boolean getFlag() {
			return flag;
		}



		public HelloItemizedOverlay(Drawable defaultMarker) {
			  super(boundCenterBottom(defaultMarker));
		}
		
		public HelloItemizedOverlay(Drawable defaultMarker, Context context) {
			  super(boundCenterBottom(defaultMarker));
			  mContext = context;
		}
		
		public void addOverlay(OverlayItem overlay) {
		    mOverlays.add(overlay);
		    populate();
		}

		@Override
		protected OverlayItem createItem(int i) {
		  return mOverlays.get(i);
		}

		@Override
		public int size() {
		  return mOverlays.size();
		}
		
		@Override
		protected boolean onTap(int index) {
		  item = mOverlays.get(index);
		  AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
		  dialog.setTitle(item.getTitle());
		  dialog.setMessage(item.getSnippet())
			  .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
		          public void onClick(DialogInterface dialog, int id) {
		              // User confirm the dialog
//		        	  mListener.onDialogPositiveClick(d.this);
//		      		  startActivity( new Intent("com.example.easylife.add"));
		        	  flag = true;
//		        	  System.out.println("user entered: " + flag);
		        	  
		        	  

		        	  
		          }
		      })
		      .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
		          public void onClick(DialogInterface dialog, int id) {
		              // User cancelled the dialog
		        	  
		          }
		      });;
		  dialog.show();
		  
		  return true;
		}
		
//		 @Override
//		    public Dialog onCreateDialog(Bundle savedInstanceState) {
//		        // Use the Builder class for convenient dialog construction
//		        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//		        builder.setMessage(R.string.dialog_fire_missiles)
//		               .setPositiveButton(R.string.fire, new DialogInterface.OnClickListener() {
//		                   public void onClick(DialogInterface dialog, int id) {
//		                       // FIRE ZE MISSILES!
//		                   }
//		               })
//		               .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
//		                   public void onClick(DialogInterface dialog, int id) {
//		                       // User cancelled the dialog
//		                   }
//		               });
//		        // Create the AlertDialog object and return it
//		        return builder.create();
//		    }

	}

   

}
