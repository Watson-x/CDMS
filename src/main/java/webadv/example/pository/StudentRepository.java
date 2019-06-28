/**
*  162013班 第17组
*  课程设计管理系统--过程管理
*  @author: 吴国伟
*  date: 2019-6-4
*  主要功能说明：学生的数据库操作
*/

package webadv.example.pository;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import webadv.example.bean.NewStudent;
import webadv.example.bean.Student;
import webadv.example.bean.StudentMsg;

@Repository
public class StudentRepository {	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	
	//获取该班级的所有学生的信息以及报告平均分和组员评分
	public List<StudentMsg> allStudentMsgForClass(String class_id) {
		String sql="select wr.stu_id,s.name,s.sex,s.age,s.contact,so.org_id,"
				+ "avg(r.grade) as re_grade,so.grade as stu_grade "
				+ "from student s,report r,weekly_report wr,student_organize so "
				+ "where s.stu_id=so.stu_id and so.stu_id=wr.stu_id and wr.re_id=r.re_id "
				+ "and class_id=? group by wr.stu_id";
		RowMapper<StudentMsg> rowMapper=new BeanPropertyRowMapper<StudentMsg>(StudentMsg.class);
		List<StudentMsg> list = jdbcTemplate.query(sql, rowMapper,class_id);
		return list;
	}
	
	//获取该学生同组的所有学生的id(不包括自己)
	public List<Student> allStudentForOrganize(String stu_id) {
		String sql="select * from student where stu_id in("
				+ "select stu_id from student_organize where org_id in("
				+ "select org_id from student_organize where stu_id = ?) and stu_id!=?)";
		RowMapper<Student> rowMapper=new BeanPropertyRowMapper<Student>(Student.class);
		List<Student> list = jdbcTemplate.query(sql, rowMapper,stu_id,stu_id);
		return list;
	}
	
	//获取该学生同组的所有学生(不包括自己)新的学生对象
	public List<NewStudent> allNewStudentForOrganize(String stu_id) {
		String sql="select s.stu_id,s.name,s.sex,s.class_id,s.age,s.contact,so.org_id,so.grade "
				+ "from student s,student_organize so where s.stu_id in("
				+ "select stu_id from student_organize where org_id in("
				+ "select org_id from student_organize where stu_id = ?) and s.stu_id=so.stu_id) and s.stu_id != ?";
		RowMapper<NewStudent> rowMapper=new BeanPropertyRowMapper<NewStudent>(NewStudent.class);
		List<NewStudent> list = jdbcTemplate.query(sql, rowMapper,stu_id,stu_id);
		return list;
	}
	
	//获取该教师所带班级的所有学生信息和学生的互评成绩
	public List<NewStudent> allNewStudentForTeacher(String tea_id) {
		String sql="select s.stu_id,s.name,s.sex,s.class_id,s.age,s.contact,so.grade "
				+ "from student s,student_organize so where s.stu_id=so.stu_id and s.class_id in("
				+ "select class_id from clazz c,teacher t where c.tea_id=t.tea_id and c.tea_id=?)";
		RowMapper<NewStudent> rowMapper=new BeanPropertyRowMapper<NewStudent>(NewStudent.class);
		List<NewStudent> list = jdbcTemplate.query(sql, rowMapper,tea_id);
		return list;
	}
	
	//获取该学生信息和学生的互评成绩
	public NewStudent allNewStudentForStudent(String stu_id) {
		String sql="select s.stu_id,s.name,s.sex,s.class_id,s.age,s.contact,so.grade "
				+ "from student s,student_organize so where s.stu_id=so.stu_id and s.stu_id=?";
		RowMapper<NewStudent> rowMapper=new BeanPropertyRowMapper<NewStudent>(NewStudent.class);
		NewStudent n = jdbcTemplate.queryForObject(sql, rowMapper,stu_id);
		return n;
	}
			
	//获取该班级的所有学生
	public List<Student> allStudentForClass(String class_id){
		String sql = "select * from student s,clazz c where s.class_id=c.class_id and c.class_id=?";
		RowMapper<Student> row = new BeanPropertyRowMapper<Student>(Student.class);
		List<Student> list = jdbcTemplate.query(sql,row,class_id);
		return list;
	}
	
	//获取该教师所带班级的所有学生所有学生
	public List<Student> allStudentForTeacher(String tea_id) {
		String sql="select s.stu_id,s.name,s.sex,s.class_id,s.age,s.contact,s.password "
				+ "from student s,clazz c,teacher t "
				+ "where c.class_id = s.class_id and c.tea_id=t.tea_id and t.tea_id=?";
		RowMapper<Student> rowMapper=new BeanPropertyRowMapper<Student>(Student.class);
		List<Student> list = jdbcTemplate.query(sql, rowMapper,tea_id);
		return list;
	}
	
	//获取所有学生
	public List<Student> allStudent() {
		String sql="select * from student";
		RowMapper<Student> rowMapper=new BeanPropertyRowMapper<Student>(Student.class);
		List<Student> list = jdbcTemplate.query(sql, rowMapper);
		return list;
	}
	
	//获取一个学生
	public Student getStudent(String stu_id) {
		String sql="select * from student where stu_id = ?";
		RowMapper<Student> rowMapper=new BeanPropertyRowMapper<Student>(Student.class);
		Student st = null;
		try{
			st = jdbcTemplate.queryForObject(sql, rowMapper,stu_id);
		}catch(EmptyResultDataAccessException e) {
			System.out.println("Student---没有数据");
		}
		return st;
	}
	
	//添加一个学生
	public int addStudent(Student st) {
		String sql = "insert into student(stu_id,name,sex,class_id,age,contact,password) values (?,?,?,?,?,?,?)";
		int result = jdbcTemplate.update(sql,st.getStu_id(),st.getName(),st.getSex(),st.getClass_id(),st.getAge(),st.getContact(),st.getPassword());
		return result;
	}
	
	//删除一个学生
	public int deleteStudent(String stu_id) {
		String sql =  "delete from student where stu_id = ?";
		int result = jdbcTemplate.update(sql,stu_id);
		return result;
	}
	
	//修改一个学生
	public int updateStudent(Student te) {
		String sql = "update student set name = ?,sex = ?,age = ?,contact = ? where stu_id = ?";
		int result = jdbcTemplate.update(sql,te.getName(),te.getSex(),te.getAge(),te.getContact(),te.getStu_id());
		return result;
	}
	
	public int  updatePassword(String newpassword, String stu_id) {
		// TODO Auto-generated method stub
		String sql = "update student set password = ?  where stu_id = ?";
		int result = jdbcTemplate.update(sql,newpassword,stu_id);
		return result;
	}

	
	//检查账号密码
	public int checkIdPassword(String stu_id, String password) {
		String sql="select * from student where stu_id = ? and password = ?";
		
		RowMapper<Student> rowMapper=new BeanPropertyRowMapper<Student>(Student.class);
		List<Student> list = jdbcTemplate.query(sql, rowMapper,stu_id,password);
		
		if(list.size() == 0) {
			return 0;
		}else {
			return 1;
		}
	}
}