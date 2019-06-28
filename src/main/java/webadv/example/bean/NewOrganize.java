package webadv.example.bean;

public class NewOrganize {
	private String org_id;
	private String stu_id;
	private String name;
	private String cd_id;
	private String title;
	private String content;
	private String message;
	
	@Override
	public String toString() {
		return "NewOrganize [org_id=" + org_id + ", stu_id=" + stu_id + ", name=" + name + ", cd_id=" + cd_id
				+ ", title=" + title + ", content=" + content + ", message=" + message + "]";
	}
	
	
	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}


	public NewOrganize() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getOrg_id() {
		return org_id;
	}
	public void setOrg_id(String org_id) {
		this.org_id = org_id;
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
	
}
