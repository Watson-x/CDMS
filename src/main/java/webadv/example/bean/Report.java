/**
*  162013班 第17组
*  课程设计管理系统
*  @author: 吴国伟
*  date: 2019-5-30
*  主要功能说明：实体类
*/

package webadv.example.bean;

public class Report {
	private String re_id;
	private String title;
	private String content;
	private String filename;
	private int grade = -1;
	
	
	public Report(String re_id, String title, String content, String filename, int grade) {
		super();
		this.re_id = re_id;
		this.title = title;
		this.content = content;
		this.filename = filename;
		this.grade = grade;
	}
	public int getGrade() {
		return grade;
	}
	public void setGrade(int grade) {
		this.grade = grade;
	}
	public Report() {
		super();
	}
	public String getRe_id() {
		return re_id;
	}
	public void setRe_id(String re_id) {
		this.re_id = re_id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	@Override
	public String toString() {
		return "Report [re_id=" + re_id + ", title=" + title + ", content=" + content + ", filename=" + filename
				+ ", grade=" + grade + "]";
	}
	
	

}
