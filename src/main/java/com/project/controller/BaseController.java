package com.project.controller;

import com.project.entity.User;
import com.project.service.FileRepositoryService;
import com.project.service.UserService;
import com.project.service.impl.FileRepositoryServiceImpl;
import com.project.service.impl.UserServiceImpl;
import com.project.util.LogUtil;
import com.project.util.ServiceFactory;
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

//所有controller方法执行前先初始化
@ModelAttribute
    public void setResAndReq(HttpServletRequest request,HttpServletResponse response){
        this.request=request;
        this.response=response;
        this.session=request.getSession();
        this.loginUser= (User) session.getAttribute("loginUser");  //得到当前的登录对象

    }
}
