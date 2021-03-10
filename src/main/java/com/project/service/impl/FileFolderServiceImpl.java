package com.project.service.impl;

import com.project.dao.FileFolderDao;
import com.project.entity.FileFolder;
import com.project.service.FileFolderService;
import com.project.util.SqlSessionUtil;

import java.util.List;

public class FileFolderServiceImpl implements FileFolderService {

    @Override
    public boolean insertFileFolder(FileFolder fileFolder) {
        FileFolderDao fileFolderDao= SqlSessionUtil.getSqlSession().getMapper(FileFolderDao.class);
        if(fileFolderDao.insertFileFolder(fileFolder)==1){
            return true;
        }
        else{
            return  false;
        }

    }

    @Override
    public FileFolder selectFolderByNameAndRepository(String folderName, String fileRepositoryId) {
        FileFolderDao fileFolderDao= SqlSessionUtil.getSqlSession().getMapper(FileFolderDao.class);
        return fileFolderDao.selectFolderByNameAndRepository(folderName,fileRepositoryId);
    }

    @Override
    public List<FileFolder> getFolderByParentFolderId(String parentFolderId) {
        FileFolderDao fileFolderDao= SqlSessionUtil.getSqlSession().getMapper(FileFolderDao.class);
        return fileFolderDao.getFolderByParentFolderId(parentFolderId);
    }

    @Override
    public boolean updateFolderName( FileFolder folder) {
        FileFolderDao fileFolderDao= SqlSessionUtil.getSqlSession().getMapper(FileFolderDao.class);
        if(fileFolderDao.updateFolderName(folder)==1){
            return  true;
        }else {
            return false;
        }

    }

    @Override
    public FileFolder getFolderByPath(String fileFolderPath) {
        FileFolderDao fileFolderDao= SqlSessionUtil.getSqlSession().getMapper(FileFolderDao.class);
        return fileFolderDao.getFolderByPath(fileFolderPath);
    }
}
