package com.hiddenma.TestSingle;

// 饿汉式
public class User01 {

	private static final User01 user01 = new User01();

	private User01() {
	}

	public static User01 getInstance() {
		return user01;
	}

	public static void main(String[] args) {
		User01 user1 = User01.getInstance();
		User01 user2 = User01.getInstance();
		System.out.println(user1 == user2);
	}
	
}
