/**
*  162013班 第17组
*  课程设计管理系统--过程管理
*  @author: 吴国伟
*  date: 2019-6-4
*  主要功能说明：学生分组的数据库操作
*/

package webadv.example.pository;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import webadv.example.bean.NewOrganize;
import webadv.example.bean.StudentOrganize;

@Repository
public class StudentOrganizeRepository {	
	@Autowired
	private JdbcTemplate jdbcTemplate;	
	
	//获取学生的组内评分grade
	public int getGradeForStudent(String stu_id) {
		String sql = "select grade from student_organize where stu_id=?";
		int grade = jdbcTemplate.queryForObject(sql,Integer.class,stu_id);
		return grade;
	}
	
	//获取所有学生分组
	public List<StudentOrganize> allStudentForOrganize() {
		String sql="select * from student_organize";
		RowMapper<StudentOrganize> rowMapper=new BeanPropertyRowMapper<StudentOrganize>(StudentOrganize.class);
		List<StudentOrganize> list = jdbcTemplate.query(sql, rowMapper);
		return list;
	}
	
	//获取所有学生分组
	public List<StudentOrganize> allStudentOrganize() {
		String sql="select * from student_organize";
		RowMapper<StudentOrganize> rowMapper=new BeanPropertyRowMapper<StudentOrganize>(StudentOrganize.class);
		List<StudentOrganize> list = jdbcTemplate.query(sql, rowMapper);
		return list;
	}
	
	
	//添加一个学生分组
	public int addStudentOrganize(StudentOrganize so) {
		String sql = "insert into student_organize(stu_id,org_id) values (?,?)";
		int result = jdbcTemplate.update(sql,so.getStu_id(),so.getOrg_id());
		return result;
	}
	
	//修改学生的组内评分
	public int organizeGrade(String stu_id,int grade) {
		String sql = "update student_organize set grade = ? where stu_id  = ?";
		int result = jdbcTemplate.update(sql,grade,stu_id);
		return result;
	}
	
	//获取该学生同组的所有学生的id(不包括自己)
	public List<String> allStudentIdToStudentForOrganize(String stu_id) {
		String sql="select stu_id from student_organize where org_id in("
				+ "select org_id from student_organize where stu_id = ?) and stu_id!=?";
		List<String> list = jdbcTemplate.queryForList(sql, String.class,stu_id,stu_id);
		return list;
	}
	
	
	//获取
	public List<NewOrganize> allNewStudentOrganize() {
		String sql="select s.name,s.stu_id,o.org_id,cd.cd_id,cd.title,cd.content,o.message  "
				+ "from student s,course_design cd,organize o where o.stu_id=s.stu_id and o.cd_id=cd.cd_id";
		RowMapper<NewOrganize> rowMapper=new BeanPropertyRowMapper<NewOrganize>(NewOrganize.class);
		List<NewOrganize> list = jdbcTemplate.query(sql, rowMapper);
		return list;
	}
	
	
	
	//添加一个学生分组
	public int addStudentOrganize(String stu_id,String org_id,int grade) {
		String sql = "insert into student_organize(stu_id,org_id,grade) values (?,?,?)";
		int result = jdbcTemplate.update(sql,stu_id,org_id,grade);
		return result;
	}
	
	//判断是否加队或创建队伍
	public boolean justify(String stu_id) {
		String sql="select count(*) from student_organize where stu_id = ?";
		int i = 0;
		try{
			i = jdbcTemplate.queryForObject(sql, Integer.class,stu_id);
		}catch(EmptyResultDataAccessException e) {
			System.out.println("studentOrganize---没有数据");
		}
		if(i>0){
			return true;
		}else
			return false;
	}
}