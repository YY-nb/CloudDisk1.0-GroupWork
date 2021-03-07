package com.project.service.impl;

import com.project.dao.FileRepositoryDao;
import com.project.entity.FileRepository;
import com.project.exception.RegisterException;
import com.project.service.FileRepositoryService;
import com.project.util.SqlSessionUtil;

public class FileRepositoryServiceImpl  implements FileRepositoryService {

    @Override
    public boolean insertRepository(FileRepository fileRepository)  {
        FileRepositoryDao fileRepositoryDao= SqlSessionUtil.getSqlSession().getMapper(FileRepositoryDao.class);
        if(fileRepositoryDao.insertRepository(fileRepository)==1){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public FileRepository getRepositoryByUserId(String userId) {
        FileRepositoryDao fileRepositoryDao= SqlSessionUtil.getSqlSession().getMapper(FileRepositoryDao.class);
        return fileRepositoryDao.getRepositoryByUserId(userId);
    }
}
