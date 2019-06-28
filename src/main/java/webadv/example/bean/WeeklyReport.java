/**
*  162013班 第17组
*  课程设计管理系统--过程管理
*  @author: 吴国伟
*  date: 2019-6-2
*  主要功能说明：实体类
*/

package webadv.example.bean;

public class WeeklyReport {
	private String we_re_id;
	private String stu_id;
	private String re_id;
	
	public WeeklyReport() {
		super();
	}

	public String getStu_id() {
		return stu_id;
	}

	public String getWe_re_id() {
		return we_re_id;
	}

	public void setWe_re_id(String we_re_id) {
		this.we_re_id = we_re_id;
	}

	public void setStu_id(String stu_id) {
		this.stu_id = stu_id;
	}

	public String getRe_id() {
		return re_id;
	}

	public void setRe_id(String re_id) {
		this.re_id = re_id;
	}

	public WeeklyReport(String stu_id, String re_id) {
		super();
		this.stu_id = stu_id;
		this.re_id = re_id;
	}

	@Override
	public String toString() {
		return "WeeklyReport [we_re_id=" + we_re_id + ", stu_id=" + stu_id + ", re_id=" + re_id + "]";
	}
	
}
