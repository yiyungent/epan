package com.stu.yun.config;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录检查
 */
public class LoginHandlerIntercptor  implements HandlerInterceptor {

    //目标方法执行之前
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 这个是登录时注册到session中的值
        Object user=request.getSession().getAttribute("user");
        if(user==null){
            //未登录
            request.setAttribute("message","没有权限请先登录");
            request.getRequestDispatcher("/login.html").forward(request,response);
            return false;
        }
        else {
            return true;
        }
    }

    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }

}
