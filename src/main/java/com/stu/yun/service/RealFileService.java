package com.stu.yun.service;

import com.stu.yun.model.RealFile;

public interface RealFileService {

    boolean insert(RealFile realFile);

    RealFile queryBySignKey(String signKey);
}
