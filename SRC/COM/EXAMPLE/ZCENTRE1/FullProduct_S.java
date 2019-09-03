package com.example.zcentre1;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class FullProduct_S extends Activity{
	String title,brand,detail;
	@Override
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fullproduct_s);
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

	}

}
