/**
*  162013班 第17组
*  课程设计管理系统
*  @author: 吴国伟
*  date: 2019-5-30
*  主要功能说明：实体类
*/

package webadv.example.bean;

public class StudentOrganize {
	private String stu_id;
	private String org_id;
	private int grade = -1;
	
	public StudentOrganize() {
		super();
		// TODO Auto-generated constructor stub
	}


	public StudentOrganize(String stu_id, String org_id, int grade) {
		super();
		this.stu_id = stu_id;
		this.org_id = org_id;
		this.grade = grade;
	}


	public int getGrade() {
		return grade;
	}


	public void setGrade(int grade) {
		this.grade = grade;
	}


	public String getStu_id() {
		return stu_id;
	}

	public void setStu_id(String stu_id) {
		this.stu_id = stu_id;
	}

	public String getOrg_id() {
		return org_id;
	}

	public void setOrg_id(String org_id) {
		this.org_id = org_id;
	}

	@Override
	public String toString() {
		return "StudentOrganize [stu_id=" + stu_id + ", org_id=" + org_id + ", grade=" + grade + "]";
	}
}