package studentmanager.controller;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import studentmanager.page.Page;
import studentmanager.po.User;
import studentmanager.service.UserService;

import java.util.HashMap;
import java.util.Map;

@RequestMapping("/user")
@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/list")
    public String list() {

        return "user/user_list";
    }

    /*显示列表*/
    @PostMapping("/get_list")
    @ResponseBody
    public Map<String, Object> getList(
            @RequestParam(value = "username", required = false, defaultValue = "") String username,
            Page page
    ) {
        Map<String, Object> ret = new HashMap<>();
        Map<String, Object> queryMap = new HashMap<>();
//        下面map的key值是easyui规定好了的，
        queryMap.put("username", "%" + username + "%");
        queryMap.put("offset", page.getOffset());
        queryMap.put("pageSize", page.getRows());
        ret.put("rows", userService.findList(queryMap));
        ret.put("total", userService.getTotal(queryMap));

        return ret;
    }

    /*添加用户*/
    @PostMapping("/add")
    @ResponseBody
    public Map<String, String> add(User user) {
        Map<String, String> ret = new HashMap<>();
        if (user == null) {
            ret.put("type", "error");
            ret.put("msg", "数据绑定出错！请联系开发者！");
            return ret;
        }
        if (StringUtils.isEmpty(user.getUsername())) {
            ret.put("type", "error");
            ret.put("msg", "名字不能为空！");
            return ret;
        }
        if (StringUtils.isEmpty(user.getPassword())) {
            ret.put("type", "error");
            ret.put("msg", "密码不能为空！");
            return ret;
        }
        User existUser = userService.findByUserName(user.getUsername());
        if (existUser != null) {
            ret.put("type", "error");
            ret.put("msg", "已添加相同名字的用户，请换个名字！");
            return ret;
        }
        if (userService.add(user) <= 0) {
            ret.put("type", "error");
            ret.put("msg", "添加失败！");
            return ret;
        }
        ret.put("type", "success");
        ret.put("msg", "添加成功!");
        return ret;
    }

    /*修改用户*/
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, String> edit(User user) {
        Map<String, String> ret = new HashMap<>();
        if (user == null) {
            ret.put("type", "error");
            ret.put("msg", "数据绑定出错！请联系开发者！");
            return ret;
        }
        if (StringUtils.isEmpty(user.getUsername())) {
            ret.put("type", "error");
            ret.put("msg", "名字不能为空！");
            return ret;
        }
        if (StringUtils.isEmpty(user.getPassword())) {
            ret.put("type", "error");
            ret.put("msg", "密码不能为空！");
            return ret;
        }
        User existUser = userService.findByUserName(user.getUsername());
        if (existUser != null) {
            if (user.getId() != existUser.getId()) {
                ret.put("type", "error");
                ret.put("msg", "已添加相同名字的用户，请换个名字！");
                return ret;
            }
        }
        if (userService.edit(user) <= 0) {
            ret.put("type", "error");
            ret.put("msg", "修改失败！");
            return ret;
        }
        ret.put("type", "success");
        ret.put("msg", "修改成功!");
        return ret;
    }

    //    删除用户操作
    @PostMapping("/delete")
    @ResponseBody
    public Map<String, String> delete(
            @RequestParam(value = "ids[]", required = true) Long[] ids) {
        Map<String, String> ret = new HashMap<>();
        if (ids == null) {
            ret.put("type", "error");
            ret.put("msg", "请选择要删除的列");
            return ret;
        }
        String idsString = "";
        for (Long id : ids) {
            idsString += id + ",";
        }
        idsString = idsString.substring(0, idsString.length() - 1);//去除最后一个，
        if (userService.delete(idsString) <= 0) {
            ret.put("type", "error");
            ret.put("msg", "删除失败");
            return ret;
        }
        ret.put("type", "success");
        ret.put("msg", "修改成功");
        return ret;
    }


}
