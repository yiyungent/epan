package com.stu.yun.service.impl;

import com.stu.yun.dao.UserDao;
import com.stu.yun.model.UserInfo;
import com.stu.yun.service.UserService;
import com.stu.yun.utils.HttpServletUtil;
import com.stu.yun.utils.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Override
    public UserInfo currentUser() {
        UserInfo user = null;
        // 1. 先尝试从 headers.Authorization 中找 token
        String token = HttpServletUtil.getRequestHeader("Authorization");
        if (token != null && !token.isEmpty()) {
            token = token.substring("Bearer ".length());
            user = JWTUtil.verify(token, this.jwtSecret);
            if (user != null) {
                user = this.userDao.queryById(user.getId());
            }
        } else {
            // 2. 再尝试从 Cookie 中找
            token = HttpServletUtil.getCookie("token");
            if (token != null && !token.isEmpty()){
                user = JWTUtil.verify(token, this.jwtSecret);
                if (user != null) {
                    user = this.userDao.queryById(user.getId());
                }
            }
        }

        return user;
    }

    @Override
    public UserInfo queryByUserName(String userName) {
        return this.userDao.queryByUserName(userName);
    }

    @Override
    public UserInfo insert(UserInfo userInfo) {
        boolean isSuccess = this.userDao.insert(userInfo) == 1;
        if (isSuccess) {
            return userInfo;
        } else {
            return null;
        }
    }

    @Override
    public boolean update(UserInfo userInfo) {
        return this.userDao.update(userInfo) == 1;
    }

    @Override
    public UserInfo queryById(int id) {
        return this.userDao.queryById(id);
    }

    @Override
    public List<UserInfo> queryByIpAddress(String ipAddress) {
        return this.userDao.queryByIpAddress(ipAddress);
    }

}
