package com.hiddenma.TestMybatis;

import com.hiddenma.mybatisFramework.entity.User;
import com.hiddenma.mybatisFramework.mapper.UserMapper;
import com.hiddenma.mybatisFramework.sql.SqlSession;

import junit.framework.TestCase;

public class TestMybatis01 extends TestCase {

	public static void main(String[] args) {
		UserMapper userMapper = SqlSession.getMapper(UserMapper.class);
		int inserts = userMapper.insertUser("hhh2", 4);
		if (inserts == -1) {
			System.out.println("insert error!");
		}
		User user = userMapper.selectUser(null, 3);
		if (user != null) {
			System.out.println("name:" + user.getName() + "   age:" + user.getAge());
		}
	}

}
