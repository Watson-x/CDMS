/**
*  162013班 第17组
*  课程设计管理系统
*  @author: 吴国伟
*  date: 2019-5-30
*  主要功能说明：实体类
*/

package webadv.example.bean;

public class CourseDesign {
	
	private String cd_id;
	private String title;
	private String content;
	
	public CourseDesign() {
		super();
	}
	public CourseDesign(String cd_id, String title, String content) {
		super();
		this.cd_id = cd_id;
		this.title = title;
		this.content = content;
	}
	public String getCd_id() {
		return cd_id;
	}
	public void setCd_id(String cd_id) {
		this.cd_id = cd_id;
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
	@Override
	public String toString() {
		return "CourseDesign [cd_id=" + cd_id + ", title=" + title + ", content=" + content + "]";
	}
}
