package com.example.zcentre1;
import java.util.ArrayList;
import java.util.List;
 
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
 
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;




public class EditProduct extends Activity

{

	EditText txtName;
    EditText txtPrice;
    EditText txtDesc;
    EditText txtBrand;
    Button btnSave;
    Button btnDelete;
 
    String pid;
    String name;
    String description;
    String price;
    String brand;
 
    // Progress Dialog
    private ProgressDialog pDialog;
 
    // JSON parser class
    JSONParser jsonParser = new JSONParser();
 
    // single product url
    private static final String url_product_detials = "http://api.androidhive.info/android_connect/get_product_details.php";
 
    // url to update product
    private static final String url_update_product = "http://yoomino.com/UpdateProduct_fmk.php";
 
    // url to delete product
    private static final String url_delete_product = "http://yoomino.com/DeleteProduct_fmk.php";
 
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_PRODUCT = "product";
    private static final String TAG_PID = "pid";
    private static final String TAG_NAME = "name";
    private static final String TAG_PRICE = "price";
    private static final String TAG_DESCRIPTION = "description";
    private static final String TAG_BRAND = "brand";
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editproduct);
        
        Intent i = getIntent();
        
        // getting product id (pid) from intent
        pid = i.getStringExtra(TAG_PID);
        name= i.getStringExtra(TAG_NAME);
        description= i.getStringExtra(TAG_DESCRIPTION);
        price= i.getStringExtra(TAG_PRICE);
        brand=i.getStringExtra(TAG_BRAND);
        
        txtName = (EditText) findViewById(R.id.inputName);
        txtPrice = (EditText) findViewById(R.id.inputPrice);
        txtDesc = (EditText) findViewById(R.id.inputDesc);
        txtBrand = (EditText) findViewById(R.id.inputBrand);
        
        txtName.setText(name);
        txtPrice.setText(price);
        txtDesc.setText(description);
        txtBrand.setText(brand);
        
        btnDelete = (Button) findViewById(R.id.btnDelete);
        
        btnDelete.setOnClickListener(new View.OnClickListener() {
        	 
            @Override
            public void onClick(View arg0) {
                // deleting product in background thread
                new DeleteProduct().execute();
            }
        });
        
        btnSave = (Button) findViewById(R.id.btnSave);
        
        btnSave.setOnClickListener(new View.OnClickListener() {
        	 
            @Override
            public void onClick(View arg0) {
                // starting background task to update product
                new SaveProductDetails().execute();
            }
        });
    
    }
    
    /*****************************************************************
     * Background Async Task to Delete Product
     * */
    class DeleteProduct extends AsyncTask<String, String, String> {
 
        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(EditProduct.this);
            pDialog.setMessage("Deleting Product...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
 
        /**
         * Deleting product
         * */
        protected String doInBackground(String... args) {
 
            // Check for success tag
            int success;
            try {
                // Building Parameters
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("pid", pid));
 
                // getting product details by making HTTP request
                JSONObject json = jsonParser.makeHttpRequest(
                        url_delete_product, "POST", params);
 
                // check your log for json response
                Log.d("Delete Product", json.toString());
 
                // json success tag
                success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                    // product successfully deleted
                    // notify previous activity by sending code 100
                   // Intent i = getIntent();
                    // send result code 100 to notify about product deletion
                  //  setResult(100, i);
                   // finish();
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
            // dismiss the dialog once product deleted
            pDialog.dismiss();
 
        }
 
    }
    
    
    class SaveProductDetails extends AsyncTask<String, String, String> {
    	 
        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(EditProduct.this);
            pDialog.setMessage("Saving product ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
 
        /**
         * Saving product
         * */
        protected String doInBackground(String... args) {
 
            // getting updated data from EditTexts
            String name = txtName.getText().toString();
            String price = txtPrice.getText().toString();
            String description = txtDesc.getText().toString();
            String brand = txtBrand.getText().toString();
 
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair(TAG_PID, pid));
            params.add(new BasicNameValuePair(TAG_NAME, name));
            params.add(new BasicNameValuePair(TAG_PRICE, price));
            params.add(new BasicNameValuePair(TAG_DESCRIPTION, description));
           params.add(new BasicNameValuePair(TAG_BRAND, brand));
 
            // sending modified data through http request
            // Notice that update product url accepts POST method
            JSONObject json = jsonParser.makeHttpRequest(url_update_product,
                    "POST", params);
 
            Log.d("Create Response", json.toString());
            // check json success tag
            try {
                int success = json.getInt(TAG_SUCCESS);
 
                if (success == 1) {
                    // successfully updated
                    Intent i = getIntent();
                    // send result code 100 to notify about product update
                    setResult(100, i);
                    finish();
                } else {
                    // failed to update product
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
            // dismiss the dialog once product uupdated
            pDialog.dismiss();
        }
    }
 
    
    
    
    
    
}
