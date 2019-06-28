/**
*  162013班 第17组
*  课程设计管理系统--课设选题
*  @author: 王迅
*  date: 2019-6-2
*  主要功能说明：关于课设的数据库操作
*/

package webadv.example.pository;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import webadv.example.bean.CourseDesign;

@Repository
public class CourseDesignRepository {	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	//获取所有汇报
	public List<CourseDesign> allCourseDesign() {
		String sql="select * from course_design";
		RowMapper<CourseDesign> rowMapper=new BeanPropertyRowMapper<CourseDesign>(CourseDesign.class);
		List<CourseDesign> list = jdbcTemplate.query(sql, rowMapper);
		return list;
	}
	
	//获取一个汇报
	public CourseDesign getCourseDesign(String cd_id) {
		String sql="select * from course_design where cd_id = ?";
		RowMapper<CourseDesign> rowMapper=new BeanPropertyRowMapper<CourseDesign>(CourseDesign.class);
		CourseDesign cd = null;
		try{
			cd = jdbcTemplate.queryForObject(sql, rowMapper,cd_id);
		}catch(EmptyResultDataAccessException e) {
			System.out.println("CouseDesign---没有数据");
		}
		return cd;
	}
	
	//添加一个课设
	public int addCourseDesign(CourseDesign cd) {
		String sql = "insert into course_design(title,content) values (?,?)";
		int result = jdbcTemplate.update(sql,cd.getTitle(),cd.getContent());
		return result;
	}
	
	//删除一个课设
	public int deleteCourseDesign(String cd_id) {
		String sql =  "delete from course_design where cd_id = ?";
		int result = jdbcTemplate.update(sql,cd_id);
		return result;
	}
	
	//修改一个课设
	public int updateCourseDesign(CourseDesign cd) {
		String sql = "update course_design set title = ?,content = ? where cd_id = ?";
		int result = jdbcTemplate.update(sql,cd.getTitle(),cd.getContent(),cd.getCd_id());
		return result;
	}
	//获取课设内容
	public String getContent(String org_id) {
		String sql = "select content from organize o,course_design cd where o.cd_id=cd.cd_id and o.org_id=?";
		String result = jdbcTemplate.queryForObject(sql,String.class,org_id);
		return result;
	}
			
		
}