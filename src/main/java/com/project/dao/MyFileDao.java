package com.project.dao;

import com.project.entity.MyFile;

import java.util.List;

public interface MyFileDao {
    /**
     * 添加文件
     * @param myFile
     * @return 数据库影响的行数
     */
    Integer insertFile(MyFile myFile);

    /**
     * 根据父文件夹id查询父文件夹下的所有文件
     * @param parentFolderId
     * @return 文件列表
     */
    List<MyFile> getFileByParentFolderId(String parentFolderId);

    /**
     * 根据路径获得所有匹配的文件
     * @param filePath
     * @return 文件列表
     */
    List<MyFile> getFileByPath(String filePath);

    /**
     * 删除文件
     * @param file
     * @return 数据库影响的行数
     */
    Integer deleteFile(MyFile file);
}
