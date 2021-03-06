package com.stu.yun.config;

import com.stu.yun.model.UserInfo;
import com.stu.yun.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录检查
 */
public class LoginHandlerIntercptor implements HandlerInterceptor {

    @Autowired
    private UserService userService;

    //目标方法执行之前
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 这个是登录时注册到session中的值
//        Object user=request.getSession().getAttribute("user");
        UserInfo user = this.userService.currentUser();
        System.out.println("登录拦截 start");
        if (user == null) {
            //未登录
            System.out.println("登录拦截: 未登录");
            request.setAttribute("message", "没有权限请先登录");
            request.getRequestDispatcher("/login.html").forward(request, response);
            return false;
        } else {
            System.out.println("登录拦截: " + user.getUserName());

            return true;
        }
    }

    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }

}
