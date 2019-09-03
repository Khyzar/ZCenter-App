package com.example.zcentre1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.zcentre1.Products_S.LoadAllProducts;

import android.R.string;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.util.Base64;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Products_U extends Activity {
	 private ProgressDialog pDialog;
	 SwipeRefreshLayout Swiper;
	    // Creating JSON Parser object
	    JSONParser jParser = new JSONParser();
	 
	    boolean CategoryBool = false;
	    boolean ShopBool = false;
	    boolean SearchBool = false;
	    String SearchString = "";
	    
	 
	    // url to get all products list
	    private static String url_all_products = "http://yoomino.com/ShopKeeperProducts_fmk.php";
	 
	    // JSON Node names
	    private static final String TAG_SUCCESS = "success";
	    
	    
	    private String ShopIDofProducts="JERO";
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
	    
	    //FOR SHOPS
	   
	    // products JSONArray
	    JSONArray products = null;
		
		ArrayList<String> Categories;
		byte [] encodeByte;
		ArrayList<Product> arr;
		@Override
	    public void onCreate(Bundle savedInstanceState) {
			//SWIPER
			setContentView(R.layout.products_s);
          
	        super.onCreate(savedInstanceState);
	        
	       
	       
	        Intent i=this.getIntent();
	       
	        if (i.hasExtra("Search"))
	        {
	        	SearchBool = true;
	        	SearchString = i.getStringExtra("Search");
	        }
	        
	        if (i.hasExtra("Categories"))
	        {
	          Categories=i.getStringArrayListExtra("Categories");
	          CategoryBool = true;
	        }
	        
//	        
        else
	        {
	        	Categories=new ArrayList<String>();
	        }
	        
	        
	        if (i.hasExtra("shopid"))
	        {
	        	ShopIDofProducts = i.getStringExtra("shopid");
	        	ShopBool = true;
	        }

	      
	        
	        // Get listview
	           final ListView lv = (ListView)findViewById(R.id.Productlist_S);
	           
	           Swiper =(SwipeRefreshLayout)findViewById(R.id.JuniSwiper);
	            Swiper.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
					
					@Override
					public void onRefresh() {
						Toast.makeText(Products_U.this,"LORD OMER",Toast.LENGTH_LONG).show();
//						// TODO Auto-generated method stub
//						ListView lv = (ListView)findViewById(R.id.Productlist_S);
//						   new LoadAllProducts(lv,this).execute();
						Swiper.setRefreshing(false);
						  new LoadAllProducts(lv,Products_U.this).execute();
						  
					}
				});
				
	           
	           
	           // Loading products in Background Thread
	      new LoadAllProducts(lv,this).execute();
	    //      new LoadAllShops(this).execute();
	        
		}
		
		
		class LoadAllProducts extends AsyncTask<String, String, String> {
			ArrayList<HashMap<String, String>> productsList;
		    ArrayList<Bitmap> Images;
		    ArrayList<Bitmap> ImagesCategories;
		    
	    	ListView lv; 
	    	Context context;
	    	
	    	LoadAllProducts(ListView lv,Context c){
	    		this.lv = lv;
	    		this.context=c;
	    		  productsList = new ArrayList<HashMap<String, String>>();
	    		  Images = new ArrayList<Bitmap>();
	    	}
	    	
	    	
	    	
	        /**
	         * Before starting background thread Show Progress Dialog
	         * */
	        @Override
	        protected void onPreExecute() {
	            super.onPreExecute();
	            pDialog = new ProgressDialog(Products_U.this);
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
	 
	        /**
	         * After completing background task Dismiss the progress dialog
	         * **/
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
	                	for (int i=0;i<productsList.size();i++)
	                	{
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
	                	ImagesCategories = new ArrayList <Bitmap>();
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
	                      ProductsAdapter_U adapter=new ProductsAdapter_U(context,R.layout.rowproduct_u,UpdatedList, ImagesCategories);
	                      lv.setAdapter(adapter);
	                	}
	                	else
	                	{
	                		  ProductsAdapter_U adapter=new ProductsAdapter_U(context,R.layout.rowproduct_u,ListOfProducts,Images);
	                		  lv.setAdapter(adapter);
	                	}
	
	                	}
	                	else if (ShopBool == true)
	                	{
	                		ArrayList<Product> ShopProductList=new ArrayList<Product>();
	                		ArrayList<Bitmap> ImagesShopProduct = new ArrayList <Bitmap>();
	                		for (int i = 0 ; i< ListOfProducts.size(); i++)
	                		{
	                			if (ListOfProducts.get(i).getShopID() == Integer.parseInt(ShopIDofProducts))
	                			{
	                				ShopProductList.add(ListOfProducts.get(i));
	                				ImagesShopProduct.add(Images.get(i));
	                			}
	                		}
	                	      ProductsAdapter_U adapter=new ProductsAdapter_U(context,R.layout.rowproduct_u,ShopProductList,ImagesShopProduct);
	                		  lv.setAdapter(adapter);
	                	}
	                	else if (SearchBool == true)
	                	{
	                		ArrayList<Product> SearchProductList=new ArrayList<Product>();
	                		ArrayList<Bitmap> ImagesSearchProduct = new ArrayList <Bitmap>();
	                		boolean iterator = false;
	                		for (int i = 0 ; i< ListOfProducts.size(); i++)
	                		{
	                			iterator = false;
	                			if (ListOfProducts.get(i).getName().toLowerCase().contains(SearchString.toLowerCase())&& iterator==false)
	                			{
	                				SearchProductList.add(ListOfProducts.get(i));
	                				ImagesSearchProduct.add(Images.get(i));
	                				iterator = true;
	                			}
	                			if (ListOfProducts.get(i).getBrand().toLowerCase().contains(SearchString.toLowerCase())&& iterator==false)
	                			{
	                				SearchProductList.add(ListOfProducts.get(i));
	                				ImagesSearchProduct.add(Images.get(i));
	                				iterator = true;
	                			}
	                			if (ListOfProducts.get(i).getDetails().toLowerCase().contains(SearchString.toLowerCase())&& iterator==false)
	                			{
	                				SearchProductList.add(ListOfProducts.get(i));
	                				ImagesSearchProduct.add(Images.get(i));
	                				iterator = true;
	                			}
	                			if (ListOfProducts.get(i).getCategory().toLowerCase().contains(SearchString.toLowerCase())&& iterator==false)
	                			{
	                				SearchProductList.add(ListOfProducts.get(i));
	                				ImagesSearchProduct.add(Images.get(i));
	                				iterator = true;
	                			}
	                			
	                		}
	                		 ProductsAdapter_U adapter=new ProductsAdapter_U(context,R.layout.rowproduct_u,SearchProductList,ImagesSearchProduct);
	                		  lv.setAdapter(adapter);
	                	}
	                	else
	                	{
	                		 ProductsAdapter_U adapter=new ProductsAdapter_U(context,R.layout.rowproduct_u,ListOfProducts,Images);
	                		  lv.setAdapter(adapter);
	                		  
	                	}
	                	
	                	
	                
	                }
	            });	
	 
	        }
	 
	    }
		
		
		

		}
		
		

//		@Override
//		public void onRefresh() {
//			// TODO Auto-generated method stub
//			Swiper.setRefreshing(true);
//			ListView lv = (ListView)findViewById(R.id.Productlist_S);
//	           
//	           // Loading products in Background Thread
//	       new LoadAllProducts(lv,this).execute();
//	       Swiper.setRefreshing(false);
//		}
	
}
