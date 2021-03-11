package com.project.service.impl;

import com.project.dao.MyFileDao;
import com.project.entity.MyFile;
import com.project.service.MyFileService;
import com.project.util.SqlSessionUtil;

import java.util.List;

public class MyFileServiceImpl implements MyFileService {


    @Override
    public boolean insertFile(MyFile myFile) {
        MyFileDao myFileDao=  SqlSessionUtil.getSqlSession().getMapper(MyFileDao.class);
        if(myFileDao.insertFile(myFile)==1){
            return true;
        }
        else{
            return false;
        }
    }

    @Override
    public List<MyFile> getFileByParentFolderId(String parentFolderId) {
        MyFileDao myFileDao=  SqlSessionUtil.getSqlSession().getMapper(MyFileDao.class);
        return myFileDao.getFileByParentFolderId(parentFolderId);
    }

    @Override
    public boolean updateFileName(MyFile myFile) {
        MyFileDao myFileDao=  SqlSessionUtil.getSqlSession().getMapper(MyFileDao.class);
        if(myFileDao.updateFileName(myFile)==1){
            return true;
        }
        else{
            return false;
        }

    }

    @Override
    public MyFile getFileByPath(String filePath) {
        MyFileDao myFileDao=  SqlSessionUtil.getSqlSession().getMapper(MyFileDao.class);
        return myFileDao.getFileByPath(filePath);
    }

    @Override
    public boolean deleteFile(MyFile myFile) {
        MyFileDao myFileDao=  SqlSessionUtil.getSqlSession().getMapper(MyFileDao.class);
        if(myFileDao.deleteFile(myFile)==1){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public boolean deleteFileByPath(List<String> filePath) {
        MyFileDao myFileDao=  SqlSessionUtil.getSqlSession().getMapper(MyFileDao.class);
        if(myFileDao.deleteFileByPath(filePath)==1){
            return true;
        }
        else{
            return false;
        }
    }
}
