package com.stu.yun.service.impl;

import com.stu.yun.dao.UserDao;
import com.stu.yun.model.UserInfo;
import com.stu.yun.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

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

}
