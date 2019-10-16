package com.hiddenma.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hiddenma.entity.UserDao;

// 事务注解
@Service
public class UserService {

	@Autowired
	private UserDao userDao;

	@Transactional
	public void add() {
		userDao.add("mark", 10);
		// int i = 1 / 0;
		System.out.println("user 实现 add 方法##########");
		userDao.add("John", 40);
	}

}
