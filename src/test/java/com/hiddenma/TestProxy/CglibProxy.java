package com.hiddenma.TestProxy;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

// 使用asm开源包，将代理对象类的class文件加载进来，修改字节码生成子类来处理
public class CglibProxy implements MethodInterceptor {

	private Object targetObject;

	// 目标类型为Object，可以接受任意一种参数作为被代理类，实现了动态代理
	public Object getInstance(Object target) {
		// 设置需要创建的子类
		this.targetObject = target;
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(target.getClass());
		enhancer.setCallback(this);
		return enhancer.create();
	}

	public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
		System.out.println("开启事务");
		Object result = proxy.invoke(targetObject, args);
		System.out.println("关闭事务");
		// 返回代理对象
		return result;
	}

	public static void main(String[] args) {
		CglibProxy cglibProxy = new CglibProxy();
		UserDao userDao = (UserDao) cglibProxy.getInstance(new UserDao());
		userDao.save();
	}
}
