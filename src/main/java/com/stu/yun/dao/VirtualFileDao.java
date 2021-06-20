package com.stu.yun.dao;

import com.stu.yun.model.VirtualFile;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface VirtualFileDao {


    int insert(VirtualFile virtualFile);
}
