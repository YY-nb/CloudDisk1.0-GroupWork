package com.project.service;

import com.project.entity.FileFolder;

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
}
