/**
*  162013班 第17组
*  课程设计管理系统--学生队伍
*  @author: 王迅
*  date: 2019-6-2
*  主要功能说明：关于学生队伍的数据库操作
*/

package webadv.example.pository;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import webadv.example.bean.Organize;

@Repository
public class OrganizeRepository {	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	//获取所有队伍
	public List<Organize> allOrganize() {
		String sql="select * from organize";
		RowMapper<Organize> rowMapper=new BeanPropertyRowMapper<Organize>(Organize.class);
		List<Organize> list = jdbcTemplate.query(sql, rowMapper);
		return list;
	}
	
	//获取一个队伍
	public Organize getOrganize(String org_id) {
		String sql="select * from organize where org_id = ?";
		RowMapper<Organize> rowMapper=new BeanPropertyRowMapper<Organize>(Organize.class);
		Organize or = null;
		try{
			or = jdbcTemplate.queryForObject(sql, rowMapper,org_id);
		}catch(EmptyResultDataAccessException e) {
			System.out.println("Organize---没有数据");
		}
		return or;
	}
	
	
	//判断是否建队
	public boolean justify(String stu_id) {
		String sql="select count(*) from organize where stu_id = ?";
		int i = 0;
		try{
			i = jdbcTemplate.queryForObject(sql, Integer.class,stu_id);
		}catch(EmptyResultDataAccessException e) {
			System.out.println("Organize---没有数据");
		}
		if(i>0){
			return true;
		}else
			return false;
	}
		
	//获取该学生的组id
	public String getOrganizeIdForStudent(String stu_id) {
		String sql="select org_id from organize where stu_id = ?";
		String str = null;
		try{
			str = jdbcTemplate.queryForObject(sql, String.class,stu_id);
		}catch(EmptyResultDataAccessException e) {
			System.out.println("Organize---没有数据");
		}
		return str;
	}
	
	//添加一个队伍
	public int addOrganize(Organize or) {
		String sql = "insert into organize(cd_id,stu_id,message) values (?,?,?)";
		int result = jdbcTemplate.update(sql,or.getCd_id(),or.getStu_id(),or.getMessage());
		return result;
	}
	
	//删除一个队伍
	public int deleteOrganize(String org_id) {
		String sql =  "delete from organize where org_id = ?";
		int result = jdbcTemplate.update(sql,org_id);
		return result;
	}
	
	//修改一个队伍
	public int updateOrganize(Organize or) {
		String sql = "update organize set cd_id = ?,stu_id = ?,message = ? where org_id = ?";
		int result = jdbcTemplate.update(sql,or.getCd_id(),or.getStu_id(),or.getMessage(),or.getOrg_id());
		return result;
	}
}