package com.example.zcentre1;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ShopsAdapter  extends ArrayAdapter<Shop>{

	Context c;
    int layoutFile;
    ArrayList<Shop> DataList;
    
    public ShopsAdapter(Context context, int resource, ArrayList<Shop> objects)
    {
    	super(context,resource,objects);
    	c = context;
    	layoutFile = resource;
    	DataList = objects;
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
        }
        
        TextView shopname = (TextView)row.findViewById(R.id.ShopName);
        TextView owner = (TextView)row.findViewById(R.id.OwnerName);
        TextView address = (TextView)row.findViewById(R.id.Address);
        
        shopname.setText(DataList.get(position).getShopName());
        owner.setText(DataList.get(position).getOwnerName());
        address.setText(DataList.get(position).getAddress());
        
        
        row.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v) 
			{
		      Intent i=new Intent(c,Products_U.class);
		      String sid = String.valueOf(DataList.get(position).getShopID());
		      i.putExtra("shopid", sid);
		
		      c.startActivity(i);
		     
				
			}
			
		
		});
        
     return row;   
    }

}
	

