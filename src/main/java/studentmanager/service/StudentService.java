package studentmanager.service;

import studentmanager.po.Student;

import java.util.List;
import java.util.Map;

public interface StudentService {
    Student findByStudentName(String username);

    public int add(Student student);
    public int edit(Student student);
    public int delete(String ids);
    public List<Student> findList(Map<String, Object> queryMap);
    public List<Student> findAll();
    public int getTotal(Map<String, Object> queryMap);

}
