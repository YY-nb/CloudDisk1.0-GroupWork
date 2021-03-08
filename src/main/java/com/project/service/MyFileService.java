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
}
