package com.stu.yun.service.impl;

import com.stu.yun.dao.RealFileDao;
import com.stu.yun.model.RealFile;
import com.stu.yun.service.RealFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RealFileServiceImpl  implements RealFileService {

    @Autowired
    private RealFileDao realFileDao;


    @Override
    public boolean insert(RealFile realFile) {
        return this.realFileDao.insert(realFile) == 1;
    }

    @Override
    public RealFile queryBySignKey(String signKey) {
        return this.realFileDao.queryBySignKey(signKey);
    }




}
