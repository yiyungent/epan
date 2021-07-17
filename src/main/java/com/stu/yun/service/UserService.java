package com.stu.yun.service;

import com.stu.yun.model.UserInfo;

import java.util.List;

public interface UserService {

    UserInfo currentUser();

    UserInfo queryByUserName(String userName);

    UserInfo insert(UserInfo userInfo);

    boolean update(UserInfo userInfo);

    UserInfo queryById(int id);

    List<UserInfo> queryByIpAddress(String ipAddress);
}
