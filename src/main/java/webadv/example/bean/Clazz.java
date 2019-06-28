/**
*  162013班 第17组
*  课程设计管理系统
*  @author: 吴国伟
*  date: 2019-5-30
*  主要功能说明：实体类
*/

package webadv.example.bean;

public class Clazz {
	
	private String class_id;
	private String tea_id;
	private String message;
	public Clazz(String class_id, String tea_id, String message) {
		super();
		this.class_id = class_id;
		this.tea_id = tea_id;
		this.message = message;
	}
	public Clazz() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getClass_id() {
		return class_id;
	}
	public void setClass_id(String class_id) {
		this.class_id = class_id;
	}
	public String getTea_id() {
		return tea_id;
	}
	public void setTea_id(String tea_id) {
		this.tea_id = tea_id;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	@Override
	public String toString() {
		return "Clazz [class_id=" + class_id + ", tea_id=" + tea_id + ", message=" + message + "]";
	}
	
	

}
