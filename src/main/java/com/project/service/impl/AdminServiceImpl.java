package com.project.service.impl;

import com.project.dao.AdminDao;
import com.project.entity.Admin;
import com.project.service.AdminService;
import com.project.util.SqlSessionUtil;

public class AdminServiceImpl implements AdminService {
    @Override
    public Admin selectAdminByName(String name) {
        AdminDao adminDao= SqlSessionUtil.getSqlSession().getMapper(AdminDao.class);
        return adminDao.selectAdminByName(name);


    }
}
