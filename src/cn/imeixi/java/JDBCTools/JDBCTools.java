package cn.imeixi.java.JDBCTools;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class JDBCTools {
	
	/**
	 * 通过PreparedStatement 实现的update 静态方法（可防止sql注入）
	 * @param sql
	 * @param strings
	 */
	public static void update(String sql,Object ... args){
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		try{
			connection = JDBCTools.getConnetction();
			
			preparedStatement = connection.prepareStatement(sql);
			
			for(int i = 0;i < args.length;i++){
				preparedStatement.setObject(i+1, args[i]);
			}
			
			preparedStatement.executeUpdate();
			
			
			
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			JDBCTools.release(null, preparedStatement, connection);
		}
	}
	
	
	
	/**
	 * 通过Statement实现的 update（增删改）静态方法
	 * @param sql
	 */
	public static void update(String sql){
		Connection connection = null;
		Statement statement = null;
		
		try{
			connection = JDBCTools.getConnetction();
			statement = connection.createStatement();
			statement.executeUpdate(sql);
			
		}catch(Exception e)
		{
			e.printStackTrace();		
		}finally{
			release(null, statement, connection);
		}
		
		
		
	}
	
	/**
	 * 创建数据库链接的具体方法
	 * @return
	 * @throws Exception
	 */
	public static Connection getConnetction() throws Exception{
		
		//1、创建必备的4个String参数
		InputStream inStream = JDBCTools.class.getClassLoader().getResourceAsStream("jdbc.properties");
		
		Properties properties = new Properties();
		properties.load(inStream);
		
		String driverClass = properties.getProperty("driverClass");
		String jdbcUrl = properties.getProperty("jdbcUrl");
		String user = properties.getProperty("user");
		String password = properties.getProperty("password");
		
		//2、加载驱动程序
		Class.forName(driverClass);
		Connection connection = DriverManager.getConnection(jdbcUrl, user, password);

		return connection;
	}
	
	
	/**
	 * 释放数据库资源的具体方法（Statement）
	 * @param resultSet
	 * @param statement
	 * @param connection
	 */
	public static void release(ResultSet resultSet,Statement statement,Connection connection){
		try {
			if(resultSet != null){
				resultSet.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try {
			if(statement != null){
				statement.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try {
			if(connection != null){
				connection.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 释放数据库资源的重载方法（PrepareedStatement）
	 * @param resultSet
	 * @param preparedstatement
	 * @param connection
	 */
	public static void release(ResultSet resultSet,PreparedStatement preparedStatement,Connection connection){
		try {
			if(resultSet != null){
				resultSet.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try {
			if(preparedStatement != null){
				preparedStatement.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try {
			if(connection != null){
				connection.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
