package com.example.zcentre1;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ProductsAdapter_U extends ArrayAdapter<Product>{
	public ProductsAdapter_U(Context context, int resource,
			int textViewResourceId, List<Product> objects) {
		super(context, resource, textViewResourceId, objects);
		// TODO Auto-generated constructor stub
	}

	Context c;
    int layoutFile;
    ArrayList<Product> DataList;
    ArrayList<Bitmap> Images;
    ArrayList<Shop> Shops;
    
    public ProductsAdapter_U (Context context, int resource, ArrayList<Product> objects,ArrayList<Bitmap> i)
    {
    	super(context,resource,objects);
    	c=context;
    	layoutFile=resource;
        Images= i;
    	DataList=objects;
    }
    
    
    public ProductsAdapter_U (Context context, int resource, ArrayList<Product> objects,ArrayList<Bitmap> i,ArrayList<Shop> s)
    {
    	super(context,resource,objects);
    	c=context;
    	layoutFile=resource;
        Images= i;
    	DataList=objects;
    	Shops = s;
    }
    
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v;
		final View row;
        if(convertView == null)
        {
        	LayoutInflater inflater = LayoutInflater.from(c);
            row = inflater.inflate(layoutFile, parent, false);
        }
        else
        {
            row = (View) convertView;
            ImageButton delete=(ImageButton)row.findViewById(R.id.imageButton1);
            if (DataList.get(position).GetVisibility()==true)
            {
            	delete.setVisibility(View.VISIBLE);
            }
            else
            {
            	delete.setVisibility(View.GONE);
            }
        }
        
        TextView title = (TextView)row.findViewById(R.id.Title);
      TextView brand = (TextView)row.findViewById(R.id.Brand);
      TextView price = (TextView)row.findViewById(R.id.Price);
      
    title.setText(DataList.get(position).getName());
    brand.setText(DataList.get(position).getBrand());
    
  price.setText(String.valueOf(DataList.get(position).getPrice()));
      
     ImageButton delete=(ImageButton)row.findViewById(R.id.imageButton1);
       delete.setVisibility(View.GONE);
       
     ImageView img = (ImageView)row.findViewById(R.id.imageView1);
       img.setImageBitmap(Images.get(position));
//       
       
       //EDIT MODE
       delete.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v) 
			{
//		     DataBaseHandlerProducts db = new DataBaseHandlerProducts(c);
//		     ByteArrayOutputStream bao = new ByteArrayOutputStream();
//             //bm.compress(Bitmap.CompressFormat.JPEG, 90, bao);
//             Images.get(position).compress(Bitmap.CompressFormat.JPEG, 90, bao);
//              byte[] ba = bao.toByteArray(); 
//		     db.addNews(DataList.get(position),ba);
				 ByteArrayOutputStream stream = new ByteArrayOutputStream();
			        Images.get(position).compress(CompressFormat.PNG, 0, stream);
			        ProductDB db = new ProductDB(c);
			        db.addNews(DataList.get(position),stream.toByteArray());
		     Toast.makeText(c, "Product Added to Favourites.",Toast.LENGTH_SHORT).show();
			}
			
		
		});

      row.setOnLongClickListener(new View.OnLongClickListener()
		{

			@Override
			public boolean onLongClick(View v) {
			
				ImageButton delete=(ImageButton)row.findViewById(R.id.imageButton1);
				delete.setVisibility(View.VISIBLE);
				delete.setFocusable(false);
				
			for (int i=0;i<DataList.size();i++)
			{
				DataList.get(position).SetVisibility(false);
				
			}
				// TODO Auto-generated method stub
				return true;
			}
			
		});
        
      row.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Intent i=new Intent(c,FullProduct.class);
				i.putExtra("title",DataList.get(position).getName());
				i.putExtra("price",String.valueOf(DataList.get(position).getPrice()));
				i.putExtra("details",DataList.get(position).getDetails());
				i.putExtra("brand",DataList.get(position).getBrand());
				 Bitmap temp = Images.get(position);
			      i.putExtra("BitmapImage", temp);  
				i.putExtra("ShopID", String.valueOf(DataList.get(position).getShopID()));
				c.startActivity(i);	
				
			}
			
		});
		
		
      
      
        return row;
    }

}
