package com.example.zcentre1;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
 
import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class Products_S extends Activity {
	 // Progress Dialog
    private ProgressDialog pDialog;
 
    // Creating JSON Parser object
    JSONParser jParser = new JSONParser();
 
    ArrayList<HashMap<String, String>> productsList;
    ArrayList<Bitmap> Images;
 
    // url to get all products list
    private static String url_all_products = "http://yoomino.com/ShopKeeperProducts_fmk.php";
 
    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    
    
    
    private static final String TAG_PRODUCTS = "products";
    private static final String TAG_PID = "ProductID";
    private static final String TAG_SHOP="ShopID";
    private static final String TAG_BRAND="Brand";
    private static final String TAG_CATEGORY="Category";
    private static final String TAG_IMAGE="Image";
    private static final String TAG_NAME = "Name";
    private static final String TAG_DETAILS="Details";
    private static final String TAG_PRICE="Price";
    private static final String TAG_IMG = "Img";
  
    // products JSONArray
    JSONArray products = null;
	
	ArrayList<String> Categories;
	  boolean CategoryBool = false;
	  
	byte [] encodeByte;
	ArrayList<Product> arr;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.products_s);
        
        Images = new ArrayList<Bitmap>();
        Intent i=this.getIntent();
        
        if (i.hasExtra("Categories"))
        {
        Categories=i.getStringArrayListExtra("Categories");
        CategoryBool = true;
        }
        else
        {
        	Categories=new ArrayList<String>();
        	CategoryBool = false;
        }
        productsList = new ArrayList<HashMap<String, String>>();
        
        // Get listview
           ListView lv = (ListView)findViewById(R.id.Productlist_S);
           
           // Loading products in Background Thread
           new LoadAllProducts(lv,this).execute();
        
	}
	
	
	class LoadAllProducts extends AsyncTask<String, String, String> {
		 
    	ListView lv; 
    	Context context;
    	
    	LoadAllProducts(ListView lv,Context c){
    		this.lv = lv;
    		this.context=c;
    	}

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Products_S.this);
            pDialog.setMessage("Loading products. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }
 
       
        protected String doInBackground(String... args) {
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            // getting JSON string from URL
            JSONObject json = jParser.makeHttpRequest(url_all_products, "GET", params);
 
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
                        String id = c.getString(TAG_PID);
                        String name = c.getString(TAG_NAME);
                        String brand=c.getString(TAG_BRAND);
                        String price_=c.getString(TAG_PRICE);
                        String details=c.getString(TAG_DETAILS);
                        String img=c.getString(TAG_IMAGE);
                        String category=c.getString(TAG_CATEGORY);
                        String shop=c.getString(TAG_SHOP);
                        String Imageid = c.getString(TAG_IMG);
                        
                        // creating new HashMap
                        HashMap<String, String> map = new HashMap<String, String>();
 
                        // adding each child node to HashMap key => value
                        map.put(TAG_PID, id);
                        map.put(TAG_NAME, name);
                        map.put(TAG_BRAND, brand);
                        map.put(TAG_PRICE, price_);
                        map.put(TAG_DETAILS, details);
                        map.put(TAG_IMAGE, img);
                        map.put(TAG_CATEGORY, category);
                        map.put(TAG_SHOP, shop);
                        map.put(TAG_IMG, Imageid);
                        
                      
    					
    				
                      //  Images.add(ProductImg);
                        // adding HashList to ArrayList
                        productsList.add(map);
                    }
                } 
            } catch (JSONException e) {
                e.printStackTrace();
            }
 
            return null;
        }
 
             
        @Override
        protected void onPostExecute(String file_url) {
 		 
          // dismiss the dialog after getting all products
          pDialog.dismiss();
          {
          // updating UI from Background Thread
          runOnUiThread(new Runnable() {
              public void run() {
                  /**
                   * Updating parsed JSON data into ListView
                   * */
              	ArrayList<Product> ListOfProducts=new ArrayList<Product>();  
            	SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(Products_S.this);
    		    String ID = settings.getString("ID","0");
              	for (int i=0;i<productsList.size();i++)
              	{
              		if (productsList.get(i).get(TAG_SHOP).equals(ID))
              		{
              		{
              		String name_=productsList.get(i).get(TAG_NAME);
                		String brand_=productsList.get(i).get(TAG_BRAND);
                		int price_=Integer.parseInt((productsList.get(i).get(TAG_PRICE)));
                	    int productid_=Integer.parseInt(productsList.get(i).get(TAG_PID));
                	    int shopid_=Integer.parseInt(productsList.get(i).get(TAG_SHOP));
                		String category_=productsList.get(i).get(TAG_CATEGORY);
                		String details_=productsList.get(i).get(TAG_DETAILS);
                	    encodeByte=Base64.decode(productsList.get(i).get(TAG_IMG),Base64.DEFAULT);
                      Images.add(BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length));
                      Product obj=new Product(name_,brand_,details_,category_,price_,shopid_,productid_);
                		ListOfProducts.add(obj);
              		}
              		}
                  }
              
              	
              	if (CategoryBool == true)
              	{
              	ArrayList<Product> UpdatedList=new ArrayList<Product>();
              	ArrayList<Bitmap>ImagesCategories = new ArrayList <Bitmap>();
              	for (int i=0;i<Categories.size();i++)
              	{
              		
              		for (int j=0;j<ListOfProducts.size();j++)
              		{
              		if (ListOfProducts.get(j).getCategory().equals(Categories.get(i)))
              		{
              		    ImagesCategories.add((Images.get(j)));
              			UpdatedList.add(ListOfProducts.get(j));
              		}
              		}
              	}
              	
              	if (UpdatedList.size()>0)
              	{
              		
                    ProductsAdapter_S adapter=new ProductsAdapter_S(Products_S.this,R.layout.rowproduct_s,UpdatedList, ImagesCategories);
                    lv.setAdapter(adapter);
              	}
              	else
              	{

              		
              		  ProductsAdapter_S adapter=new ProductsAdapter_S(Products_S.this,R.layout.rowproduct_s,ListOfProducts,Images);
              		  lv.setAdapter(adapter);
              	}

              	}
              	else
              	{

              		
              		  ProductsAdapter_S adapter=new ProductsAdapter_S(Products_S.this,R.layout.rowproduct_s,ListOfProducts,Images);
              		  lv.setAdapter(adapter);
              	}
              	             	
              
              }
          });	

      }

  }

 
        }
	
	
	
	

}
	
	
	/**
//   * Before starting background thread Show Progress Dialog
//   * */
//  @Override
//  protected void onPreExecute() {
//      super.onPreExecute();
//      pDialog = new ProgressDialog(Products_S.this);
//      pDialog.setMessage("Loading products. Please wait...");
//      pDialog.setIndeterminate(false);
//      pDialog.setCancelable(false);
//      pDialog.show();
//  }
//
//  /**
//   * getting All products from url
//   * */
//  protected String doInBackground(String... args) {
//      // Building Parameters
//      List<NameValuePair> params = new ArrayList<NameValuePair>();
//      // getting JSON string from URL
//      JSONObject json = jParser.makeHttpRequest(url_all_products, "GET", params);
//
//      // Check your log cat for JSON reponse
//      Log.d("All Products: ", json.toString());
//
//      try {
//          // Checking for SUCCESS TAG
//          int success = json.getInt(TAG_SUCCESS);
//
//          if (success == 1) {
//              // products found
//              // Getting Array of Products
//              products = json.getJSONArray(TAG_PRODUCTS);
//
//              // looping through All Products
//              for (int i = 0; i < products.length(); i++) {
//                  JSONObject c = products.getJSONObject(i);
//
//                  
//                 
//                  
//                  
//                  // Storing each json item in variable
//                  String id = c.getString(TAG_PID);
//                  String name = c.getString(TAG_NAME);
//                  String brand=c.getString(TAG_BRAND);
//                  String price_=c.getString(TAG_PRICE);
//                  String details=c.getString(TAG_DETAILS);
//                  String img=c.getString(TAG_IMAGE);
//                  String category=c.getString(TAG_CATEGORY);
//                  String shop=c.getString(TAG_SHOP);
//                  String Imageid = c.getString(TAG_IMG);
//                  
//                  // creating new HashMap
//                  HashMap<String, String> map = new HashMap<String, String>();
//
//                  // adding each child node to HashMap key => value
//                  map.put(TAG_PID, id);
//                  map.put(TAG_NAME, name);
//                  map.put(TAG_BRAND, brand);
//                  map.put(TAG_PRICE, price_);
//                  map.put(TAG_DETAILS, details);
//                  map.put(TAG_IMAGE, img);
//                  map.put(TAG_CATEGORY, category);
//                  map.put(TAG_SHOP, shop);
//                  map.put(TAG_IMG, Imageid);
//                  
//                  encodeByte=Base64.decode(Imageid,Base64.DEFAULT);
//                  Images.add(BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length));
//                  
//					
//				
//                //  Images.add(ProductImg);
//                  // adding HashList to ArrayList
//                  productsList.add(map);
//              }
//          } 
//      } catch (JSONException e) {
//          e.printStackTrace();
//      }
//
//      return null;
//  }
//
//  /**
//   * After completing background task Dismiss the progress dialog
//   * **/
//  protected void onPostExecute(String file_url) {
//      // dismiss the dialog after getting all products
//      pDialog.dismiss();
//      
//      if (productsList.size()==0)
//      {
//      	
//      }
//      
//      else{
//      // updating UI from Background Thread
//      runOnUiThread(new Runnable() {
//          public void run() {
//              /**
//               * Updating parsed JSON data into ListView
//               * */
//          	ArrayList<Product> ListOfProducts=new ArrayList<Product>();  	
//        	String temp="";
//        	SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(Products_S.this);
//  	    String sid = settings.getString("ID","0");
//          	for (int i=0;i<productsList.size();i++)
//          	{
//          		
//          		if (sid.equals(productsList.get(i).get(TAG_SHOP)))
//          		{
//          		String name_=productsList.get(i).get(TAG_NAME);
//            		String brand_=productsList.get(i).get(TAG_BRAND);
//            		int price_=Integer.parseInt((productsList.get(i).get(TAG_PRICE)));
//            	    int productid_=Integer.parseInt(productsList.get(i).get(TAG_PID));
//            	    int shopid_=Integer.parseInt(productsList.get(i).get(TAG_SHOP));
//            		String category_=productsList.get(i).get(TAG_CATEGORY);
//            		String details_=productsList.get(i).get(TAG_DETAILS);
//            		
//            		Product obj=new Product(name_,brand_,details_,category_,price_,shopid_,productid_);
//            		ListOfProducts.add(obj);
//          		}
//          	}
//          	  TextView t=(TextView)findViewById(R.id.ProductHeading);
//              
//          	  ArrayList<Product> UpdatedList=new ArrayList<Product>();
//          	for (int i=0;i<Categories.size();i++)
//          	{
//          		
//          		for (int j=0;j<ListOfProducts.size();j++)
//          		{
//          			t.setText(ListOfProducts.get(j).getCategory());
//          		if (ListOfProducts.get(j).getCategory().equals(Categories.get(i)))
//          		{
//          			
//          			UpdatedList.add(ListOfProducts.get(j));
//          		}
//          		}
//          	}
//          	
//          	if (UpdatedList.size()>0)
//          	{
//                ProductsAdapter_S adapter=new ProductsAdapter_S(context,R.layout.rowproduct_s,UpdatedList, Images);
//                lv.setAdapter(adapter);
//          	}
//          	else
//          	{
//          		  ProductsAdapter_S adapter=new ProductsAdapter_S(context,R.layout.rowproduct_s,ListOfProducts,Images);
//          		  lv.setAdapter(adapter);
//          	}
//
//              // updating listview
//            
//        
//              
//          }
//      });	
//
//  }
//
//}
//
//
//
//
//}

