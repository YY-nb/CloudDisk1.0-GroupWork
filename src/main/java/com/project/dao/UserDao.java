package com.project.dao;

import com.project.entity.User;


public interface UserDao {
    /**
     * 注册添加用户
     * @param user
     * @return 插入操作的影响行数
     */
    Integer insertUser(User user);

    /**
     * 登录验证用户 或注册时检测邮箱是否已被注册
     * @param email
     * @return User
     */
    User selectByEmail( String email);

    /**
     *注册时检测用户名是否已被注册
     * @param userName
     * @return User
     */
    User selectByUserName(String userName);

    /**
     * 根据文件仓库id查用户
     * @param fileRepositoryId
     * @return
     */
    User selectByRepositoryId(String fileRepositoryId);
    /**
     * 修改用户
     * @param user
     * @return  数据库影响行数
     */
    Integer updateUser(User user);

    /**
     * 通过id删除用户
     * @param userId
     * @return 数据库影响行数
     */
    Integer deleteUserById(String userId);


}
