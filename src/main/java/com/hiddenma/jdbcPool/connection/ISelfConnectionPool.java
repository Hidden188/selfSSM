package com.hiddenma.jdbcPool.connection;

import java.sql.Connection;

public interface ISelfConnectionPool {

	public Connection getConnection();
	
	public void releaseConnection(Connection connection);
	
}
