package studentmanager.service;

import org.springframework.stereotype.Service;
import studentmanager.po.User;

import java.util.List;
import java.util.Map;
public interface UserService {
    User findByUserName(String username);
    int add(User user);
    List<User> findList(Map<String ,Object> queryMap);
    int getTotal(Map<String ,Object> queryMap);
    int edit(User user);
    int delete(String ids);


}
