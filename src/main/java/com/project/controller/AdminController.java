package com.project.controller;

import com.project.entity.Admin;
import com.project.entity.MyFile;
import com.project.exception.LoginException;
import com.project.service.AdminService;
import com.project.service.impl.AdminServiceImpl;
import com.project.util.*;
import com.project.vo.ResultVo;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class AdminController extends BaseController{
    private Logger logger= LogUtil.getInstance(AdminController.class);
    @RequestMapping(value = {"/admin/login"})
    @ResponseBody
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
    public ResultVo getCheckList(){
        ResultMessageUtil.removeData(result);
        List<MyFile> fileToCheck= FileRepositoryController.getFileToCheck();
        List<Map<String,String>> responseList=new ArrayList<>();


        return null;
    }

    @RequestMapping(value = {"/admin/checkFile"},method = RequestMethod.GET)
    @ResponseBody
    public ResultVo checkFile(String path){

        return null;
    }
}
