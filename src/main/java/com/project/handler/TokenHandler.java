package com.project.handler;

import com.project.util.LogUtil;
import com.project.util.PrintJson;
import com.project.util.TokenUtil;
import com.project.vo.ResultVo;
import org.slf4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TokenHandler implements HandlerInterceptor {
    private Logger logger= LogUtil.getInstance(TokenHandler.class);
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String path=request.getServletPath();
        request.setCharacterEncoding("utf-8");
        ResultVo result=new ResultVo();
        //登录或注册时的请求直接放行
        if(("/user/login".equals(path))||("/user/register".equals(path))||
                ("/user/getCode".equals(path))||("/user/checkName".equals(path))||"/test".equals(path)){
            return true;
        }
        String token=request.getParameter("Authorization");
        if(token!=null){
            boolean flag = TokenUtil.verify(token);
            //如果token验证成功
            if (flag) {
                return true;
            }
        }
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json;charset=utf-8");
        result.setMessage("failed");
        String error="找不到token，用户处于未登录状态";
        result.setError(error);
        logger.info(error);
        PrintJson.parseToJson(response,result);
        return false;




    }


}
