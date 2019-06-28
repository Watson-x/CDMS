/**
*  162013班 第17组
*  课程设计管理系统--登录
*  @author: 吴国伟
*  date: 2019-5-30
*  主要功能说明：验证码图片刷新和登录功能
*/

package webadv.example.controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import webadv.example.pository.StudentRepository;
import webadv.example.pository.TeacherRepository;
import webadv.example.util.DateUtil;
import webadv.example.util.MD5Util;
import webadv.example.util.RandomValidateCodeUtil;

@Controller
public class LoginDo{
	
	@Autowired
	private TeacherRepository teacher_r;
	@Autowired
	private StudentRepository student_r;
	@Autowired
	DateUtil du;
	@Autowired
	MD5Util md5Util;
	
	/*
	 * 获取随机验证码图片
	 * */
	@RequestMapping(value = "/getVerify")
	public void getVerify(HttpServletRequest request, HttpServletResponse response) {
		
	    try {
	        response.setContentType("image/jpeg");//设置相应类型,告诉浏览器输出的内容为图片
	        response.setHeader("Pragma", "No-cache");//设置响应头信息，告诉浏览器不要缓存此内容
	        response.setHeader("Cache-Control", "no-cache");
	        response.setDateHeader("Expire", 0);
	        RandomValidateCodeUtil randomValidateCode = new RandomValidateCodeUtil();
	        randomValidateCode.getRandcode(request, response);//输出验证码图片方法
	    } catch (Exception e) {
	        System.out.println("验证码获取失败");
	    }
	}
	
	/**
	 * 账号密码验证码校验
	 */
	@RequestMapping(value = "/checkVerify")
	public String checkVerify(HttpSession session,HttpServletResponse response,String username,String password,String code,String type,Model model) {
		
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		System.out.println("type:"+type);
		System.out.println("验证:"+username+"-"+password+"-"+code);
		
		model.addAttribute("type",type);
		model.addAttribute("username",username);
		model.addAttribute("password", password);
		model.addAttribute("code", code);
		
        //从session中获取随机数
        String code_ = (String) session.getAttribute("RANDOMVALIDATECODEKEY");
        System.out.println("正确验证码："+code_);
    	
        if (code_ == null) {
        	System.out.println("session验证失败");
			out.println("<script> window.alert('session获取验证码失败!') </script>");
            return "login";
        }
        
        if (code_.equals(code)) {  //验证验证码
        	
        	password = md5Util.md5("123");
        	
        	session.setAttribute("weakAndWeakday", du.getRelativeWeakAndWeakday());
        	
        	
        	if("student".equals(type)) { //判断类型为学生
        		session.setAttribute("StudentUser", student_r.getStudent(username));
        		
        		int result = -1;
        		try {
        			result = student_r.checkIdPassword(username, password);
        		}catch(Exception e) {
        			System.out.println("数据库连接失败");
        			result = -1;
        		}
            	if(result == -1) {
            		System.out.println("数据库连接失败");
            		out.println("<script> window.alert('数据库连接失败!') </script>");
            		return "login";
            	}else if(result  == 0){
            		out.println("<script> window.alert('学生账号密码错误!') </script>");
            		System.out.println("学生账号密码错误");
            		return "login";
            	}else{
            		System.out.println("学生登陆成功");
            		return "index_student";
            	}
        	}else if("teacher".equals(type)) {//判断为教师学生
        		session.setAttribute("TeacherUser", teacher_r.getTeacher(username));
        		
        		int result = -1;
        		try{
        			result = teacher_r.checkIdPassword(username, password);
        		}catch(Exception e) {
        			System.out.println("数据库连接失败!");
        			result = -1;
        		}
        		
            	if(result == -1) {
            		out.println("<script> window.alert('数据库连接失败!') </script>");
            		System.out.println("数据库连接失败");
            		return "login";
            	}else if(result  == 0){
            		out.println("<script> window.alert('教师账号密码错误!') </script>");
            		System.out.println("教师账号密码错误");
            		return "login";
            	}else{
            		System.out.println("教师登陆成功");
            		return "index_teacher";
            	}
        	}else { //判断为错误类型
        		System.out.println("登录类型错误");
        		
    			out.println("<script>");
    			out.println("window.alert('登录类型错误!')");
    			out.println("</script>");
        		return "login";
        	}
        } else {
        	out.println("<script> window.alert('验证码错误!') </script>");
        	model.addAttribute("result", 3);
        	System.out.println("验证码错误");
            return "login";
        }
	}
}