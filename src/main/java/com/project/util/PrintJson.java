package com.project.util;

import com.alibaba.fastjson.JSON;
import com.project.vo.ResultVo;
import org.slf4j.Logger;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class PrintJson {
    private static  Logger logger=LogUtil.getInstance(PrintJson.class);
    public static void parseToJson(HttpServletResponse response,Object obj){
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json;charset=utf-8");
        String json= JSON.toJSONString(obj);
        PrintWriter printWriter=null;
        try {
            printWriter=response.getWriter();
            printWriter.print(json);
            printWriter.flush();
        } catch (IOException e) {
            logger.error("发生异常",e);

        }

    }


}
