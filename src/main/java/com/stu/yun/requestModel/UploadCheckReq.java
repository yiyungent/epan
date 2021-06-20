package com.stu.yun.requestModel;

public class UploadCheckReq {

    private String fileName;

    private int fileParentId;

    private String fileSignKey;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getFileParentId() {
        return fileParentId;
    }

    public void setFileParentId(int fileParentId) {
        this.fileParentId = fileParentId;
    }

    public String getFileSignKey() {
        return fileSignKey;
    }

    public void setFileSignKey(String fileSignKey) {
        this.fileSignKey = fileSignKey;
    }


}
