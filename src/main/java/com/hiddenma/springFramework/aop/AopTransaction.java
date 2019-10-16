package com.hiddenma.springFramework.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.hiddenma.springFramework.transaction.TransactionUtils;

// 事务管理aop
@Component
@Aspect
public class AopTransaction {

	// 异常通知
	@AfterThrowing("execution(* com.hiddenma.service.impl.UserImpl.add(..))")
	public void afterThrowing() {
		System.out.println("程序回滚");
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
	}

	// 环绕通知
	@Around("execution(* com.hiddenma.service.impl.UserImpl.add(..))")
	public void around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		TransactionUtils transactionUtils = new TransactionUtils();
		System.err.println("开启事务");
		TransactionStatus begin = transactionUtils.begin();
		proceedingJoinPoint.proceed();
		transactionUtils.commit(begin);
		System.out.println("提交事务");
	}

}
