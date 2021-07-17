package com.stu.yun.service.impl;

import com.stu.yun.dao.VirtualFileDao;
import com.stu.yun.model.VirtualFile;
import com.stu.yun.service.VirtualFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
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

    @Override
    public List<VirtualFile> userList(int userId, String path) {
        // path: 文件夹a/文件夹b/文件夹c, 不存在普通文件
        // 根据路径查询到最后一个文件夹的 fileId
        // TODO: 注意: 这里可能存在空字符串, 添加 -1 也没用
        String[] folderNameArr = path.split("/");

        System.out.println("userList.folderNameArr:  " + folderNameArr.length + " " + String.join(",", folderNameArr));
        int tempFileParentId = 0;
        for (int i = 0; i < folderNameArr.length; i++) {
            if (folderNameArr[i].isEmpty()){
                continue;
            }
            int fileParentId = tempFileParentId;
            // 此用户 此文件夹下(fileParentId) 此文件夹 folderNameArr[i]
            VirtualFile virtualFile = this.virtualFileDao.queryByPath(userId, folderNameArr[i], fileParentId);

            System.out.println(virtualFile.getFileName());

            tempFileParentId = virtualFile.getId();
        }
        List<VirtualFile> result = this.virtualFileDao.userList(userId, tempFileParentId);

        return result;
    }

    @Override
    public VirtualFile queryById(int id) {
        return this.virtualFileDao.queryById(id);
    }

    @Override
    public boolean deleteById(int id) {
        return this.virtualFileDao.deleteById(id) == 1;
    }

    @Override
    public List<VirtualFile> queryByParentId(int parentId) {
        return this.virtualFileDao.queryByParentId(parentId);
    }

    @Override
    public int queryFileIdByPath(int userId, String path) {
        // path: 文件夹a/文件夹b/文件夹c, 不存在普通文件
        // 根据路径查询到最后一个文件夹的 fileId
        // TODO: 注意: 这里可能存在空字符串, 添加 -1 也没用
        String[] folderNameArr = path.split("/");

        System.out.println("queryFileIdByPath.folderNameArr:  " + folderNameArr.length + " " + String.join(",", folderNameArr));
        int tempFileParentId = 0;
        for (int i = 0; i < folderNameArr.length; i++) {
            if (folderNameArr[i].isEmpty()){
                continue;
            }
            int fileParentId = tempFileParentId;
            // 此用户 此文件夹下(fileParentId) 此文件夹 folderNameArr[i]
            VirtualFile virtualFile = this.virtualFileDao.queryByPath(userId, folderNameArr[i], fileParentId);

            System.out.println(virtualFile.getFileName());

            tempFileParentId = virtualFile.getId();
        }

        return tempFileParentId;
    }

    @Override
    public boolean mkdir(int userId, String path) {
        boolean isSuccess = true;
        // path: 文件夹a/文件夹b/文件夹c, 不存在普通文件
        // 根据路径查询到最后一个文件夹的 fileId
        // TODO: 注意: 这里可能存在空字符串, 添加 -1 也没用
        String[] folderNameArr = path.split("/");

        System.out.println("mkdir.folderNameArr:  " + folderNameArr.length + " " + String.join(",", folderNameArr));
        int tempFileParentId = 0;
        Date now = new Date();
        for (int i = 0; i < folderNameArr.length; i++) {
            if (folderNameArr[i].isEmpty()){
                continue;
            }
            int fileParentId = tempFileParentId;
            // 此用户 此文件夹下(fileParentId) 此文件夹 folderNameArr[i]
            VirtualFile virtualFile = this.virtualFileDao.queryByPath(userId, folderNameArr[i], fileParentId);
            if (virtualFile == null) {
                // 不存在此文件夹: 新建
                // insert VirtualFile
                virtualFile = new VirtualFile();
                virtualFile.setCreateTime(now);
                virtualFile.setFileName(folderNameArr[i]);
                virtualFile.setFileType(1);
                virtualFile.setParentId(fileParentId);
                virtualFile.setUserInfoId(userId);
                virtualFile.setRealFileId(0);
                this.insert(virtualFile);
            } else {
                // 已存在: 什么都不做
            }

            tempFileParentId = virtualFile.getId();
        }

        return isSuccess;
    }

    @Override
    public List<VirtualFile> queryByRealFileId(int realFileId) {
        return this.virtualFileDao.queryByRealFileId(realFileId);
    }

    @Override
    public List<VirtualFile> queryAllByLimit(Integer page, Integer limit) {
        int offset = 0;
        //判断offset   和limit  是否为null
        if(page != null && limit != null){
            offset = (page-1)*limit;
        }else{
            offset = 0;
            limit = 10;
        }
        List<VirtualFile> models = this.virtualFileDao.queryAllByLimit(offset, limit);
        return models;
    }


}
