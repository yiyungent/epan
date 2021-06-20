package com.stu.yun.responseModel;

import java.util.List;

public class FileList {

    private List<FileListItem> list;

    /**
     * 当前 虚拟文件父级ID
     */
    private int fileParentId;


    public List<FileListItem> getList() {
        return list;
    }

    public void setList(List<FileListItem> list) {
        this.list = list;
    }

    public int getFileParentId() {
        return fileParentId;
    }

    public void setFileParentId(int fileParentId) {
        this.fileParentId = fileParentId;
    }




}


