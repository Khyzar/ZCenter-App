package com.example.zcentre1;

import android.graphics.Bitmap;

public class Product {
	int ProductID=0;
	String Name=null;
	String Details=null;
	int Img;
	Bitmap Image;
	String Brand=null;
	String Category=null;
	int Price=0;
	int ShopID;
	Boolean Visibility=false;
	
	
	
	
	public Bitmap getImage() {
		return Image;
	}

	public void setImage(Bitmap image) {
		Image = image;
	}

	public Boolean GetVisibility()
	{
		return this.Visibility;
	}
	
	public void SetVisibility(Boolean b)
	{
		this.Visibility=b;
	}
	
	public Product(String n,String b,String d,String c,int p,int sid,int pid)
	{
		this.Name=n;
		this.Brand=b;
		this.Price=p;
		this.ShopID=sid;
		this.ProductID=pid;
		this.Details=d;
		this.Category=c;
	}
	
	public int getProductID() {
		return ProductID;
	}
	public void setProductID(int productID) {
		ProductID = productID;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getDetails() {
		return Details;
	}
	public void setDetails(String details) {
		Details = details;
	}
	public int getImg() {
		return Img;
	}
	public void setImg(int img) {
		Img = img;
	}
	public String getBrand() {
		return Brand;
	}
	public void setBrand(String brand) {
		Brand = brand;
	}
	public String getCategory() {
		return Category;
	}
	public void setCategory(String category) {
		Category = category;
	}
	public int getPrice() {
		return Price;
	}
	public void setPrice(int price) {
		Price = price;
	}
	public int getShopID() {
		return ShopID;
	}
	public void setShopID(int shopID) {
		ShopID = shopID;
	}
	
}
