package com.stu.yun.service;

import com.stu.yun.model.VirtualFile;

import java.util.List;

public interface VirtualFileService {

    boolean insert(VirtualFile virtualFile);

    List<VirtualFile> userList(int userId, Integer fileParentId);
}
