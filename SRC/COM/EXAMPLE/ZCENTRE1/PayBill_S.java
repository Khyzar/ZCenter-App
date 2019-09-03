package com.example.zcentre1;

import java.util.Locale;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;


public class PayBill_S extends Activity {
	
	@Override
    public void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paybill_s);
        AppLocationService appLocationService = new AppLocationService(
    			getApplicationContext());
    		
    	Location gpsLocation= appLocationService.getLocation();

    final double latitude = gpsLocation.getLatitude();
    		final double longitude = gpsLocation.getLongitude();
    		Toast.makeText(this,"Latitude"+latitude,Toast.LENGTH_LONG).show();
    		Toast.makeText(this,"Longitude"+longitude,Toast.LENGTH_LONG).show();
    		String uri = String.format(Locale.ENGLISH, "geo:%f,%f", latitude, longitude);
    		Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
    		 
    	    startActivity(intent);
    	//	TutorialsPoint = new LatLng(latitude , longitude);
//    		
//    		   try { 
//    	            if (googleMap == null) {
//    	               googleMap = ((MapFragment) getFragmentManager().
//    	               findFragmentById(R.id.map)).getMap();
//    	            }
//    	         googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
//    	         Marker TP = googleMap.addMarker(new MarkerOptions().
//    	         position(TutorialsPoint).title("TutorialsPoint"));
//
//    	      } catch (Exception e) {
//    	         e.printStackTrace();
//    	      }		
    		
	}
}
