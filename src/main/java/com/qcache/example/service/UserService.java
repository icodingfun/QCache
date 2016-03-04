package com.qcache.example.service;

import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qcache.QCache;
import com.qcache.example.db.mapper.UserMapper;
import com.qcache.example.db.model.User;

@Service
public class UserService {

	@Autowired
	private QCache cache;
	@Autowired
	private UserMapper userMapper;

	public int addUser(User userToAdd) {
		cache.put(new Integer(userToAdd.getId()), userToAdd);
		return 1;
	}

	public <T> void getUser(Integer userId, Consumer<T> callback) {
		cache.getAsync(userId, (key) -> {
			return (T) userMapper.getUser(userId);
		} , callback);
	}

	public User getUser2(Integer id) {
		return userMapper.getUser(id);
	}
}
