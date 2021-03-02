package com.project.service.impl;

import com.project.dao.FileRepositoryDao;
import com.project.entity.FileRepository;
import com.project.exception.RegisterException;
import com.project.service.FileRepositoryService;
import com.project.util.SqlSessionUtil;

public class FileRepositoryServiceImpl  implements FileRepositoryService {
    private FileRepositoryDao fileRepositoryDao= SqlSessionUtil.getSqlSession().getMapper(FileRepositoryDao.class);
    @Override
    public boolean insertRepository(FileRepository fileRepository)  {
        if(fileRepositoryDao.insertRepository(fileRepository)==1){
            return true;
        }else {
            return false;
        }
    }
}
