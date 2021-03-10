package com.project.dao;

import com.project.entity.FileRepository;
import com.project.entity.User;

public interface FileRepositoryDao {
    /**
     * 添加文件仓库
     * @param repository
     * @return 数据库影响的行数
     */
    Integer insertRepository(FileRepository repository);

    /**
     * 通过用户名查询对应的文件仓库
     * @param userId
     * @return 查询出的文件仓库
     */
    FileRepository getRepositoryByUserId(String userId);

    /**
     * 更改仓库当前容量
     * @param fileRepository
     * @return 数据库影响的行数
     */
    Integer updateSize(FileRepository fileRepository);
}
