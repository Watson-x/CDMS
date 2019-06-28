/**
*  16201323班 第17组
*  课程设计管理系统——过程管理模块
*  @author: 吴国伟
*  date: 2019-6-2
*  主要功能说明：教师计算总评,学生组内互评
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import webadv.example.bean.Clazz;
import webadv.example.bean.NewStudent;
import webadv.example.bean.Report;
import webadv.example.bean.Student;
import webadv.example.bean.StudentMsg;
import webadv.example.bean.Teacher;
import webadv.example.pository.ClazzRepository;
import webadv.example.pository.ReportRepository;
import webadv.example.pository.StudentOrganizeRepository;
import webadv.example.pository.StudentRepository;
import webadv.example.session.SessionCheck;
import webadv.example.util.DateUtil;
import webadv.example.util.PropertyUtil;

@Controller
public class GradeDo {
	@Autowired
	ReportRepository report_p;
	@Autowired
	SessionCheck sessionCheck;
	@Autowired
	ClazzRepository clazz_p;
	@Autowired
	StudentRepository student_p;
	@Autowired
	StudentOrganizeRepository studentOrganize_p;
	@Autowired
	PropertyUtil property_p;
	@Autowired
	DateUtil du;
	
	//----------------教师查看一个班级的学生总分---------------------------------------------------------------
	
	//查看分数页面，返回该教师所带的班级
	@RequestMapping(value = "/lookAllGrade")
	public String lookAllGrade(HttpSession session, HttpServletResponse response,Model model) throws IOException {
		System.out.println("lookAllGrade----------------");
		
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();

		if (!sessionCheck.sessionCheck_teacher(session)) {
			out.println("<script>");
			out.println("window.alert('教师未登录或登录失效!') ");
			out.println("window.top.location.href='/'");
			out.println("</script>");
			return "login";
		}
		
		Teacher te = (Teacher)session.getAttribute("TeacherUser");
		System.out.println(te);
		
		List<Clazz> list = clazz_p.allClazzForTeacher(te.getTea_id());
		System.out.println(list);
		
		model.addAttribute("classList", list);
		
		return "look_all_grade";
	}
	//获取该班级的所有学生
	@ResponseBody
	@RequestMapping(value="/all_grade_BestNewStudent/{class_id}")
	public List<StudentMsg> all_grade_StudentMsg(@PathVariable (value="class_id") String class_id){
		System.out.println("all_grade_BestNewStudent-------------");
		
		List<StudentMsg> list = student_p.allStudentMsgForClass(class_id);
		System.out.println(list); 
		
		return list;
	}
	
	//----------------教师查看学生互评情况----------------------------------------------
	//查看分数页面，返回该教师所带的班级
	@RequestMapping(value = "/lookOrganizeGrade")
	public String lookOrganizeGrade(HttpSession session, HttpServletResponse response,Model model) throws IOException {
		System.out.println("lookOrganizeGrade----------------");
		
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();

		if (!sessionCheck.sessionCheck_teacher(session)) {
			out.println("<script>");
			out.println("window.alert('教师未登录或登录失效!') ");
			out.println("window.top.location.href='/'");
			out.println("</script>");
			return "login";
		}
		
		Teacher te = (Teacher)session.getAttribute("TeacherUser");
		System.out.println(te);
		
		List<Clazz> list = clazz_p.allClazzForTeacher(te.getTea_id());
		System.out.println(list);
		
		model.addAttribute("classList", list);
		
		return "look_organize_grade";
	}
	//获取该班级的所有学生
	@ResponseBody
	@RequestMapping(value="/organize_allStudent_teacher/{class_id}")
	public List<Student> organize_allStudent_teacher(@PathVariable (value="class_id") String class_id){
		System.out.println("allStudent_teacher-------------");
		
		List<Student> list = student_p.allStudentForClass(class_id);
		System.out.println(list);
		return list;
	}
	//获取该班级学生的所有提交的报告
	@ResponseBody
	@RequestMapping(value="/organize_allNewStudent_student/{stu_id}")
	public NewStudent organize_allNewStudent_student(HttpSession session,@PathVariable (value="stu_id") String stu_id){
		System.out.println("organize_allNewStudent_student-------------");
		
		NewStudent n = student_p.allNewStudentForStudent(stu_id);
		System.out.println(n);
		return n;
	}
	
	//---------------教师查看学生报告评分---------------------------------------
	
	//判断该教师的学生的汇报是否都批改完，如果没有批改完则调到批改页面，如果批改完了则显示分数页面
	@ResponseBody
	@RequestMapping(value = "/checkGrade")
	public void lookGrade(HttpSession session, HttpServletResponse response) throws IOException {
		System.out.println("lookGrade--------------");
		
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();

		if (!sessionCheck.sessionCheck_teacher(session)) {
			out.println("<script>");
			out.println("window.alert('教师未登录或登录失效!') ");
			out.println("window.top.location.href='/'");
			out.println("</script>");
			return ;
		}
		
		Teacher te = (Teacher)session.getAttribute("TeacherUser");
		System.out.println(te);
		
		int result = report_p.unmarkedForReportToStudentOfTeacher(te.getTea_id());//该教师所带学生提交过的报告中未批阅的分数
		System.out.println("result:"+result);
		
		if(result > 0) {//有未批阅的汇报,跳转到报告批阅页面
			System.out.println("有未批阅的汇报,跳转到报告批阅页面");
			out.println("<script>");
			out.println("var op = window.confirm('还有报告未批阅,是否开始批阅?') ");
			out.println("if(op == true)");
			out.println("	window.location.href='/report_correct'");
			out.println("else");
			out.println("	window.location.href='/lookGrade'");
			out.println("</script>");
			return ;
		}else { //没有未批阅的报告，跳转到查看总评页面
			System.out.println("没有未批阅的报告，跳转到查看总评页面");
			out.println("<script>");
			out.println("window.confirm('批阅完毕,开始查看分数!') ");
			out.println("window.location.href='/lookGrade'");
			out.println("</script>");
			return ;
		}
	}
	
	//查看分数页面，返回该教师所带的班级
	@RequestMapping(value = "/lookGrade")
	public String lookGrade(HttpSession session, HttpServletResponse response,Model model) throws IOException {
		System.out.println("lookGrade----------------");
		
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();

		if (!sessionCheck.sessionCheck_teacher(session)) {
			out.println("<script>");
			out.println("window.alert('教师未登录或登录失效!') ");
			out.println("window.top.location.href='/'");
			out.println("</script>");
			return "login";
		}
		
		Teacher te = (Teacher)session.getAttribute("TeacherUser");
		System.out.println(te);
		
		List<Clazz> list = clazz_p.allClazzForTeacher(te.getTea_id());
		System.out.println(list);
		
		model.addAttribute("classList", list);
		int thisWeak = du.getThisWeak();
		System.out.println("本周周次:"+thisWeak);
		
		model.addAttribute("thisWeak", thisWeak);
		
		boolean flag;
		if(thisWeak == list.size()) {
			flag = true;
		}else {
			flag = false;
		}
		System.out.println("flag:"+flag);
		model.addAttribute("flag", flag);
		
		return "look_grade";
	}
	
	//获取该班级的所有学生
	@ResponseBody
	@RequestMapping(value="/allStudent_teacher/{class_id}")
	public List<Student> allStudent_teacher(@PathVariable (value="class_id") String class_id){
		System.out.println("allStudent_teacher-------------");
		
		List<Student> list = student_p.allStudentForClass(class_id);
		System.out.println(list);
		return list;
	}
	
	//获取该班级学生的所有提交的报告
	@ResponseBody
	@RequestMapping(value="/allReport_student/{stu_id}")
	public List<Report> allReport_student(@PathVariable (value="stu_id") String stu_id){
		System.out.println("allReport_student-------------");
		
		List<Report> list = report_p.allReportForStudent(stu_id);
		System.out.println(list);
		return list;
	}
	
	
	//------------------------学生组内评分-----------------------------------------
	
	//跳转到组内评分页面
	@RequestMapping(value = "/gradeForOrganize")
	public String gradeForOrganize(HttpSession session, HttpServletResponse response,Model model) throws IOException {
		System.out.println("gradeForOrganize----------------");
		
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();

		if (!sessionCheck.sessionCheck_student(session)) {
			out.println("<script>");
			out.println("window.alert('学生未登录或登录失效!') ");
			out.println("window.top.location.href='/'");
			out.println("</script>");
			return "login";
		}
		
		if(property_p.isGrade()) { //去配置文件查看grade参数值，如果为true是可以开始评分
			Student st = (Student)session.getAttribute("StudentUser");
			System.out.println(st);
			List<NewStudent> list = student_p.allNewStudentForOrganize(st.getStu_id());//获取这个学生所在组的学生（不包括自己）
			System.out.println(list);
			
			model.addAttribute("newStudentList", list);
			return "organize_grade";
		}else {//为false不可以开始
			out.println("<script>");
			out.println("window.alert('教师未开启组内互评!') ");
			out.println("</script>");
			return "move";
		}
	}
	
	//学生给组内学生评分
	@ResponseBody
	@RequestMapping(value = "/gradeDo/{stu_id}/{grade}")
	public void gradeDo(HttpSession session, HttpServletResponse response,
			@PathVariable (value="stu_id") String stu_id,@PathVariable (value="grade") int grade) throws IOException {
		System.out.println("gradeDo---------");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();

		if (!sessionCheck.sessionCheck_student(session)) {
			out.println("<script>");
			out.println("window.alert('学生未登录或登录失效!') ");
			out.println("window.top.location.href='/'");
			out.println("</script>");
			return ;
		}
		int stu_grade = studentOrganize_p.getGradeForStudent(stu_id);
		if(stu_grade >= 0) {
			grade = (grade+stu_grade)/2;
		}
		
		int result = studentOrganize_p.organizeGrade(stu_id,grade);
		
		if(result > 0 ) {
			out.println("<script>");
			out.println("window.alert('评分成功!') ");
			out.println("window.location.href='/gradeForOrganize'");
			out.println("</script>");
		}else {
			out.println("<script>");
			out.println("window.alert('评分成功!')");
			out.println("window.location.href='/gradeForOrganize'");
			out.println("</script>");
		}
	}
	
	//--------------------配置文件grade参数开闭设置-----------------------------------------------------------
	
	//教师打开学生互评
	@RequestMapping(value="/openGrade")
	public String openGrade(HttpSession session, HttpServletResponse response) throws IOException {
		System.out.println("openGrade---------");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();

		if (!sessionCheck.sessionCheck_teacher(session)) {
			out.println("<script>");
			out.println("window.alert('教师未登录或登录失效!') ");
			out.println("window.top.location.href='/'");
			out.println("</script>");
			return "login";
		}
		boolean flag = property_p.isGrade();
		System.out.println("grade:"+flag);
		if(flag) {
			out.println("<script>");
			out.println("window.alert('学生互评开启中!') ");
			out.println("</script>");
			return "move";
		}else {
			property_p.openGrade();
			out.println("<script>");
			out.println("window.alert('已开启学生互评!') ");
			out.println("</script>");
			return "move";
		}
	}
	
	//教师关闭学生互评
	@RequestMapping(value="/closeGrade")
	public String closeGrade(HttpSession session, HttpServletResponse response) throws IOException {
		System.out.println("closeGrade---------");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();

		if (!sessionCheck.sessionCheck_teacher(session)) {
			out.println("<script>");
			out.println("window.alert('教师未登录或登录失效!') ");
			out.println("window.top.location.href='/'");
			out.println("</script>");
			return "login";
		}
		
		boolean flag = property_p.isGrade();
		System.out.println("grade:"+flag);
		if(!flag) {
			out.println("<script>");
			out.println("window.alert('学生互评关闭中!') ");
			out.println("</script>");
			return "move";
		}else {
			property_p.openGrade();
			out.println("<script>");
			out.println("window.alert('已关闭学生互评!') ");
			out.println("</script>");
			return "move";
		}
	}
}
