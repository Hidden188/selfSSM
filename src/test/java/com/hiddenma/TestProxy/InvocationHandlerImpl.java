package com.hiddenma.TestProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

// jdk动态代理，面向接口，每次生成动态代理对象时，实现了InvocationHandler接口的调用处理器对象
public class InvocationHandlerImpl implements InvocationHandler {

	private Object target; // 业务实现类对象，用来调用具体的业务方法

	// 通过构造函数传入目标对象
	public InvocationHandlerImpl(Object target) {
		this.target = target;
	}

	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		Object result = null;
		System.out.println("调用开始处理");
		result = method.invoke(target, args);
		System.out.println("调用结束");
		return result;
	}
	
	public static void main(String[] args) {
		// 被代理对象
		IUserDao userDao = new UserDao();
		InvocationHandlerImpl invocationHandlerImpl = new InvocationHandlerImpl(userDao);
		ClassLoader loader = userDao.getClass().getClassLoader();
		Class<?>[] interfaces = userDao.getClass().getInterfaces();
		// 主要装载器、一组接口及调用处理动态代理实例
		IUserDao newProxyInstance = (IUserDao) Proxy.newProxyInstance(loader, interfaces, invocationHandlerImpl);
		newProxyInstance.save();
	}

}
