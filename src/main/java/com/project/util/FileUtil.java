package com.project.util;

import com.project.service.FileFolderService;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

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

}
