package com.hiddenma.TestProxy;

public class UserDaoProxy implements IUserDao {

	private IUserDao target;

	public UserDaoProxy(IUserDao iuserDao) {
		this.target = iuserDao;
	}
	
	public void save() {
		System.out.println("开启事务、、、、、");
		target.save();
		System.out.println("关闭事务、、、、、");
	}

}
