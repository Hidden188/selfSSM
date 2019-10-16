package com.hiddenma.springFramework.retention;

import java.lang.reflect.Method;

public class TestAnnotation {

	@SelfTransactional(userId = 8, name = "dfds")
	public void add() {
	}

	public void del() {
	}

	public static void main(String[] args) throws ClassNotFoundException {
		Class<?> forName = Class.forName("com.hiddenma.retention.TestAnnotation");
		Method[] methods = forName.getDeclaredMethods();
		for (Method method : methods) {
			System.out.println(method.getName());
			SelfTransactional selfTransactional = method.getDeclaredAnnotation(SelfTransactional.class);
			if (selfTransactional == null) {
				System.out.println("方法没有加注解");
				continue;
			}
			System.out.println(selfTransactional.userId());
			System.out.println(selfTransactional.name());
		}
	}
}
