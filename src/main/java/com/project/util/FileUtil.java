package com.project.util;

import com.project.entity.FileFolder;
import com.project.entity.MyFile;
import com.project.exception.FileException;
import com.project.service.FileFolderService;
import com.project.service.MyFileService;
import org.slf4j.Logger;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

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
}
