package com.project.service;

import com.project.entity.User;
import com.project.exception.LoginException;
import com.project.exception.RegisterException;

public interface UserService {
    /**
     * 用户注册
     * @param user
     * @return 注册是否成功
     */
    boolean register(User user) ;

    /**
     * 验证登录信息是否与数据库中相同
     * @param email
     * @return 查询到的用户
     */
    User selectByEmail(String email) ;

    /**
     * 注册之前先验证填写的邮箱是否存在于数据库中，返回false代表未查到相关数据，之后可以正常注册
     * @param email
     * @return  注册时数据是否已存在于数据库中

     */
    boolean checkUserEmail(String email) ;

    /**
     * 注册之前先验证填写的用户名是否存在于数据库中，返回false代表未查到相关数据，之后可以正常注册
     * @param userName
     * @return 注册时数据是否已存在于数据库中

     */
    boolean checkUserName(String userName) ;

    /**
     * 更新用户数据
     * @param user
     * @return 是否成功
     */
    boolean updateUser(User user) ;

    /**
     * 通过id删除用户
     * @param userId
     * @return 是否成功
     */
    boolean deleteUserById(String userId) ;


}
