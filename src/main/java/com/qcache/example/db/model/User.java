package com.qcache.example.db.model;

import com.qcache.entity.Entity;
import com.qcache.example.db.DBHelper;

public class User extends Entity {
	private int id;
	private String email;
	private String userName;

	public User() {
	}

	public User(int id, String email, String userName) {
		this.id = id;
		this.email = email;
		this.userName = userName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Override
	public void saveToDB() {
		DBHelper.getInstance().save(this);
	}

}
