package com.hiddenma.Connection;

import java.sql.Connection;

import com.hiddenma.jdbcPool.connection.ConnectionManger;

import junit.framework.TestCase;

public class TestConnection extends TestCase {

	public static void main(String[] args) {
		ConnectionThread connectionThread = new ConnectionThread();
		for (int i = 0; i < 4; i++) {
			Thread thread = new Thread(connectionThread);
			thread.start();
		}
	}
}

class ConnectionThread implements Runnable {
	public void run() {
		Connection connection = ConnectionManger.getConnection();
		System.out.println(Thread.currentThread().getName() + ",  " + connection);
		ConnectionManger.releaseConnection(connection);
	}
}
