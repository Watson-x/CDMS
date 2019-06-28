/**
*  162013班 第17组
*  课程设计管理系统--过程管理
*  @author: 吴国伟
*  date: 2019-6-4
*  主要功能说明：每周汇报的数据库操作
*/

package webadv.example.pository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import webadv.example.bean.WeeklyReport;

@Repository
public class WeeklyReportRepository {	
	@Autowired
	private JdbcTemplate jdbcTemplate;	
	
	//获取学生所提交的报告的周次
	public List<String> allWeaksForStudent(String stu_id) {
		String sql="select re_id from weekly_report where stu_id = ?";
		List<String> list = jdbcTemplate.queryForList(sql, String.class,stu_id);
		for(int i = 0;i < list.size();i ++) {
			list.set(i,list.get(i).substring(8));
		}
		return list;
	}
	
	//获取该学生的所有报告id
	public List<String> getWeaksForStudent(String stu_id){
		String sql = "select re_id from weekly_report where stu_id = ?";
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql,stu_id);
		
		List<String> li = new ArrayList<String>();
		for(Map<String,Object> map : list) {
			for(String str : map.keySet()) {
				String s = (String)map.get(str);
				li.add(s.substring(8));
			}
		}
		
		return li;
	}
	
	//获取该学生的所有每周汇报
	public List<WeeklyReport> allWeeklyReportForStudent(String stu_id) {
		String sql="select * from weekly_report where stu_id = ?";
		RowMapper<WeeklyReport> rowMapper=new BeanPropertyRowMapper<WeeklyReport>(WeeklyReport.class);
		List<WeeklyReport> list = jdbcTemplate.query(sql, rowMapper,stu_id);
		return list;
	}
	
	//判断该汇报是否存在
	public boolean haveWeeklyReport(String stu_id,String re_id) {
		String sql="select * from weekly_report where stu_id = ? and re_id = ?";
		RowMapper<WeeklyReport> rowMapper=new BeanPropertyRowMapper<WeeklyReport>(WeeklyReport.class);
		WeeklyReport wr = null;
		try{
			wr = jdbcTemplate.queryForObject(sql, rowMapper,stu_id,re_id);
		}catch(EmptyResultDataAccessException e) {
			System.out.println(stu_id+":"+re_id+"---没有数据");
		}
		if(wr == null)
			return false;
		return true;
	}
	
	//添加一个每周汇报
	public int addWeeklyReport(WeeklyReport wr) {
		String sql = "insert into weekly_report(stu_id,re_id) values (?,?)";
		int result = jdbcTemplate.update(sql,wr.getStu_id(),wr.getRe_id());
		return result;
	}
	
	//删除一个每周汇报
	public int deleteWeeklyReport(String stu_id,String re_id) {
		String sql =  "delete from weekly_report where stu_id = ? and re_id = ?";
		int result = jdbcTemplate.update(sql,stu_id,re_id);
		return result;
	}
	
}