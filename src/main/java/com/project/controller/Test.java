package com.project.controller;

import com.project.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class Test {
    @RequestMapping(value="/test")
    public ModelAndView doSome(){
        ModelAndView mv=new ModelAndView();
        //框架在最后把数据放入到request域
        mv.addObject("message","哈哈哈");
        //指定视图的完整路径，框架对视图执行forward操作
        mv.setViewName("/show.jsp");
        return mv;
    }
    @RequestMapping(value = "/printJson",produces ={"application/json"} )
    @ResponseBody
    public User printUser(String userName){
        User user=new User();
        user.setUserName("哈哈哈");
        return  user;
    }
}
