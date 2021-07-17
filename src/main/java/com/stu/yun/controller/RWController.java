package com.stu.yun.controller;

import com.stu.yun.model.VirtualFile;
import com.stu.yun.responseModel.JsonResponse;
import com.stu.yun.responseModel.MapRes;
import com.stu.yun.service.FileService;
import com.stu.yun.service.RealFileService;
import com.stu.yun.service.UserService;
import com.stu.yun.service.VirtualFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/rw")
public class RWController {

    @Autowired
    private FileService hdfsService;

    @Autowired
    private VirtualFileService virtualFileService;

    @Autowired
    private RealFileService realFileService;

    @Autowired
    private UserService userService;


    @GetMapping("maps")
    public JsonResponse maps(Integer page, Integer limit) {
        // 2. 获取 虚拟文件目录
        List<VirtualFile> virtualFileList = this.virtualFileService.queryAllByLimit(page, limit);
        if (virtualFileList == null) {
            virtualFileList = new ArrayList<>();
        }
        // 筛选文件: 只要 xxx.tmx
        virtualFileList = virtualFileList.stream().filter(m -> m.getFileName().endsWith(".tmx")).collect(Collectors.toList());
        List<MapRes> mapResList = new ArrayList<>();
        for (VirtualFile file : virtualFileList) {
            MapRes map = new MapRes();
            map.setFileId(file.getId());
            map.setAuthor(file.getUserInfo().getUserName());
            map.setFileName(file.getFileName());
            String downloadUrl = "https://static-rw.moeci.com" + file.getRealFile().getFilePath();
            map.setDownloadUrl(downloadUrl);

            mapResList.add(map);
        }

        JsonResponse response = new JsonResponse();
        response.setCode(1);
        response.setMessage("加载地图列表成功");
        response.setData(mapResList);

        return response;
    }


}
