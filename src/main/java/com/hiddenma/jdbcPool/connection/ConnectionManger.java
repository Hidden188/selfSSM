package com.hiddenma.jdbcPool.connection;

import java.sql.Connection;

public class ConnectionManger {

	static DbBean dbBean = new DbBean();
	static SelfConnectionPool selfConnectionPool = new SelfConnectionPool(dbBean);
	
	public static Connection getConnection(){
		return selfConnectionPool.getConnection();
	}
	
	public static void releaseConnection(Connection connection) {
		selfConnectionPool.releaseConnection(connection);
	}
	
	
	
}
