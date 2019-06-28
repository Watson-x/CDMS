/**
*  162013班 第17组
*  课程设计管理系统--过程管理
*  @author: 吴国伟
*  date: 2019-6-4
*  主要功能说明：教师的数据库操作
*/

package webadv.example.pository;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import webadv.example.bean.Teacher;

@Repository
public class TeacherRepository {	
	@Autowired
	private JdbcTemplate jdbcTemplate;	
	
	//获取所有教师
	public List<Teacher> allTeacher() {
		String sql="select * from teacher";
		RowMapper<Teacher> rowMapper=new BeanPropertyRowMapper<Teacher>(Teacher.class);
		List<Teacher> list = jdbcTemplate.query(sql, rowMapper);
		return list;
	}
	
	//获取一个教师
	public Teacher getTeacher(String tea_id) {
		String sql="select * from teacher where tea_id = ?";
		RowMapper<Teacher> rowMapper=new BeanPropertyRowMapper<Teacher>(Teacher.class);
		Teacher te = null;
		try{
			te = jdbcTemplate.queryForObject(sql, rowMapper,tea_id);
		}catch(EmptyResultDataAccessException e) {
			System.out.println("teacher---没有数据");
		}
		return te;
	}
	
	//添加一个教师
	public int addTeacher(Teacher te) {
		String sql = "insert into teacher(tea_id,name,sex,age,contact,password) values (?,?,?,?,?,?)";
		int result = jdbcTemplate.update(sql,te.getTea_id(),te.getName(),te.getSex(),te.getAge(),te.getContact(),te.getPassword());
		return result;
	}
	
	//删除一个教师
	public int deleteTeacher(String tea_id) {
		String sql =  "delete from teacher where tea_id = ?";
		int result = jdbcTemplate.update(sql,tea_id);
		return result;
	}
	
	//修改一个教师
	public int updateTeacher(Teacher te) {
		String sql = "update teacher set name = ?,sex = ?,age = ?,contact = ? where tea_id = ?";
		int result = jdbcTemplate.update(sql,te.getName(),te.getSex(),te.getAge(),te.getContact(),te.getTea_id());
		return result;
	}

	//检查账号密码
	public int checkIdPassword(String tea_id, String password) {
		String sql="select * from teacher where tea_id = ? and password = ?";
		RowMapper<Teacher> rowMapper=new BeanPropertyRowMapper<Teacher>(Teacher.class);
		List<Teacher> list = jdbcTemplate.query(sql, rowMapper,tea_id,password);
		if(list.size() == 0)
			return 0;
		else
			return 1;
	}
	
	public int updatePassword(String password ,String tea_id) {
		// TODO Auto-generated method stub
		String sql = "update teacher set password = ?  where tea_id = ?";
		int result = jdbcTemplate.update(sql,password,tea_id);
		return result;
	}

}