package com.hiddenma.TestSingle;

// 枚举式
public class User03 {

	private User03() {
	}

	public static User03 getInstance() {
		return User03.enu.INSTANCE.getInstance();
	}

	private enum enu {
		INSTANCE;
		private User03 user03;

		private enu() {
			user03 = new User03();
		}

		public User03 getInstance() {
			return user03;
		}
	}

	public static void main(String[] args) {
		User03 user1 = User03.getInstance();
		User03 user2 = User03.getInstance();
		System.out.println(user1 == user2);
	}

}
