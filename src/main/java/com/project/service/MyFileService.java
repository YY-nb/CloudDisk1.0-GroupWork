package com.project.service;

import com.project.entity.MyFile;

import java.util.List;

public interface MyFileService {
    /**
     * 插入文件
     * @param myFile
     * @return 是否成功
     */
    boolean insertFile(MyFile myFile);

    /**
     * 根据父文件夹id查父文件夹下的所有文件
     * @param parentFolderId
     * @return 文件列表
     */
    List<MyFile> getFileByParentFolderId(String parentFolderId);
    /**
     * 根据文件仓库id查询根目录所有文件
     * @param fileRepositoryId
     * @return 文件列表
     */
    List<MyFile> getRootFileByRepositoryId(String fileRepositoryId);

    /**
     * 修改文件
     * @param myFile
     * @return  是否成功
     */
    boolean updateFile(MyFile myFile);

    /**
     * 根据路径找文件
     * @param filePath
     * @return MyFile
     */
    MyFile getFileByPath(String filePath);

    /**
     * 删除文件
     * @param myFile
     */
    boolean deleteFile(MyFile myFile);

    /**
     * 根据路径删文件
     * @param filePath
     */
    boolean deleteFileByPath(List<String> filePath);
}
