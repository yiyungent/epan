package com.stu.yun.model;

import java.util.Date;

/**
 * 虚拟文件 节点
 */
public class VirtualFile {

    private int id;

    /**
     * 父级文件
     */
    private int parentId;

    /**
     * 文件类型:
     * 0: 普通文件
     * 1: 文件夹
     */
    private int fileType;

    /**
     * 文件名
     */
    private String fileName;

    /**
     * 上传者/拥有者 ID
     */
    private int userInfoId;

    /**
     * 一个用户 有 多个 虚拟文件 (一个虚拟文件 只能 属于 一个用户)
     * 上传者/拥有者:
     * 1. 首次上传=真实文件创建者
     * 2. 秒传=已有文件
     */
    private UserInfo userInfo;

    private int realFileId;

    private RealFile realFile;

    /**
     * 虚拟文件 创建(上传)时间
     */
    private Date createTime;



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public int getFileType() {
        return fileType;
    }

    public void setFileType(int fileType) {
        this.fileType = fileType;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getUserInfoId() {
        return userInfoId;
    }

    public void setUserInfoId(int userInfoId) {
        this.userInfoId = userInfoId;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public int getRealFileId() {
        return realFileId;
    }

    public void setRealFileId(int realFileId) {
        this.realFileId = realFileId;
    }

    public RealFile getRealFile() {
        return realFile;
    }

    public void setRealFile(RealFile realFile) {
        this.realFile = realFile;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }




}
