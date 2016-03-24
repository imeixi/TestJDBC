package cn.imeixi.java.JDBC;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.SQLException;
import java.util.Properties;

import org.junit.Test;

public class TestJDBC {
	
	@Test
	public Connection getConnection(){
		/**
		 * 1、创建连接必备的4个参数 driverClass，jdbcUrl，uesr，password
		 * 2、通过Properties对象获取四个参数
		 * 3、加载driverclass
		 * 4、
		 */
		
		
		
		return null;
	}
	
	
	
	

	@Test
	public void testDriver() throws Exception {
		/**
		 * 1、创建Driver对象
		 * 2、定义 String，info
		 * 3、创建连接Connection对象，使用Driver 对象的connection（url，info）方法。
		 */
		
//		Driver driver = new com.mysql.jdbc.Driver();
//		String url = "jdbc:mysql://172.31.30.83/test";
//		String user = "root";
//		String password = "123456";
		
		Driver driver = new com.mysql.jdbc.Driver();
		String url = "jdbc:mysql://172.31.30.83/test";
		String user = "root";
		String password = "123456";
	
		Properties info = new Properties();
		info.put("user", user);
		info.put("password", password);
		
		Connection connection = driver.connect(url, info);
		System.out.println(connection);
	
	}

}
