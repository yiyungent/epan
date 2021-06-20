package com.stu.yun.service;

import java.io.InputStream;

public interface HDFSService {

    boolean upload(String filePath, InputStream input);

    InputStream download(String filePath);

}
