package studentmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import studentmanager.po.Student;
import studentmanager.po.User;
import studentmanager.service.StudentService;
import studentmanager.service.UserService;
import studentmanager.utils.CpachaUtil;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/*
* 系统主页控制器*/
@RequestMapping("/system")
@Controller
public class SystemController {
    @Autowired
    private StudentService studentService;
    @Autowired
    private UserService userService;
    @RequestMapping(value = "/index",method = RequestMethod.GET )

    public String index(){
        return "system/index";
    }
    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public  String  login(){
        return "system/login";
    }
    /*
    * 登陆表单提交
    * */
    @PostMapping("/login")
    @ResponseBody
    public Map<String ,String > login1(@RequestParam(value = "username" ,required = true) String username,
                                       @RequestParam(value = "password" ,required = true) String password,
                                       @RequestParam(value = "vcode",required = true) String vcode,
                                       @RequestParam(value = "type",required = true) int type,
                                       HttpServletRequest request
                                       ){
        Map<String ,String> ret=new HashMap<>();
        if (StringUtils.isEmpty(username)){
            ret.put("type","error");
            ret.put("msg","用户名不能为空！！");
            return ret;
        }
        if (StringUtils.isEmpty(password)){
            ret.put("type","error");
            ret.put("msg","密码不能为空！！");
            return ret;
        }
        if (StringUtils.isEmpty(vcode)){
            ret.put("type","error");
            ret.put("msg","验证码不能为空！！");
            return ret;
        }
        String  loginCpacha= (String) request.getSession().getAttribute("loginCpacha");//获取之前放在session里面的验证码
        if (StringUtils.isEmpty(loginCpacha)){
            ret.put("tpye","error");
            ret.put("msg","长时间未操作，会话已失效，请刷新后重试！");
            return ret;
        }
        if (!vcode.toUpperCase().equals(loginCpacha.toUpperCase())){//用string的toUpperCase（）方法将验证码都转换成大写，然后再对比
                ret.put("tpye","error");
                ret.put("msg","验证码错误！");
            return ret;
        }
        request.getSession().setAttribute("loginCpacha",null);//清空session
        //从数据库中去查找用户
        if (type==1){
            //管理员
            User user = userService.findByUserName(username);
            if (user==null){
                ret.put("tpye","error");
                ret.put("msg","不存再该用户！");
                return ret;
            }
            if (!password.equals(user.getPassword())){
                ret.put("tpye","error");
                ret.put("msg","密码错误！");
                return ret;
            }
            user.setPassword("");
            request.getSession().setAttribute("user",user);
        }
        if (type==2){
            //学生
            Student student = studentService.findByStudentName(username);
            if (student==null){
                ret.put("tpye","error");
                ret.put("msg","不存再该该学生！");
                return ret;
            }
            if (!password.equals(student.getPassword())){
                ret.put("tpye","error");
                ret.put("msg","密码错误！");
                return ret;
            }
            student.setPassword("");
            request.getSession().setAttribute("user",student);


        }
        request.getSession().setAttribute("userType",type);
        ret.put("type","success");
        ret.put("msg","登陆成功");
        return ret;
    }
    /*验证码变化*/
    @GetMapping("/get_cpacha")
    public  void getCpacha(HttpServletRequest request,
                           @RequestParam(value = "v1",defaultValue = "4",required = false) Integer v1,//required表示这个参数是不是必须的
                           @RequestParam(value = "w",defaultValue = "98",required = false) Integer w,
                           @RequestParam(value = "h",defaultValue = "33",required = false) Integer h,
                           HttpServletResponse response){
        CpachaUtil cpachaUtil=new CpachaUtil(v1,w,h);//调用生成验证吗的方法
        String generatorVCode =cpachaUtil.generatorVCode();//生成字符串
        request.getSession().setAttribute("loginCpacha",generatorVCode);
        BufferedImage generatorRotateVCodeImage =cpachaUtil.generatorRotateVCodeImage(generatorVCode,true);//根据字符串生成图片 参数true代表允许出现干扰线
        try {
            ImageIO.write(generatorRotateVCodeImage,"gif",response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    /*退出系统*/
    @GetMapping("/login_out")
    public String  loginOut(HttpServletRequest request){
        request.getSession().setAttribute("user",null);
        request.getSession().setAttribute("userType",null);
        return "redirect:login";
    }



}
