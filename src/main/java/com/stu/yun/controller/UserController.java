package com.stu.yun.controller;

import com.stu.yun.model.UserInfo;
import com.stu.yun.responseModel.JsonResponse;
import com.stu.yun.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("login")
    public JsonResponse login(String userName, String password, HttpSession session){
        UserInfo userInfo = this.userService.queryByUserName(userName);
        // TODO: 用户密码加盐md5
        JsonResponse response = new JsonResponse();
        if (!userInfo.getPassword().equals(password)) {
            response.setCode(-1);
            response.setMessage("登录失败: 用户名或密码不正确");
            return response;
        }
        response.setCode(1);
        response.setMessage("登录成功");
        // TODO: user responseModel
        // Temp: 敏感数据置空
        userInfo.setPassword("");
        response.setData(userInfo);
        // TODO: 对于 WebAPI 不使用 Session 时, 采用 JWT
        session.setAttribute("user", userInfo);

        return response;
    }

    @PostMapping("register")
    public JsonResponse register(String userName, String password){
        UserInfo dbUser = new UserInfo();
        // TODO: 效验是否已经存在此用户名
        dbUser.setUserName(userName);
        dbUser.setPassword(password);
        dbUser.setUsedDiskSize(0L);
        dbUser.setDiskSize(10*1024*1024*1024L);
        UserInfo userInfo = this.userService.insert(dbUser);
        JsonResponse response = new JsonResponse();
        if (userInfo != null) {
            response.setCode(1);
            response.setMessage("注册成功: 你的用户ID: " + userInfo.getId());
            response.setData(userInfo);
        } else {
            response.setCode(-1);
            response.setMessage("注册失败");
        }

        return response;
    }

    @GetMapping("info")
    public JsonResponse info(@SessionAttribute("user") UserInfo user, HttpSession session){
        JsonResponse response = new JsonResponse();
        if (user == null) {
            response.setCode(-1);
            response.setMessage("未登录");
            return response;
        }
        response.setCode(1);
        response.setMessage("已登录");

        // 更新用户信息
        user = this.userService.queryById(user.getId());
        session.setAttribute("user", user);
        // TODO: user responseModel
        // Temp: 敏感数据置空
        user.setPassword("");
        response.setData(user);

        return response;
    }

}
