package com.project.util;

import com.project.entity.FileFolder;
import com.project.entity.FileRepository;
import com.project.entity.MyFile;
import com.project.exception.FileException;
import com.project.service.FileFolderService;
import com.project.service.FileRepositoryService;
import com.project.service.MyFileService;
import org.slf4j.Logger;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileUtil {
    /**
     * 把文件保存到指定地址
     * @param myFile
     * @param path
     * @param fileName
     * @throws IOException
     */
    public  static void uploadFile(MultipartFile myFile,String path,String fileName) throws IOException {
        new File(path).mkdirs();
        File file =new File(path,fileName);
        myFile.transferTo(file);
    }

    /**
     * 得到文件的父文件夹id
     * @param service
     * @param path
     * @param repositoryId
     */
    public static String getParentFolderId(FileFolderService service,String path,String repositoryId){
        String parentFolderName=path.substring(path.lastIndexOf("/")+1,path.length()); //分离出父文件夹的名字
        String parentFolderId=null;
        if(parentFolderName.equals("disk")){
            parentFolderId="0";  //根目录id默认为0
        }
        else{
            parentFolderId=service.selectFolderByNameAndRepository(parentFolderName,repositoryId).getFileFolderId();
        }
        return parentFolderId;
    }

    public static  void reName(String newPath,String formerPath){
        new File(formerPath).renameTo(new File(newPath));
    }
    public static void checkFileName(MyFileService myFileService, String fileName, String parentFolderId, Logger logger) throws FileException {
        List<MyFile> myFiles=myFileService.getFileByParentFolderId(parentFolderId);
        for(MyFile file:myFiles){
            if(file.getFileName().equals(fileName)){
                String error="文件名已存在";
                logger.error(error);
                throw new FileException(error);
            }
        }
    }
    public static void checkFolderName(FileFolderService fileFolderService, String fileFolderName, String parentFolderId, Logger logger) throws FileException {
        List<FileFolder> folders = fileFolderService.getFolderByParentFolderId(parentFolderId);
        for (FileFolder folder : folders) {
            if (folder.getFileFolderName().equals(fileFolderName)) {
                String error = "新文件夹与其他文件夹名字重复";
                logger.error(error);
                throw new FileException(error);
            }
        }
    }
    public static void deleteFile(List<String> pathList, MyFileService myFileService, FileRepositoryService fileRepositoryService,FileRepository fileRepository) throws FileException {
        //执行到这里说明数据库中的相关数据删除成功，接下来删除服务器上对应位置的文件
        for(String path:pathList){
            MyFile file=myFileService.getFileByPath(path);
            if(!myFileService.deleteFile(file)){
                throw new FileException("文件删除发生异常，服务器内部错误");
            }
            //修改文件仓库容量
            fileRepository.setCurrentSize(fileRepository.getCurrentSize()-file.getSize());
            fileRepositoryService.updateSize(fileRepository);
            new File(path).delete();
        }
    }
    public static void deleteFolder(FileFolder folder,FileFolderService fileFolderService,
                MyFileService myFileService,FileRepositoryService fileRepositoryService,
                                    FileRepository fileRepository) throws FileException {
        //获取当前文件夹下的所有文件夹和文件
        List<MyFile> files=myFileService.getFileByParentFolderId(folder.getFileFolderId());
        List<FileFolder> folders=fileFolderService.getFolderByParentFolderId(folder.getFileFolderId());
        if(files!=null){
            List<String> pathList=new ArrayList<>();
            for(MyFile file:files){
                pathList.add(file.getFilePath());
            }
            deleteFile(pathList,myFileService,fileRepositoryService,fileRepository);
        }
        if (folders!=null){
            for(FileFolder fileFolder:folders){
                deleteFolder(fileFolder,fileFolderService,myFileService,fileRepositoryService,fileRepository);
            }
        }
    }
    //给传给前端的文件列表添加数据
    public static void addResponseListForFile(List<MyFile> files,List<Map<String,String>> responseList,String userName){
        for(MyFile file:files){
            Map<String,String> dataMap=new HashMap<>();
            dataMap.put("date",DateTimeUtil.timeToString(file.getUploadTime()));
            dataMap.put("creator",userName);
            dataMap.put("name",file.getFileName());
            dataMap.put("path",file.getFilePath());
            dataMap.put("size",String.valueOf(file.getSize()));
            dataMap.put("type","file");
            responseList.add(dataMap);
        }

    }
    public static void addResponseListForFolder(List<FileFolder>folders,List<Map<String,String>>responseList,String userName){
        for(FileFolder folder:folders){
            Map<String,String> dataMap=new HashMap<>();
            dataMap.put("date",DateTimeUtil.timeToString(folder.getCreateTime()));
            dataMap.put("creator",userName);
            dataMap.put("name",folder.getFileFolderName());
            dataMap.put("path",folder.getFileFolderPath());
            dataMap.put("type","dir");
            responseList.add(dataMap);
        }
    }
}
