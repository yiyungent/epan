package com.stu.yun.responseModel;

public class FileListItemRes {

    /**
     * 虚拟文件ID
     */
    private int fileId;

    /**
     * Byte
     * TODO: 文件大小
     * 1. 普通文件: 对应真实文件大小
     * 2. 文件夹: 这个文件夹下 所有文件大小之和 (实时计算, 不单独查看时不计算, 即为文件夹时, 此项为 null)
     */
    private Long fileSize;

    private String fileName;

    /**
     * 文件类型:
     * 0: 普通文件
     * 1: 文件夹
     */
    private int fileType;

    private String createTime;

    public int getFileId() {
        return fileId;
    }

    public void setFileId(int fileId) {
        this.fileId = fileId;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getFileType() {
        return fileType;
    }

    public void setFileType(int fileType) {
        this.fileType = fileType;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }


}
