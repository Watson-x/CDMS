/**
*  16201323班 第17组
*  课程设计管理系统——过程管理模块
*  @author: 吴国伟
*  date: 2019-6-2
*  主要功能说明：报告的数据库操作
*/


package webadv.example.pository;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import webadv.example.bean.Report;

@Repository
public class ReportRepository {	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	//获取所有汇报
	public List<Report> allReport() {
		String sql="select * from report";
		RowMapper<Report> rowMapper=new BeanPropertyRowMapper<Report>(Report.class);
		List<Report> list = jdbcTemplate.query(sql, rowMapper);
		return list;
	}
	//获取report的文件路径和文件名
	public String getFileName(String re_id) {
		String sql = "select filename from report where re_id = ?";
		String filename = null;
		try{
			filename = jdbcTemplate.queryForObject(sql, String.class,re_id);
		}catch(EmptyResultDataAccessException e) {
			System.out.println("fileName---没有数据");
		}
		return filename;
	}
	
	//获取所有汇报
	public List<Report> allReportForStudent(String stu_id) {
		String sql="select * from report where re_id in(select re_id from weekly_report where stu_id = ?)";
		RowMapper<Report> rowMapper=new BeanPropertyRowMapper<Report>(Report.class);
		List<Report> list = jdbcTemplate.query(sql, rowMapper,stu_id);
		return list;
	}
	
	//获取一个汇报
	public Report getReport(String re_id) {
		String sql="select * from report where re_id = ?";
		RowMapper<Report> rowMapper=new BeanPropertyRowMapper<Report>(Report.class);
		Report re = null;
		try{
			re = jdbcTemplate.queryForObject(sql, rowMapper,re_id);
		}catch(EmptyResultDataAccessException e) {
			System.out.println("Report---没有数据");
		}
		return re;
	}
	
	//添加一个教师
	public int addReport(Report re) {
		String sql = "insert into report(re_id,title,content,filename,grade) values (?,?,?,?,?)";
		int result = jdbcTemplate.update(sql,re.getRe_id(),re.getTitle(),re.getContent(),re.getFilename(),re.getGrade());
		return result;
	}
	
	//删除一个汇报
	public int deleteReport(String re_id) {
		String sql =  "delete from report where re_id = ?";
		int result = jdbcTemplate.update(sql,re_id);
		return result;
	}
	
	//修改一个汇报
	public int updateReport(Report re) {
		String sql = "update Report set title = ?,content = ?,filename = ?,grade = ? where re_id = ?";
		int result = jdbcTemplate.update(sql,re.getTitle(),re.getContent(),re.getFilename(),re.getGrade(),re.getRe_id());
		return result;
	}
	
	//修改一个汇报成绩
	public int updateReportGrade(String re_id,int grade) {
		String sql = "update Report set grade = ? where re_id = ?";
		int result = jdbcTemplate.update(sql,grade,re_id);
		return result;
	}
	
	//返回该教师没有修改的学生的每周报告的个数
	public int unmarkedForReportToStudentOfTeacher(String tea_id) {
		//sql语句查询
		String sql="select count(*) from report where re_id in"
				+ "(select re_id from weekly_report where stu_id in"
				+ "(select s.stu_id from teacher t,student s,clazz c "
				+ "where t.tea_id=c.tea_id and s.class_id=c.class_id and t.tea_id= ?)) and grade=-1";
		
		int	result = jdbcTemplate.queryForObject(sql, Integer.class ,tea_id);
		return result;
	}
}