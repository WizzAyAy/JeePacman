package com.bean;

import java.util.ArrayList;

public class User {
	
	private String username;
	private String email;
	private String password;
	private ArrayList<Cosmetic> comestics;
	
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public ArrayList<Cosmetic> getComestics() {
		return comestics;
	}
	public void setComestics(ArrayList<Cosmetic> comestics) {
		this.comestics = comestics;
	}
}