package com.example.zcentre1;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ProductsAdapter_S extends ArrayAdapter<Product>
{
	Context c;
    int layoutFile;
    ArrayList<Product> DataList;
    ArrayList<Bitmap> Images;
    public ProductsAdapter_S (Context context, int resource, ArrayList<Product> objects,ArrayList<Bitmap> i)
    {
    	super(context,resource,objects);
    	c=context;
    	layoutFile=resource;
        Images= i;
    	DataList=objects;
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
		      Intent i=new Intent(c,EditProduct.class);
		      i.putExtra("pid", String.valueOf(DataList.get(position).getProductID()));
		      i.putExtra("name",DataList.get(position).getName());
			  i.putExtra("price",String.valueOf(DataList.get(position).getPrice()));
			  i.putExtra("description",DataList.get(position).getDetails());
			  i.putExtra("brand",DataList.get(position).getBrand());
			  //i.putExtra("Image",Images.get(position));
		      c.startActivity(i);
		     
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
				
				Intent i=new Intent(c,FullProduct_S.class);
				i.putExtra("title",DataList.get(position).getName());
				i.putExtra("price",String.valueOf(DataList.get(position).getPrice()));
				i.putExtra("details",DataList.get(position).getDetails());
				i.putExtra("brand",DataList.get(position).getBrand());
				 Bitmap temp = Images.get(position);
			      i.putExtra("BitmapImage", temp);  
				
				c.startActivity(i);	
				
			}
			
		});
		
		
      
      
        return row;
    }

    
    
    
    
    

}
