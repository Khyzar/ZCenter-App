package com.example.zcentre1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.ListView;

public class Shops extends Activity {
	
	private ProgressDialog pDialog;
	 
    // Creating JSON Parser object
    JSONParser jParser = new JSONParser();
 
    ArrayList<HashMap<String, String>> ShopsList;
    ArrayList<Bitmap> Images;
 
    // url to get all products list
    private static String url_all_shops = "http://yoomino.com/AllShops.php";
 
    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    
    
    
    private static final String TAG_SHOPID = "ShopID";
    private static final String TAG_SHOPNAME = "ShopName";
    private static final String TAG_OWNER="OwnerName";
    private static final String TAG_ADDRESS="Address";
    private static final String TAG_MAIL="Email";
    private static final String TAG_PHONENO="PhoneNo";
    private static final String TAG_PRODUCTS = "products";
    JSONArray products = null;
	
	ArrayList <Shop> Shops;

	
	
	   public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.shops);
	        ShopsList = new ArrayList<HashMap<String, String>>();
	        ListView lv = (ListView)findViewById(R.id.listView1);
	        new LoadAllShops(lv,this).execute();
}
	   
	   class LoadAllShops extends AsyncTask<String, String, String> {
			 
	    	ListView lv; 
	    	Context context;
	    	
	    	LoadAllShops(ListView lv,Context c){
	    		this.lv = lv;
	    		this.context=c;
	    	}
	    	
	    	
	    	
	        /**
	         * Before starting background thread Show Progress Dialog
	         * */
	        @Override
	        protected void onPreExecute() {
	            super.onPreExecute();
	            pDialog = new ProgressDialog(Shops.this);
	            pDialog.setMessage("Loading Shops. Please wait...");
	            pDialog.setIndeterminate(false);
	            pDialog.setCancelable(false);
	            pDialog.show();
	        }
	 
	        /**
	         * getting All products from url
	         * */
	        protected String doInBackground(String... args) {
	            // Building Parameters
	            List<NameValuePair> params = new ArrayList<NameValuePair>();
	            // getting JSON string from URL
	            JSONObject json = jParser.makeHttpRequest(url_all_shops, "GET", params);
	 
	            // Check your log cat for JSON reponse
	            Log.d("All Products: ", json.toString());
	 
	            try {
	                // Checking for SUCCESS TAG
	                int success = json.getInt(TAG_SUCCESS);
	 
	                if (success == 1) {
	                    // products found
	                    // Getting Array of Products
	                    products = json.getJSONArray(TAG_PRODUCTS);
	 
	                    // looping through All Products
	                    for (int i = 0; i < products.length(); i++) {
	                        JSONObject c = products.getJSONObject(i);
	 
	                        
	                       
	                        
	                        
	                        // Storing each json item in variable
	                        String ShopID = c.getString(TAG_SHOPID);
	                        String ShopName = c.getString(TAG_SHOPNAME);
	                        String Owner=c.getString(TAG_OWNER);
	                        String Address=c.getString(TAG_ADDRESS);
	                        String Mail=c.getString(TAG_MAIL);
	                        String Phone=c.getString(TAG_PHONENO);
	                     
	                        // creating new HashMap
	                        HashMap<String, String> map = new HashMap<String, String>();
	 
	                        // adding each child node to HashMap key => value
	                        map.put(TAG_SHOPID, ShopID);
	                        map.put(TAG_SHOPNAME, ShopName);
	                        map.put(TAG_OWNER, Owner);
	                        map.put(TAG_ADDRESS, Address);
	                        map.put(TAG_MAIL, Mail);
	                        map.put(TAG_PHONENO, Phone);
	                       
	    					
	    				
	                      //  Images.add(ProductImg);
	                        // adding HashList to ArrayList
	                        ShopsList.add(map);
	                    }
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
	            // dismiss the dialog after getting all products
	            pDialog.dismiss();
	            
	            if (ShopsList.size()==0)
	            {
	            	
	            }
	            
	            else{
	            // updating UI from Background Thread
	            runOnUiThread(new Runnable() {
	                public void run() {
	                    /**
	                     * Updating parsed JSON data into ListView
	                     * */
	                	ArrayList<Shop> ListOfShops=new ArrayList<Shop>();  	
	              	String temp="";
	                	for (int i=0;i<ShopsList.size();i++)
	                	{
	               
	                  		String Owner_=ShopsList.get(i).get(TAG_OWNER);
	                  		int Shopid_=Integer.parseInt((ShopsList.get(i).get(TAG_SHOPID)));
	                  		String Address_=ShopsList.get(i).get(TAG_ADDRESS);
	                  		String Mail_=ShopsList.get(i).get(TAG_MAIL);
	                  		String PhoneNo_= ShopsList.get(i).get(TAG_PHONENO);
	                  		String ShopName_ =	ShopsList.get(i).get(TAG_SHOPNAME);	 
	                  		Shop obj=new Shop(Shopid_,ShopName_,Address_,Mail_,PhoneNo_,Owner_);
	                  		ListOfShops.add(obj);
	                	}
	                	  
	                    ShopsAdapter adp = new ShopsAdapter(context,R.layout.rowshops,ListOfShops);
	                    lv.setAdapter(adp);
	                		                  
	              
	                    
	                }
	            });	
	 
	        }
	 
	    }
		
		
		

		}


	   
}