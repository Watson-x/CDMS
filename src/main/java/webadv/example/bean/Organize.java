/**
*  162013班 第17组
*  课程设计管理系统
*  @author: 吴国伟
*  date: 2019-5-30
*  主要功能说明：实体类
*/

package webadv.example.bean;

public class Organize {
	private String org_id;
	private String cd_id;
	private String message;
	private String stu_id;
	public Organize(String org_id, String cd_id, String message, String stu_id) {
		super();
		this.org_id = org_id;
		this.cd_id = cd_id;
		this.message = message;
		this.stu_id = stu_id;
	}
	public Organize() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getOrg_id() {
		return org_id;
	}
	public void setOrg_id(String org_id) {
		this.org_id = org_id;
	}
	public String getCd_id() {
		return cd_id;
	}
	public void setCd_id(String cd_id) {
		this.cd_id = cd_id;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getStu_id() {
		return stu_id;
	}
	public void setStu_id(String stu_id) {
		this.stu_id = stu_id;
	}
	@Override
	public String toString() {
		return "Organize [org_id=" + org_id + ", cd_id=" + cd_id + ", message=" + message + ", stu_id=" + stu_id + "]";
	}
}
