package com.project.controller;


import com.project.entity.FileRepository;
import com.project.entity.User;
import com.project.exception.LoginException;
import com.project.exception.RegisterException;
import com.project.util.*;
import com.project.vo.ResultVo;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;

@Controller
public class UserController extends BaseController{
    private Logger logger= LogUtil.getInstance(UserController.class);

    @RequestMapping(value = {"/user/login"},produces = {"application/json;charset=utf-8"})
    @ResponseBody
    public ResultVo login(User user) throws LoginException {
        String email=user.getEmail();
        String password=user.getPassword();
        User userByEmail=userService.selectByEmail(email);
        if(userByEmail==null){
            throw new LoginException("邮箱未注册");
        } else {
            if(!userByEmail.getPassword().equals(password)){
                throw new LoginException("密码错误");
            }
            else{
                //执行到此步说明登录验证成功
                logger.info("登录状态：已登录");
                //分配token给前端
                String token= TokenUtil.sign(userByEmail.getEmail(), DateTimeUtil.getDateTime());
                ResultMessageUtil.setSuccessByString(token,result);
                return result;
            }
        }



    }
    @RequestMapping(value = {"/user/checkName"},produces = {"application/json;charset=utf-8"})
    @ResponseBody
    public ResultVo checkName(String userName) throws RegisterException {
        //没找到重复名字
        if(!userService.checkUserName(userName)) {
            ResultMessageUtil.setSuccess(result);
            logger.info("验证成功，用户名未重复");
            return result;
        }
        else{
            throw new RegisterException("用户名重复");


        }
    }
    @RequestMapping(value={"/user/getCode"},produces = {"application/json;charset=utf-8"})
    @ResponseBody
    public ResultVo validCode(String email) throws RegisterException {
        if(!userService.checkUserEmail(email)){
            //执行到这里说明邮箱未重复
            //生成验证码发送邮箱
            String authCode= MailUtil.createCode();
            MailUtil.sendEmail(email,authCode);
            logger.info("注册验证码已发送");
            //把验证码存入session中，定时2分钟
            session.setAttribute("authCode",authCode);
            session.setMaxInactiveInterval(120);
            ResultMessageUtil.setSuccess(result);
            return result;
        }
        else{
            throw new RegisterException("邮箱重复");
        }

    }

    @RequestMapping(value = {"/user/register"},produces = {"application/json;charset=utf-8"})
    @ResponseBody
    public  ResultVo register(String userName,String email,String password,String authCode,String avatar) throws RegisterException {
        String code= (String) session.getAttribute("authCode");
        //检查验证码
        if(!authCode.equals(code)){
            String error="验证码错误或过期";
            ResultMessageUtil.setSuccessByString(error,result);
            return  result;
        }
        User user=new User();
        user.setUserName(userName.trim());//用户名去空格
        user.setEmail(email);
        user.setPassword(password);
        user.setAvatar(avatar);
        user.setRole("1");
        //生成UUID
        String id=UUIDUtil.getUUID();
        user.setId(id);
        user.setFileRepositoryId(id);
        user.setRegisterTime(LocalDateTime.now());
        //初始化用户专属 的文件仓库
        FileRepository repository=new FileRepository();
        repository.setFileRepositoryId(id);
        repository.setUserId(id);
        if(userService.register(user)){
            if(fileRepositoryService.insertRepository(repository)){
                ResultMessageUtil.setSuccess(result);
                logger.info("用户注册成功！");
                logger.info("用户专属文件仓库创建成功！");
                return result;
            }
            else {
                //，文件仓库添加错误，先把之前添加的用户删掉
                userService.deleteUserById(id);
                throw new RegisterException("注册失败，服务器内部出错");
            }
        }
        else {
            //此时是添加用户的过程中出错
            throw new RegisterException("注册失败，服务器内部出错");
        }
    }



}
