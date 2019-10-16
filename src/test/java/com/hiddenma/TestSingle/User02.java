package com.hiddenma.TestSingle;

public class User02 {
	private User02() {
	}

	private static User02 user02;

	public synchronized static User02 getInstance() {
		if (user02 == null) {
			user02 = new User02();
		}
		return user02;
	}

	public static void main(String[] args) {
		User02 user1 = User02.getInstance();
		User02 user2 = User02.getInstance();
		System.out.println(user1 == user2);
	}
}
