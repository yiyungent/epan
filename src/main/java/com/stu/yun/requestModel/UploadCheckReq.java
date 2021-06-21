package com.stu.yun.requestModel;

public class UploadCheckReq {

    private String fileName;

    private String path;

    private String fileSignKey;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileSignKey() {
        return fileSignKey;
    }

    public void setFileSignKey(String fileSignKey) {
        this.fileSignKey = fileSignKey;
    }


}
