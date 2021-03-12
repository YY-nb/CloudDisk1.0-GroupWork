package com.project.service.impl;

import com.project.dao.UserDao;
import com.project.entity.User;
import com.project.service.UserService;
import com.project.util.SqlSessionUtil;

public class  UserServiceImpl   implements UserService {


    @Override
    public boolean register(User user)  {
        UserDao userDao= SqlSessionUtil.getSqlSession().getMapper(UserDao.class);
        if(userDao.insertUser(user)==1){
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public User selectByEmail(String email) {
        UserDao userDao= SqlSessionUtil.getSqlSession().getMapper(UserDao.class);
        return userDao.selectByEmail(email);

    }

    @Override
    public boolean checkUserEmail(String email)  {
        UserDao userDao= SqlSessionUtil.getSqlSession().getMapper(UserDao.class);
        if(userDao.selectByEmail(email)==null){
            return false;
        }
        else {
            return true;
        }
    }

    @Override
    public boolean checkUserName(String userName)  {
        UserDao userDao= SqlSessionUtil.getSqlSession().getMapper(UserDao.class);
        if(userDao.selectByUserName(userName)!=null){
            return true;

        }
        return false;
    }

    @Override
    public boolean updateUser(User user) {
        UserDao userDao= SqlSessionUtil.getSqlSession().getMapper(UserDao.class);
        if(userDao.updateUser(user)==1){
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public boolean deleteUserById(String userId) {
        UserDao userDao= SqlSessionUtil.getSqlSession().getMapper(UserDao.class);
        if(userDao.deleteUserById(userId)==1){
            return  true;
        }
        else{
            return false;
        }
    }

    @Override
    public User selectByRepositoryId(String fileRepositoryId) {
        UserDao userDao= SqlSessionUtil.getSqlSession().getMapper(UserDao.class);
        return userDao.selectByRepositoryId(fileRepositoryId);
    }


}
