package com.stone.study.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stone.study.domain.User;
import com.stone.study.persistence.UserMapper;

@Service
public class UserService {
	@Autowired
	private UserMapper userMapper;

	/**
	 * 用户登录
	 * 
	 * @param uname
	 * @param upwd
	 * @return
	 */
	public User login(String uname, String upwd) {
		User user = null;
		try {
			user = userMapper.findUserByNameAndPwd(uname, upwd.toLowerCase());
			if (null == user) {
				return user;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return user;
		}
		return user;
	}

	/**
	 * 用户是否注册
	 * 
	 * @param id
	 * @param openid
	 * @return
	 */
	public User isRegister(long id, String openid) throws Exception {
		return userMapper.findByIdOrOpenid(id, openid);
	}

	public User findUserByUsername(String username) {
		return userMapper.findUserByUsername(username);
	}

	/**
	 * 新增用户
	 * 
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public boolean addUser(User user) throws Exception {
		return userMapper.insertUser(user);
	}

	/**
	 * 更新用户
	 * 
	 * @param user
	 * @return
	 */
	public boolean updateUser(User user) {
		return userMapper.updateUser(user);
	}
}
