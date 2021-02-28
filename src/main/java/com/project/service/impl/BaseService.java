package com.project.service.impl;

import com.project.dao.FileRepositoryDao;
import com.project.dao.UserDao;
import com.project.util.SqlSessionUtil;

public class BaseService {
    protected UserDao userDao= SqlSessionUtil.getSqlSession().getMapper(UserDao.class);
    protected FileRepositoryDao fileRepositoryDao=SqlSessionUtil.getSqlSession().getMapper(FileRepositoryDao.class);
}
