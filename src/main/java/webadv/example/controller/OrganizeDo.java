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
import webadv.example.bean.CourseDesign;
import webadv.example.bean.Organize;
import webadv.example.bean.Student;
import webadv.example.pository.CourseDesignRepository;
import webadv.example.pository.OrganizeRepository;
import webadv.example.pository.StudentOrganizeRepository;
import webadv.example.session.SessionCheck;


@Controller
public class OrganizeDo {
	@Autowired
	SessionCheck sessionCheck;
	@Autowired
	CourseDesignRepository courseDesign_c;
	@Autowired
	OrganizeRepository organize_o;
	@Autowired
	StudentOrganizeRepository studentOrganize_s;
	@Autowired
	DateUtil du;
	
	
	
	@RequestMapping(value="/organize_create")
	public String reportCorrect(HttpSession session, HttpServletResponse response,Model model) throws IOException {
		System.out.println("organize_create-----------");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		System.out.println(3);
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
			out.println("window.alert('您已在队伍中,不能创建队伍!') ");
			out.println("</script>");
			return "move";
		}
		
		List<CourseDesign> list = courseDesign_c.allCourseDesign();
		System.out.println("courseDesignList:"+list);
		
		model.addAttribute("courseDesignList", list);
		
		return "organize_create";
	}
	

	
	@ResponseBody
	@GetMapping("/organize_create_cd_id/{cd_id}")
	public CourseDesign CourseDesign_cd(HttpSession session,HttpServletResponse response,Model model,
			@PathVariable (value="cd_id") String cd_id) throws IOException {
		System.out.println("organize_create_cd_id------------");
		
		System.out.println("thisCourseDesign:"+(cd_id));
		CourseDesign cd = courseDesign_c.getCourseDesign(cd_id);
		
		if(cd == null) {
			System.out.println("没有该课设!!!");
		}else {
			System.out.println("courseDesign:"+cd);
			return cd;
		}
		return null;
	}
	
	@RequestMapping(value = "/organize_create_do")
	public String courseDesignAdd(HttpSession session, HttpServletResponse response, Organize organize, String write_time)
			throws IOException {
		System.out.println("organize_create_do------------------------------------");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();

		System.out.println(organize);
		
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
		
		organize.setStu_id(st.getStu_id());
		System.out.println(organize);
		int result = organize_o.addOrganize(organize);//添加组
		
		String orgId = organize_o.getOrganizeIdForStudent(st.getStu_id());
		System.out.println(orgId);
		
		int result2 = studentOrganize_s.addStudentOrganize(st.getStu_id(),orgId,-1);
		
		if (result > 0 && result2 > 0) {
			out.println("<script> window.alert('队伍创建成功!') </script>");
		} else {
			out.println("<script> window.alert('队伍创建失败!') </script>");
		}

		return "move";
	}
	
}
