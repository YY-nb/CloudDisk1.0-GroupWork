package com.project.service;

import com.project.entity.FileRepository;
import com.project.exception.RegisterException;

public interface FileRepositoryService {
    /**
     * 添加文件仓库
     * @param fileRepository
     * @return 是否添加成功
     */
    boolean insertRepository(FileRepository fileRepository) ;
}
