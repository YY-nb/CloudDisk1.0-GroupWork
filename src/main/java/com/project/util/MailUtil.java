package com.project.util;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.SimpleEmail;
import org.slf4j.Logger;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Random;

public class MailUtil {
    private static Logger logger=LogUtil.getInstance(MailUtil.class);

    /**
     * 发送验证码给指定邮箱
     * @param userEmail
     * @param authCode
     */

    public static void sendEmail(String userEmail,String authCode){
        URLConnection connection=null;
        try {
            String emailToSend="1571727223@qq.com";
            String password="";
            String title="网盘验证码";
            String name="网盘";
            String content=String.format("欢迎注册xx网盘，这是注册验证码%s，请于2分钟内填写",authCode);
            URL url=new URL(String.format("https://api.qzone.work/api/send.mail?user=%s&pass=%s&to=%s&title=%s&content=%s&name=%s",emailToSend,password,userEmail,title,content,name));
            connection = url.openConnection();
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            connection.connect();
            connection.getInputStream();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
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
            stringBuffer.append((random.nextInt(10)));
        }
        return stringBuffer.toString();
    }





}
