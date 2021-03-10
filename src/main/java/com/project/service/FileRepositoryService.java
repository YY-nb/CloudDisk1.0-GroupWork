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

    /**
     * 通过id查文件仓库
     * @param userId
     * @return fileRepository
     */
    FileRepository getRepositoryByUserId(String userId);

    /**
     * 修改仓库当前容量
     * @param fileRepository
     * @return  是否修改成功
     */
    boolean updateSize(FileRepository fileRepository);
}
