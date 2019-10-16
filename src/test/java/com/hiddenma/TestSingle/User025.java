package com.hiddenma.TestSingle;

// 静态内部类方式，中和懒汉式和饿汉式的优点
public class User025 {

	private User025() {
	}

	public static User025 getInstance() {
		return User025.innerC.user025;
	}

	private static class innerC {
		private static final User025 user025 = new User025();
	}

	public static void main(String[] args) {
		User025 user1 = User025.getInstance();
		User025 user2 = User025.getInstance();
		System.out.println(user1 == user2);
	}

}
