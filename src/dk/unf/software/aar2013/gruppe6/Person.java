package dk.unf.software.aar2013.gruppe6;

import android.net.Uri;

public class Person {
	
	private int id;
	String personName;
	double balance;
	Uri profilePic;
	
	public Person(String personName, double balance, Uri profilePic){
		this.personName = personName;
		this.balance = balance;
		this.profilePic = profilePic;
	}
	
	public Person() {
		// TODO Auto-generated constructor stub
	}

	public String getPersonName(){
		return personName;
	}
	
	public void setPersonName(String name){
		personName = name;
	}
	
	public void setBalance(double balance){
		this.balance = balance;
	}
	
	public double getBalance(){
		return balance;
	}
	
	public void setProfilePic(Uri profilePic){
		this.profilePic = profilePic;
	}
	
	public Uri getProfilePic(){
		return profilePic;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
}
