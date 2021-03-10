package com.project.controller;

import com.project.entity.User;

import com.project.vo.ResultVo;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@Controller
public class BaseController {
    protected ResultVo result=new ResultVo();
    protected HttpSession session;

    protected HttpServletResponse response;
    protected HttpServletRequest request;
    protected User loginUser;
    protected String formerPath="/home/cloudDisk/www/user/";
    protected String avatarPath="http://120.25.105.43:10219/avatars/";
//所有controller方法执行前先初始化
@ModelAttribute
    public void setResAndReq(HttpServletRequest request,HttpServletResponse response){
        this.request=request;
        this.response=response;
        this.session=request.getSession();
        this.loginUser= (User) session.getAttribute("loginUser");  //得到当前的登录对象

    }
}
