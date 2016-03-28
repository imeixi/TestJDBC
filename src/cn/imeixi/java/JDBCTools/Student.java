package cn.imeixi.java.JDBCTools;

import java.util.Date;

public class Student {
	private int id;
	private String name;
	private String sex;
	private Date birth;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public Date getBirth() {
		return birth;
	}
	public void setBirth(Date birth) {
		this.birth = birth;
	}
	public Student(int id, String name, String sex, Date birth) {
		super();
		this.id = id;
		this.name = name;
		this.sex = sex;
		this.birth = birth;
	}
	public Student() {
		super();
	}
	
	@Override
	public String toString() {
		return "Student [id=" + id + ", name=" + name + ", sex=" + sex + ", birth=" + birth + "]";
	}
	

}
