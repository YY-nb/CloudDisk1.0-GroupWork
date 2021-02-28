package com.project.handler;

import com.project.exception.LoginException;
import com.project.exception.RegisterException;
import com.project.util.ResultMessageUtil;
import com.project.util.LogUtil;
import com.project.vo.ResultVo;
import org.slf4j.Logger;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


@ControllerAdvice
public class GlobalExceptionHandler {
    private ResultVo result=new ResultVo();
    private Logger logger= LogUtil.getInstance(GlobalExceptionHandler.class);
    @ExceptionHandler(value = LoginException.class)
    @ResponseBody
    public ResultVo doLoginException(LoginException e){
        ResultMessageUtil.setErrorByException(e,result);
        logger.error("登录失败,{}",e.getMessage());
        return result;
    }

    @ExceptionHandler(value = RegisterException.class)
    @ResponseBody
    public ResultVo doRegisterException(RegisterException e){
        ResultMessageUtil.setErrorByException(e,result);
        logger.error(e.getMessage());
        return result;
    }

}
