package com.project.service;

import com.project.entity.Admin;

public interface AdminService {
    /**
     * 查询是否有该管理员
     * @param name
     *
     * @return
     */
    Admin selectAdminByName(String name);

}
