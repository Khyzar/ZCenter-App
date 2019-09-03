package com.example.zcentre1;


import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;


import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class User extends Activity {
	 String username,password,firstname,lastname,city,phoneNo,email,address;
	 private ProgressDialog pDialog;
	 JSONParser jsonParser = new JSONParser();
	 private static String url_create_product = "http://yoomino.com/SignInUser_fmk.php";
	 
	    // JSON Node names
	    private static final String TAG_SUCCESS = "success";
	  
	
	
	@Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.user);
	 }

	public void add_user(View v)
	{
		 EditText unm=(EditText)findViewById(R.id.editText1);
		 EditText pass=(EditText)findViewById(R.id.editText8);
		 EditText fname=(EditText)findViewById(R.id.editText2);
		 EditText lname=(EditText)findViewById(R.id.editText3);
		 EditText phNo=(EditText)findViewById(R.id.editText5);
		 EditText em=(EditText)findViewById(R.id.editText7);
		 EditText add=(EditText)findViewById(R.id.editText6);
		 EditText cit=(EditText)findViewById(R.id.editText4);
		
		 username=unm.getText().toString();
		 password=pass.getText().toString();
		 firstname=fname.getText().toString();
		 phoneNo=phNo.getText().toString();
		 email=em.getText().toString();
		 address=add.getText().toString();
		 lastname=lname.getText().toString();
		 city=cit.getText().toString();
		 new SignUpUser().execute(); 
		
		
	}
	
	 class SignUpUser extends AsyncTask<String, String, String> {
    	 
	        /**
	         * Before starting background thread Show Progress Dialog
	         * */
	        @Override
	        protected void onPreExecute() {
	            super.onPreExecute();
	            pDialog = new ProgressDialog(User.this);
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
	            params.add(new BasicNameValuePair("UserName", username));
	            params.add(new BasicNameValuePair("Password", password));
	            params.add(new BasicNameValuePair("FirstName", firstname));
	            params.add(new BasicNameValuePair("PhoneNo", phoneNo));
	            params.add(new BasicNameValuePair("Email", email));
	            params.add(new BasicNameValuePair("Address", address));
	            params.add(new BasicNameValuePair("City", city));
	            params.add(new BasicNameValuePair("LastName", lastname));
	            
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
