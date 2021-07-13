package com.stu.yun.service.impl;

import com.stu.yun.service.FileService;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;

import java.io.InputStream;
import java.net.URI;

// 注释掉: 取消使用 HDFS
//@Service
public class HDFSServiceImpl implements FileService {

    @Value("${hadoop.hdfs.uri}")
    private String hdfsURI;

    @Value("${dfs.client.use.datanode.hostname}")
    private String dfsClientUseDataNodeHostName;

    @Value("${dfs.replication}")
    private String dfsReplication;

    /**
     * 获取 HDFS 文件系统对象
     */
    public FileSystem getFileSystem() {
        System.setProperty("HADOOP_USER_NAME", "root");
        FileSystem fs = null;
        try {
            Configuration conf = new Configuration();
            conf.set("dfs.client.use.datanode.hostname", dfsClientUseDataNodeHostName);
            conf.set("dfs.replication", dfsReplication);

            fs = FileSystem.get(URI.create(hdfsURI), conf);
        } catch (Exception e) {
            System.out.println("HDFS: getFileSystem: " + e.getMessage());
        }
        return fs;
    }

    @Override
    public boolean upload(String filePath, InputStream input) {
        boolean flag = false;
        FSDataOutputStream fos = null;
        try {
            fos = this.getFileSystem().create(new Path(filePath));
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = input.read(buffer)) != -1) {
                fos.write(buffer, 0, len);
            }
            IOUtils.closeStream(fos);
            flag = true;
        } catch (Exception e) {
            System.out.println("HDFS: upload: " + e.getMessage());
        }
        return flag;
    }

    @Override
    public InputStream download(String filePath) {
        FSDataInputStream fis = null;
        try {
            fis = this.getFileSystem().open(new Path(filePath));
        } catch (Exception e) {
            System.out.println("HDFS: download: " + e.getMessage());
        }
        return fis;
    }

    @Override
    public boolean delete(String filePath) {
        boolean flag = false;
        try {
            // 第二个参数: true: 递归删除
            flag = this.getFileSystem().delete(new Path(filePath), true);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return flag;
    }

}
