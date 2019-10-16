package com.hiddenma.entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public class UserDaoImpl implements UserDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public void add(String name, int age) {
		String sql = "insert into testU(name, age) value(?, ?)";
		int updateResult = jdbcTemplate.update(sql);
		System.out.println("updateResult: " + updateResult);
	}
}
