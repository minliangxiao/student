package studentmanager.dao;

import org.springframework.stereotype.Repository;
import studentmanager.po.User;

import java.util.List;
import java.util.Map;

@Repository
public interface UserDao {
    User findByUserName(String username);
    int add(User user);
    List<User> findList(Map<String ,Object> queryMap);
    int getTotal(Map<String ,Object> queryMap);
    int edit(User user);
    int delete(String ids);


}
