/**
*  162013班 第17组
*  课程设计管理系统
*  @author: 吴国伟
*  date: 2019-5-30
*  主要功能说明：实体类
*/

package webadv.example.bean;

public class Student{
	private String stu_id;
	private String name;
	private String sex;
	private String class_id;
	private int age;
	private String contact;
	private String password;
	
	
	
	public Student() {
		super();
	}

	public Student(String stu_id, String name, String sex, String class_id, int age, String contact) {
		super();
		this.stu_id = stu_id;
		this.name = name;
		this.sex = sex;
		this.class_id = class_id;
		this.age = age;
		this.contact = contact;
	}

	public Student(String stu_id, String name, String sex, String class_id, int age, String contact, String password) {
		super();
		this.stu_id = stu_id;
		this.name = name;
		this.sex = sex;
		this.class_id = class_id;
		this.age = age;
		this.contact = contact;
		this.password = password;
	}
	
	public String getStu_id() {
		return stu_id;
	}
	public void setStu_id(String stu_id) {
		this.stu_id = stu_id;
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
	public String getClass_id() {
		return class_id;
	}
	public void setClass_id(String class_id) {
		this.class_id = class_id;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public String toString() {
		return "Student [stu_id=" + stu_id + ", name=" + name + ", sex=" + sex + ", class_id=" + class_id + ", age="
				+ age + ", contact=" + contact + ", password=" + password + "]";
	}
	
	
}
