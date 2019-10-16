package com.hiddenma.springFramework.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

// 切面类
@Component
// 注解是切面类的意思
@Aspect
public class AopLog {

	// aop 的几个通知：前置通知 后置通知 运行通知 异常通知 环绕通知

	// 前置通知
	@Before("execution(* com.hiddenma.service.User.add())")
	public void before() {
		System.out.println("before前置通知");
	}

	// 后置通知
	@After("execution(* com.hiddenma.service.User.add())")
	public void after() {
		System.out.println("after后置通知");
	}

	// 运行通知
	@AfterReturning("execution(* com.hiddenma.service.User.add())")
	public void returning() {
		System.out.println("运行异常");
	}

	// 异常通知
	@AfterThrowing("execution(* com.hiddenma.service.User.add())")
	public void afterThrowing() {
		System.out.println("异常通知");
	}

	// 环绕通知
	@Around("execution(* com.hiddenma.service.User.add())")
	public void around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		System.out.println("环绕通知开启");
		proceedingJoinPoint.proceed(); // 作用是实际调用add()方法，如果抛出异常，不会执行后面代码
		System.out.println("环绕通知结束");
	}
	
	
}
