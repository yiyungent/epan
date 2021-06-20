package com.stu.yun.dao;

import com.stu.yun.model.RealFile;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RealFileDao {


    int insert(RealFile realFile);
}
