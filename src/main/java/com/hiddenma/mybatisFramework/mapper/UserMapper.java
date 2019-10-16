package com.hiddenma.mybatisFramework.mapper;

import com.hiddenma.mybatisFramework.annotation.SelfInsert;
import com.hiddenma.mybatisFramework.annotation.SelfParam;
import com.hiddenma.mybatisFramework.annotation.SelfSelect;
import com.hiddenma.mybatisFramework.entity.User;

public interface UserMapper {

	@SelfInsert("insert into user(name, age) values(#{name},#{age})")
	public int insertUser(@SelfParam("name") String name, @SelfParam("age") int age);

	@SelfSelect("select * from user where name=#{name} and age=#{age}")
	public User selectUser(@SelfParam("name") String name, @SelfParam("age") int age);

	
}
