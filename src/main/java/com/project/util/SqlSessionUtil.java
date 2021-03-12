package com.project.util;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.slf4j.Logger;

import java.io.IOException;
import java.io.InputStream;

public class SqlSessionUtil {
    private static Logger logger=LogUtil.getInstance(SqlSessionUtil.class);
    private SqlSessionUtil(){}

    private static SqlSessionFactory factory;
    private static ThreadLocal<SqlSession> sqlSessionMap = new ThreadLocal<>();
    static{

        String resource = "mybatis.xml";
        InputStream inputStream = null;
        try {
            inputStream = Resources.getResourceAsStream(resource);

        } catch (IOException e) {
            logger.error("发生异常",e);
        }
        factory = new SqlSessionFactoryBuilder().build(inputStream);


    }



    public static SqlSession getSqlSession(){

        SqlSession session = sqlSessionMap.get();

        if(session==null){

            session = factory.openSession();
            sqlSessionMap.set(session);
        }

        return session;

    }

    public static void myClose(SqlSession session){

        if(session!=null){
            session.close();
            sqlSessionMap.remove();
        }

    }
}


