package webadv.example.controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import webadv.example.bean.Teacher;
import webadv.example.pository.TeacherRepository;
import webadv.example.session.SessionCheck;
import webadv.example.util.MD5Util;

@Controller
public class TeacherDo {
	@Autowired
	private TeacherRepository teacher_r;
	@Autowired
	SessionCheck sessionCheck;
	@Autowired
	MD5Util md5Util;
	
	@RequestMapping(value = "/intoTeachershow")
	public String intoTeachershow(Model model,HttpSession session, HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		if (!sessionCheck.sessionCheck_teacher(session)) {
			out.println("<script>");
			out.println("window.alert('未登录或登录失效!') ");
			out.println("window.top.location.href='/'");
			out.println("</script>");
			return "login";
		}
		
		Teacher teacher = (Teacher) session.getAttribute("TeacherUser");
		System.out.println(teacher);
		model.addAttribute("Teacher", teacher);
		return "teacher_message";
	}
	@RequestMapping(value = "/intoEdit")
	public String editTeachershow(Model model,HttpSession session, HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		if (!sessionCheck.sessionCheck_teacher(session)) {
			out.println("<script>");
			out.println("window.alert('未登录或登录失效!') ");
			out.println("window.top.location.href='/'");
			out.println("</script>");
			return "login";
		}
		
		Teacher teacher = (Teacher) session.getAttribute("TeacherUser");
		System.out.println(teacher);
		model.addAttribute("Teacher", teacher);
		return "teacher_edit";
	}
	@RequestMapping(value = "/teacher_edit_do")
	public String updateTeacher(Model model,HttpSession session, HttpServletResponse response,Teacher teacher) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		if (!sessionCheck.sessionCheck_teacher(session)) {
			out.println("<script>");
			out.println("window.alert('未登录或登录失效!') ");
			out.println("window.top.location.href='/'");
			out.println("</script>");
			return "login";
		}
		
		System.out.println("TeacherEdit:"+teacher);
		Teacher sessionTeacher = (Teacher)session.getAttribute("TeacherUser");
		
		Teacher te = new Teacher(teacher.getTea_id(),teacher.getName(),teacher.getSex(),teacher.getAge(),teacher.getContact(),sessionTeacher.getPassword());
		System.out.println("te::"+te);
		int result = teacher_r.updateTeacher(te);
		
		session.setAttribute("TeacherUser", te);
		
		if (result > 0) {
			out.println("<script> window.alert('修改成功!') </script>");
		} else {
			out.println("<script> window.alert('修改失败!') </script>");
		}
		return "move";
	}
	
	@RequestMapping(value = "/go_teacher_passoord")
	public String passwordshow(Model model,HttpSession session, HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		if (!sessionCheck.sessionCheck_teacher(session)) {
			out.println("<script>");
			out.println("window.alert('未登录或登录失效!') ");
			out.println("window.top.location.href='/'");
			out.println("</script>");
			return "login";
		}
		
		Teacher teacher = (Teacher) session.getAttribute("TeacherUser");
		System.out.println(teacher);
		model.addAttribute("Teacher", teacher);
		return "teacher_password";
	}
	
	@RequestMapping(value = "/teacher_password")
	public String passwordshow(HttpSession session, HttpServletResponse response,String newpassword) throws IOException {
		
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		if (!sessionCheck.sessionCheck_teacher(session)) {
			out.println("<script>");
			out.println("window.alert('未登录或登录失效!') ");
			out.println("window.top.location.href='/'");
			out.println("</script>");
			return "login";
		}
		
		Teacher te= (Teacher)session.getAttribute("TeacherUser");
		System.out.println(te);
		
		newpassword = md5Util.md5(newpassword);
		
		int result = teacher_r.updatePassword(newpassword,te.getTea_id());
		if (result > 0) {
			out.println("<script> window.alert('修改成功!') </script>");
		} else {
			out.println("<script> window.alert('修改失败!') </script>");
		}
		return "move";
	}
	
}
