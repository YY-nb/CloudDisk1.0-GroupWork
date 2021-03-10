package com.project.dao;

import com.project.entity.Admin;
import org.apache.ibatis.annotations.Param;

public interface AdminDao {
    /**
     * 查询管理员
     * @param name
     * @param password
     * @return Admin
     */
    Admin selectAdminByName(String name);
}
