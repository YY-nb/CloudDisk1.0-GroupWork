package com.project.service.impl;

import com.project.dao.FileFolderDao;
import com.project.entity.FileFolder;
import com.project.service.FileFolderService;
import com.project.util.SqlSessionUtil;

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
}
