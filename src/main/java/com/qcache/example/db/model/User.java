package com.qcache.example.db.model;

import org.springframework.beans.factory.annotation.Autowired;

import com.qcache.entity.Entity;
import com.qcache.example.db.mapper.UserMapper;

public class User extends Entity {

	@Autowired
	private UserMapper userMapper;

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
		User u = userMapper.getUser(id);
		if (u == null) {
			userMapper.addUser(this);
		} else {
			userMapper.updateUser(this);
		}
	}

}
