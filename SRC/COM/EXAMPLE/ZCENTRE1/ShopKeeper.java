package com.example.zcentre1;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

public class ShopKeeper  extends Activity{
	 String name,password,shopname,phoneNo,email,address;
	 private ProgressDialog pDialog;
	 JSONParser jsonParser = new JSONParser();
	 private static String url_create_product = "http://yoomino.com/SignInShopKeeper_fmk.php";
	 
	    // JSON Node names
	    private static final String TAG_SUCCESS = "success";
	 
	 
	 
	 @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.shopkeeper);
	 }
	 
	 public void Add_shopkeeper(View v)
	 {
		 EditText nm=(EditText)findViewById(R.id.editText1);
		 EditText pass=(EditText)findViewById(R.id.editText3);
		 EditText shname=(EditText)findViewById(R.id.editText2);
		 EditText phNo=(EditText)findViewById(R.id.editText4);
		 EditText em=(EditText)findViewById(R.id.editText5);
		 EditText add=(EditText)findViewById(R.id.editText6);
		
		 name=nm.getText().toString();
		 password=pass.getText().toString();
		 shopname=shname.getText().toString();
		 phoneNo=phNo.getText().toString();
		 email=em.getText().toString();
		 address=add.getText().toString();
		 new SignUpShopKeeper().execute(); 
	 }

	 class SignUpShopKeeper extends AsyncTask<String, String, String> {
    	 
	        /**
	         * Before starting background thread Show Progress Dialog
	         * */
	        @Override
	        protected void onPreExecute() {
	            super.onPreExecute();
	            pDialog = new ProgressDialog(ShopKeeper.this);
	            pDialog.setMessage("Signing up..");
	            pDialog.setIndeterminate(false);
	            pDialog.setCancelable(true);
	            pDialog.show();
	        }
	 
	        /**
	         * Creating product
	         * */
	        protected String doInBackground(String... args) {
	           
	            
	            // Building Parameters
	            List<NameValuePair> params = new ArrayList<NameValuePair>();
	            params.add(new BasicNameValuePair("OwnerName", name));
	            params.add(new BasicNameValuePair("Password", password));
	            params.add(new BasicNameValuePair("ShopName", shopname));
	            params.add(new BasicNameValuePair("PhoneNo", phoneNo));
	            params.add(new BasicNameValuePair("Email", email));
	            params.add(new BasicNameValuePair("Address", address));
	            
	            // getting JSON Object
	            // Note that create product url accepts POST method
	            JSONObject json = jsonParser.makeHttpRequest(url_create_product,
	                    "POST", params);
	 
	            // check log cat fro response
	            Log.d("Create Response", json.toString());
	 
	            // check for success tag
	            try {
	                int success = json.getInt(TAG_SUCCESS);
	 
	                if (success == 1) {
	                    // successfully created product
	                   // Intent i = new Intent(getApplicationContext(), Products_S.class);
	                   // startActivity(i);
	                	
	                    // closing this screen
	                    //finish();
	                } else {
	                    // failed to create product
	                }
	            } catch (JSONException e) {
	                e.printStackTrace();
	            }
	 
	            return null;
	        }
	 
	        /**
	         * After completing background task Dismiss the progress dialog
	         * **/
	        protected void onPostExecute(String file_url) {
	            // dismiss the dialog once done
	            pDialog.dismiss();
	        }
	 
	    }
	 
	 
	 
}
