package com.project.dao;

import com.project.entity.MyFile;

public interface MyFileDao {
    /**
     * 添加文件
     * @param myFile
     * @return 数据库影响的行数
     */
    Integer insertFile(MyFile myFile);

}
