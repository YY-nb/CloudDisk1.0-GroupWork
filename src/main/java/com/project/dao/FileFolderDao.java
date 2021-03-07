package com.project.dao;

import com.project.entity.FileFolder;
import org.apache.ibatis.annotations.Param;

public interface FileFolderDao {
    /**
     * 添加文件夹
     * @param fileFolder
     * @return 数据库影响的行数
     */
    Integer insertFileFolder(FileFolder fileFolder);

    /**
     *根据文件夹名和文件仓库id来定位文件夹
     * @param fileFolderName
     * @param fileRepositoryId
     * @return FileFolder
     */
    FileFolder selectFolderByNameAndRepository(@Param("filerFolderName") String fileFolderName, @Param("fileRepositoryId") String fileRepositoryId);
}
