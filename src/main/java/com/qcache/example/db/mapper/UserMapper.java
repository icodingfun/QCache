package com.qcache.example.db.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.qcache.example.db.model.User;

public interface UserMapper {
	@Select("SELECT * FROM user WHERE id = #{userId}")
	public User getUser(long userId);

	@Insert("insert into user (email, userName) values(#{email}, #{userName})")
	public int addUser(User user);
	
	@Update("update user set email=#{email}, userName=#{userName}")
	public int updateUser(User user);
}
