package com.stu.yun.dao;

import com.stu.yun.model.VirtualFile;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface VirtualFileDao {


    int insert(VirtualFile virtualFile);

    List<VirtualFile> userList(int userId, Integer fileParentId);
}
