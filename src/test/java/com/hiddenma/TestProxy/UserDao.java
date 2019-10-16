package com.hiddenma.TestProxy;

public class UserDao implements IUserDao {

	public void save() {
		System.out.println("已经保存了数据");
	}

}
