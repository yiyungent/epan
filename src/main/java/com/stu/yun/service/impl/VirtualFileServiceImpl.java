package com.stu.yun.service.impl;

import com.stu.yun.dao.VirtualFileDao;
import com.stu.yun.model.VirtualFile;
import com.stu.yun.service.VirtualFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VirtualFileServiceImpl implements VirtualFileService {

    @Autowired
    private VirtualFileDao virtualFileDao;

    @Override
    public boolean insert(VirtualFile virtualFile) {
        return this.virtualFileDao.insert(virtualFile) == 1;
    }

    @Override
    public List<VirtualFile> userList(int userId, Integer fileParentId) {
        return this.virtualFileDao.userList(userId, fileParentId);
    }
}
