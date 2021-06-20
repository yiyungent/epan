package com.stu.yun.model;

public class UserInfo {

    private String userName;
    private String password;

    /**
     * 允许的磁盘大小: KB
     */
    private Long diskSize;

    /**
     * 已经使用的磁盘大小: KB
     */
    private Long usedDiskSize;

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
