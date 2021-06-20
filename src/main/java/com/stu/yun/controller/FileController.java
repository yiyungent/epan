package com.stu.yun.controller;

import com.stu.yun.model.RealFile;
import com.stu.yun.model.UserInfo;
import com.stu.yun.model.VirtualFile;
import com.stu.yun.responseModel.FileList;
import com.stu.yun.responseModel.FileListItem;
import com.stu.yun.responseModel.JsonResponse;
import com.stu.yun.service.HDFSService;
import com.stu.yun.service.RealFileService;
import com.stu.yun.service.VirtualFileService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.InputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/file")
public class FileController {

    @Autowired
    private HDFSService hdfsService;

    @Autowired
    private VirtualFileService virtualFileService;

    @Autowired
    private RealFileService realFileService;

    private static final String HDFS_FilePath_Base ="/epan-hdfs/";

    /**
     * 当前登录用户 文件列表
     * @param session
     * @param fileParentId 当前要查看的 虚拟文件父级ID, 查看根目录则为 null/0
     * @return
     */
    @GetMapping("list")
    public JsonResponse list(HttpSession session, Integer fileParentId){
        // 1. 获取当前登录用户
        UserInfo currentUser = (UserInfo)session.getAttribute("user");

        // 2. 获取 虚拟文件目录
        List<VirtualFile> virtualFileList = this.virtualFileService.userList(currentUser.getId(), fileParentId);
        if (virtualFileList == null) {
            virtualFileList = new ArrayList<>();
        }
        // dbModel -> DTO
        FileList fileList = new FileList();
        List<FileListItem> listItemList = new ArrayList<>();
        SimpleDateFormat sformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (VirtualFile virtualFile : virtualFileList) {
            FileListItem item = new FileListItem();
            item.setFileId(virtualFile.getId());
            item.setFileName(virtualFile.getFileName());
            item.setCreateTime(sformat.format(virtualFile.getCreateTime()));
            item.setFileType(virtualFile.getFileType());

            RealFile realFile = virtualFile.getRealFile();
            item.setFileSize(realFile.getFileSize());

            listItemList.add(item);
        }
        fileList.setFileParentId(fileParentId);
        fileList.setList(listItemList);

        JsonResponse response = new JsonResponse();
        response.setCode(1);
        response.setMessage("加载文件列表成功");
        response.setData(fileList);

        return response;
    }


    /**
     * 文件上传检查: 秒传: 通过接收客户端计算的文件MD5，判断是否需要上传到服务端
     * @param fileSignKey 文件MD5
     * @return
     */
    @PostMapping("uploadCheck")
    public JsonResponse uploadCheck(HttpSession session, String fileSignKey){
        // TODO: 1. 秒传检验
        // 1.1 接收客户端计算的文件MD5
        // 1.2 select RealFile: 查询 RealFile 是否已存在 相同 MD5
        // 1.3 已存在: insert VirtualFile, 响应 秒传成功
        // 1.4 不存在: 响应 客户端 需要 继续普通上传文件 -> upload()

        JsonResponse response = new JsonResponse();

        return response;
    }

    @PostMapping("upload")
    public String upload(HttpSession session, @RequestParam("file") MultipartFile file, @RequestParam("fileSignKey") String fileSignKey)
            throws Exception {
        // TODO: 1. 上传文件
        // 注意: 抵达此处，说明文件不存在于服务端，这里相信客户端计算结果，不再上传到服务端后，再次计算文件MD5, 而是直接上传
        // 1.1 接收文件上传
        // 1.2 insert RealFile
        // 1.3 insert VirtualFile
        // 1.4 update UserInfo.usedDiskSize
        // 1.5 响应 普通上传成功
        boolean flag = this.hdfsService.upload(HDFS_FilePath_Base + file.getOriginalFilename(), file.getInputStream());
        if (flag) {
            System.out.println("start insert into db .....");
        }

        return "/index";
    }

    /**
     * 下载文件
     * @param fileId 虚拟文件ID
     * @return
     * @throws Exception
     */
    @GetMapping("download")
    public ResponseEntity<byte[]> download(HttpSession session, String fileId)
            throws Exception {
        // TODO: 1. 下载文件
        // TODO: 效验文件权限: 此 VirtualFile 是否属于 当前用户
        // 1.1 select VirtualFile: 根据 fileId 查询
        // 1.2 select RealFile: 查询到对应的 RealFile.filePath 真实文件路径
        // 1.3 读取 filePath 的文件 -> 响应 文件流

        String filePath = "";
        InputStream inputStream = this.hdfsService.download(HDFS_FilePath_Base + filePath); // /hdfs/Linux.txt
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", URLEncoder.encode(filePath, "UTF-8"));
        return new ResponseEntity<byte[]>(IOUtils.toByteArray(inputStream), headers, HttpStatus.OK);
    }
    

}
