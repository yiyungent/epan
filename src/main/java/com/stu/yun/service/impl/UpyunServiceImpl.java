package com.stu.yun.service.impl;

import com.stu.yun.service.FileService;
import com.upyun.RestManager;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
public class UpyunServiceImpl implements FileService {

    @Value("${upload.upyun.bucketName}")
    private String bucketName;

    @Value("${upload.upyun.userName}")
    private String userName;

    @Value("${upload.upyun.password}")
    private String password;

    public RestManager getRestManager() {
        RestManager manager = null;
        try {
            manager = new RestManager(this.bucketName, this.userName, this.password);
            manager.setTimeout(300);
        } catch (Exception e) {
            System.out.println("Upyun: getRestManager: " + e.getMessage());
        }

        return manager;
    }

    @Override
    public boolean upload(String filePath, InputStream input) {
        // 上传文件，自动创建父级目录
        RestManager manager = getRestManager();
        try {
            Response response = manager.writeFile(filePath, input, null);

            return response.isSuccessful();
        } catch (Exception ex) {

        }

        return false;
    }

    @Override
    public InputStream download(String filePath) {
        RestManager manager = getRestManager();
        try {
            Response response = manager.readFile(filePath);

            return response.body().byteStream();
        }catch (Exception ex) {

        }

        return null;
    }

    @Override
    public boolean delete(String filePath) {
        RestManager manager = getRestManager();
        try {
            Response response = manager.deleteFile(filePath, null);

            return response.isSuccessful();
        } catch (Exception ex) {

        }

        return false;
    }


}
