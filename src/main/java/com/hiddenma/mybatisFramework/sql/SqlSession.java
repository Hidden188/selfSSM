package com.hiddenma.mybatisFramework.sql;

import java.lang.reflect.Proxy;

import com.hiddenma.mybatisFramework.handler.SelfInvocationHandler;

/**
 * 获取sqlsession对象
 * 
 * @author Ma.jian
 *
 */
public class SqlSession {

	// 获取getMapper
	@SuppressWarnings("unchecked")
	public static <T> T getMapper(Class<T> clas) {
		return (T) Proxy.newProxyInstance(clas.getClassLoader(), new Class[] { clas }, new SelfInvocationHandler(clas));
	}

}
