package com.stu.yun.responseModel;

public class FileListItem {

    /**
     * 虚拟文件ID
     */
    private int fileId;

    /**
     * Byte
     */
    private Long fileSize;

    private String fileName;

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

    /**
     * 文件类型:
     * 0: 普通文件
     * 1: 文件夹
     */
    private int fileType;

    private String createTime;
}
