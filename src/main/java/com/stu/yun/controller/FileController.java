package com.stu.yun.controller;

import com.stu.yun.model.RealFile;
import com.stu.yun.model.UserInfo;
import com.stu.yun.model.VirtualFile;
import com.stu.yun.requestModel.UploadCheckReq;
import com.stu.yun.responseModel.FileListItemRes;
import com.stu.yun.responseModel.FileListRes;
import com.stu.yun.responseModel.JsonResponse;
import com.stu.yun.responseModel.UploadCheckRes;
import com.stu.yun.service.HDFSService;
import com.stu.yun.service.RealFileService;
import com.stu.yun.service.UserService;
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
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/file")
public class FileController {

    @Autowired
    private HDFSService hdfsService;

    @Autowired
    private VirtualFileService virtualFileService;

    @Autowired
    private RealFileService realFileService;

    @Autowired
    private UserService userService;

    private static final String HDFS_FilePath_Base = "/epan-hdfs/";

    /**
     * 当前登录用户 文件列表
     *
     * @param session
     * @param fileParentId 当前要查看的 虚拟文件父级ID, 查看根目录则为 null/0
     * @return
     */
    @GetMapping("list")
    public JsonResponse list(HttpSession session, Integer fileParentId) {
        // 1. 获取当前登录用户
        UserInfo currentUser = (UserInfo) session.getAttribute("user");

        // 2. 获取 虚拟文件目录
        List<VirtualFile> virtualFileList = this.virtualFileService.userList(currentUser.getId(), fileParentId);
        if (virtualFileList == null) {
            virtualFileList = new ArrayList<>();
        }
        // dbModel -> DTO
        FileListRes fileList = new FileListRes();
        List<FileListItemRes> listItemList = new ArrayList<>();
        SimpleDateFormat sformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (VirtualFile virtualFile : virtualFileList) {
            FileListItemRes item = new FileListItemRes();
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
     *
     * @return
     */
    @PostMapping("uploadCheck")
    public JsonResponse uploadCheck(HttpSession session, @RequestBody UploadCheckReq inputModel) {
        JsonResponse response = new JsonResponse();
        try {
            // 1. 获取当前登录用户
            UserInfo currentUser = (UserInfo) session.getAttribute("user");

            // 2. 秒传检验
            // 2.1 接收客户端计算的文件MD5
            // 2.2 select RealFile: 查询 RealFile 是否已存在 相同 MD5
            RealFile realFile = this.realFileService.queryBySignKey(inputModel.getFileSignKey());
            if (realFile != null) {
                // 2.3 已存在: insert VirtualFile, 响应 秒传成功
                VirtualFile virtualFile = new VirtualFile();
                virtualFile.setCreateTime(new Date());
                virtualFile.setFileName(inputModel.getFileName());
                virtualFile.setFileType(0);
                virtualFile.setParentId(inputModel.getFileParentId());
                virtualFile.setUserInfoId(currentUser.getId());
                virtualFile.setRealFileId(realFile.getId());

                boolean isSuccess = this.virtualFileService.insert(virtualFile);
                if (!isSuccess) {
                    response.setCode(-2);
                    response.setMessage("秒传失败");
                    UploadCheckRes uploadCheckRes = new UploadCheckRes();
                    uploadCheckRes.setFileName(virtualFile.getFileName());
                    uploadCheckRes.setFileSignKey(realFile.getSignKey());
                    response.setData(uploadCheckRes);

                    return response;
                }
                response.setCode(1);
                response.setMessage("上传成功: 秒传");
                UploadCheckRes uploadCheckRes = new UploadCheckRes();
                uploadCheckRes.setFileId(virtualFile.getId());
                uploadCheckRes.setFileName(virtualFile.getFileName());
                uploadCheckRes.setFileSignKey(realFile.getSignKey());
                response.setData(uploadCheckRes);
            } else {
                // 2.4 不存在: 响应 客户端 需要 继续普通上传文件 -> upload()
                response.setCode(2);
                response.setMessage("无法秒传, 需要继续上传");
                response.setData(inputModel.getFileSignKey());
            }
        } catch (Exception e) {
            response.setCode(-1);
            response.setMessage("失败: " + e.getMessage());
        }

        return response;
    }

    @PostMapping("upload")
    public JsonResponse upload(HttpSession session, @RequestParam("file") MultipartFile file, @RequestParam("fileSignKey") String fileSignKey, int fileParentId)
            throws Exception {
        JsonResponse response = new JsonResponse();
        try {
            // 1. 获取当前登录用户
            UserInfo currentUser = (UserInfo) session.getAttribute("user");

            // 2. 上传文件
            // 注意: 抵达此处，说明文件不存在于服务端，这里相信客户端计算结果，不再上传到服务端后，再次计算文件MD5, 而是直接上传
            // 2.1 接收文件上传
            String fullFilePath = HDFS_FilePath_Base;
            // 使用 日期作为 目录
            SimpleDateFormat dfTime = new SimpleDateFormat("yyyy/MM/dd/");
            fullFilePath += dfTime.format(new Date());
            // uuid 作为文件名
            fullFilePath += UUID.randomUUID().toString();
            boolean isSuccess = this.hdfsService.upload(fullFilePath, file.getInputStream());
            if (!isSuccess) {
                response.setCode(-1);
                response.setMessage("上传失败: 上传文件");

                return response;
            }

            // 2.2 insert RealFile
            RealFile realFile = new RealFile();
            realFile.setSourceType(0);
            realFile.setFilePath(fullFilePath);
            realFile.setSignKey(fileSignKey);
            // TODO: 获取上传文件的大小 (Byte)
            Long fileSizeByte = file.getSize();
            realFile.setFileSize(fileSizeByte);

            isSuccess = this.realFileService.insert(realFile);
            if (!isSuccess) {
                response.setCode(-3);
                response.setMessage("上传失败: insert realFile");

                return response;
            }

            // 2.3 insert VirtualFile
            // 使用 用户上传的文件名 作为 虚拟文件名
            String fileName = file.getOriginalFilename();
            VirtualFile virtualFile = new VirtualFile();
            virtualFile.setRealFileId(realFile.getId());
            virtualFile.setUserInfoId(currentUser.getId());
            virtualFile.setParentId(fileParentId);
            virtualFile.setFileType(0);
            virtualFile.setFileName(fileName);
            virtualFile.setCreateTime(new Date());
            isSuccess = this.virtualFileService.insert(virtualFile);
            if (!isSuccess) {
                response.setCode(-4);
                response.setMessage("上传失败: insert virtualFile");

                return response;
            }

            // 2.4 update UserInfo.usedDiskSize
            Long usedDiskSize = currentUser.getUsedDiskSize();
            usedDiskSize += realFile.getFileSize();
            isSuccess = this.userService.update(currentUser);

            // 2.5 响应 普通上传成功
            response.setCode(1);
            response.setMessage("普通上传成功");

        } catch (Exception e) {
            response.setCode(-2);
            response.setMessage("失败: " + e.getMessage());
        }

        return response;
    }

    /**
     * 下载文件
     *
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


    @PostMapping("mkdir")
    public JsonResponse MkDir(HttpSession session, String folderName, String fileParentId){
        JsonResponse response = new JsonResponse();
        try {

        } catch (Exception e) {

        }

        return response;
    }

}
