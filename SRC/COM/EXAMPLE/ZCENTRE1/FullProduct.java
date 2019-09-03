package com.example.zcentre1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class FullProduct extends Activity
{
	private ProgressDialog pDialog;
	 
    // Creating JSON Parser object
    JSONParser jParser = new JSONParser();
 
    ArrayList<HashMap<String, String>> ShopsList;
    ArrayList<Bitmap> Images;
 
    // url to get all products list
    private static String url_all_shops = "http://yoomino.com/AllShops.php";
 
    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    
    
    String title,brand,detail;
    private static final String TAG_SHOPID = "ShopID";
    private static final String TAG_SHOPNAME = "ShopName";
    private static final String TAG_OWNER="OwnerName";
    private static final String TAG_ADDRESS="Address";
    private static final String TAG_MAIL="Email";
    private static final String TAG_PHONENO="PhoneNo";
    private static final String TAG_PRODUCTS = "products";
    JSONArray products = null;
    CallbackManager callbackManager;
    ShareDialog shareDialog;
	ArrayList <Shop> Shops;
	String sid="JERO";
	
	public void OpenSMS(View v)
	{
//		if (sid.equals("JERO"))
//		{
//			Toast.makeText(this,"Shop not set", Toast.LENGTH_LONG).show();
//		}
//		else
//		{
//			Toast.makeText(this,"SCENE ON HAI", Toast.LENGTH_LONG).show();
//			for (int i=0 ;i<Shops.size();i++)
//			{
//				if (Shops.get(i).getShopID()==Integer.parseInt(sid))
//				{
//					Intent smsIntent = new Intent(Intent.ACTION_VIEW);
//					smsIntent.setType("vnd.android-dir/mms-sms");
//					smsIntent.putExtra("address",String.valueOf(Shops.get(i).getPhoneNo()));
//					smsIntent.putExtra("sms_body","Product Name: "+ title +"\nProduct Brand: "+ brand+"\n");
//					startActivity(smsIntent);
//				}
//			}
//		}
	}
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fullproduct);
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(this);
        Context c = this.getApplicationContext();
    	
        Button b1 = (Button)findViewById(R.id.button1);
        registerForContextMenu(b1);
        ShopsList = new ArrayList<HashMap<String, String>>();
        Intent i=this.getIntent();
        ImageView img = (ImageView)findViewById(R.id.imageView1);
        Bitmap bitmap = (Bitmap) i.getParcelableExtra("BitmapImage");
        img.setImageBitmap(bitmap);
        
        String price=i.getStringExtra("price");
        title=i.getStringExtra("title");
        detail=i.getStringExtra("details");
        brand=i.getStringExtra("brand");
        TextView title_t=(TextView)findViewById(R.id.textView5);
        TextView price_t=(TextView)findViewById(R.id.textView6);
        TextView brand_t=(TextView)findViewById(R.id.textView7);
        TextView detail_t=(TextView)findViewById(R.id.textView8);
        title_t.setText(title);
        price_t.setText((price));
        detail_t.setText(detail);
        brand_t.setText(brand);
        if (i.hasExtra("ShopID"))
        {
        	sid = i.getStringExtra("ShopID");
        	new LoadAllShops(c).execute();
        
        }
	}
	 public void open(View view){
		 TextView title_t=(TextView)findViewById(R.id.textView5);
	        TextView price_t=(TextView)findViewById(R.id.textView6);
	        TextView brand_t=(TextView)findViewById(R.id.textView7);
	        TextView detail_t=(TextView)findViewById(R.id.textView8);
	        String title=title_t.getText().toString();
	        String price=price_t.getText().toString();
	        String brand=brand_t.getText().toString();
		
	      Intent shareIntent = new Intent();
	      shareIntent.setAction(Intent.ACTION_SEND);
	      shareIntent.setType("text/plain");
	      shareIntent.putExtra(Intent.EXTRA_TEXT, "Title: "+title+" "+"Brand: "+brand+" "+"Price: "+price);
	      startActivity(Intent.createChooser(shareIntent, "Share your thoughts"));

	   }

	 public void share_button(View v)
	    {
	    	//Intent i=this.getIntent();
		 SharedPreferences sett = PreferenceManager.getDefaultSharedPreferences(FullProduct.this);
		    String value = sett.getString("status","0");
	    	  if(value.equals("success"))
	    	  { 
	    		  TextView title_t=(TextView)findViewById(R.id.textView5);
	    	        TextView price_t=(TextView)findViewById(R.id.textView6);
	    	        TextView brand_t=(TextView)findViewById(R.id.textView7);
	    	        TextView detail_t=(TextView)findViewById(R.id.textView8);
	    	        String title=title_t.getText().toString();
	    	        String price=price_t.getText().toString();
	    	        String brand=brand_t.getText().toString();
	    	        String detail=detail_t.getText().toString();
	    		  if (ShareDialog.canShow(ShareLinkContent.class)) {
			    		ShareLinkContent linkContent = new ShareLinkContent.Builder()
	    	            .setContentTitle("Title: "+title)
	    	            .setContentDescription(
	    	                    "Brand: "+brand+" "+"Price: "+price+" "+"Details: "+detail)
	    	            .setContentUrl(Uri.parse("http://developers.facebook.com/android"))
	    	            .build();

			    		shareDialog.show(linkContent);
			    	}
	    	  }
	    	  else
			    	Toast.makeText(FullProduct.this, "Please login to continue!", Toast.LENGTH_LONG).show();
			   
	    }
	    
	  
	    @Override
	    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
	        super.onActivityResult(requestCode, resultCode, data);
	        callbackManager.onActivityResult(requestCode, resultCode, data);
	    }
	    
	
	
	public void show_location(View v){
		Intent i=new Intent(this,PayBill_S.class);
		startActivity(i);
	}
	
	
	 class LoadAllShops extends AsyncTask<String, String, String> {
		 
	    	
	    	Context context;
	    	
	    	LoadAllShops(Context c){
	    		
	    		this.context=c;
	    	}
	    	
	    	
	    	
	        /**
	         * Before starting background thread Show Progress Dialog
	         * */
	        @Override
	        protected void onPreExecute() {
	            super.onPreExecute();
	            pDialog = new ProgressDialog(FullProduct.this);
	            pDialog.setMessage("Loading products. Please wait...");
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
	                	Shops=new ArrayList<Shop>();  	
	                    String SName="",SAddress="";
	                	for (int i=0;i<ShopsList.size();i++)
	                	{
	               
	                  		String Owner_=ShopsList.get(i).get(TAG_OWNER);
	                  		int Shopid_=Integer.parseInt((ShopsList.get(i).get(TAG_SHOPID)));
	                  		String Address_=ShopsList.get(i).get(TAG_ADDRESS);
	                  		String Mail_=ShopsList.get(i).get(TAG_MAIL);
	                  		String PhoneNo_= ShopsList.get(i).get(TAG_PHONENO);
	                  		String ShopName_ =	ShopsList.get(i).get(TAG_SHOPNAME);	 
	                  		Shop obj=new Shop(Shopid_,ShopName_,Address_,Mail_,PhoneNo_,Owner_);
	                  		if (sid.equals(ShopsList.get(i).get(TAG_SHOPID)))
	                  		{
	                  			SName = ShopName_;
	                  			SAddress = Address_;
	                  		}
	                  		Shops.add(obj);
	                	}
	                	  TextView ShopName=(TextView)findViewById(R.id.textView10);
	                	  TextView ShopAddress=(TextView)findViewById(R.id.textView12);
	                      ShopName.setText(SName);
	                      ShopAddress.setText(SAddress);
	                	  
	                   
	                		                  
	              
	                    
	                }
	            });	
	            
	            
	        }
	            
	    }
		
		
		

		}

	 @Override
	 public void onCreateContextMenu(ContextMenu menu, View v,
	   ContextMenuInfo menuInfo) {
	  super.onCreateContextMenu(menu, v, menuInfo);
	  menu.setHeaderTitle("Contact");
	  menu.add(0, v.getId(), 0, "Via SMS");
	  menu.add(0, v.getId(), 0, "Via Call");
	 }
	 @Override
	 public boolean onContextItemSelected(MenuItem item) {
	
	  if (item.getTitle() == "Via SMS")
	  {
			if (sid.equals("JERO"))
			{
				Toast.makeText(this,"Shop not set", Toast.LENGTH_LONG).show();
			}
			else
			{
			
				for (int i=0 ;i<Shops.size();i++)
				{
					if (Shops.get(i).getShopID()==Integer.parseInt(sid))
					{
						Intent smsIntent = new Intent(Intent.ACTION_VIEW);
						smsIntent.setType("vnd.android-dir/mms-sms");
						smsIntent.putExtra("address",String.valueOf(Shops.get(i).getPhoneNo()));
						smsIntent.putExtra("sms_body","Product Name: "+ title +"\nProduct Brand: "+ brand+"\n");
						startActivity(smsIntent);
					}
				}
			}

	  
	 
	  }
	  
	  else if (item.getTitle() == "Via Call") 
	  {
		  if (sid.equals("JERO"))
			{
				Toast.makeText(this,"Shop not set", Toast.LENGTH_LONG).show();
			}
			else
			{
			
				for (int i=0 ;i<Shops.size();i++)
				{
					if (Shops.get(i).getShopID()==Integer.parseInt(sid))
					{

						Toast.makeText(this,String.valueOf(Shops.get(i).getPhoneNo()), Toast.LENGTH_LONG).show();
						  Intent intent = new Intent(Intent.ACTION_CALL);
							intent.setData(Uri.parse("tel:"+String.valueOf(Shops.get(i).getPhoneNo())));
							startActivity(intent);

					}
				}
			}

          
	  } 
	  
	  	 
	  
	  else {
	   return false;
	  }
	  return true;
	 }


	
}
