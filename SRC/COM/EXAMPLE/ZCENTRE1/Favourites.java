package com.example.zcentre1;

import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

public class Favourites extends Activity {
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.fvrt);
	        ArrayList<Product> Products ;
	        //ArrayList <Bitmap> Images;
	        ProductDB db = new ProductDB(this);
	        Products  = db.getAllProducts();
            ArrayList<Bitmap> Images = new ArrayList<Bitmap>();
            for (int i = 0 ; i< Products.size(); i++)
            {
            	String temp = Products.get(i).getName();
            	Toast.makeText(this, temp, Toast.LENGTH_SHORT).show();
            	Images.add(i, (Products.get(i).getImage()));
            	
            }
          
//           ProductsAdapter_U adb = new ProductsAdapter_U(this,R.layout.rowproduct_u,Products,Images); 
         FavouriteAdapter adb = new FavouriteAdapter(this,R.layout.rowproduct_fvrt,Products,Images);
	     ListView lv = (ListView)findViewById(R.id.Productlist_S);
	      lv.setAdapter(adb);
//	        
	 }

}
