package com.project.service.impl;

import com.project.entity.FileRepository;
import com.project.exception.RegisterException;
import com.project.service.FileRepositoryService;

public class FileRepositoryServiceImpl extends BaseService implements FileRepositoryService {
    @Override
    public boolean insertRepository(FileRepository fileRepository)  {
        if(fileRepositoryDao.insertRepository(fileRepository)==1){
            return true;
        }else {
            return false;
        }
    }
}
