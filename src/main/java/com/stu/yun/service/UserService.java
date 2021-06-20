package com.stu.yun.service;

import com.stu.yun.model.UserInfo;

public interface UserService {

    UserInfo queryByUserName(String userName);

    UserInfo insert(UserInfo userInfo);

}
