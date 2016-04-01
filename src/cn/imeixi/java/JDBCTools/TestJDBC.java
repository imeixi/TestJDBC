package cn.imeixi.java.JDBCTools;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.apache.commons.beanutils.BeanUtils;
import org.junit.Test;

import java.sql.ResultSetMetaData;

import java.sql.PreparedStatement;

public class TestJDBC {
	/*******************************************************************************************************
	 * 练习一：通过对象的方式，在Student表中插入insert 数据
	 * 1、创建student对象，（通过从控制台输入参数的方式创建）
	 * 2、执行sql，update到student表中
	 * @throws Exception 
	 * 
	 */
	@Test
	public void addNewStudent() throws Exception{
		//1、创建Student对象，通过键盘输入各个参数值，创建student对象
		Student student = getStudentFromConsle();
		
		//2、编写sql
		String sql = "INSERT INTO student (id,name,sex,birth) VALUES("
				+ student.getId() + ",'" 
				+ student.getName() + "','" 
				+ student.getSex() + "','" 
				+ student.getBirth() + "')";
		
		//3、执行sql
		JDBCTools.update(sql);
		System.out.println(sql);
		
	}

	private Student getStudentFromConsle() throws Exception {
		
		Student student = new Student();
		
		Scanner scan = new Scanner(System.in);
		
		System.out.print("ID:");
		student.setId(scan.nextInt());
		
		System.out.print("NAME:");
		student.setName(scan.next());
		
		System.out.print("SEX:");
		student.setSex(scan.next());
		
		System.out.print("birth:(格式类似 1970-10-10)");
		String str = scan.next();
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
		Date da = sd.parse(str);
		System.out.println(str);
		System.out.println(da);
		student.setBirth(new java.sql.Date(da.getTime()));
		
		return student;
	}
	
	/*****************************************************************************************************************
	 * 练习二：通过prepareStatement 创建update student对象方法
	 * @throws Exception 
	 */
	@Test
	public void addNewStudent2() throws Exception{
		//1、创建student
		Student student = getStudentFromConsle();
		
		String sql = "INSERT INTO student(id,name,sex,birth) VALUES (?,?,?,?)";
		
		JDBCTools.update(sql,"14","black2","male","1995-10-10");
//		JDBCTools.update(sql,student.getId(),student.getName(),student.getSex(),student.getBirth());
		
		
		System.out.println(sql);
		
	}
	
	
	/**
	 * 练习三，通过sql查询语句，获取参数值，创建Student对象
	 * 通过Connection ,Statement, ResultSet
	 * 
	 */
	
	@Test
	public void getStudent(){
		Student student = null;
		Student stu = null;
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		String sql = "SELECT id,name,sex,birth FROM student "
					+ "WHERE id = 12";
		
		try {
			student = new Student();
			connection = JDBCTools.getConnetction();
			statement = connection.createStatement();
			resultSet = statement.executeQuery(sql);
			
			while(resultSet.next()){
				student.setId(resultSet.getInt(1));
				student.setName(resultSet.getString(2));
				student.setSex(resultSet.getString(3));
				student.setBirth(resultSet.getDate(4));
				
				//方法二：推荐
				stu = new Student(resultSet.getInt(1), resultSet.getString(2),
						resultSet.getString(3), resultSet.getDate(4));
			}
			
			
			
			System.out.println(student);
			System.out.println(stu);
			
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			JDBCTools.release(resultSet, statement, connection);
		}
		
	}
	
	
	/**
	 * 练习三，写一个通用的get方法，可根据不同的对象类型返回对象
	 * 
	 */
	public <T> T get(Class <T> clazz,String sql,Object ... args){
		
		T entity = null;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultset = null;
		
		try {
			//1、创建数据库链接
			connection = JDBCTools.getConnetction();
			//2、获得PreparedStatment对象，填充sql
			preparedStatement = connection.prepareStatement(sql);
			
			for(int i = 0; i < args.length; i++){
				preparedStatement.setObject(i+1, args[i]);
			}
			//3、执行sql，得到Resultset
			
			resultset = preparedStatement.executeQuery();
			//4、创建 Map<>
			Map<String, Object> values = new HashMap<String, Object>();
			
			//5、判断Resultset是否为空（.next（））
			if(resultset != null){
				//6、不为空，创建ResultsetMeteData对象，获取sql别名，（对象属性名）
				ResultSetMetaData rsmd = resultset.getMetaData();
				//7、ResultSet获取属性值
				if(resultset.next()){
					for(int i = 0;i < rsmd.getColumnCount();i++){
						String columnLable = rsmd.getColumnLabel(i+1);
						Object object = resultset.getObject(i+1);
						//8、Map赋值
						values.put(columnLable, object);
					}
				}
				//9、用反射创建class对象
				
				entity = clazz.newInstance();
				
				//10、便利Map，使用Utilbean 工具类给对象赋值
				for(Map.Entry<String, Object> entry:values.entrySet()){
					String propertiesName = entry.getKey();
					Object propertiesValue = entry.getValue();
					
					BeanUtils.setProperty(entity, propertiesName, propertiesValue);
					
				}
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			JDBCTools.release(resultset, preparedStatement, connection);
		}
		
		
		
		return null;
		
	}
	
	
}
