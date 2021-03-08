package com.project.controller;


import com.project.entity.FileRepository;
import com.project.entity.User;
import com.project.exception.LoginException;
import com.project.exception.RegisterException;
import com.project.exception.UpdateException;
import com.project.service.FileRepositoryService;
import com.project.service.UserService;
import com.project.service.impl.FileRepositoryServiceImpl;
import com.project.service.impl.UserServiceImpl;
import com.project.util.*;
import com.project.vo.ResultVo;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;

@Controller
@CrossOrigin(origins = {"http://120.25.105.43"})
public class UserController extends BaseController{
    private Logger logger= LogUtil.getInstance(UserController.class);


    @RequestMapping(value = {"/user/login"},produces = {"application/json;charset=utf-8"},method=RequestMethod.POST)
    @ResponseBody
    @CrossOrigin(methods = RequestMethod.POST)
    public ResultVo login(User user) throws LoginException {
        ResultMessageUtil.removeData(result);
        String email=user.getEmail();
        String password=user.getPassword();
        UserService userService= (UserService) ServiceFactory.getService(new UserServiceImpl());
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
                ResultMessageUtil.setSuccess(result);
                ResultMessageUtil.setDataByString("email",userByEmail.getEmail(),result);
                ResultMessageUtil.setDataByString("userName",userByEmail.getUserName(),result);
                ResultMessageUtil.setDataByString("token",token,result);
                session.setAttribute("loginUser",userByEmail);
                return result;
            }
        }



    }
    @RequestMapping(value = {"/user/checkName"},produces = {"application/json;charset=utf-8"},method = RequestMethod.GET)
    @ResponseBody
    @CrossOrigin(methods = RequestMethod.GET)
    public ResultVo checkName(String userName) throws RegisterException {
        UserService userService= (UserService) ServiceFactory.getService(new UserServiceImpl());
        //没找到重复名字
        if(!userService.checkUserName(userName)) {
            ResultMessageUtil.setSuccess(result);
            logger.info("验证成功，用户名未重复");
            return result;
        }
        else{
            throw new RegisterException("用户名已存在");


        }
    }



    @RequestMapping(value={"/user/getCode"},produces = {"application/json;charset=utf-8"},method=RequestMethod.POST)
    @ResponseBody
    @CrossOrigin(methods = RequestMethod.POST)
    public ResultVo validCode(String email) throws RegisterException {
        UserService userService= (UserService) ServiceFactory.getService(new UserServiceImpl());
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
            throw new RegisterException("邮箱已存在，请更换邮箱");
        }

    }

    @RequestMapping(value = {"/user/register"},produces = {"application/json;charset=utf-8"},method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin(methods = RequestMethod.POST)
    public  ResultVo register(String userName, String email, String password, String authCode, MultipartFile avatar) throws RegisterException, IOException {
        String code= (String) session.getAttribute("authCode");
        //检查验证码
        if(!authCode.equals(code)){
            String error="验证码错误或过期";
            ResultMessageUtil.setSuccessByString(error,result);
            return  result;
        }
        User user=new User();
        //用户名去空格
        user.setUserName(userName.trim());
        user.setEmail(email);
        user.setPassword(password);
        user.setRole("1");
        //生成UUID
        String id=UUIDUtil.getUUID();
        user.setId(id);
        user.setFileRepositoryId(id);
        user.setRegisterTime(LocalDateTime.now());

        //文件最后要储存的路径
        String filePath=formerPath+userName+"/avatar";
        //上传文件到指定目录
        FileUtil.uploadFile(avatar,filePath,avatar.getOriginalFilename());
        //没抛出异常说明上传成功
        logger.info("头像上传至本地成功");
        user.setAvatar(filePath);
        //初始化用户专属 的文件仓库
        FileRepository repository=new FileRepository();
        repository.setFileRepositoryId(id);
        repository.setUserId(id);
        UserService userService= (UserService) ServiceFactory.getService(new UserServiceImpl());


       if(userService.register(user)){
            //用户注册成功后为用户创建文件仓库
            FileRepositoryService fileRepositoryService= (FileRepositoryService) ServiceFactory.getService(new FileRepositoryServiceImpl());
            if(fileRepositoryService.insertRepository(repository)){
                ResultMessageUtil.setSuccess(result);
                logger.info("用户注册成功！");
                logger.info("用户专属文件仓库创建成功！");
                return result;
            }
            else {
                //文件仓库添加错误，先把之前添加的用户删掉
                userService.deleteUserById(id);
                throw new RegisterException("注册失败，服务器内部出错1");
            }
        }
        else {
            //此时是添加用户的过程中出错
            throw new RegisterException("注册失败，服务器内部出错2");
        }
    }

    @RequestMapping(value = {"/user/update"},method = RequestMethod.GET)
    @ResponseBody
    @CrossOrigin(methods = RequestMethod.GET)
    public ResultVo update(User user) throws UpdateException {
        ResultMessageUtil.removeData(result);
        UserService userService= (UserService) ServiceFactory.getService(new UserServiceImpl());
        String userName=user.getUserName();
        String email=user.getEmail();
        StringBuffer error=new StringBuffer();
        boolean flag=true;
        //先检查昵称邮箱是否重复
        if(userName!=null){
            if(userService.checkUserName(userName)){
                 error.append("用户名已存在 ");
                 flag=false;

            }
        }
        if(email!=null){
            if(userService.checkUserName(email)){
                error.append("邮箱已存在 ");
                flag=false;
            }
        }
        if(!flag){
            throw new UpdateException(error.toString());
        }
        else {
            if (userService.updateUser(user)) {
                logger.info("用户信息修改成功");
                ResultMessageUtil.setSuccess(result);
                String token=TokenUtil.sign(email,DateTimeUtil.getDateTime());
                ResultMessageUtil.setDataByString("email",email,result);
                ResultMessageUtil.setDataByString("userName",userName,result);
                ResultMessageUtil.setDataByString("token",token,result);
                return result;
            } else {
                throw new UpdateException("更新失败，服务器内部错误");
            }
        }
    }





}
