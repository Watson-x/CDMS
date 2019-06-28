package webadv.example.session;

import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import webadv.example.bean.Manager;
import webadv.example.bean.Student;
import webadv.example.bean.Teacher;
import org.springframework.beans.factory.annotation.Autowired;

@Component
public class SessionCheck {
	@Autowired
	SessionCheck sessionCheck;
	
	public boolean sessionCheck_student(HttpSession session) {
		if(session == null) {
			return false;
		}
		Student st = (Student)session.getAttribute("StudentUser");
		if(st==null) {
			return false;
		}
		return true;
	}
	
	public boolean sessionCheck_teacher(HttpSession session) {
		if(session == null) {
			return false;
		}
		Teacher te = (Teacher)session.getAttribute("TeacherUser");
		if(te == null) {
			return false;
		}
		return true;
	}
	public boolean sessionCheck_manager(HttpSession session) {
		if(session == null) {
			return false;
		}
		Manager ma = (Manager)session.getAttribute("ManagerUser");
		if(ma == null) {
			return false;
		}
		return true;
	}
}