package com.stu.yun.dao;

import com.stu.yun.model.UserInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao {


    UserInfo queryByUserName(String userName);

    int insert(UserInfo userInfo);
}
