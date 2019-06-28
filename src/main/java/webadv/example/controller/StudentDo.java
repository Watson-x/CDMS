package webadv.example.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import webadv.example.bean.Student;
import webadv.example.pository.StudentRepository;
import webadv.example.session.SessionCheck;
import webadv.example.util.MD5Util;
@Controller
public class StudentDo {
	@Autowired
	private StudentRepository student_r;
	@Autowired
	SessionCheck sessionCheck;
	@Autowired
	MD5Util md5Util;
	
	@RequestMapping(value = "/intoStudentshow")
	public String intoStudentshow(Model model,HttpSession session, HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		if (!sessionCheck.sessionCheck_student(session)) {
			out.println("<script>");
			out.println("window.alert('未登录或登录失效!') ");
			out.println("window.top.location.href='/'");
			out.println("</script>");
			return "login";
		}
		
		Student student = (Student) session.getAttribute("StudentUser");
		System.out.println(student);
		model.addAttribute("Student", student);
		return "student_message";
	}
	@RequestMapping(value = "/intosEdit")
	public String editStudentshow(Model model,HttpSession session, HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		if (!sessionCheck.sessionCheck_student(session)) {
			out.println("<script>");
			out.println("window.alert('未登录或登录失效!') ");
			out.println("window.top.location.href='/'");
			out.println("</script>");
			return "login";
		}
		
		Student student = (Student) session.getAttribute("StudentUser");
		System.out.println(student);
		model.addAttribute("Student", student);
		return "student_edit";
	}
	@RequestMapping(value = "/student_edit_do")
	public String updateStudent(Model model,HttpSession session, HttpServletResponse response,Student student) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		if (!sessionCheck.sessionCheck_student(session)) {
			out.println("<script>");
			out.println("window.alert('未登录或登录失效!') ");
			out.println("window.top.location.href='/'");
			out.println("</script>");
			return "login";
		}
		
		System.out.println("EditStudent:"+student);
		Student sessionStudent = (Student)session.getAttribute("StudentUser");
		
		Student thisSessionStudent = new Student(student.getStu_id(),student.getName(),student.getSex(),student.getClass_id(),student.getAge(),student.getContact(),sessionStudent.getPassword());
		int result = student_r.updateStudent(thisSessionStudent);
		
		session.setAttribute("StudentUser", thisSessionStudent);
		
		if (result > 0) {
			out.println("<script> window.alert('修改成功!') </script>");
		} else {
			out.println("<script> window.alert('修改失败!') </script>");
		}
		return "move";
	}
	
	@RequestMapping(value = "/go_student_passoord")
	public String passwordshow(Model model,HttpSession session, HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		if (!sessionCheck.sessionCheck_student(session)) {
			out.println("<script>");
			out.println("window.alert('未登录或登录失效!') ");
			out.println("window.top.location.href='/'");
			out.println("</script>");
			return "login";
		}
		
		Student student = (Student) session.getAttribute("StudentUser");
		System.out.println(student);
		model.addAttribute("Student", student);
		return "student_password";
	}
	
	@RequestMapping(value = "/student_password")
	public String passwordshow(HttpSession session, HttpServletResponse response,String newpassword) throws IOException {
		
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		if (!sessionCheck.sessionCheck_student(session)) {
			out.println("<script>");
			out.println("window.alert('未登录或登录失效!') ");
			out.println("window.top.location.href='/'");
			out.println("</script>");
			return "login";
		}
		
		Student st = (Student)session.getAttribute("StudentUser");
		System.out.println(st);
		
		newpassword = md5Util.md5(newpassword);
		
		int result = student_r.updatePassword(newpassword,st.getStu_id());
		if (result > 0) {
			out.println("<script> window.alert('修改成功!') </script>");
		} else {
			out.println("<script> window.alert('修改失败!') </script>");
		}
		return "move";
	}
	
}
