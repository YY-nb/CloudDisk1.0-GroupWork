package com.project.controller;

import com.project.entity.Admin;
import com.project.entity.MyFile;
import com.project.entity.User;
import com.project.exception.AdminException;
import com.project.exception.LoginException;
import com.project.service.AdminService;
import com.project.service.MyFileService;
import com.project.service.UserService;
import com.project.service.impl.AdminServiceImpl;
import com.project.service.impl.MyFileServiceImpl;
import com.project.service.impl.UserServiceImpl;
import com.project.util.*;
import com.project.vo.ResultVo;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@CrossOrigin(origins = {"http://120.25.105.43"})
public class AdminController extends BaseController{
    private Logger logger= LogUtil.getInstance(AdminController.class);
    @RequestMapping(value = {"/admin/login"})
    @ResponseBody
    @CrossOrigin(methods = RequestMethod.POST)
    public ResultVo login(String name,String password) throws LoginException {
        ResultMessageUtil.removeData(result);
        AdminService adminService= (AdminService) ServiceFactory.getService(new AdminServiceImpl());
        Admin admin=adminService.selectAdminByName(name);
        if(admin==null){
            throw new LoginException("管理员名字错误");
        }else {
            if(!admin.getPassword().equals(password)){
                throw new LoginException("管理员密码错误");
            }else {
                logger.info("管理员已登录");
                String token= TokenUtil.sign(name, DateTimeUtil.getDateTime());
                ResultMessageUtil.setSuccess(result);
                ResultMessageUtil.setDataByString("name",name,result);
                ResultMessageUtil.setDataByString("token",token,result);
                session.setAttribute("loginAdmin",admin);
                return result;
            }

        }

    }
    @RequestMapping(value = {"/admin/getCheckList"},method =RequestMethod.GET)
    @ResponseBody
    @CrossOrigin(methods = RequestMethod.GET)
    public ResultVo getCheckList() throws AdminException {
        ResultMessageUtil.removeData(result);
        List<MyFile> fileToCheck= FileRepositoryController.getFileToCheck();
        List<Map<String,String>> responseList=new ArrayList<>();
        UserService userService= (UserService) ServiceFactory.getService(new UserServiceImpl());
        for(MyFile file:fileToCheck){
            User user=userService.selectByRepositoryId(file.getFileRepositoryId());  //根据文件的仓库id找出对应用户，最后需要得到文件的主人名
            if(user==null){
                throw new AdminException("用户信息出错，服务器内部错误");
            }
            FileUtil.addResponseListForFile(file,responseList,user.getUserName());
        }
        ResultMessageUtil.setSuccess(result);
        ResultMessageUtil.setDataByString("item",responseList,result);
        return result;
    }

    @RequestMapping(value = {"/admin/checkFile"},method = RequestMethod.GET)
    @ResponseBody
    @CrossOrigin(methods = RequestMethod.POST)
    public ResultVo checkFile(List<String> files){
        ResultMessageUtil.removeData(result);
        FileRepositoryController.getFileToCheck();
        MyFileService myFileService= (MyFileService) ServiceFactory.getService(new MyFileServiceImpl());
        for(String filePath:files){
            MyFile file=myFileService.getFileByPath(filePath);
            file.setState("1"); //1代表审核通过
            myFileService.updateFile(file);  //修改数据库中信息
            FileRepositoryController.removeFileInCheck(file); //从待审核表中删除对应文件
        }
        logger.info("已删除待审核表中通过审核的文件");
        ResultMessageUtil.setSuccess(result);
        return null;
    }
}
