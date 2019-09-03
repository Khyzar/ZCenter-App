package com.example.zcentre1;

public class Shop {

	int ShopID;
	String ShopName;
	String OwnerName;
	String Address;
	String Email;
	String PhoneNo;
	
	Shop(int sid, String sn, String a , String e , String p, String o)
	{
		ShopID = sid;
		ShopName = sn;
		OwnerName = o;
		Address = a;
		Email = e;
		PhoneNo = p;
	}
	
	public int getShopID() {
		return ShopID;
	}
	public void setShopID(int shopID) {
		ShopID = shopID;
	}
	public String getShopName() {
		return ShopName;
	}
	public void setShopName(String shopName) {
		ShopName = shopName;
	}
	public String getOwnerName() {
		return OwnerName;
	}
	public void setOwnerName(String ownerName) {
		OwnerName = ownerName;
	}
	public String getAddress() {
		return Address;
	}
	public void setAddress(String address) {
		Address = address;
	}
	public String getEmail() {
		return Email;
	}
	public void setEmail(String email) {
		Email = email;
	}
	public String getPhoneNo() {
		return PhoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		PhoneNo = phoneNo;
	}
	
	
	
}
