package com.project.controller;

import com.project.entity.FileFolder;
import com.project.entity.FileRepository;
import com.project.entity.MyFile;
import com.project.exception.FileException;
import com.project.service.FileFolderService;
import com.project.service.FileRepositoryService;
import com.project.service.MyFileService;
import com.project.service.impl.FileFolderServiceImpl;
import com.project.service.impl.FileRepositoryServiceImpl;
import com.project.service.impl.MyFileServiceImpl;
import com.project.util.*;
import com.project.vo.ResultVo;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@CrossOrigin(origins = {"http://120.25.105.43"})
public class FileRepositoryController extends BaseController{
    private Logger logger= LogUtil.getInstance(FileRepositoryController.class);
    //绝对路径前面固定的路径 (文件存放的路径的前半部分）


    @RequestMapping(value = {"/user/uploadFile"},method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin(methods = RequestMethod.POST)
    public ResultVo uploadFile(MultipartFile myFile,String path) throws IOException, FileException {
        ResultMessageUtil.removeData(result);
        if(myFile.isEmpty()){
            logger.info("文件为空，上传失败");
            throw  new FileException("文件为空，请重新提交");
        }
        FileFolderService fileFolderService= (FileFolderService) ServiceFactory.getService(new FileFolderServiceImpl());
        //当前用户的一些信息
        String loginUserId= loginUser.getUserId();
        String loginUserName=loginUser.getUserName();
        String loginUserRepositoryId=loginUser.getFileRepositoryId();
        //获取源文件的名字
        String name= myFile.getOriginalFilename();
        //分离出父文件夹的名字,得到父文件夹id
        String parentFolderId=FileUtil.getParentFolderId(fileFolderService,path,loginUserRepositoryId);
        //判断上传的文件是否已存在与数据库中
        MyFileService myFileService= (MyFileService) ServiceFactory.getService(new MyFileServiceImpl());
        List<MyFile> myFiles=myFileService.getFileByParentFolderId(parentFolderId);
        for(MyFile file:myFiles){
            if(file.getFileName().equals(name)){
                String error="上传的文件已存在";
                logger.error(error);
                throw new FileException(error);
            }
        }
        //判断是否超出文件仓库最大容量，先要知道是哪个用户的仓库
        FileRepositoryService fileRepositoryService= (FileRepositoryService) ServiceFactory.getService(new FileRepositoryServiceImpl());
        FileRepository repository= fileRepositoryService.getRepositoryByUserId(loginUserId);
        Double fileSize= (double) myFile.getSize();
        if((repository.getCurrentSize()+fileSize/1024.0)>repository.getMaxSize()){
            String error="超出文件仓库最大容量";
            logger.error(error);
            throw new FileException(error);
        }
        //文件最后要储存的路径
        String filePath=formerPath+loginUserName+path;
        //上传文件到指定目录
        FileUtil.uploadFile(myFile,filePath,name);
        //没抛出异常说明上传成功
        logger.info("文件上传至本地成功");
        //接下来要把文件信息存数据库
        //获取上传时间
        LocalDateTime uploadTime=LocalDateTime.now();
        MyFile file=new MyFile();
        file.setUploadTime(uploadTime);
        file.setFileId(UUIDUtil.getUUID());
        file.setFileName(name);
        file.setFileRepositoryId(loginUserRepositoryId);
        file.setParentFolderId(parentFolderId);
        file.setFilePath(path); //只存前端给的path
        file.setSize(fileSize);
        file.setType(myFile.getContentType());
        file.setState("1");
        if(myFileService.insertFile(file)){
            logger.info("用户文件上传至数据库成功！");
            ResultMessageUtil.setSuccess(result);
            return result;
        }else {
            throw new FileException("文件上传至数据库失败，服务器内部错误");
        }

    }
    @RequestMapping(value = {"/user/getFileList"},method = RequestMethod.GET)
    @ResponseBody
    public List<String> getFileList(String path){
        ResultMessageUtil.removeData(result);

        return null;
    }

    @RequestMapping(value = {"/user/createFolder"},method = RequestMethod.POST)
    @ResponseBody
    public ResultVo createFolder(String path,String name) throws FileException {
        FileFolderService fileFolderService= (FileFolderService) ServiceFactory.getService(new FileFolderServiceImpl());
        String loginUserName=loginUser.getUserName();
        String loginUserRepositoryId=loginUser.getFileRepositoryId();
        //分离出父文件夹的名字,得到父文件夹id
        String parentFolderId=FileUtil.getParentFolderId(fileFolderService,path,loginUserRepositoryId);
        //判断上传的文件是否已存在与数据库中
        List<FileFolder> folders=fileFolderService.getFolderByParentFolderId(parentFolderId);
        for(FileFolder fileFolder:folders){
            if(fileFolder.getFileFolderName().equals(name)){
                String error="文件夹名重复";
                logger.error(error);
                throw new FileException(error);
            }
        }
        //执行到这里说明文件夹名未重复
        String folderPath=formerPath+loginUserName+path;
        //在本地创建对应文件夹
        new File(folderPath).mkdirs();
        FileFolder fileFolder=new FileFolder();
        fileFolder.setFileFolderId(UUIDUtil.getUUID());
        fileFolder.setFileFolderName(name);
        fileFolder.setFileRepositoryId(loginUserRepositoryId);
        fileFolder.setCreateTime(LocalDateTime.now());
        if(fileFolderService.insertFileFolder(fileFolder)){
            ResultMessageUtil.setSuccess(result);
            logger.info("用户创建文件夹成功，数据已添加至数据库");
            return result;
        }else {
            throw new FileException("数据上传数据库失败，服务器内部错误");
        }


    }
    @RequestMapping(value = {"/user/delete"},method = RequestMethod.POST)
    @ResponseBody
    public ResultVo delete(List<String> files){

        return null;
    }

}
