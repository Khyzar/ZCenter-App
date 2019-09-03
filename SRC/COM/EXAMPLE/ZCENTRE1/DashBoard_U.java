package com.example.zcentre1;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;




















import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class DashBoard_U extends Activity {
	private static final String OUR_BUGSENSE_API_KEY = "dd15fe80";
	ArrayList<String> Categories;
	 private CallbackManager callbackManager;
	    private LoginButton fbLoginButton;
	    String stat="fail";
	public void Search(View v)
	{
		EditText txt = (EditText)findViewById(R.id.editText1);
        String search = txt.getText().toString();
        Intent intent = new Intent(this,Products_U.class);
        intent.putExtra("Search", search);
        startActivity(intent);
	}
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        stat="fail";
        SharedPreferences sett = PreferenceManager.getDefaultSharedPreferences(DashBoard_U.this);
		SharedPreferences.Editor editor = sett.edit();
		editor.putString("status",stat);
	      editor.commit();
        //You need this method to be used only once to configure
        //your key hash in your App Console at
        // developers.facebook.com/apps

       getFbKeyHash("org.code2care.fbloginwithandroidsdk");

        setContentView(R.layout.dashboard_u);
       // Mint.initAndStartSession(this, "13df2298");
  // BugSenseHandler.setup(this, OUR_BUGSENSE_API_KEY);
        fbLoginButton = (LoginButton)findViewById(R.id.fb_login_button);
        fbLoginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
            	 
            	
            	stat="success";
            	SharedPreferences sett = PreferenceManager.getDefaultSharedPreferences(DashBoard_U.this);
    			SharedPreferences.Editor editor = sett.edit();
    			editor.putString("status",stat);
    		      editor.commit();
                System.out.println("Facebook Login Successful!");
                System.out.println("Logged in user Details : ");
                System.out.println("--------------------------");
                System.out.println("User ID  : " + loginResult.getAccessToken().getUserId());
                System.out.println("Authentication Token : " + loginResult.getAccessToken().getToken());
                Toast.makeText(DashBoard_U.this, "Login Successful!", Toast.LENGTH_LONG).show();
            	
            }
            @Override
            public void onCancel() {
            	stat="fail";
            	
                Toast.makeText(DashBoard_U.this, "Login cancelled by user!", Toast.LENGTH_LONG).show();
                System.out.println("Facebook Login failed!!");

            }

            @Override
            public void onError(FacebookException e) {
            	stat="fail";
            	
                Toast.makeText(DashBoard_U.this, "Login unsuccessful!", Toast.LENGTH_LONG).show();
                System.out.println("Facebook Login failed!!");
            }
        });
      
        
	}
	  public void getFbKeyHash(String packageName) {

	        try {
	            PackageInfo info = getPackageManager().getPackageInfo(
	                    packageName,
	                    PackageManager.GET_SIGNATURES);
	            for (Signature signature : info.signatures) {
	                MessageDigest md = MessageDigest.getInstance("SHA");
	                md.update(signature.toByteArray());
	                Log.d("YourKeyHash :", Base64.encodeToString(md.digest(), Base64.DEFAULT));
	                System.out.println("YourKeyHash: "+ Base64.encodeToString(md.digest(), Base64.DEFAULT));
	            }
	        } catch (PackageManager.NameNotFoundException e) {

	        } catch (NoSuchAlgorithmException e) {

	        }

	    }

	    @Override
	    protected void onActivityResult(int reqCode, int resCode, Intent i) {
	        callbackManager.onActivityResult(reqCode, resCode, i);
	       
	    }
	public void OpenFvrt(View v)
	{
		Intent i =new Intent(this,Favourites.class);
		startActivity(i);
	}
	
	public void CategorySelected(View v)
	{
		Categories=new ArrayList<String>();
		CheckBox Mobile=(CheckBox)findViewById(R.id.checkBox1);
		CheckBox Laptops=(CheckBox)findViewById(R.id.checkBox2);
		CheckBox Tablets=(CheckBox)findViewById(R.id.checkBox3);
		CheckBox Desktop=(CheckBox)findViewById(R.id.checkBox4);
		CheckBox TV=(CheckBox)findViewById(R.id.checkBox5);
		CheckBox Consoles=(CheckBox)findViewById(R.id.checkBox6);
		CheckBox PCTools=(CheckBox)findViewById(R.id.checkBox7);
		CheckBox MobileTools=(CheckBox)findViewById(R.id.checkBox8);
		
		if(Mobile.isChecked())
		{
		  Categories.add("Mobile");
		 
		}
		
		if (Laptops.isChecked())
		{
			 Categories.add("Laptop");
			 
		}
		
		if (Tablets.isChecked())
		{
			 Categories.add("Tablets");
			 
		}
		
		if (Desktop.isChecked())
		{
			 Categories.add("Desktop");
			  
		}
		
		if (TV.isChecked())
		{
			Categories.add("TV");
			  
		}
		
		if (Consoles.isChecked())
		{
			
			Categories.add("Consoles");
			 
		}
		
		if (PCTools.isChecked())
		{
			
			Categories.add("PCTools");
			
		}
		
		if (MobileTools.isChecked())
		{
			Categories.add("MobileTools");
		}
		
		Intent i=new Intent(this,Products_U.class);
		i.putStringArrayListExtra("Categories",Categories);
		startActivity(i);
	}
	
}