package cn.imeixi.java.JDBC;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.Properties;

import org.junit.Test;


public class TestJDBC {
	
	@Test
	public void testStatment(){
		/**
		 * 0、申请链接
		 * 1、创建连接必备的4个参数 driverClass，jdbcUrl，uesr，password
		 * 2、通过Properties对象获取四个参数
		 * 3、加载driverClass
		 * 4、DriverManager创建对象
		 * 5、通过对象的 getConnection方法获取链接
		 * 6、connection对象创建 statement对象
		 * 7、准备sql语句
		 * 8、调用statement。executeUpdate方法执行sql
		 * 
		 * 10、关闭链接
		 */
		//1、创建4个必备参数
		String driverClass = null;
		String jdbcUrl = null;
		String user = null;
		String password = null;
		
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null; 
		
		String sql1 = null;
		String sql2 = null;
		String sql3 = null;
		String sql4 = null;
		String sql5 = null;
		String sql6 = null;
		
		int id = 0;
		String name = null;
		String sex = null;
		Date birth = null;
		
		
		try {
			
			InputStream inStream = getClass().getClassLoader().getResourceAsStream("jdbc.properties");
			
			Properties properties = new Properties();
			properties.load(inStream);
			driverClass = properties.getProperty("driverClass");
			jdbcUrl = properties.getProperty("jdbcUrl");
			user = properties.getProperty("user");
			password = properties.getProperty("password");
			
			connection = DriverManager.getConnection(jdbcUrl, user, password);
			statement = connection.createStatement();
			
			sql1 = "insert into student(id,name,sex,birth) values (5,'jim','male','1990-1-5')";
			sql2 = "insert into student(id,name,sex,birth) values (6,'jerry','famale','1995-1-5')";
			sql3 = "insert into student(id,name,sex,birth) values (7,'tom','male','1970-1-5')";
			sql4 = "update student set name = 'lucy' where id = 2";
			sql5 = "update student set name = 'Lily' where id = 5";
			sql6 = "select id,name,sex,birth from student";
//			statement.executeUpdate(sql4);			
//			statement.executeUpdate(sql5);	
			
			resultSet = statement.executeQuery(sql6);
			System.out.println(resultSet);
			
			while(resultSet.next()){
				id = resultSet.getInt("id");
				name = resultSet.getString(2);
				sex = resultSet.getString("sex");
				birth = resultSet.getDate("birth");
				
				System.out.println(id + "," + name + "," + sex +"," + birth);
			}
			
			

			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			try {
				if (resultSet != null)
					resultSet.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			try {
				if (statement != null)
					statement.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			try {
				if (connection != null)
					connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		
		
		
		
	}
	
	
	
	@Test
	public void testDriverManager() throws Exception{
		/**
		 * 1、创建连接必备的4个参数 driverClass，jdbcUrl，uesr，password
		 * 2、通过Properties对象获取四个参数
		 * 3、加载driverClass
		 * 4、DriverManager创建对象
		 * 5、通过对象的 getConnection方法获取链接
		 */
		String jdbcUrl = null;
		String driverClass = null;
		String user = null;
		String password = null;
		
		InputStream inStream = getClass().getClassLoader().getResourceAsStream("jdbc.properties");
		
		Properties properties = new Properties();
		properties.load(inStream);
		driverClass = properties.getProperty("driverClass");
		jdbcUrl = properties.getProperty("jdbcUrl");
		user = properties.getProperty("user");
		password = properties.getProperty("password");
		
		Class.forName(driverClass);
		
		Connection connection = DriverManager.getConnection(jdbcUrl, user, password);
		System.out.println(connection);
		
	}

	@Test
	public void getConnection() throws Exception{
		/**
		 * 1、创建连接必备的4个参数 driverClass，jdbcUrl，uesr，password
		 * 2、通过Properties对象获取四个参数
		 * 3、加载driverClass
		 * 4、创建driver 对象，通过反射.newInstance();
		 * 5、获取connection链接
		 */
		String jdbcUrl = null;
		String driverClass = null;
		String user = null;
		String password = null;
		
		InputStream inStream = getClass().getClassLoader().getResourceAsStream("jdbc.properties");
		
		Properties properties = new Properties();
		properties.load(inStream);
		driverClass = properties.getProperty("driverClass");
		jdbcUrl = properties.getProperty("jdbcUrl");
		user = properties.getProperty("user");
		password = properties.getProperty("password");
		
		Driver driver = (Driver) Class.forName(driverClass).newInstance(); 
		Properties info = new Properties();
		info.put("user", user);
		info.put("password", password);
		
		Connection connection = driver.connect(jdbcUrl, info);
		System.out.println(connection);

	}


	


	@Test
	public void testDriver() throws Exception {
		/**
		 * 1、创建Driver对象
		 * 2、定义 String，info
		 * 3、创建连接Connection对象，使用Driver 对象的connection（url，info）方法。
		 */
		
		//mysql链接
//		Driver driver = new com.mysql.jdbc.Driver();
//		String url = "jdbc:mysql://172.31.30.83/test";
//		String user = "root";
//		String password = "123456";
//		

		//oracle链接
		Driver driver = new oracle.jdbc.driver.OracleDriver();
		String url = "jdbc:oracle:thin:@172.31.30.84:1521:orcl";
		String user = "scott";
		String  password= "123456";
	
		Properties info = new Properties();
		info.put("user", user);
		info.put("password", password);
		
		Connection connection = driver.connect(url, info);
		System.out.println(connection);
	
	}

}
