package com.stu.yun.responseModel;

public class UploadCheckRes {

    private int fileId;

    private String fileName;

    private String fileSignKey;

    public String getFileSignKey() {
        return fileSignKey;
    }

    public void setFileSignKey(String fileSignKey) {
        this.fileSignKey = fileSignKey;
    }

    public int getFileId() {
        return fileId;
    }

    public void setFileId(int fileId) {
        this.fileId = fileId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }


}
