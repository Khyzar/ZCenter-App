package com.example.zcentre1;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

public class DashBoard_S extends Activity {
	
	ArrayList<String> Categories;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_s);
        
	}
	
	
	public void CategorySelected(View v)
	{
		Categories=new ArrayList<String>();
		CheckBox Mobile=(CheckBox)findViewById(R.id.checkBox1);
		CheckBox Laptops=(CheckBox)findViewById(R.id.checkBox2);
		CheckBox Tablets=(CheckBox)findViewById(R.id.checkBox3);
		CheckBox Desktop=(CheckBox)findViewById(R.id.checkBox4);
		CheckBox TV=(CheckBox)findViewById(R.id.checkBox5);
		CheckBox Consoles=(CheckBox)findViewById(R.id.checkBox6);
		CheckBox PCTools=(CheckBox)findViewById(R.id.checkBox7);
		CheckBox MobileTools=(CheckBox)findViewById(R.id.checkBox8);
		
		if(Mobile.isChecked())
		{
		  Categories.add("Mobile");
		 
		}
		
		if (Laptops.isChecked())
		{
			 Categories.add("Laptop");
			 
		}
		
		if (Tablets.isChecked())
		{
			 Categories.add("Tablets");
			 
		}
		
		if (Desktop.isChecked())
		{
			 Categories.add("Desktop");
			  
		}
		
		if (TV.isChecked())
		{
			Categories.add("TV");
			  
		}
		
		if (Consoles.isChecked())
		{
			
			Categories.add("Consoles");
			 
		}
		
		if (PCTools.isChecked())
		{
			
			Categories.add("PCTools");
			
		}
		
		if (MobileTools.isChecked())
		{
			Categories.add("MobileTools");
		}
		
		Intent i=new Intent(this,Products_S.class);
		i.putStringArrayListExtra("Categories",Categories);
		startActivity(i);
	}

	
}
