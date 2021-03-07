package com.project.util;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.slf4j.Logger;

import java.util.Random;

public class MailUtil {
    private static Logger logger=LogUtil.getInstance(MailUtil.class);

    /**
     * 发送验证码给指定邮箱
     * @param userEmail
     * @param authCode
     */
    public static void sendEmail(String userEmail,String authCode){
        try {
            HtmlEmail email=new HtmlEmail();
            email.setHostName("smtp.qq.com");//邮箱的smtp服务器
            email.setCharset("utf-8");//设置发送的字符类型

            email.addTo(userEmail);//设置收件人
            email.setFrom("1571727223@qq.com","网盘");//设置发送人，邮箱和用户名，发送人的邮箱为自己的，用户名可以随便填
            email.setAuthentication("1571727223@qq.com","");//设置发送人到的邮箱和用户名和授权码(授权码是自己设置的)
            email.setSubject("网盘验证码");//设置发送主题
            email.setMsg(String.format("欢迎注册xx网盘，这是注册验证码%s，请于2分钟内填写",authCode));//设置发送内容
            email.send();//进行发送
        } catch (EmailException e) {
            logger.error(e.getMessage());
        }
    }

    /**
     * 生成一个6位随机数字的验证码
     * @return 验证码
     */
    public static String createCode(){
        StringBuffer stringBuffer=new StringBuffer();
        Random random=new Random();
        for(int i=1;i<=6;i++){
            stringBuffer.append((random.nextInt(7)));
        }
        return stringBuffer.toString();
    }





}
