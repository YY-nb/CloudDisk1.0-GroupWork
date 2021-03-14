package com.project.dao;

import com.project.entity.FileFolder;
import org.apache.ibatis.annotations.Param;

import javax.mail.Folder;
import java.util.List;

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
    FileFolder selectFolderByNameAndRepository(@Param("fileFolderName") String fileFolderName, @Param("fileRepositoryId") String fileRepositoryId);

    /**
     * 根据父文件夹id查询文件夹
     * @param parentFolderId
     * @return FileFolder
     */
    List<FileFolder> getFolderByParentFolderId(String parentFolderId);
    /**
     * 根据文件仓库id查根目录下的所有文件
     * @param fileRepositoryId
     */
    List<FileFolder> getRootFolderByRepositoryId(String fileRepositoryId);
    /**
     * 修改文件夹名字
     * @param folder
     * @return 数据库影响的行数
     */
    Integer updateFolder( FileFolder folder);

    /**
     * 根据路径获取文件夹
     * @param fileFolderPath
     * @return
     */
    FileFolder getFolderByPath(String fileFolderPath);



}

