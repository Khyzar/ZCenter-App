package com.example.zcentre1;

import android.app.Activity;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
 
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import android.net.Uri;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
 
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;


public class AddProduct extends Activity 

{

	private ProgressDialog pDialog;
	 
	//IMAGE VARIABLES
	 Button btpic, btnup;
	    private Uri fileUri;
	    String picturePath;
	    Uri selectedImage;
	    Bitmap photo;
	    String ba1;
	
    JSONParser jsonParser = new JSONParser();
    EditText inputName;
    EditText inputPrice;
    EditText inputDesc;
    EditText inputBrand;
    String current_category;
 
    // url to create new product
    private static String url_create_product = "http://yoomino.com/AddProduct_fmk.php";
 
    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    
    
    //SPINNER
    Spinner spinnerOsversions;
    
    private String[] state = { "Mobile", "Laptop", "Tablets", "Desktop",
      "TV", "Consoles", "PCTools", "MobileTools" };

    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addproduct);
        
        //Image Upload Button
        Button imgBtn= (Button)findViewById(R.id.ImageUpload);
        imgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TakeImage();
            }
        });
        
        //Spinner
        spinnerOsversions = (Spinner) findViewById(R.id.osversions);
        ArrayAdapter<String> adapter_state = new ArrayAdapter<String>(this,
          android.R.layout.simple_spinner_item, state);
        adapter_state
          .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerOsversions.setAdapter(adapter_state);
      //  spinnerOsversions.setOnItemSelectedListener((OnItemSelectedListener) this);

        
        
 
        // Edit Text
        inputName = (EditText) findViewById(R.id.inputName);
        inputPrice = (EditText) findViewById(R.id.inputPrice);
        inputDesc = (EditText) findViewById(R.id.inputDesc);
        inputBrand=(EditText)findViewById(R.id.inputBrand);
        current_category=(String)spinnerOsversions.getSelectedItem();
        // Create button
        Button btnCreateProduct = (Button) findViewById(R.id.btnCreateProduct);
 
      //  Toast.makeText(this,(String)spinnerOsversions.getSelectedItem(), Toast.LENGTH_LONG).show();
        
        // button click event
        btnCreateProduct.setOnClickListener(new View.OnClickListener() {
 
            @Override
            public void onClick(View view) {
              //   creating new product in background thread
            	 current_category=(String)spinnerOsversions.getSelectedItem();
            	 Log.e("path", "----------------" + picturePath);
            	 
                 // Image
                 //Bitmap bm = BitmapFactory.decodeFile(picturePath);
                 ByteArrayOutputStream bao = new ByteArrayOutputStream();
                 //bm.compress(Bitmap.CompressFormat.JPEG, 90, bao);
                 photo.compress(Bitmap.CompressFormat.JPEG, 90, bao);
                  byte[] ba = bao.toByteArray();
                 ba1 = Base64.encodeToString(ba, 0);
          
                 Log.e("base64", "-----" + ba1);
          
            	
            	
            	
                new CreateNewProduct().execute();
            
            	
            }
        });
    }
    
    class CreateNewProduct extends AsyncTask<String, String, String> {
    	 
        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(AddProduct.this);
            pDialog.setMessage("Creating Product..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
 
        /**
         * Creating product
         * */
        protected String doInBackground(String... args) {
            String name = inputName.getText().toString();
            String price = inputPrice.getText().toString();
            String description = inputDesc.getText().toString();
            String brand= inputBrand.getText().toString();
            
            //SHOPID
           SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(AddProduct.this);
    	   String sid = settings.getString("ID","0");
         //   String sid = "1";
            
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("name", name));
            params.add(new BasicNameValuePair("price", price));
            params.add(new BasicNameValuePair("description", description));
            params.add(new BasicNameValuePair("brand", brand));
            params.add(new BasicNameValuePair("category", current_category));
            params.add(new BasicNameValuePair("shopid", sid));
            params.add(new BasicNameValuePair("image", ba1));
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
                    Intent i = new Intent(getApplicationContext(), Products_S.class);
                    startActivity(i);
 
                    // closing this screen
                    finish();
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
    
    void TakeImage()
    {
    	if (getApplicationContext().getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA)) {
            // Open default camera
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
 
            // start the image capture Intent
            startActivityForResult(intent, 100);
 
        } else {
            Toast.makeText(getApplication(), "Camera not supported", Toast.LENGTH_LONG).show();
        }
    	
    }
    
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100 && resultCode == RESULT_OK) {
 
            selectedImage = data.getData();
            photo = (Bitmap) data.getExtras().get("data");
 
            // Cursor to get image uri to display
 
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(selectedImage,filePathColumn, null, null, null);
            cursor.moveToFirst();
 
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            picturePath = cursor.getString(columnIndex);
            cursor.close();
 
            photo = (Bitmap) data.getExtras().get("data");
            ImageView imageView = (ImageView) findViewById(R.id.imageView1);
            imageView.setImageBitmap(photo);
        }
    }
    
    
}
