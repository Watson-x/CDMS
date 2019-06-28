/**
*  16201323班 第17组
*  课程设计管理系统——过程管理模块
*  @author: 吴国伟
*  date: 2019-6-2
*  主要功能说明：学生提交每周报告，教师给学生报告评分
*/

package webadv.example.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.tomcat.jni.OS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import webadv.example.bean.Report;
import webadv.example.bean.Student;
import webadv.example.bean.Teacher;
import webadv.example.bean.WeeklyReport;
import webadv.example.pository.ReportRepository;
import webadv.example.pository.StudentOrganizeRepository;
import webadv.example.pository.StudentRepository;
import webadv.example.pository.WeeklyReportRepository;
import webadv.example.session.SessionCheck;
import webadv.example.util.DateUtil;


@Controller
public class ReportDo {
	@Autowired
	ReportRepository report_p;
	@Autowired
	SessionCheck sessionCheck;
	@Autowired
	StudentRepository student_p;
	@Autowired
	DateUtil du;
	@Autowired
	WeeklyReportRepository weeklyReport_p;
	@Autowired
	StudentOrganizeRepository studentOrganize_p;
	
	@Value("${file.upload.path}")
    private String path = "";
	
	@RequestMapping(value = "/report_add")
	public String toReportAdd(HttpSession session, HttpServletResponse response) throws IOException {
		System.out.println("report_add------------------------------------");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();

		if (!sessionCheck.sessionCheck_student(session)) {
			out.println("<script>");
			out.println("window.alert('学生未登录或登录失效!') ");
			out.println("window.top.location.href='/'");
			out.println("</script>");
			return "login";
		}
		
		Student st = (Student) session.getAttribute("StudentUser");
		System.out.println(st);
		
		boolean justify = studentOrganize_p.justify(st.getStu_id());
		System.out.println("justify:"+justify);
		if(justify) {//判断该学生是否在队伍中,在队伍中，提交报告
			int weak = du.getRelativeWeak();
			System.out.println("weak:"+weak);
			System.out.println(st.getStu_id()+","+ st.getStu_id()+""+weak);
			boolean flag = weeklyReport_p.haveWeeklyReport(st.getStu_id(), st.getStu_id()+""+weak);
			System.out.println("flag:"+flag);
			if(flag) {//报告已提交
				out.println("<script> window.alert('本周报告已提交!') </script>");
				return "move";
			}else {//报告未提交，进入添加报告页面
				return "report_add";
			}
		}else {//不在队伍中，弹出提示信息
			out.println("<script>");
			out.println("window.alert('您未组队或入队,不能提交报告!') ");
			out.println("</script>");
			return "move";
		}
	}
	
	@RequestMapping(value = "/report_add_do")
	public String reportAdd(HttpSession session, HttpServletResponse response,@RequestParam("file") MultipartFile file, Report report, String write_time)
			throws IOException {
		System.out.println("report_add_do------------------------------------");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();

		if (!sessionCheck.sessionCheck_student(session)) {
			out.println("<script>");
			out.println("window.alert('学生未登录或登录失效!') ");
			out.println("window.top.location.href='/'");
			out.println("</script>");
			return "login";
		}
		
		System.out.println("report:"+report);
		System.out.println("filename:"+file);

		Student st = (Student) session.getAttribute("StudentUser");
		System.out.println(st);
		String[] sp = write_time.split(" ");
		int weak = du.getRelativeWeak(sp[0]);//获取服务器端的时间，再计算周次
		report.setRe_id(st.getStu_id() + weak);
		
		//-----------------文档上传-----------------------
		if (file.isEmpty()) {
			System.out.println("报告的文档为空!");
        }else {
        	String fileName = file.getOriginalFilename();
            System.out.println("fileName:"+fileName);
            String houzui = "";
            int dian = fileName.indexOf(".");
            if(dian != -1) {
            	houzui = fileName.substring(dian);
            }
            
            String absolute = new File(path).getAbsolutePath();
            System.out.println("absolute:"+absolute);
            String saveName = st.getStu_id()+"-"+weak+houzui;//学生id-周次+后缀名
            System.out.println("saveName:"+saveName);
            
            String savePathName = absolute + "/" + saveName;
            System.out.println("savePathName:"+savePathName);
            
            report.setFilename(saveName);
            
            File dest = new File(savePathName);
            if (!dest.getParentFile().exists()) { 
                dest.getParentFile().mkdirs();
            }
            try {
                file.transferTo(dest); // 保存文件
            } catch (Exception e) {
            	out.println("<script> window.alert('文档上传失败!') </script>");
    			return "move";
            }
        }
		//--------------------------------------------
		
		WeeklyReport wr = new WeeklyReport(st.getStu_id(),report.getRe_id());
		int result = -1,result2  =-1;
		try{
			result = report_p.addReport(report);
			result2 = weeklyReport_p.addWeeklyReport(wr);
		}catch(Exception e) {
			out.println("<script> window.alert('本周报告已提交!') </script>");
			return "move";
		}
		
		if (result > 0 && result2 > 0) {
			out.println("<script> window.alert('本周报告提交成功!') </script>");
			return "move";
		} else {
			out.println("<script> window.alert('本周报告提交失败!') </script>");
			return "redirect:report_add"; //重新经过  /report_add URL
		}
	}
	
	
	@RequestMapping(value="/report_look")
	public String report_look(HttpSession session, HttpServletResponse response,Model model) throws IOException {
		System.out.println("/report_look--------------");
		
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();

		if (!sessionCheck.sessionCheck_student(session)) {
			out.println("<script>");
			out.println("window.alert('学生未登录或登录失效!') ");
			out.println("window.top.location.href='/'");
			out.println("</script>");
			return "login";
		}

		Student st = (Student) session.getAttribute("StudentUser");
		System.out.println(st);
		
		List<String> list = weeklyReport_p.allWeaksForStudent(st.getStu_id());
		System.out.println(list);
		
		model.addAttribute("weakList", list);
		return "report_look";
	}
	
	@ResponseBody
	@RequestMapping(value="/report_look_report/{weak}")
	public Report report_look_report(HttpSession session,@PathVariable (value="weak") String weak) {
		System.out.println("report_look_report------------");
		
		Student st = (Student) session.getAttribute("StudentUser");
		System.out.println(st);
		Report re = report_p.getReport(st.getStu_id()+weak);
		System.out.println(re);
		return re;
	}
	
	@RequestMapping(value="/download_teacher/{stu_id}/{weak}")
    public String downloadFile(HttpSession session, HttpServletResponse response,
    		@PathVariable (value="stu_id") String stu_id,@PathVariable (value="weak") String weak) {
		System.out.println("download_teacher------------------");
		
		try {
		
			String fileName = report_p.getFileName(stu_id+weak);
			System.out.println("fileName:"+fileName);
			
	        if (fileName != null) {
	        	String filePathName = new File(path).getAbsolutePath()+"/" +fileName;
	    		System.out.println("filePathName:"+filePathName);
	        	
	            //设置文件路径
	            File file = new File(filePathName);
	            if (file.exists()) {
	                response.setContentType("application/force-download");// 设置强制下载不打开
	                response.addHeader("Content-Disposition", "attachment;fileName=" + fileName);// 设置文件名
	                byte[] buffer = new byte[1024];
	                FileInputStream fis = null;
	                BufferedInputStream bis = null;
	                
	                fis = new FileInputStream(file);
	                bis = new BufferedInputStream(fis);
	                OutputStream os  = response.getOutputStream();
	                int i = bis.read(buffer);
	                while (i != -1) {
	                    os.write(buffer, 0, i);
	                    i = bis.read(buffer);
	                }
	                os.flush();
	                bis.close();
	                fis.close();
	                os.close();
	            }
	            return "move";
	        }else {
				return "404_2";
	        }
		}catch(Exception e) {
			System.out.println("getOutputStream()异常");
		}
		return null;
    }
	
	
	@RequestMapping(value="/download_student/{weak}")
    public String downloadFile_student(HttpSession session, HttpServletResponse response,@PathVariable (value="weak") String weak) {
		System.out.println("download_student------------------");
		
		Student stu = (Student)session.getAttribute("StudentUser");
		String fileName = report_p.getFileName(stu.getStu_id()+weak);
		System.out.println("fileName:"+fileName);
		
        if (fileName != null) {
        	String filePathName = new File(path).getAbsolutePath()+"/" +fileName;
    		System.out.println("filePathName:"+filePathName);
        	
            //设置文件路径
            File file = new File(filePathName);
            if (file.exists()) {
                response.setContentType("application/force-download");// 设置强制下载不打开
                response.addHeader("Content-Disposition", "attachment;fileName=" + fileName);// 设置文件名
                byte[] buffer = new byte[1024];
                FileInputStream fis = null;
                BufferedInputStream bis = null;
                
                try {
                    fis = new FileInputStream(file);
                    bis = new BufferedInputStream(fis);
                    OutputStream os  = response.getOutputStream();
                    int i = bis.read(buffer);
                    while (i != -1) {
                        os.write(buffer, 0, i);
                        
                        i = bis.read(buffer);
                    }
                    os.flush();
                    os.close();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                	try {
	                    if (bis != null) 
	                            bis.close();
	                    if (fis != null) 
	                            fis.close();
                	}catch(Exception e) {
                		e.printStackTrace();
                	}
                }
            }
            return "move"; 
        }else {
			return "404_2";
        }
    }
	
	
	@RequestMapping(value="/report_correct")
	public String reportCorrect(HttpSession session, HttpServletResponse response,Model model) throws IOException {
		System.out.println("reportCorrect-----------");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();

		if (!sessionCheck.sessionCheck_teacher(session)) {
			out.println("<script>");
			out.println("window.alert('教师未登录或登录失效!') ");
			out.println("window.top.location.href='/'");
			out.println("</script>");
			return "login";
		}

		Teacher te = (Teacher) session.getAttribute("TeacherUser");
		System.out.println(te);
		List<Student> list = student_p.allStudentForTeacher(te.getTea_id());
		System.out.println("studentList:"+list);
		
		model.addAttribute("studentList", list);
		
		return "report_correct";
	}
	
	@ResponseBody
	@GetMapping("/report_correct_weaks/{thisStudentId}")
	public List<String> report_correct_weaks(Model model,@PathVariable (value="thisStudentId") String thisStudentId){ 
		System.out.println("report_correct_weaks--------");
		System.out.println("thisStudentId:"+thisStudentId);
		
		model.addAttribute("thisStudentId", thisStudentId);
		List<String> list= weeklyReport_p.getWeaksForStudent(thisStudentId);
		System.out.println("list:"+list);
		return list;
	}
	
	@ResponseBody
	@GetMapping("/report_correct_report/{thisStudentId}/{weak}")
	public Report report_correct_report(HttpSession session,HttpServletResponse response,Model model,
			@PathVariable (value="thisStudentId") String thisStudentId,@PathVariable (value="weak") String weak) throws IOException {
		System.out.println("report_correct_report------------");
		
		System.out.println("thisStudentId+weak:"+(thisStudentId+weak));
		Report re = report_p.getReport(thisStudentId+weak);
		
		if(re == null) {
			System.out.println("没有该周报告!!!");
		}else {
			System.out.println("report:"+re);
			return re;
		}
		return null;
	}
	
	@ResponseBody
	@RequestMapping(value = "/updateGrade")
	public void grade(HttpSession session, HttpServletResponse response,String studentid,String nowWeak,int grade) throws IOException {
		System.out.println("updateGrade------------");
		
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();

		if (!sessionCheck.sessionCheck_teacher(session)) {
			out.println("<script>");
			out.println("window.alert('教师未登录或登录失效!') ");
			out.println("window.top.location.href='/'");
			out.println("</script>");
			return ;
		}
		
		WeeklyReport wr = new WeeklyReport(studentid,studentid+nowWeak);
		System.out.println("wr:"+wr);
		
		int result = report_p.updateReportGrade(studentid+nowWeak, grade);//修改report成绩
		
		System.out.println(result);
		if(result > 0 ) {
			out.println("<script>");
			out.println("window.alert('辛苦了,批阅成功!') ");
			out.println("window.location.href='/report_correct'");
			out.println("</script>");
			return ;
		}else {
			out.println("<script>");
			out.println("window.alert('批阅失败!') ");
			out.println("window.location.href='/report_correct'");
			out.println("</script>");
			return ;
		}
	}
}