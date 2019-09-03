package com.example.zcentre1;



import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.audiofx.BassBoost.Settings;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import org.apache.http.NameValuePair;
public class login_user extends Activity {
	 private ProgressDialog pDialog;
	 
	    JSONParser jsonParser = new JSONParser();
	    EditText inputName;
	    String type;
	    EditText inputPassword;
	    String state;
	   TextView status;
	 String ID;
	    // url to create new product
	    private static String url_create_product_shopkeeper = "http://yoomino.com/login_fmk.php";
	    private static String url_create_product_user = "http://yoomino.com/login_user_fmk.php";
	    // JSON Node names
	    private static final String TAG_SUCCESS = "success";
	    private static final String TAG_PRODUCT = "product";
	    private static final String TAG_NAME_shopkeeper = "ShopID";
	    private static final String TAG_NAME_user = "UserID";
	   
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.login_user);
	 
	        // Edit Text
	        inputName = (EditText) findViewById(R.id.editText1);
	        inputPassword = (EditText) findViewById(R.id.editText2);
	       
	        status=(TextView)findViewById(R.id.textView1);
	        // Create button
	        Button btnCreateProduct = (Button) findViewById(R.id.button1);
	 
	        // button click event
	        btnCreateProduct.setOnClickListener(new View.OnClickListener() {
	 
	            @Override
	            public void onClick(View view) {
	                // creating new product in background thread
	            	 RadioButton typee=(RadioButton)findViewById(R.id.radio0);
	     	        if(typee.isChecked())
	     	        {
	     	        	type="shopkeeper";
	     	        }
	     	        else
	     	        	type="user";
	            	new CreateNewProduct().execute();
	            }
	        });
	    }
	 
	    /**
	     * Background Async Task to Create new product
	     * */
	    class CreateNewProduct extends AsyncTask<String, String, String> {
	 
	    	
	        /**
	         * Before starting background thread Show Progress Dialog
	         * */
	    	
	        @Override
	        protected void onPreExecute() {
	            super.onPreExecute();
	            pDialog = new ProgressDialog(login_user.this);
	            pDialog.setMessage("logging in..");
	            pDialog.setIndeterminate(false);
	            pDialog.setCancelable(true);
	            pDialog.show();
	            status.setText("logging");
	        }
	 
	        /**
	         * Creating product
	         * */
	        protected String doInBackground(String... args) {
	            String username = inputName.getText().toString();
	            String password = inputPassword.getText().toString();
	           
	            // Building Parameters
	            List<NameValuePair> params = new ArrayList<NameValuePair>();
	            params.add(new BasicNameValuePair("username", username));
	            params.add(new BasicNameValuePair("password", password));
	        
	            // getting JSON Object
	            // Note that create product url accepts POST method
	            JSONObject json;
	            if(type=="shopkeeper")
	            {
	           json = jsonParser.makeHttpRequest(url_create_product_shopkeeper,
	                    "POST", params);
	            }
	            else
	            	{json = jsonParser.makeHttpRequest(url_create_product_user,
		                    "POST", params);}
	            // check log cat fro response
	           Log.d("Log In", json.toString());
	 
	            // check for success tag
	            try {
	                int success = json.getInt(TAG_SUCCESS);
	 
	                if (success == 1) {
	                	state="success";
	                    // successfully created product
	                	//status.setText("login successful");		
	                	  JSONArray productObj = json
                                  .getJSONArray(TAG_PRODUCT); // JSON Array
	                	  
                          // get first product object from JSON Array
                          JSONObject product = productObj.getJSONObject(0);
                          if(type=="shopkeeper")
                          {
                          ID=Integer.toString(product.getInt(TAG_NAME_shopkeeper));
                          }
                          else
                        	  ID=Integer.toString(product.getInt(TAG_NAME_user));
	                    // closing this screen
	                   // finish();
	                } else {
	                	state="failed";
	                	// Intent i = new Intent(getApplicationContext(), login_user.class);
		                  //  startActivity(i);
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
	            if(state=="success")
	            {
	            	status.setText("login successful");
	            	Intent i;
	            	if (type=="shopkeeper")
	            	{
	              	 i = new Intent(getApplicationContext(), TabBar.class);
	            	}
	            	else
	            	{
	            	i = new Intent(getApplicationContext(), TabBar_U.class);
	            	}
	              	SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(login_user.this);
	    			SharedPreferences.Editor editor = settings.edit();
	    			editor.putString("ID",ID);
	    		      editor.commit();
	    		      
	                i.putExtra("ID",ID);      
	              	 startActivity(i);	
	            }
	            if(state=="failed")
	            {
	            	status.setText("login failed");
	            }
	        }
	 
	    }
	}