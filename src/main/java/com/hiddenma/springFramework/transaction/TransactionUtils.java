package com.hiddenma.springFramework.transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.interceptor.DefaultTransactionAttribute;

@Component
public class TransactionUtils {

	@Autowired
	private DataSourceTransactionManager dataSourceTransactionManager;

	// 开启事务
	public TransactionStatus begin() {
		TransactionStatus transactionStatus = dataSourceTransactionManager
				.getTransaction(new DefaultTransactionAttribute());
		return transactionStatus;
	}

	// 提交事务
	public void commit(TransactionStatus transactionStatus) {
		dataSourceTransactionManager.commit(transactionStatus);
	}

	// 回滚事务
	public void rollback(TransactionStatus transactionStatus) {
		dataSourceTransactionManager.rollback(transactionStatus);
	}

}
