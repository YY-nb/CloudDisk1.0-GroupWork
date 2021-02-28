package com.project.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogUtil {
    private static Logger logger;
    private LogUtil(){}
    public static Logger getInstance(Class c){
        logger= LoggerFactory.getLogger(c);
        return logger;
    }

}
