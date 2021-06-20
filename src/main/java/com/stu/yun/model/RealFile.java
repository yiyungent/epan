package com.stu.yun.model;

/**
 * 真实文件 节点
 * 一个真实文件 对 多个虚拟文件
 */
public class RealFile {

    private int id;

    /**
     * 文件 MD5
     * MD5校验秒传
     * 用于 文件秒传 (已有文件不再真上传, 而是增加虚拟文件指向真实文件)
     */
    private String signKey;

    /**
     * 来源类型 (扩展点):
     * 0: HDFS
     */
    private int sourceType;

    /**
     * 真实文件存储路径:
     * HDFS: /epan-hdfs/2021/6/20/uuid
     */
    private String filePath;

    /**
     * 文件大小 (Byte)
     * 真实文件 记录 文件大小，以免每次读取文件大小
     * 虚拟文件 不记录，免得同时维护两张表
     *
     * TODO: 其实虚拟文件也可以记录文件大小，试想：什么时候需要更新文件大小?
     * 由于不存在真正的在原文件上修改，而是新创建文件，真实文件节点 指向 新文件地址，虚拟文件指向真实文件不变，所以几乎不需要后续更新文件大小
     * 当然 修改文件时，也可以采用覆盖原文件方法，保持 文件地址不变，不过这样就没办法做 文件版本 了
     */
    private Long fileSize;

}
