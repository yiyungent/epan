package com.stu.yun.service;

import com.stu.yun.model.VirtualFile;

import java.util.List;

public interface VirtualFileService {

    boolean insert(VirtualFile virtualFile);

    List<VirtualFile> userList(int userId, Integer fileParentId);

    List<VirtualFile> userList(int userId, String path);

    VirtualFile queryById(int id);

    boolean deleteById(int id);

    List<VirtualFile> queryByParentId(int parentId);
}
