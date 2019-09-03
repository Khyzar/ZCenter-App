package com.example.zcentre1;




import com.example.zcentre1.Analytics.TrackerName;
import com.google.analytics.tracking.android.Tracker;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.analytics.HitBuilders;
import com.splunk.mint.Mint;
import com.google.android.gms.analytics.GoogleAnalytics;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
//import com.google.android.gms.ads.AdRequest;
//import com.google.android.gms.ads.AdView;
public class Login extends Activity {
	//private EasyTracker easyTracker = null;
	 @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.login);
	        
	        
	        try
	        {
	        	com.google.android.gms.analytics.Tracker t = ((Analytics) getApplication()).getTracker(TrackerName.APP_TRACKER);
	        	        t.setScreenName("Home");
	        	        t.send(new HitBuilders.AppViewBuilder().build());

	        }
	        catch(Exception  e)
	        {
	        Toast.makeText(getApplicationContext(), "Error"+e.getMessage(), 1).show();
	        }
//	        Tracker t = ((GoogleAnalyticsApp) getApplication()).getTracker(TrackerName.APP_TRACKER);
//            t.setScreenName("Home");
//            t.send(new HitBuilders.AppViewBuilder().build());

	        Mint.initAndStartSession(Login.this, "3e13cdd1");
	       // AdView ad=(AdView)findViewById(R.id.adView);
	        //ad.loadAd(new AdRequest.Builder().build());
	        AdView mAdView = (AdView) findViewById(R.id.adView);
	        AdRequest adRequest = new AdRequest.Builder().build();
	        mAdView.loadAd(adRequest);
	    }
	 public void login_user(View v)
	 {
		 Intent i=new Intent(this,login_user.class);
		 startActivity(i);
		 
	 }
	 public void sign_up_user(View v)
	 {
		 Intent i=new Intent(this,User.class);
		 startActivity(i);
		 
	 }
	 public void sign_up_shopKeeper(View v)
	 {
		 Intent i=new Intent(this,ShopKeeper.class);
		 startActivity(i);
		 
	 }
//	 @Override
//	     protected void onStart() {
//	         // TODO Auto-generated method stub
//	         super.onStart();
//	         GoogleAnalytics.getInstance(Login.this).reportActivityStart(this);
//	     }
//	     @Override
//	     protected void onStop() {
//	         // TODO Auto-generated method stub
//	         super.onStop();
//	         GoogleAnalytics.getInstance(Login.this).reportActivityStop(this);
//	
//	     }

	 @Override
	    protected void onStart() {
	        // TODO Auto-generated method stub
	        super.onStart();
	        GoogleAnalytics.getInstance(this).reportActivityStart(this);
	    }
	 
	    @Override
	    protected void onStop() {
	        // TODO Auto-generated method stub
	        super.onStop();
	        GoogleAnalytics.getInstance(this).reportActivityStop(this);
	    }
}
