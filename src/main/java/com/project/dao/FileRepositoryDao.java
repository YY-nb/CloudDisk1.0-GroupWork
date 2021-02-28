package com.project.dao;

import com.project.entity.FileRepository;

public interface FileRepositoryDao {
    /**
     * 添加文件仓库
     * @param repository
     * @return 数据库影响的行数
     */
    Integer insertRepository(FileRepository repository);
}
