package com.project.util;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

public class SqlSessionUtil {
    private static SqlSessionFactory factory;
    static {
        String conig="mybatis.xml";
        try {
            InputStream inputStream=Resources.getResourceAsStream(conig);
            factory=new SqlSessionFactoryBuilder().build(inputStream);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static SqlSession getSqlSession(){
        SqlSession sqlSession=null;
        if(factory!=null){
            sqlSession=factory.openSession();
        }
        return sqlSession;
    }
    public static void myClose(SqlSession sqlSession){
        if(sqlSession!=null){
            sqlSession.close();
        }
    }
}
