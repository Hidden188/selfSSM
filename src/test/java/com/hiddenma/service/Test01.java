package com.hiddenma.service;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test01 {

	public static void main(String[] args) {
		ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring.xml");
		UserService userService = (UserService) applicationContext.getBean("userService");
		userService.add();
	}

}
