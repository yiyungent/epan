package com.stu.yun.service;

import java.io.InputStream;

public interface FileService {

    boolean upload(String filePath, InputStream input);

    InputStream download(String filePath);

    boolean delete(String filePath);
}
