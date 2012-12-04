package com.example.easylife;


import java.io.IOException;
import java.util.List;
import java.util.Locale;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;

public class Map extends MapActivity 
{
	public MapController mapController;
    public MyLocationOverlay myPosition;
    public MapView myMapView;
    private static final int ZOOM_IN=Menu.FIRST; 
    private static final int ZOOM_OUT=Menu.FIRST+1;

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
        myMapView.displayZoomControls(false);   
        //set zooming mode and use MyLicationOverlay to dram map
        mapController.setZoom(17);
        myPosition=new MyLocationOverlay();
        List<Overlay> overlays=myMapView.getOverlays();
        overlays.add(myPosition);
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
        //update location
        updateWithNewLocation(location);
        //set to update every 3000ms
        //locationListener
        locationManager.requestLocationUpdates(provider, 3000, 0,locationListener);
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
        myLocationText.setText("you current location: \n"+latLongString+"\n"+addressString);
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
		menu.add(0, ZOOM_IN, Menu.NONE, "·Å´ó");
		menu.add(0, ZOOM_OUT, Menu.NONE, "ËõÐ¡");
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
}
