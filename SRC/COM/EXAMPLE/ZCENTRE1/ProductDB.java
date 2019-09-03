package com.example.zcentre1;

import java.util.ArrayList;
import java.util.List;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.BitmapFactory;

public class ProductDB extends SQLiteOpenHelper{

	private static final int DATABASE_VERSION = 1;
	 
    // Database Name
    private static final String DATABASE_NAME = "ProductDB";
 
    // Contacts table name
    private static final String TABLE_PRODUCTS = "Products";
 
    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_DETAILS = "details";
    private static final String KEY_IMG = "img";
    private static final String KEY_BRAND = "brand";
    private static final String KEY_CATEGORY = "category";
    private static final String KEY_PRICE = "price";
    private static final String KEY_SHOPID = "shopid";
    
    public ProductDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
 
    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_PRODUCTS_TABLE = "CREATE TABLE " + TABLE_PRODUCTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_DETAILS + " TEXT," + KEY_IMG + " BLOB,"
                + KEY_BRAND + " TEXT," + KEY_CATEGORY + " TEXT,"
                + KEY_PRICE + " INTEGER,"
                + KEY_SHOPID + " INTEGER" + ")";
        db.execSQL(CREATE_PRODUCTS_TABLE);
    }
 
    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
 
        // Create tables again
        onCreate(db);
    }
 
    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */
 
    // Adding new contact
    void addNews(Product product,byte [] image) {
        SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        values.put(KEY_ID, product.getProductID()); // Contact Name
        values.put(KEY_NAME, product.getName()); // Contact Phone
        values.put(KEY_DETAILS, product.getDetails()); // Contact Name
        values.put(KEY_IMG,  image);
        values.put(KEY_BRAND,  product.getBrand());
        values.put(KEY_CATEGORY,  product.getCategory());
        values.put(KEY_PRICE,  product.getPrice());
        values.put(KEY_SHOPID,  product.getShopID());
     
        // Inserting Row
        db.insert(TABLE_PRODUCTS, null, values);
        db.close(); // Closing database connection
    }
 
 
    // Getting All Contacts
    public ArrayList<Product> getAllProducts() {
    	ArrayList<Product> productList = new ArrayList<Product>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_PRODUCTS;
 
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
            	Product product = new Product(cursor.getString(1),cursor.getString(4),cursor.getString(2),cursor.getString(5),Integer.parseInt(cursor.getString(6)),Integer.parseInt(cursor.getString(7)),Integer.parseInt(cursor.getString(0)));
            	
            	byte[] image = cursor.getBlob(3);
            	
            	product.setImage(BitmapFactory.decodeByteArray(image, 0, image.length));
            	
            	
//            	product.SetID(Integer.parseInt(cursor.getString(0)));
//            	product.SetName(cursor.getString(1));
//            	product.SetDetails(cursor.getString(2));
//            	product.SetBrand(cursor.getString(4));
//            	product.SetCategory(cursor.getString(5));
//            	product.SetPrice(Integer.parseInt(cursor.getString(6)));
//            	product.SetShopID(Integer.parseInt(cursor.getString(7)));
                // Adding contact to list
            	productList.add(product);
            } while (cursor.moveToNext());
        }
 
        // return contact list
        return productList;
    }
 
    // Updating single contact
    
 
    // Deleting single contact
 
    public void deleteProduct(Product product) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PRODUCTS, KEY_ID + " = ?",
                new String[] { String.valueOf(product.getProductID()) });
        db.close();
    }

    public Cursor getAllProductsforProvider()
    {
    	String selectQuery = "SELECT  * FROM " + TABLE_PRODUCTS;
    	 
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        return cursor;
    }

	
	
}
