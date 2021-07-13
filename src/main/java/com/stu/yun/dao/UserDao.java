package com.stu.yun.dao;

import com.stu.yun.model.UserInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserDao {


    UserInfo queryByUserName(String userName);

    int insert(UserInfo userInfo);

    int update(UserInfo userInfo);

    UserInfo queryById(int id);

    List<UserInfo> queryByIpAddress(String ipAddress);
}
