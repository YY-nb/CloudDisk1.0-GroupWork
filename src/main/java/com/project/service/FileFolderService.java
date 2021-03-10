package com.project.service;

import com.project.entity.FileFolder;

import java.util.List;

public interface FileFolderService {
    /**
     * 添加文件夹
     * @param fileFolder
     * @return 是否成功
     */
    boolean insertFileFolder(FileFolder fileFolder);

    /**
     * 根据文件夹名和仓库id查询
     * @param folderName
     * @param fileRepositoryId
     * @return
     */
    FileFolder selectFolderByNameAndRepository(String folderName,String fileRepositoryId);

    /**
     * 根据父文件夹id查当前目录下所有文件夹
     * @param parentFolderId
     * @return
     */
    List<FileFolder> getFolderByParentFolderId(String parentFolderId);

    /**
     * 修改文件夹名字
     * @param fileFolderName
     * @return 是否成功
     */
    boolean updateFolderName(FileFolder folder);

    /**
     * 根据路径查文件夹
     * @param fileFolderPath
     * @return FileFolder
     */
    FileFolder getFolderByPath(String fileFolderPath);
}
