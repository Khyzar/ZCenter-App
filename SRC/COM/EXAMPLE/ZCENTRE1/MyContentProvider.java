package com.example.zcentre1;

import java.util.ArrayList;
import java.util.HashMap;


import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;



public class MyContentProvider extends ContentProvider {

	
	static final String PROVIDER_NAME = "com.example.zcentre1.MyContentProvider";
	 static final String URL = "content://" + PROVIDER_NAME + "/friends";
	 static final Uri CONTENT_URI = Uri.parse(URL);
	   
	 
	 
	 // integer values used in content URI
	 static final int FRIENDS = 1;
	 static final int FRIENDS_ID = 2;
	 
	 ProductDB dbHelper;
	   
	 // projection map for a query
	 private static HashMap<String, String> BirthMap;
	 
	 // maps content URI "patterns" to the integer values that were set above
	 static final UriMatcher uriMatcher;
	   static{
	      uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
	      uriMatcher.addURI(PROVIDER_NAME, "friends/#", 1);
	      uriMatcher.addURI(PROVIDER_NAME, "friends/#", FRIENDS_ID);
	   }
	   
	
	@Override
	public boolean onCreate() {
		// TODO Auto-generated method stub
		
		Context context = getContext();
		dbHelper = new ProductDB(context);

		return false;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		// TODO Auto-generated method stub
		switch (uriMatcher.match(uri)) {
	      // maps all database column names
	      case FRIENDS:
	    	  return dbHelper.getAllProductsforProvider();
	    	  
	    	  //queryBuilder.setProjectionMap(BirthMap);
	        // break;
	      case FRIENDS_ID:
	    	  return dbHelper.getAllProductsforProvider();
	        // break;
	      default:
	         throw new IllegalArgumentException("Unknown URI " + uri);
	      }

	}

	@Override
	public String getType(Uri uri) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		// TODO Auto-generated method stub
		return 0;
	}

	
	
}
