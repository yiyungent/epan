package com.stu.yun.controller;

import com.stu.yun.model.UserInfo;
import com.stu.yun.responseModel.JsonResponse;
import com.stu.yun.service.UserService;
import com.stu.yun.utils.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Value("${jwt.secret}")
    private String jwtSecret;

    @PostMapping("login")
    public JsonResponse login(String userName, String password, HttpServletRequest request) {
        UserInfo userInfo = this.userService.queryByUserName(userName);
        // TODO: 用户密码加盐md5
        JsonResponse response = new JsonResponse();
        if (userInfo == null || userInfo.getPassword() == null || !userInfo.getPassword().equals(password)) {
            response.setCode(-1);
            response.setMessage("登录失败: 用户名或密码不正确");
            return response;
        }
        response.setCode(1);
        response.setMessage("登录成功");
        // TODO: user responseModel
        // Temp: 敏感数据置空
        userInfo.setPassword("");

        // 对于 WebAPI 不使用 Session 时, 采用 JWT
        String token = JWTUtil.createToken(userInfo, this.jwtSecret);
        response.setData(token);

        System.out.println("login: " + userInfo.getUserName());

        return response;
    }

    @PostMapping("register")
    public JsonResponse register(String userName, String password, HttpServletRequest request) {

        // 效验是否已经存在此用户名
        UserInfo existUser = this.userService.queryByUserName(userName);
        if (existUser != null) {
            JsonResponse response = new JsonResponse();
            response.setCode(-3);
            response.setMessage("注册失败: " + userName + "用户名已经存在");

            return response;
        }

        // 效验用户名合法
        // 用户名 只能 包含汉字英文数字
        String regex = "^[a-zA-Z0-9\u4E00-\u9FA5]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher match = pattern.matcher(userName);
        if (!match.matches()) {
            JsonResponse response = new JsonResponse();
            response.setCode(-2);
            response.setMessage("注册失败: " + userName + " 用户名只能包含汉字英文数字");

            return response;
        }

        // 限制 ip 注册
        // TODO: 未测试: 一个 ip 一个月只允许注册5个账号
        String currentIpAddress = getIpAddr(request);
        try {
            List<UserInfo> ipUserList = this.userService.queryByIpAddress(currentIpAddress);
            int currentMonth = new Date().getMonth();
            // 此ip 本月注册账号
            List<UserInfo> tempList = ipUserList.stream().filter(m -> m.getCreateTime().getMonth() == currentMonth).collect(Collectors.toList());
            if (tempList.size() > 5) {
                JsonResponse response = new JsonResponse();
                response.setCode(-4);
                response.setMessage("注册失败: 注册频繁, 限制注册");

                return response;
            }
        } catch (Exception ex) {

        }


        UserInfo dbUser = new UserInfo();
        dbUser.setUserName(userName);
        dbUser.setPassword(password);
        dbUser.setUsedDiskSize(0L);
        dbUser.setDiskSize(10 * 1024 * 1024L);
        dbUser.setCreateTime(new Date());
        dbUser.setIpAddress(currentIpAddress);
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
    public JsonResponse info() {
        UserInfo user = this.userService.currentUser();
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
        // Temp: 敏感数据置空
        user.setPassword("");
        response.setData(user);

        return response;
    }


    public static String getIpAddr(HttpServletRequest request) {
        String ipAddress = null;
        try {
            ipAddress = request.getHeader("x-forwarded-for");
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("Proxy-Client-IP");
            }
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("WL-Proxy-Client-IP");
            }
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getRemoteAddr();
                if (ipAddress.equals("127.0.0.1")) {
                    // 根据网卡取本机配置的IP
                    InetAddress inet = null;
                    try {
                        inet = InetAddress.getLocalHost();
                    } catch (UnknownHostException e) {
                        // logger.error("获取用户的主机发生异常",e);
                    }
                    ipAddress = inet.getHostAddress();
                }
            }
            // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
            if (ipAddress != null && ipAddress.length() > 15) { // "***.***.***.***".length()
                // = 15
                if (ipAddress.indexOf(",") > 0) {
                    ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
                }
            }
        } catch (Exception e) {
            ipAddress = "";
            // logger.error("获取用户的ip地址发生异常",e);
        }
        // ipAddress = this.getRequest().getRemoteAddr();

        return ipAddress;
    }

}
