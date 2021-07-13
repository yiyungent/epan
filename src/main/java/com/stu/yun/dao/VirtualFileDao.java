package com.stu.yun.dao;

import com.stu.yun.model.VirtualFile;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface VirtualFileDao {


    int insert(VirtualFile virtualFile);

    List<VirtualFile> userList(int userId, Integer fileParentId);

    VirtualFile queryById(int id);

    int deleteById(int id);

    List<VirtualFile> queryByParentId(int parentId);

    VirtualFile queryByPath(int userId, String fileName, int parentId);

    List<VirtualFile> queryByRealFileId(int realFileId);

    List<VirtualFile> all();
}
