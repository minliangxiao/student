package studentmanager.interceptor;

import net.sf.json.JSONObject;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import studentmanager.po.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/*
* 登陆过滤器
* */
public class LoginInterceptor implements HandlerInterceptor {
//    请求拦截前
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        String  url =request.getRequestURI();

        Object user =  request.getSession().getAttribute("user");
        if (user==null){
            System.out.println("未登录或登陆失效，url="+url);
//            处理ajax请求
            if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))){
                Map<String,String> ret=new HashMap<String ,String>();
                ret.put("type","error");
                ret.put("msg","登陆状态已经失效，请刷新后去重新登陆");
                response.getWriter().write(JSONObject.fromObject(ret).toString());
                return false;
            }
            response.sendRedirect(request.getContextPath()+"/system/login");
            return false;

        }
        return true;
    }
//请求正在发生时
    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }
//请求拦截后
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
