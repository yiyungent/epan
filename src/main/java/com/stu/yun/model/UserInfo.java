package com.stu.yun.model;

public class UserInfo {

    private int id;
    private String userName;
    private String password;

    /**
     * 允许的磁盘大小: Byte
     * 10 GB = 10*1024 MB = 10*1024*1024 KB = 10*1024*1024*1024 Byte
     */
    private Long diskSize;

    /**
     * 已经使用的磁盘大小: Byte
     */
    private Long usedDiskSize;



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getDiskSize() {
        return diskSize;
    }

    public void setDiskSize(Long diskSize) {
        this.diskSize = diskSize;
    }

    public Long getUsedDiskSize() {
        return usedDiskSize;
    }

    public void setUsedDiskSize(Long usedDiskSize) {
        this.usedDiskSize = usedDiskSize;
    }

}
