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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@CrossOrigin(origins = {"http://120.25.105.43"})
public class FileRepositoryController extends BaseController{
    private Logger logger= LogUtil.getInstance(FileRepositoryController.class);

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
        FileUtil.checkFileName(myFileService,name,parentFolderId,logger);
        //判断是否超出文件仓库最大容量，先要知道是哪个用户的仓库
        FileRepositoryService fileRepositoryService= (FileRepositoryService) ServiceFactory.getService(new FileRepositoryServiceImpl());
        FileRepository repository= fileRepositoryService.getRepositoryByUserId(loginUserId);
        Double fileSize= (double) myFile.getSize();
        repository.setCurrentSize(repository.getCurrentSize()+fileSize/1024.0);
        if(repository.getCurrentSize()>repository.getMaxSize()){
            String error="超出文件仓库最大容量";
            logger.error(error);
            throw new FileException(error);
        }
        //文件最后要储存的目录
        String filePath=formerPath+loginUserName+path;
        //上传文件到指定目录
        FileUtil.uploadFile(myFile,filePath,name);
        //没抛出异常说明上传成功
        logger.info("文件上传至本地成功");
        //上传文件成功要修改文件仓库大小
        if(fileRepositoryService.updateSize(repository)){
            logger.info("文件仓库容量已更新");
        }
        else {
            logger.error("文件仓库容量修改失败");
            throw new FileException("修改仓库容量时出错,服务器内部错误");
        }
        //接下来要把文件信息存数据库
        //获取上传时间
        LocalDateTime uploadTime=LocalDateTime.now();
        MyFile file=new MyFile();
        file.setUploadTime(uploadTime);
        file.setFileId(UUIDUtil.getUUID());
        file.setFileName(name);
        file.setFileRepositoryId(loginUserRepositoryId);
        file.setParentFolderId(parentFolderId);
        file.setFilePath(filePath+"/"+name);
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
    public ResultVo getFileList(String path) throws FileException {
        ResultMessageUtil.removeData(result);
        MyFileService myFileService= (MyFileService) ServiceFactory.getService(new MyFileServiceImpl());
        FileFolderService fileFolderService= (FileFolderService) ServiceFactory.getService(new FileFolderServiceImpl());
        String loginUserName=loginUser.getUserName();
        String loginUserRepositoryId=loginUser.getFileRepositoryId();
        String filePath=formerPath+loginUserName+path;

        //分离出父文件夹的名字,得到父文件夹id
        String parentFolderId=FileUtil.getParentFolderId(fileFolderService,path,loginUserRepositoryId);
        //获取当前目录下的所有文件夹
        List<MyFile> files=myFileService.getFileByParentFolderId(parentFolderId);
        if(files==null){
            throw new FileException("当前目录下没有文件");
        }
        List<FileFolder> folders=fileFolderService.getFolderByParentFolderId(parentFolderId);

        List<Map<String,String>> responseList=new ArrayList<>(); //给前端的响应列表，包含文件或文件夹的相关信息
        for(MyFile file:files){
            Map<String,String> dataMap=new HashMap<>();
            dataMap.put("date",DateTimeUtil.timeToString(file.getUploadTime()));
            dataMap.put("creator",loginUserName);
            dataMap.put("name",file.getFileName());
            dataMap.put("path",file.getFilePath());
            dataMap.put("size",String.valueOf(file.getSize()));
            dataMap.put("type","file");
            responseList.add(dataMap);
        }
        for(FileFolder folder:folders){
            Map<String,String> dataMap=new HashMap<>();
            dataMap.put("date",DateTimeUtil.timeToString(folder.getCreateTime()));
            dataMap.put("creator",loginUserName);
            dataMap.put("name",folder.getFileFolderName());
            dataMap.put("path",folder.getFileFolderPath());
            dataMap.put("type","dir");
            responseList.add(dataMap);
        }
        ResultMessageUtil.setSuccess(result);
        ResultMessageUtil.setDataByString("items",responseList,result);
        return result;
    }

    @RequestMapping(value = {"/user/createFolder"},method = RequestMethod.POST)
    @ResponseBody
    public ResultVo createFolder(String path,String name) throws FileException {
        ResultMessageUtil.removeData(result);
        FileFolderService fileFolderService= (FileFolderService) ServiceFactory.getService(new FileFolderServiceImpl());
        String loginUserName=loginUser.getUserName();
        String loginUserRepositoryId=loginUser.getFileRepositoryId();
        //分离出父文件夹的名字,得到父文件夹id
        String parentFolderId=FileUtil.getParentFolderId(fileFolderService,path,loginUserRepositoryId);
        //判断上传的文件夹是否已存在与数据库中
        FileUtil.checkFolderName(fileFolderService,name,parentFolderId,logger);
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
    @RequestMapping(value = {"/user/updateName"},method = RequestMethod.POST)
    @ResponseBody
    public ResultVo updateName(String path,String newName,String type) throws FileException {
        ResultMessageUtil.removeData(result);
        String userName=loginUser.getUserName();
        String filePath=formerPath+userName+path;
        //处理文件
        if(type.equals("file")){
            MyFileService myFileService= (MyFileService) ServiceFactory.getService(new MyFileServiceImpl());
            MyFile myFile= myFileService.getFileByPath(filePath);
            if(myFile==null){
                String error="找不到该文件";
                logger.error(error);
                throw new FileException(error);
            }
            else {
                //检查新文件名有无重复
                FileUtil.checkFileName(myFileService,newName,myFile.getParentFolderId(),logger);
                //修改服务器上图片储存的地址
                String newPath = formerPath + userName + path.substring(0, path.lastIndexOf("/") + 1) + newName;
                FileUtil.reName(newPath,filePath);
                //修改数据库数据
                String oldName=myFile.getFileName();
                myFile.setFileName(newName);
                if(myFileService.updateFileName(myFile)){
                    logger.info(String.format("文件重命名成功，原名字为%s，新名字为%s",oldName,myFile.getFileName()));
                    ResultMessageUtil.setSuccess(result);
                    return result;
                }else {
                    throw new FileException("重命名失败，服务器内部错误");
                }
            }
        }
        //处理文件夹
        else  {
            FileFolderService fileFolderService = (FileFolderService) ServiceFactory.getService(new FileFolderServiceImpl());
            FileFolder fileFolder = fileFolderService.getFolderByPath(filePath);
            if (fileFolder == null) {
                String error = "找不到该文件夹";
                logger.error(error);
                throw new FileException(error);
            } else {
                //先检查新文件夹和当前目录下的其他文件夹名字是否重复
                List<FileFolder> folders = fileFolderService.getFolderByParentFolderId(fileFolder.getParentFolderId());
                for (FileFolder folder : folders) {
                    if (folder.getFileFolderName().equals(newName)) {
                        String error = "新文件夹与其他文件夹名字重复";
                        logger.error(error);
                        throw new FileException(error);
                    }
                }
                //修改文件夹在本地中的名字
                String newPath = formerPath + userName + path.substring(0, path.lastIndexOf("/") + 1) + newName;
                FileUtil.reName(newPath, filePath);
                //修改数据库信息
                String oldName=fileFolder.getFileFolderName();
                fileFolder.setFileFolderName(newName);
                if(fileFolderService.updateFolderName(fileFolder)){
                    logger.info(String.format("文件夹名字修改成功，原名字为%s,新名字为%s",oldName,newName));
                    ResultMessageUtil.setSuccess(result);
                    return result;
                }else {
                    throw new FileException("文件夹重命名失败，服务器内部错误");
                }

            }

        }
    }


}
