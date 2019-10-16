package com.hiddenma.jdbcPool.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 核心参数<br>
 * 1.空闲线程 容器没有被使用的连接存放<br>
 * 2.活动线程 容器正在使用的连接 核心步骤<br>
 * 1.初始化线程池（初始化空闲线程）<br>
 * 2.调用getConnection方法，获取连接<br>
 * 先freeConnection获取当前连接，存放在activeConnection<br>
 * 3.调用releaseConnection方法，释放连接，资源回收<br>
 * 获取activeConnection集合连接，转移到freeConnection集合中<br>
 */
public class SelfConnectionPool implements ISelfConnectionPool {

	// 计数器
	private static int connectionCount = 0;

	// 空闲线程集合，没有使用的线程存放
	private List<Connection> freeConnections = new ArrayList<>();

	// 活动线程集合，正在使用的线程存放
	private List<Connection> activeConnections = new ArrayList<>();

	DbBean dbBean = null;

	public SelfConnectionPool(DbBean dbBean) {
		this.dbBean = dbBean;
		init();
	}

	private void init() {
		if (dbBean == null) {
			throw new IllegalStateException("找不到配置文件");
		}
		int initConnections = dbBean.getInitConnections();
		for (int i = 0; i < initConnections; i++) {
			freeConnections.add(createConnecton());
		}
	}

	private Connection createConnecton() {
		try {
			Class.forName(dbBean.getDriverName());
			Connection connection = DriverManager.getConnection(dbBean.getUrl(), dbBean.getUserName(), dbBean.getPassword());
			connectionCount++;
			return connection;
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Connection getConnection() {
		// 判断活动线程数是否大于最大线程，如果大于，则进行等待
		Connection connection = null;
		try {
			if (connectionCount < dbBean.getMaxConnections()) {
				// 判断活动线程数是否大于0，如果空闲线程数小于0， 创建新的连接
				if (freeConnections.size() > 0) {
					connection = freeConnections.remove(0);
				} else {
					connection = createConnecton();
				}
				// 判断线程是否可用
				if (isAviableConnection(connection)) {
					activeConnections.add(connection);
				} else {
					connectionCount--;
					connection = getConnection();
				}
			} else {
				// 大于最大线程数，进行等待，重新获取连接
				wait(dbBean.getConnTimeOut());
				connection = getConnection();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return connection;
	}

	/**
	 * 判断连接是否可用
	 * 
	 * @param connection
	 * @return
	 */
	private boolean isAviableConnection(Connection connection) {
		try {
			if (connection == null || connection.isClosed()) {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public void releaseConnection(Connection connection) {
		if (connection == null) {
			return;
		}
		try {
			if (isAviableConnection(connection)) {
				// 判断空闲线程是否大于最大线程数
				if (freeConnections.size() < dbBean.getMaxConnections()) {
					freeConnections.add(connection);
				} else {
					// 空闲线程数已经满了
					connection.close();
				}
				activeConnections.remove(connection);
				connectionCount--;
				notifyAll();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
