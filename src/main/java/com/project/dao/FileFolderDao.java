package com.project.dao;

import com.project.entity.FileFolder;

public interface FileFolderDao {
    /**
     * 添加文件夹
     * @param fileFolder
     * @return 数据库影响的行数
     */
    Integer insertFileFolder(FileFolder fileFolder);
}
