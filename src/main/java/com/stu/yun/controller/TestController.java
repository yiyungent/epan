package com.stu.yun.controller;

import com.stu.yun.service.HDFSService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.net.URLEncoder;

@RestController
@RequestMapping("api/test")
public class TestController {

    @Autowired
    private HDFSService hdfsService;

    @PostMapping("upload")
    public String upload(@RequestParam("file") MultipartFile file) throws Exception {
        boolean flag = this.hdfsService.upload(file.getOriginalFilename(), file.getInputStream());
        if (flag) {
            System.out.println("start insert into db .....");
        }

        return "/index";
    }

    @GetMapping("download")
    public ResponseEntity<byte[]> download(HttpServletRequest request)
            throws Exception {
        String fileName = request.getParameter("fileName");
        InputStream inputStream = this.hdfsService.download("/hdfs/" + fileName); // /hdfs/Linux.txt
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", URLEncoder.encode(fileName, "UTF-8"));
        return new ResponseEntity<byte[]>(IOUtils.toByteArray(inputStream), headers, HttpStatus.OK);
    }

}
