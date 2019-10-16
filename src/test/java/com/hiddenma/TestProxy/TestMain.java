package com.hiddenma.TestProxy;

public class TestMain {

	UserDao ud = new UserDao();
	UserDaoProxy udp = new UserDaoProxy(ud);

}
