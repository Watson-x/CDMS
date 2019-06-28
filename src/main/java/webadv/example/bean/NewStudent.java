package webadv.example.bean;

public class NewStudent {
	private String stu_id;
	private String name;
	private String sex;
	private String class_id;
	private int age;
	private String contact;
	private String org_id;
	private int grade = -1;
	
	
	
	public NewStudent() {
		super();
		// TODO Auto-generated constructor stub
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
	public int getGrade() {
		return grade;
	}
	public void setGrade(int grade) {
		this.grade = grade;
	}
	@Override
	public String toString() {
		return "NewStudent [stu_id=" + stu_id + ", name=" + name + ", sex=" + sex + ", class_id=" + class_id + ", age="
				+ age + ", contact=" + contact + ", org_id=" + org_id + ", grade=" + grade + "]";
	}

	public String getOrg_id() {
		return org_id;
	}

	public void setOrg_id(String org_id) {
		this.org_id = org_id;
	}
	
	
}
