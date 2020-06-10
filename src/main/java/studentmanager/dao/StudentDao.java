package studentmanager.dao;

import org.springframework.stereotype.Repository;
import studentmanager.po.Clazz;
import studentmanager.po.Student;

import java.util.List;
import java.util.Map;

@Repository
public interface StudentDao {
    Student findByStudentName(String username);

    int add(Student student);

    int edit(Student student);

    int delete(String ids);

    List<Student> findList(Map<String, Object> queryMap);

    List<Student> findAll();

    int getTotal(Map<String, Object> queryMap);

}
