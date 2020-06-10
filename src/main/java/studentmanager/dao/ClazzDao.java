package studentmanager.dao;

import org.springframework.stereotype.Repository;
import studentmanager.po.Clazz;
import java.util.List;
import java.util.Map;

@Repository
public interface ClazzDao {
     int add(Clazz clazz);
     int edit(Clazz clazz);
     int delete(String ids);
     List<Clazz> findList(Map<String, Object> queryMap);
     List<Clazz> findAll();
     int getTotal(Map<String, Object> queryMap);

}
