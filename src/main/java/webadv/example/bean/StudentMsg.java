package webadv.example.bean;

public class StudentMsg {
	private String stu_id;
	private String name;
	private String sex;
	private int age;
	private String contact;
	private String org_id;
	private double re_grade;
	private double stu_grade;
	
	
	public StudentMsg(String stu_id, String name, String sex, int age, String contact, String org_id, double re_grade,
			double stu_grade) {
		super();
		this.stu_id = stu_id;
		this.name = name;
		this.sex = sex;
		this.age = age;
		this.contact = contact;
		this.org_id = org_id;
		this.re_grade = re_grade;
		this.stu_grade = stu_grade;
	}
	public StudentMsg() {
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
	public String getOrg_id() {
		return org_id;
	}
	public void setOrg_id(String org_id) {
		this.org_id = org_id;
	}
	public double getRe_grade() {
		return re_grade;
	}
	public void setRe_grade(double re_grade) {
		this.re_grade = re_grade;
	}
	public double getStu_grade() {
		return stu_grade;
	}
	public void setStu_grade(double stu_grade) {
		this.stu_grade = stu_grade;
	}
	@Override
	public String toString() {
		return "StudentMsg [stu_id=" + stu_id + ", name=" + name + ", sex=" + sex + ", age=" + age + ", contact="
				+ contact + ", org_id=" + org_id + ", re_grade=" + re_grade + ", stu_grade=" + stu_grade + "]";
	}
	
	

}
