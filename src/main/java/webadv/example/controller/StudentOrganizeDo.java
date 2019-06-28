/**
*  162013班 第17组
*  课程设计管理系统——队伍管理模块
*  @author: 王迅
*  date: 2019-6-16
*  主要功能说明：学生创建队伍，学生加入队伍
*/

package webadv.example.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import webadv.example.util.DateUtil;
import webadv.example.bean.NewOrganize;
import webadv.example.bean.Student;
import webadv.example.pository.CourseDesignRepository;
import webadv.example.pository.OrganizeRepository;
import webadv.example.pository.StudentOrganizeRepository;
import webadv.example.session.SessionCheck;


@Controller
public class StudentOrganizeDo {
	@Autowired
	SessionCheck sessionCheck;
	@Autowired
	OrganizeRepository organize_o;
	@Autowired
	StudentOrganizeRepository studentOrganize_s;
	@Autowired
	CourseDesignRepository courseDesign_c;
	@Autowired
	DateUtil du;
	
	
	
	@RequestMapping(value="/organize_join")
	public String orgainzeJoin(HttpSession session, HttpServletResponse response,Model model) throws IOException {
		System.out.println("organize_join-----------");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		if (!sessionCheck.sessionCheck_student(session)) {
			System.out.println(1);
			out.println("<script>");
			out.println("window.alert('学生未登录或登录失效!') ");
			out.println("window.top.location.href='/'");
			out.println("</script>");
			return "login";
		}
		Student st = (Student) session.getAttribute("StudentUser");
		System.out.println(st);
		
		if(studentOrganize_s.justify(st.getStu_id())) {
			out.println("<script>");
			out.println("window.alert('您已加入队伍!') ");
			out.println("</script>");
			return "move";
		}
		
		List<NewOrganize> list = studentOrganize_s.allNewStudentOrganize();
		System.out.println("organizeList:"+list);
		
		model.addAttribute("newOrganizeList", list);
		
		return "organize_join";
	}
	
	@ResponseBody
	@GetMapping("/organize_join_org_id/{org_id}")
	public String getContent(HttpSession session,HttpServletResponse response,Model model,
			@PathVariable (value="org_id") String org_id) throws IOException {
		System.out.println("organize_join_org_id------------");
		
		System.out.println("thisOrganize:"+(org_id));
		
		String content = courseDesign_c.getContent(org_id);
		
		if(content == null) {
			System.out.println("没有该队伍!!!");
		}else {
			System.out.println("content:"+content);
			return content;
		}
		return null;
	}
	
	@RequestMapping(value = "/organize_join_do")
	public String courseDesignAdd(HttpSession session, HttpServletResponse response,String org_id)
			throws IOException {
		System.out.println("organize_join_do------------------------------------");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();

		
		if (!sessionCheck.sessionCheck_student(session)) {
			System.out.println("go");
			out.println("<script>");
			out.println("window.alert('学生未登录或登录失效!') ");
			out.println("window.top.location.href='/'");
			out.println("</script>");
			System.out.println("to");
			return "login";
		}

		Student st = (Student) session.getAttribute("StudentUser");
		System.out.println(st);
		
		int result = studentOrganize_s.addStudentOrganize(st.getStu_id(),org_id,-1);
		
		if (result > 0 ) {
			out.println("<script> window.alert('队伍加入成功!') </script>");
		} else {
			out.println("<script> window.alert('队伍加入失败!') </script>");
		}

		return "move";
	}
	
}
