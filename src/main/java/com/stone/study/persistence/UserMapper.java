package com.stone.study.persistence;

import org.apache.ibatis.annotations.Param;

import com.stone.study.domain.User;

public interface UserMapper {
	User findUserByNameAndPwd(@Param("uname") String uname,
			@Param("upwd") String upwd);

	User findByIdOrOpenid(@Param("id") long id, @Param("openid") String openid);

	User findUserByUsername(@Param("username") String username);

	boolean insertUser(User user);

	boolean updateUser(User user);
}
