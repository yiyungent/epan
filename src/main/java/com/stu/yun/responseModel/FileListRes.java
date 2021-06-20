package com.stu.yun.responseModel;

import java.util.List;

public class FileListRes {

    private List<FileListItemRes> list;

    /**
     * 当前 虚拟文件父级ID
     */
    private int fileParentId;


    public List<FileListItemRes> getList() {
        return list;
    }

    public void setList(List<FileListItemRes> list) {
        this.list = list;
    }

    public int getFileParentId() {
        return fileParentId;
    }

    public void setFileParentId(int fileParentId) {
        this.fileParentId = fileParentId;
    }




}


