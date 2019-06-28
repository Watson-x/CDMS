/**
*  162013班 第17组
*  课程设计管理系统
*  @author: 吴国伟
*  date: 2019-5-30
*  主要功能说明：实体类
*/

package webadv.example.bean;

public class Teacher {
	
	private String tea_id;
	private String name;
	private String sex;
	private int age;
	private String contact;
	private String password;
	
	
	
	public Teacher() {
		super();
	}

	public Teacher(String tea_id, String name, String sex, int age, String contact) {
		super();
		this.tea_id = tea_id;
		this.name = name;
		this.sex = sex;
		this.age = age;
		this.contact = contact;
	}

	public Teacher(String tea_id, String name, String sex, int age, String contact, String password) {
		super();
		this.tea_id = tea_id;
		this.name = name;
		this.sex = sex;
		this.age = age;
		this.contact = contact;
		this.password = password;
	}
	
	public String getTea_id() {
		return tea_id;
	}
	public void setTea_id(String tea_id) {
		this.tea_id = tea_id;
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
		return "Teacher [tea_id=" + tea_id + ", name=" + name + ", sex=" + sex + ", age=" + age + ", contact=" + contact
				+ ", password=" + password + "]";
	}
}
