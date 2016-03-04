package com.qcache.example.db.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qcache.example.App;
import com.qcache.example.db.mapper.UserMapper;

/**
 * @author guofeng.qin
 */
@Component
public class DBHelper {

	@Autowired
	private UserMapper userMapper;

	public void save(User u) {
		if (u == null) {
			return;
		}
		User ou = userMapper.getUser(u.getId());
		if (ou == null) {
			userMapper.addUser(u);
		} else {
			userMapper.updateUser(u);
		}
	}

	public static DBHelper getInstance() {
		return App.getCtx().getBean(DBHelper.class);
	}
}
