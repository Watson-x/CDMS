/**
*  162013班 第17组
*  课程设计管理系统--过程管理
*  @author: 吴国伟
*  date: 2019-6-4
*  主要功能说明：班级的数据库操作
*/

package webadv.example.pository;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import webadv.example.bean.Clazz;

@Repository
public class ClazzRepository {	
	@Autowired
	private JdbcTemplate jdbcTemplate;	
	
	//获取该教师所带的所有班级
	public List<Clazz> allClazzForTeacher(String tea_id) {
		String sql="select * from clazz where tea_id =  ?";
		RowMapper<Clazz> rowMapper=new BeanPropertyRowMapper<Clazz>(Clazz.class);
		List<Clazz> list = jdbcTemplate.query(sql, rowMapper,tea_id);
		return list;
	}
	
	//获取所有班级
	public List<Clazz> allClazz() {
		String sql="select * from clazz";
		RowMapper<Clazz> rowMapper=new BeanPropertyRowMapper<Clazz>(Clazz.class);
		List<Clazz> list = jdbcTemplate.query(sql, rowMapper);
		return list;
	}
	
	//获取一个班级
	public Clazz getClazz(String class_id) {
		String sql="select * from clazz where class_id = ?";
		RowMapper<Clazz> rowMapper=new BeanPropertyRowMapper<Clazz>(Clazz.class);
		Clazz st = null;
		try{
			st = jdbcTemplate.queryForObject(sql, rowMapper,class_id);
		}catch(EmptyResultDataAccessException e) {
			System.out.println("Clazz---没有数据");
		}
		return st;
	}
	
	//添加一个班级
	public int addClazz(Clazz c) {
		String sql = "insert into clazz(class_id,tea_id,message) values (?,?,?)";
		int result = jdbcTemplate.update(sql,c.getClass_id(),c.getTea_id(),c.getMessage());
		return result;
	}
	
}